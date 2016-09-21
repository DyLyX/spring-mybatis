package com.cn.dyl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractUtil {

	public static String getFirstContent(String src, String regex, int index) {
		Pattern pattern = Pattern.compile(regex);
		return getFirstContent(src, pattern, index);
	}

	public static String getFirstContent(String src, Pattern pattern, int index) {
		String result = "";
		Matcher m = pattern.matcher(src);
		if (m.find()) {
			result = m.group(index);
		}
		return result;
	}

	public static List<String> getContents(String src, String regex, int index) {
		Pattern pattern = Pattern.compile(regex);
		return getContents(src, pattern, index);
	}

	public static List<String> getContents(String src, Pattern pattern, int index) {
		List<String> results = new ArrayList<String>();
		Matcher m = pattern.matcher(src);
		while (m.find()) {
			results.add(m.group(index));
		}
		return results;
	}

	public static String formatString(String str) {
		return str.replaceAll("[\\.*+?|(){}^$]", ".");
	}

	public static String escapeHtmlString(String str) {
		// html mark: < > " & ™ ® © ™ 半个空白位 一个空白位 不断行的空白
		String[] htmlMarks = { "&amp;", "&lt;", "&gt;", "&quot;", "&reg;", "&copy;", "&trade;", "&ensp;", "&emsp;", "&nbsp;" };
		String[] escapeMarks = { "&", "<", ">", "\"", "®", "©", "™", " ", " ", " " };
		for (int i = 0; i < htmlMarks.length; i++) {
			str = str.replace(htmlMarks[i], escapeMarks[i]);
		}
		return str.trim();
	}

	public static String getDigitFromUS(String digitString) {
		if (digitString.equals(""))
			return null;
		String[] results = digitString.split(" ");

		if (!results[0].contains(",")) {
			return results[0];
		} else {
			String[] digits = results[0].split(",");
			StringBuilder result = new StringBuilder();
			for (String digit : digits) {
				result.append(digit);
			}
			return result.toString();
		}
	}

	private static final Pattern HREF_PATTERN = Pattern.compile("href=\"([^\"]+)\"");

	public static String getHref(String rawHtml) {
		Matcher m = HREF_PATTERN.matcher(rawHtml);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}

	public static String getUrlRegex4Http() {
		return "http(s)?://[^'\"]*";
	}

	public static String getUrlRegex4NotHttp() {
		return "\\w+(?<!http(s)?)://[^'\"]*";
	}

	public static Integer toInteger(String integerStr, int defaultValue) {
		try {
			return Integer.parseInt(integerStr.replaceAll(",", ""));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static String replaceHtml(String source) {
		source = source.replaceAll("<!--[\\s\\S]*?-->", " ");
		source = source.replaceAll("onclick=(\"|')[\\s\\S]*?\\1", "");
		source = source.replaceAll("onload=(\"|')[\\s\\S]*?\\1", "");
		source = source.replaceAll("‘", "");
		source = source.replaceAll("’", "");
		source = source.replaceAll("\"", "");
		source = source.replaceAll("<[\\s\\S]*?>", "");
		source = source.replaceAll("“", "");
		source = source.replaceAll("”", "");
		source = source.replaceAll("&nbsp;", " ");
		source = source.replaceAll("\\s+", " ");
		source = source.trim();
		return source;
	}

}
