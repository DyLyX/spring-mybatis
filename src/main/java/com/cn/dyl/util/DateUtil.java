package com.cn.dyl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static final String[] REGULAR_EXPS = { "(?is)<!DOCTYPE.*?>", "(?is)<!--.*?-->", "(?is)<script.*?>.*?</script>", "(?is)<style.*?>.*?</style>",
		"(?is)</.*?>", "&.{2,5};|&#.{2,5};" };
	
	private static final Pattern pattern_date = Pattern
			.compile("\\d{4}(-|/|\\\\|年)\\d{1,2}(-|/|\\\\|月)\\d{1,2}(([日]\\s*|\\s+)?\\d{1,2}(:|时)\\d{1,2}((:|分)\\d{1,2}[秒]?)?)?");
	
	/**
	 * 得到开发时间.
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	public static Date toDevelopDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, 0, 0, 0);
		return c.getTime();
	}

	/**
	 * 得到开发时间.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	public static Date toDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, 0, 0, 0);
		return c.getTime();
	}

	/**
	 * 格式化 发表时间.
	 * 
	 * @param date String
	 * @param createTime Date
	 * @return Date
	 * @return
	 */
	public static Date toDate(String date, Date createTime) {
		if (date == null || "".equals(date)) {
			return createTime;
		}

		// 2. 2011-08-31
		// 3. 2011-09-01 06:11
		// 4. 2011-09-01 15:19:09
		// 9. 2011-9-1
		// 15. 2011/9/1
		// 16. 2011/9/1 06:11
		// 17. 2011/9/1 15:19:09
		// 18. 11/9/1

		String result = ExtractUtil.getFirstContent(date, "(\\d{1,4}[-|/|\\.]\\d{1,2}[-|/|\\.]\\d{1,2})", 1);
		if (!"".equals(result)) {
			return changeDateForDate(date, createTime);
		}

		result = ExtractUtil.getFirstContent(date, "(\\d{1,2}[-|/]\\d{1,2} \\d{1,2}:\\d{1,2})", 1);
		if (!"".equals(result)) {
			return changeDateForDateNotYear(date, createTime);
		}

		// 6. 2011年5月1日
		// 7. 2011年05月01日
		result = ExtractUtil.getFirstContent(date, "(\\d{1,4}年\\d{1,2}月\\d{1,2}日)", 1);
		if (!"".equals(result)) {
			return changeDateForChDate(date, createTime);
		}

		// 13. MM月DD日
		if (date.indexOf("月") != -1 && date.indexOf("日") != -1 && date.indexOf("年") == -1) {
			return changeDateForChDateOutYear(date, createTime);
		}

		// 12. 很久以前
		if (date.indexOf("很久以前") != -1) {
			return changeDateForLong(date, createTime);
		}

		// 5. 昨天、前天、今天 06:11
		if (date.indexOf("昨天") != -1 || date.indexOf("前天") != -1 || date.indexOf("今天") != -1) {
			return changeDateForDay(date, createTime);
		}

		// 1. 1天前 、1周前、3小时前、12分钟前、2秒前
		result = ExtractUtil.getFirstContent(date, "([\\s\\S]*?)前", 1);
		if (!"".equals(result)) {
			return changeDateForBefore(date, createTime);
		}

		// 无法解析
		logger.debug("无法解析发帖时间");
		return createTime;
	}

	/**
	 * 1. 1天前 、1周前、3小时前、12分钟前、2秒前
	 * @param dateStr String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForBefore(String date, Date createTime) {
		String count = ExtractUtil.getFirstContent(date, "(\\d+)", 1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createTime);
		if(StringUtils.isEmpty(count)){
			return createTime;
		}

		if (date.indexOf("年") != -1) {
			try {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - Integer.valueOf(count));
			} catch (NumberFormatException ignore) {
			}
		} else if (date.indexOf("月") != -1) {
			try {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - Integer.valueOf(count));
			} catch (NumberFormatException ignore) {
			}
		} else if (date.indexOf("周") != -1) {
			try {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - (Integer.valueOf(count) * 7));
			} catch (NumberFormatException ignore) {
			}
		} else if (date.indexOf("天") != -1) {
			try {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - Integer.valueOf(count));
			} catch (NumberFormatException ignore) {
			}
		} else if (date.indexOf("小时") != -1) {
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - Integer.valueOf(count));
		} else if (date.indexOf("分钟") != -1) {
			try {
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - Integer.valueOf(count));
			} catch (NumberFormatException ignore) {
			}
		} else if (date.indexOf("秒") != -1) {
			try {
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - Integer.valueOf(count));
			} catch (NumberFormatException ignore) {
			}
		}
		return calendar.getTime();
	}

	/**
	 *  5. 今天、 昨天、前天
	 *  
	 * @param dateStr String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForDay(String date, Date createTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createTime);
		if (date.indexOf("昨天") != -1) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		} else if (date.indexOf("前天") != -1) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 2);
		} else if (date.indexOf("今天") != -1) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));

			if (date.length() > 3) {
				String hour = ExtractUtil.getFirstContent(date, "(\\d{1,2}):\\d{1,2}", 1);
				if (!"".equals(hour)) {
					try {
						calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
					} catch (NumberFormatException ignore) {
					}
				}

				String mm = ExtractUtil.getFirstContent(date, "\\d{1,2}:(\\d{1,2})", 1);
				if (!"".equals(mm)) {
					try {
						calendar.set(Calendar.MINUTE, Integer.valueOf(mm));
					} catch (NumberFormatException ignore) {
					}
				}
			}
		}
		return calendar.getTime();
	}

	/**
	 * 2. 2011-08-31
	 * 3. 2011-09-01 06:11
	 * 4. 2011-09-01 15:19:09
	 * 9. 2011-9-1
	 * 15. 2011/9/1
	 * 16. 2011/9/1 06:11
	 * 17. 2011/9/1 15:19:09
	 * 18. 11/9/1
	 * 
	 * @param dateStr String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForDate(String date, Date createTime) {
		try {
			return formatPostTime(date, createTime);
		} catch (Exception e) {
			logger.warn("无法解析发帖时间");
			return createTime;
		}
	}

	/**
	 * 6. 2011年5月1日
	 * 7. 2011年05月01日
	 * 
	 * @param dateStr String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForChDate(String date, Date createTime) {
		try {
			date = date.replaceAll("年", "-");
			date = date.replaceAll("月", "-");
			date = date.replaceAll("日\\s*", " ");
			return formatPostTime(date, createTime);
		} catch (Exception e) {
			logger.warn("无法解析发帖时间");
			return createTime;
		}
	}

	/**
	 * 12. 很久以前
	 * 
	 * @param dateStr String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForLong(String date, Date createTime) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createTime);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 3);
			return calendar.getTime();
		} catch (Exception e) {
			logger.warn("无法解析发帖时间");
			return createTime;
		}
	}
	
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 13. MM月DD日
	 */
	public static Date changeDateForChDateOutYear(String date, Date createTime) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createTime);
			date = calendar.get(Calendar.YEAR) + "-" + date;
			date = date.replaceAll("月", "-");
			date = date.replaceAll("日", "");
			return formatPostTime(date, createTime);
		} catch (RuntimeException e) {
			logger.warn("无法解析发帖时间");
			return createTime;
		}
	}

	/**
	 * 14. 09-22 08:49
	 * @param date String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date changeDateForDateNotYear(String date, Date createTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createTime);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

		String hour = ExtractUtil.getFirstContent(date, "(\\d{1,2}):\\d{1,2}", 1);
		if (!"".equals(hour)) {
			try {
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
			} catch (NumberFormatException ignore) {
			}
		}

		String mm = ExtractUtil.getFirstContent(date, "\\d{1,2}:(\\d{1,2})", 1);
		if (!"".equals(mm)) {
			try {
				calendar.set(Calendar.MINUTE, Integer.valueOf(mm));
			} catch (NumberFormatException ignore) {
			}
		}

		String month = ExtractUtil.getFirstContent(date, "(\\d{1,2})-\\d{1,2}", 1);
		if (!"".equals(month)) {
			try {
				calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
			} catch (NumberFormatException ignore) {
			}
		}

		String day = ExtractUtil.getFirstContent(date, "\\d{1,2}-(\\d{1,2})", 1);
		if (!"".equals(day)) {
			try {
				calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
			} catch (NumberFormatException ignore) {
			}
		}

		return calendar.getTime();
	}

	/**
	 * 时间格式化
	 * @param date String
	 * @param createTime Date
	 * @return Date
	 */
	public static Date formatPostTime(String date, Date createTime) {
		date = date.replaceAll("\\s+", " ");
		date = date.replaceAll("　", " ");
		date = date.replaceAll("/", "-");
		date = date.replaceAll("\\.", "-");
		date = date.trim();

		try {
			int yearIndex = date.indexOf("-");
			if (yearIndex >= 0) {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				try {
					year = Integer.valueOf(date.substring(0, yearIndex));
				} catch (NumberFormatException ignore) {
				}

				if (yearIndex == 4 && (year < 1970 || year > 2037)) {
					logger.debug("发帖时间年份有误: " + date);
					return createTime;
				}

				if (date.indexOf("-", yearIndex + 1) < 0) {
					Calendar now = Calendar.getInstance();
					now.setTime(createTime);
					date = now.get(Calendar.YEAR) + "-" + date;
				}

				if (yearIndex == 2) {
					date = "20" + date;
				}
			}

			int index = date.indexOf(":");
			if (date.indexOf(":") < 0) {
				date += " 00:00:00";
			} else if (date.indexOf(":", index + 1) < 0) {
				date += ":00";
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = simpleDateFormat.parse(date);
			simpleDateFormat = null;

			if (date1.getTime() > createTime.getTime()) {
				logger.warn("发帖时间有误");
				return createTime;
			}
			return date1;
		} catch (Exception e) {
			logger.warn("发帖时间解析异常");
			return createTime;
		}
	}

	public static Date getDateFromDateString(String dateString) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(dateString);
		return date;
	}
	
	public static String getTime(String input) {
		String html = preprocess(input);
		Matcher m = pattern_date.matcher(html);
		String dateTime = null;
		
		if (m.find()) {
			dateTime = formatDateTime(m.group());
		}
		return dateTime;
	}
	
	private static String formatDateTime(String dateTime) {
		String str = dateTime.replaceAll("(/|\\\\|年|月|\\s+)", "-");
		str = str.replaceAll("日", "-");
		str = str.replaceAll("(时|分|:)", "-");
		str = str.replaceAll("秒", "");
		str = str.replaceAll("-+", "-");
		
		String[] s = str.split("-");
		if (s.length < 3) {
			return null;
		}
		
		String rDateTime = s[0] + "-" + s[1] + "-" + s[2] + " ";
		for (int i = 0; i < 3; i++) {
			if (i + 3 >= s.length) {
				rDateTime = rDateTime + "0:";
			} else {
				rDateTime = rDateTime + s[(i + 3)] + ":";
			}
		}
		
		rDateTime = rDateTime.substring(0, rDateTime.length() - 1);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date d = sdf.parse(rDateTime);
			if (d == null) {
				return null;
			}
			return sdf.format(d);
		} catch (ParseException e) {
		}
		return null;
	}
	
	private static String preprocess(String input) {
		for (String exp : REGULAR_EXPS) {
			input = input.replaceAll(exp, "");
		}
		
		input = input.replaceAll("(?is)<.*?>", "\n\n");
		return input;
	}
	
}
