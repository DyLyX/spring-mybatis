package com.cn.dyl.util;

import static com.cn.dyl.commom.HttpClientConstant.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;

public class HtmlTextUtil {
	
	private static Pattern htmlEncodingPattern = Pattern.compile("charset\\s*=\\s*['\"]?([\\w-]*)");

	private static Pattern xmlEncodingPattern = Pattern.compile("encoding\\s*=\\s*['\"]?([\\w-]*)");
	
	//由于StringUtils.deleteWhitespace 删除了所有的空格，当要格式化的内容是英语时会导致单词间的空格也被删除，
	//使所有的单词连在一块
	public static String formatContent(String rawInput) {
		if(rawInput == null) {
			return "";
		}
		return deleteScriptAndStyleTag(
				replaceAllIgnoreCase(rawInput, "<[^>]*? style=\"[^\"]*?display\\s*:\\s*none[^>]*?>[\\s\\S]*?</[^>]*?>", "")).replaceAll("\n", "")
				.replaceAll("(<[bB][rR]\\s*?/?>|</[pP]>|<[Ll][Ii]>|</[Ll][Ii]>|<[Tt][Rr]\\s*?/?>|</[Tt][Rr]>|<[Uu][Ll]\\s*?/?>|</[Uu][Ll]>)", "@BR@")
				.replaceAll("<[\\S\\s]*?>", "").replaceAll("[ ]+", " ")
					.replace("&nbsp;", " ")
					.replaceAll("(@BR@\\s*)+", "\n");
	}
	
	//由于StringUtils.deleteWhitespace 删除了所有的空格，当要格式化的内容是英语时会导致单词间的空格也被删除，
	//使所有的单词连在一块
	public static String deleteHtmlTags(String rawInput) {
		if(rawInput == null) {
			return "";
		}
		
		return replaceAllIgnoreCase(rawInput, "<[^>]*? style=\"[^\"]*?display\\s*:\\s*none[^>]*?>[\\s\\S]*?</[^>]*?>", "")
				.replaceAll("<[\\S\\s]*?>", "").replaceAll("[ ]+", " ").replace("&nbsp;", " ");
	}
	
	/**
	 * 不区分大小写替换
	 * @param source
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAllIgnoreCase(String source, String regex, String replacement){ 
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE); 
		Matcher m = p.matcher(source); 
		return m.replaceAll(replacement); 
	}
	
	/**
	 * 删除font干扰标签
	 * 比如：<font style="font-size:0px;color:#FFF">2 b* I) R3 D) H) ?6 L</font>
	 * @param rawInput
	 * @return
	 */
	public static String deleteFontAndSizeZoreTag(String rawInput){
		return replaceAllIgnoreCase(rawInput, "<[^>]*? style=\"[^\"]*?font-size\\s*:\\s*0px[^>]*?>[\\s\\S]*?</[^>]*?>", "");
	}
	
	/**
	 * 删除script和style标签，以及标签中的内容
	 * @param rawInput
	 * @return
	 */
	public static String deleteScriptAndStyleTag(String rawInput){
		String temp = replaceAllIgnoreCase(rawInput, "<script[\\s\\S]*?</script>", "");
		return replaceAllIgnoreCase(temp, "<style[\\s\\S]*?</style>", "");
	}
	

	/**
	 * 针对网页进行解析, 并页面编码自动识别
	 * 
	 * @param content String
	 * @return String
	 */
	public static String getPageEncoding(String content) {
		content = content.toLowerCase();
		String encoding = "";
		Matcher htmlMatcher = htmlEncodingPattern.matcher(content);

		if (htmlMatcher.find()) {
			encoding = htmlMatcher.group(1);
			if (encoding.startsWith("gb")) {
				return ENCODE_GBK;
			} else if (encoding.equalsIgnoreCase(ENCODE_UTF_8)) {
				return ENCODE_UTF_8;
			} else if (encoding.equalsIgnoreCase(ENCODE_BIG_5)) {
				return ENCODE_BIG_5;
			} else if (encoding.equalsIgnoreCase(ENCODE_EUC_KR)) {
				return ENCODE_EUC_KR;
			} else if (encoding.equalsIgnoreCase(ENCODE_ISO_8859_1)) {
				return ENCODE_ISO_8859_1;
			}
		}

		// 针对 xml 文件进行解析
		Matcher xmlMatcher = xmlEncodingPattern.matcher(content);
		if (xmlMatcher.find()) {
			encoding = xmlMatcher.group(1);
			if (encoding.equalsIgnoreCase(ENCODE_UTF_8)) {
				return ENCODE_UTF_8;
			} else if (encoding.equalsIgnoreCase(ENCODE_GB_2312)) {
				return ENCODE_GB_2312;
			} else if (encoding.equalsIgnoreCase(ENCODE_GBK)) {
				return ENCODE_GBK;
			}
		}
		return "";
	}

	/**
	 * 页面编码自动识别
	 * 
	 * @param content String
	 * @return String
	 */
	public static String getPageEncoding(String content, byte[] date) {
		String encoding = getPageEncoding(content);
		if (encoding == null || encoding.equals("")) {
			content = new String(date);
			encoding = getPageEncoding(content);
		}
		return encoding;
	}
	
	/**
	 * 网页源码的编码探测一般有两种方式，一种是通过分析网页源码中Meta信息，比如contentType，来取得编码，但是某些网页不的contentType中不含任何编码信息，
	 * 这时需要通过第二种方式进行探测，第二种是使用统计学和启发式方法对网页源码进行编码探测。ICU4J就是基于第二种方式的类库。由IBM提供。
	 * 采用 Mozilla 自动识别编码  用于Firefox的自动编码识别
	 * 
	 * @param bytes byte[]
	 * @return String
	 */
	public static String getPageEncoding(byte[] bytes) {
		UniversalDetector detector = new UniversalDetector(null);
	    detector.handleData(bytes, 0, bytes.length);
	    detector.dataEnd();
	    String encoding = detector.getDetectedCharset();
	    detector.reset();
	    
	    if(StringUtils.isNotBlank(encoding)) {
	    	return encoding;
	    }
		return "";
	}
	
}
