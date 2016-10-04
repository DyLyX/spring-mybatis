package com.cn.dyl.parser.xmlParser;

import java.util.Date;

import org.springside.modules.mapper.JaxbMapper;

import com.cn.dyl.util.DownLoadUtil;

public class XmlParser {

	public static void main(String[] args) {
		String url = "http://www.w3school.com.cn/example/xmle/note.xml";
		DownLoadUtil downLoadUtil =new DownLoadUtil();
		String xmlContent = downLoadUtil.getByCallBack(url);
		System.out.println(xmlContent);
//		XmlObject object = JaxbMapper.fromXml(xmlContent, XmlObject.class);
//		System.out.println(object);
		XmlObject root =new XmlObject();
		XmlObject.Pen p=new XmlObject.Pen();
		p.setColor("red");
		p.setName("dool");
		root.setBody("hello");
		root.setFrom("sahgnhai");
		root.setTo("wuhan");
		root.setPen(p);
		root.setHeading("xml");
		root.setPublishDate(new Date());
		root.getAddress().add("janed@example.com");
		root.getAddress().add("jdoe@example.org");
		 xmlContent = JaxbMapper.toXml(root,"ISO-8859-1");
		 System.out.println(xmlContent);
	}
}
