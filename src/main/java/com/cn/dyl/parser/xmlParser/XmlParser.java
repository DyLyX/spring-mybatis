package com.cn.dyl.parser.xmlParser;

import java.util.Date;

import org.springside.modules.mapper.JaxbMapper;

import com.cn.dyl.util.DownLoadUtil;

public class XmlParser {
	private static final int retryCount = 5;
	public static  DownLoadUtil downLoadUtil ;
	static {
		 downLoadUtil = new DownLoadUtil();
	}

	public static void main(String[] args) {
		String url = "http://www.w3school.com.cn/example/xmle/note.xml";
		String xmlContent = downLoadUtil.getByCallBack(url);
		System.out.println(xmlContent);
//		XmlObject object = JaxbMapper.fromXml(xmlContent, XmlObject.class);
		XmlObject object = getXmlData(url, XmlObject.class);
		System.out.println(object);
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
	
	/** xml格式转换成java对象
	 * @param url
	 * @param cls
	 * @return
	 */
	   private static <T> T getXmlData(String url, Class<T> cls) {
	        T t = null;
	        String xml;
	        for (int i = 0; i < retryCount; i++) {
	            xml = downLoadUtil.getByCallBack(url);
	            if ("<doc />".equals(xml) || xml == null ||"".equals(xml)) {
	                break;
	            }
	            try {
	                t = JaxbMapper.fromXml(xml, cls);
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
	            if (t != null) {
	                break;
	            }
	        }
	        return t;
	    }

}
