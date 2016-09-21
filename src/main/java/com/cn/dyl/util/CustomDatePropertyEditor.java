package com.cn.dyl.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 自定义时间编辑器
 * @author DengYinLei
 * @date   2016年8月6日 下午6:07:28
 */
public class CustomDatePropertyEditor extends PropertyEditorSupport {
	
	private String format;
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat sf=new SimpleDateFormat(format);
		try {
			this.setValue(sf.parse(text));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

}
