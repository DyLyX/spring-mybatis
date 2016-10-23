package com.cn.dyl.util;

import static com.cn.dyl.commom.HttpClientConstant.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springside.modules.mapper.JsonMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;



/**
 *  下载的工具类。
 * @author Denley
 * @date   2016年10月16日 下午10:13:35
 */
@Scope("prototype")
@Component
public class HttpClientDownloadCollect {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientDownloadCollect.class);
	private static final int retryCounts = 5;
	private static int CHARSET_BUF_SIZE = 3000;
	private static final JsonMapper mapper = JsonMapper.nonDefaultMapper();
   /**
     * 该特性决定parser是否允许单引号来包住属性名称和字符串值。
     *
     * 注意：默认下，该属性也是关闭的。需要设置JsonParser.Feature.ALLOW_SINGLE_QUOTES为true
     *
     *
     *	ALLOW_SINGLE_QUOTES(false),
     */
    static {
    	mapper.getMapper().isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    }
    
	private  HttpRequestRetryHandler retryHandler() {
		return new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				System.out.println("重连接次数" + executionCount);
				if (executionCount >= retryCounts) {// 如果连接次数超过5次，就不进行重复连接
					return false;
				}
				if (exception instanceof InterruptedIOException) {//// io操作中断
					return false;
				}
				if (exception instanceof UnknownHostException)// 未找到主机
				{
					return false;
				}
				if (exception instanceof ConnectTimeoutException)// 链接超时
				{
					return false;
				}
				if (exception instanceof SSLException) {
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					return true;
				}
				return false;
			}
		};
		
	}
    
	private CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setRetryHandler(retryHandler())
				.disableContentCompression()
				.build();
		return httpClient;
	}
	/**
	 * 使用 httpGet 下载页面, 不对返回内容进行编码 需要帶請求頭的話在httpGet中設置setHeader();
	 * 
	 * @param httpGet HttpGet
	 * @return String 网页内容(HTML)
	 */
	public  String download(HttpGet httpGet) {
		return download(httpGet, null);
	}

	public String download(String url) {
		return download(url, false);
	}
	
	public  String downloadWithBrowserCookieHead(String url) {
		return download(url, true);
	}
	
	/**
	 * 根据 URL地址抓取网页
	 * @param url String
	 * @return String HTML 网页内容
	 */
	public  String download(String url, boolean withBrowserCookieHead) {
		if (StringUtils.isBlank(url))
			return "";

		HttpGet httpGet;
		try {
			//如果URL带%说明已经转过码
			if (url.indexOf("%") > 0 || url.replace("http://", "").indexOf(":") >= 0) {
				httpGet = new HttpGet(url);
			} else {
				URL urlObject = new URL(url);
				URI uri = new URI(urlObject.getProtocol(), urlObject.getHost(), urlObject.getPath(), urlObject.getQuery(), null);
				httpGet = new HttpGet(uri);
			}
		} catch (Exception iae) {
			logger.warn("无效的 URL地址: " + url);
			return "";
		}
		if(withBrowserCookieHead) httpGet.addHeader("http.protocol.cookie-policy", CookieSpecs.BROWSER_COMPATIBILITY);
		return download(httpGet);
	}

	/**
	 * @param url String
	 * @param param String
	 * @return String
	 */
	public String downloadByPost(String url, Object param) {
		if (StringUtils.isBlank(url))
			return "";
		HttpPost httpPost;
		try {
			//如果URL带%说明已经转过码
			if (url.indexOf("%") > 0 || url.replace("http://", "").indexOf(":") >= 0) {
				httpPost = new HttpPost(url);
			} else {
				URL urlObject = new URL(url);
				URI uri = new URI(urlObject.getProtocol(), urlObject.getHost(), urlObject.getPath(), urlObject.getQuery(), null);
				httpPost = new HttpPost(uri);
			}
		} catch (Exception e) {
			logger.warn("无效的 URL地址: " + url);
			return "";
		}

		return downloadByPost(httpPost, param, null);
	}
	/**
	 * 获取html或者或者xml格式数据
	 * @param url </html> ||</xml> 
	 * @param suffix
	 * @return
	 */
	 public  String getHtmlOrXmlStr(String url, String suffix) {
	        String data = null;
	        for (int i = 0; i < retryCounts; i++) {
	            data = download(url) ;
	            if (data != null && data.trim().endsWith(suffix)) {
	                break;
	            } else {
	                logger.warn("get下载获取数据不完整, 重新获取 url: {}, i: {}", url, i + 1);
	            }
	        }
	        return data;
	    }
	 
	 
	public  <T> T getJsonObject(String url, Class<T> cls) {
		T t = null;
		String json;
		for (int i = 0; i < retryCounts; i++) {
			json = download(url);
			t = mapper.fromJson(json, cls);
			// 重试了5次并且得到空字符串，抛出异常。
			if (json.equals("") && i == retryCounts - 1) {
				throw new DownloadException(url + "下载数据失败！");
			}
			if (t != null) {
				break;
			}
		}
		return t;
	}
	//List<T>
	public <T> List<T> getJsonArray(String url, Class<T> cls) {
		JavaType javaType = mapper.createCollectionType(List.class, cls);
		List<T> t = null;
		String json;
		for (int i = 0; i < retryCounts; i++) {
			json = download(url);
			if ("[]".equals(json)) {
				break;
			}
			if (json.equals("") && i == retryCounts - 1) {
				throw new DownloadException(url + "下载数据失败！");
			}
			try {
				t = mapper.fromJson(json, javaType);
			} catch (Exception e) {
				logger.error("json array parser error ! " + e.getMessage());
			}
			if (t != null && !t.isEmpty()) {
				break;
			}
		}
		return t;
	}
	
	 public <T> T postJsonObject(String url, Object params, Class<T> cls) {
	        T t = null;
	        String json;
	        for (int i = 0; i < retryCounts; i++) {
	            json = downloadByPost(url, params);
	            t = mapper.fromJson(json, cls);
	            if (json.equals("") && i == retryCounts - 1) {
	                throw new DownloadException(url + "下载数据失败！");
	            }
	            if (t != null) {
	                break;
	            }
	        }
	        return t;
	    }
	public  String downloadByPost(HttpPost httpPost, Object param ,String forceEncode) {
		String parameter = "";
		List<NameValuePair> formparams = null;
		if (param != null) {
			if (param instanceof String) {  //文本或者key=value的形式
				parameter = (String) param;
				StringEntity stringEntity = null;
			    try {
					stringEntity = new StringEntity(parameter);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// 如果传参以"<?xml"开头
				if (parameter.trim().startsWith("<?xml")){
					stringEntity.setContentType(CONTENT_TYPE_TEXT_XML_UTF8);
				}
				httpPost.setEntity(stringEntity);
			} else if (param instanceof Map) { //map的形式
				StringBuilder sb = new StringBuilder();
				for (Entry<?, ?> e : ((Map<?, ?>) param).entrySet()) {
					formparams = new ArrayList<NameValuePair>();
					if (e.getKey() != null && e.getValue() != null) {
						formparams.add(new BasicNameValuePair(e.getKey().toString(), e.getValue().toString()));
						parameter = e.getKey().toString() + "=" + e.getValue().toString();
						if (sb.length() > 0){ 
							sb.append("&"); 
						}
						sb.append(parameter);
					}
				}
				parameter = sb.toString();
				if (formparams.isEmpty() && StringUtils.isNotBlank(parameter)) {
					ByteArrayEntity byteArrayEntity = new ByteArrayEntity(parameter.getBytes());
					byteArrayEntity.setChunked(false);
					httpPost.setEntity(byteArrayEntity);
				}else{
					try {
						httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				logger.warn("参数类型: " + param.getClass().getSimpleName());
				parameter = param.toString();
				ByteArrayEntity byteArrayEntity = new ByteArrayEntity(parameter.getBytes());
				byteArrayEntity.setChunked(false);
				httpPost.setEntity(byteArrayEntity);
			}
		}
		return download(httpPost, forceEncode);
	}

	 
	 /**
		 *  下载通用方式, 用 forceEncode 对页面解码
		 * 
		 * @param HttpUriRequest httpUriRequest(httpPost || httpGet)
		 * @param forceEncode String(編碼)
		 * @return String 网页内容(HTML)
		 */
		public String download(HttpUriRequest httpUriRequest, final String forceEncode){
			String htmlString = "";
			long st = System.currentTimeMillis();
			// 构建请求配置 RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播:来自官方文档 超时30秒
			ResponseHandler<Object> handler = new ResponseHandler<Object>() {// 回调
				@Override
				public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					if (response == null || response.getStatusLine().getStatusCode() != 200) {
						return "";
					}
					HttpEntity entity = response.getEntity();
					if (entity == null) {
						logger.info("response entity is empty, URL: {}", entity);
						throw new ClientProtocolException("返回结果为空");
					}
					if (response.getStatusLine().getStatusCode() == 200) {
						String encode = null;
						byte[] bytes = EntityUtils.toByteArray(entity);
						if (StringUtils.isNotBlank(forceEncode)) {
							encode = forceEncode;
						} else {
							if (entity.getContentType() != null) { //先从相应体中获取编码
								String contentType = entity.getContentType().getValue();
								encode = HtmlTextUtil.getPageEncoding(contentType);
							}
							if (StringUtils.isBlank(encode) || encode.equalsIgnoreCase(ENCODE_ISO_8859_1)) {
								String encStr = new String(bytes, 0, bytes.length <= CHARSET_BUF_SIZE ? bytes.length : CHARSET_BUF_SIZE);
								encode = HtmlTextUtil.getPageEncoding(encStr, bytes);
							}
							
							if (StringUtils.isBlank(encode)) {
								encode = HtmlTextUtil.getPageEncoding(bytes);
							}
							//获取不到编码后，默认utf-8
							if (StringUtils.isBlank(encode)) {
								logger.debug("encoding fail at get");
								encode = ENCODE_UTF_8;
							}
						}
						return new String(bytes, encode);
					}
					return null;
				}

			};
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(SO_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT)
					.setConnectionRequestTimeout(CONNECT_TIMEOUT)
					.build();
			if(httpUriRequest instanceof HttpGet) {
				((HttpGet) httpUriRequest).setConfig(requestConfig);
			} else if(httpUriRequest instanceof HttpPost) {
				((HttpPost) httpUriRequest).setConfig(requestConfig);
			} else if(httpUriRequest instanceof HttpHead) {
				((HttpHead) httpUriRequest).setConfig(requestConfig);
			}
			try {
				Object object = getHttpClient().execute(httpUriRequest, handler);
				htmlString = (String) object;
				if(StringUtils.isBlank(htmlString)){
					logger.info("SUCESS get download but empty, URL: {}, {}", httpUriRequest.getURI());
				}
			} catch (Exception e) {
				long end = (System.currentTimeMillis() - st);
				if (e instanceof java.net.SocketTimeoutException) {
					logger.warn("download FAIL Socket 连接超时: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if (e instanceof java.net.SocketException) {
					logger.warn("download FAIL 通讯异常: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if (e instanceof org.apache.http.conn.ConnectTimeoutException) {
					logger.warn("download FAIL 连接超时: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if (e instanceof java.net.UnknownHostException) {
					logger.warn("download FAIL UnknownHost: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if(e instanceof org.apache.http.NoHttpResponseException) {
					logger.warn("download FAIL NoHttpResponse: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if (e instanceof NullPointerException) {
					logger.warn("download FAIL Null Pointer: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else if (e instanceof ClientProtocolException) {
					logger.warn("download connect fail: " + e.getMessage() + ", URL: " + httpUriRequest.getURI() + ", time: " + end);
				} else {
					logger.error("download FAIL other: {}, err: {}, time: {}", httpUriRequest.getURI(), e, end);
				}
				httpUriRequest.abort();
				throw new DownloadException(e);
			}finally {
				httpUriRequest.abort();
			}
			return htmlString;

		}
	 public String getBoardNextPageUrl(String currentUrl) {
			String nextPageUrl = null;
			try {
				URIBuilder uriBuilder = new URIBuilder(currentUrl);

				Map<String, String> params = Maps.newLinkedHashMap();
				StringBuilder sb = new StringBuilder(uriBuilder.getPath());
				for (NameValuePair nameValuePair : uriBuilder.getQueryParams()) {
					params.put(nameValuePair.getName(), nameValuePair.getValue());
				}

				int currentPn = 0;
				String pn = params.get("pageno");
				currentPn = NumberUtils.toInt(pn, currentPn);
				params.put("pageno", String.valueOf(currentPn + 1));

				List<NameValuePair> pairs = Lists.newArrayList();
				for (Entry<String, String> entry : params.entrySet()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				if (pairs.size() > 0) {
					uriBuilder.setParameters(pairs);
				}
				uriBuilder.setPath(sb.toString());
				nextPageUrl = uriBuilder.build().toString();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return nextPageUrl;
		}
	 
}
