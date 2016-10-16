package com.cn.dyl.parser.xmlParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateWapperAdapter extends XmlAdapter<String, Date> {

	SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//反序列化成对象
	@Override
	public Date unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return sf.parse(v);
	}
	//对象序列化成xml文件
	@Override
	public String marshal(Date v) throws Exception {
		// TODO Auto-generated method stub
		return sf.format(v);
	}

}
