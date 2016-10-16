package com.cn.dyl.commom;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * httpclient
 * @author Denley
 * @date 2016-10-16
 */
public interface HttpClientConstant {
	
	Random RANDOM = new Random();
	
	Pattern patternForProtocal = Pattern.compile("[\\w]+://");
	
	// time out
	int SOLINGER_TIMEOUT = 0;
	int SO_TIMEOUT = 30000;
	int CONNECT_TIMEOUT = 30000;
	
	int MAX_TOTAL = 20000;
	int MAX_PER_ROUTE = 300;

	String ACCEPT_ENCODEING = "Accept-Encoding";

	String ACCEPT_ENCODEING_GZIP = "gzip,deflate";

	String CONTENT_TYPE_X_FORM = "application/x-www-form-urlencoded";
	
	String CONTENT_TYPE_TEXT_XML_UTF8 = "text/xml; charset=UTF-8";

	String SOCKS_ADDRESS = "socks.address";
	
	String NO_GZIP_FLAG = "no.gzip.flag";

	String ENCODE_ISO_8859_1 = "ISO-8859-1";
	String ENCODE_UTF_8 = "UTF-8";
	String ENCODE_GBK = "GBK";
	String ENCODE_BIG_5 = "BIG5";
	String ENCODE_GB_2312 = "GB2312";
	String ENCODE_EUC_KR = "euc-kr";

	String MAX_STATUS_LINE_GARBAGE = "http.connection.max-status-line-garbage";

	//	String HEAD_USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1";
	//	String HEAD_USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	String[] HEAD_USER_AGENT_MOBIL = {
			// andorid 2.2自带浏览器，不支持HTML5视频
			"Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; HTC_Wildfire_A3333 Build/FRG83D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1" 
	};
	
	String[] HEAD_USER_AGENT = {
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.0 Safari/537.1",
			// 淘宝浏览器2.0 on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
			// 猎豹浏览器2.0.10.3198 急速模式on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
			// 猎豹浏览器2.0.10.3198 兼容模式on Windows 7 x64：
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)",
			// 猎豹浏览器2.0.10.3198 兼容模式on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)",
			// 猎豹浏览器1.5.9.2888 急速模式on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 LBBROWSER",
			// 猎豹浏览器1.5.9.2888 兼容模式 on Windows 7 x64：
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
			// QQ浏览器7.0 on Windows 7 x64 IE9：
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)",
			// QQ浏览器7.0 on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
			// 360安全浏览器5.0自带IE8内核版 on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; 360SE)",
			// 360安全浏览器5.0 on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
			// 360安全浏览器5.0 on Windows 7 x64 IE9：
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
			// 360急速浏览器6.0 急速模式 on Windows XP x86：
			"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
			// 360急速浏览器6.0 急速模式 on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
			// 360急速浏览器6.0 兼容模式 on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
			// 360急速浏览器6.0 兼容模式 on Windows 7 x64 IE9：
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
			// 360急速浏览器6.0 IE9/IE10模式 on Windows 7 x64 IE9：
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
			// 搜狗浏览器4.0 高速模式 on Windows XP x86：
			"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0",
			// 搜狗浏览器4.0 兼容模式 on Windows XP x86 IE6：
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)",
			// Waterfox 16.0 on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0",
			// Ipad：
			"Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
			// Firefox x64 4.0b13pre on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b13pre) Gecko/20110307 Firefox/4.0b13pre",
			// Firefox x64 on Ubuntu 12.04.1 x64：
			"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0",
			// Firefox x86 3.6.15 on Windows 7 x64：
			"[HTTP_USER_AGENT] => Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15",
			// Chrome x64 on Ubuntu 12.04.1 x64：
			"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
			// Chrome x86 23.0.1271.64 on Windows 7 x64：
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
			// Chrome x86 10.0.648.133 on Windows 7 x64：
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
			// IE9 x64 9.0.8112.16421 on Windows 7 x64：
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)",
			// IE9 x86 9.0.8112.16421 on Windows 7 x64：
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
			// Firefox x64 3.6.10 on ubuntu 10.10 x64：
			"Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10"
			};

	int USER_AGENT_LENGTH = HEAD_USER_AGENT.length;
	
	String HEAD_COOKIE = "Cookie";

	String HEAD_SET_COOKIE = "Set-Cookie";

}
