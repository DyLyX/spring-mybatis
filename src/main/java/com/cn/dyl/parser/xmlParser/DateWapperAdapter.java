package com.cn.dyl.parser.xmlParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateWapperAdapter extends XmlAdapter<String, Date> {

	SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Date unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return sf.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		// TODO Auto-generated method stub
		return sf.format(v);
	}

}
