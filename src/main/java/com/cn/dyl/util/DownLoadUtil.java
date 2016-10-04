package com.cn.dyl.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 以get或者post方式获取指定url的数据
 * 
 * @author DengYinLei
 * @date 2016年8月3日 下午10:02:17
 */
@Scope(value = "prototype")
@Component
public class DownLoadUtil {

	private static final Logger logger = LoggerFactory.getLogger(DownLoadUtil.class);
	private static final CloseableHttpClient client = HttpClients.createDefault();
	private static final int retryCounts = 5;

	/**
	 * 以回调方式处理返回结果 处理响应的最简单和最方便的方法是通过使用ResponseHandler的 接口。用户不必担心连接管理的问题。当使用一个
	 * ResponseHandler的时候，无论是否请求执行成功或导致异常，HttpClient将会自动释放连接。
	 */
	public String getByCallBack(String url) {
		String htmlString = "";
		/**
		 * 异常恢复机制：
		 * 
		 * HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
		 * HttpRequestRetryHandler接口须要用户自己实现；
		 * 
		 */
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			/**
			 * exception异常信息； executionCount：重连次数； context：上下文
			 */
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

		CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

		// 构建请求配置 RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播:来自官方文档
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000 * 10).setConnectTimeout(1000 * 10).build();
		ResponseHandler<Object> handler = new ResponseHandler<Object>() {// 回调
			@Override
			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					throw new ClientProtocolException("返回结果为空");
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获取返回结果的字符集：如utf-8 GBK,并以这种字符集读取数据
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					/*
					 * InputStreamReader reader=new
					 * InputStreamReader(entity.getContent(), charset);
					 * BufferedReader br=new BufferedReader(reader);
					 * StringBuilder sb=new StringBuilder(); char[] buffer=new
					 * char[1024]; while(br.read(buffer)!=-1){ sb.append(new
					 * String(buffer)); } return sb.toString();
					 */
					return EntityUtils.toString(entity, charset);
				}
				return null;
			}

		};
		HttpGet get = new HttpGet(url);
		get.setConfig(config);
		try {
			Object object = client.execute(get, handler);
			htmlString = (String) object;
		} catch (ClientProtocolException e) {
			logger.error("连接失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;

	}
	public String getByCallBackGet(HttpGet get) {
		String htmlString = "";
		/**
		 * 异常恢复机制：
		 * 
		 * HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
		 * HttpRequestRetryHandler接口须要用户自己实现；
		 * 
		 */
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			/**
			 * exception异常信息； executionCount：重连次数； context：上下文
			 */
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

		CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

		// 构建请求配置 RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播:来自官方文档
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000 * 10).setConnectTimeout(1000 * 10).build();
		ResponseHandler<Object> handler = new ResponseHandler<Object>() {// 回调
			@Override
			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					throw new ClientProtocolException("返回结果为空");
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获取返回结果的字符集：如utf-8 GBK,并以这种字符集读取数据
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					/*
					 * InputStreamReader reader=new
					 * InputStreamReader(entity.getContent(), charset);
					 * BufferedReader br=new BufferedReader(reader);
					 * StringBuilder sb=new StringBuilder(); char[] buffer=new
					 * char[1024]; while(br.read(buffer)!=-1){ sb.append(new
					 * String(buffer)); } return sb.toString();
					 */
					return EntityUtils.toString(entity, charset);
				}
				return null;
			}

		};
		get.setConfig(config);
		try {
			Object object = client.execute(get, handler);
			htmlString = (String) object;
		} catch (ClientProtocolException e) {
			logger.error("连接失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;

	}
	
	
	public String getByCallBackPost(HttpPost post) {
		String htmlString = "";
		/**
		 * 异常恢复机制：
		 * 
		 * HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
		 * HttpRequestRetryHandler接口须要用户自己实现；
		 * 
		 */
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			/**
			 * exception异常信息； executionCount：重连次数； context：上下文
			 */
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

		CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

		// 构建请求配置 RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播:来自官方文档
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000 * 10).setConnectTimeout(1000 * 10).build();
		ResponseHandler<Object> handler = new ResponseHandler<Object>() {// 回调
			@Override
			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					throw new ClientProtocolException("返回结果为空");
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获取返回结果的字符集：如utf-8 GBK,并以这种字符集读取数据
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					/*
					 * InputStreamReader reader=new
					 * InputStreamReader(entity.getContent(), charset);
					 * BufferedReader br=new BufferedReader(reader);
					 * StringBuilder sb=new StringBuilder(); char[] buffer=new
					 * char[1024]; while(br.read(buffer)!=-1){ sb.append(new
					 * String(buffer)); } return sb.toString();
					 */
					return EntityUtils.toString(entity, charset);
				}
				return null;
			}

		};
		post.setConfig(config);
		try {
			Object object = client.execute(post, handler);
			htmlString = (String) object;
		} catch (ClientProtocolException e) {
			logger.error("连接失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;

	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("User-Agent", "SAMSUNG-Android");
			conn.setRequestProperty("Content-Type", "text/xml");
			conn.setRequestProperty("Cookie", "JSESSIONID=FDFK1kLYOEUiTg0rvZdI9llR.c01w02odc04; WMONID=sLXx0_TV5rH");
			conn.setRequestProperty("Cookie2", "$Version=1");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 缺点：循环多次请求时会出现Connection pool shut
	 * down的问题（适合一次请求的情况）。多次请求请使用getByCallBack（）方法 通过get方式获取string
	 */
	public String downloadByGet(String url, String charset) {
		String htmlString = "";
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http");
		// 创建httpGet
		HttpGet httpGet = new HttpGet(url);
		System.out.println("executing request" + httpGet.getURI());
		// 执行httpGet
		try {
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println(response.getStatusLine());
			// 获得相应实体
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// 获得响应实体
				htmlString = EntityUtils.toString(entity, charset);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return htmlString;

	}

	/**
	 * 通过get方式获取string
	 */
	public static String downloadGet(String url) {
		String htmlString = "";
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http");
		// 创建httpGet
		HttpGet httpGet = new HttpGet(url);
		System.out.println("executing request" + httpGet.getURI());
		// 执行httpGet
		try {
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println(response.getStatusLine());
			// 获得相应实体
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// 获取返回结果的字符集：如utf-8 GBK,并以这种字符集读取数据
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charset = contentType.getCharset();
				// 获得响应实体
				htmlString = EntityUtils.toString(entity, charset);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(client!=null){
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return htmlString;

	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	public String downloadByPost(String url, Map<String, Object> params, String charset) {
		String htmlString = "";
		// 创建httppsot
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = Lists.newArrayList();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
			}
		}
		try {
			// 构建url加密实体，并以utf-8方式进行加密；
			UrlEncodedFormEntity uFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			httpPost.setEntity(uFormEntity);
			CloseableHttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// org.apache.http.util.EntityUtils类可以快速处理服务器返回实体对象
					htmlString = EntityUtils.toString(entity, charset);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlString;

	}

	/**
	 * 以回调方式处理返回结果 处理响应的最简单和最方便的方法是通过使用ResponseHandler的 接口。用户不必担心连接管理的问题。当使用一个
	 * ResponseHandler的时候，无论是否请求执行成功或导致异常，HttpClient将会自动释放连接。
	 */
	public static String getByCallBack() {
		String htmlString = "";
		/**
		 * 异常恢复机制：
		 * 
		 * HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
		 * HttpRequestRetryHandler接口须要用户自己实现；
		 * 
		 */
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			/**
			 * exception异常信息； executionCount：重连次数； context：上下文
			 */
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

		CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

		// 构建请求配置 RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播:来自官方文档
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000 * 10).setConnectTimeout(1000 * 10).build();
		ResponseHandler<Object> handler = new ResponseHandler<Object>() {// 回调
			@Override
			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					throw new ClientProtocolException("返回结果为空");
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获取返回结果的字符集：如utf-8 GBK,并以这种字符集读取数据
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
					BufferedReader br = new BufferedReader(reader);
					StringBuilder sb = new StringBuilder();
					char[] buffer = new char[1024];
					while (br.read(buffer) != -1) {
						sb.append(new String(buffer));
					}
					return sb.toString();
				}
				EntityUtils.consume(entity); 
				return null;
			}

		};
		/**
		 * 测试1： 构建复杂uri，这种方式会很方便的设置多个参数；
		 * 
		 * HttpClients类是client的具体一个实现类；
		 * 
		 * URIBuilder包含：协议，主机名，端口（可选），资源路径，和多个参数（可选）
		 * 
		 */
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("webservice.webxml.com.cn")
					.setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo").setParameter("", "").setParameter("", "")
					.setParameter("", "").build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpPost post = new HttpPost(uri);
		post.setConfig(config);
		try {
			Object object = client.execute(post, handler);
			htmlString = (String) object;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
