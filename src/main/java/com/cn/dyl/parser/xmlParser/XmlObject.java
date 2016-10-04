package com.cn.dyl.parser.xmlParser;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.elasticsearch.common.collect.Lists;
 //http://www.w3school.com.cn/example/xmle/note.xml
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"to","from","body","pen","address"},name="aa")
public class XmlObject { //属性或者get/set方法上不使用注解  ;类中的各个属性默认映射的是根节点的子节点而不是根节点属性，要想设置为属性必须使用@XmlAttribute
	private String to;
	private String from;
	private String heading;
	private Date publishDate;
	private Pen pen;
	private List<String> address;
	private String body;
	public XmlObject(){
		address = Lists.newArrayList();
	}
	@XmlElementWrapper
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	public Pen getPen() {
		return pen;
	}
	public void setPen(Pen pen) {
		this.pen = pen;
	}
	@XmlAttribute(name ="publishDate")
	@XmlJavaTypeAdapter(value = DateWapperAdapter.class)
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@XmlAttribute(name = "heading")
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@XmlAccessorType(XmlAccessType.PROPERTY)
	public static  class Pen{
		private String color;
		private String name;
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "Pen [color=" + color + ", name=" + name + "]";
		}
		
	}
	@Override
	public String toString() {
		return "XmlObject [to=" + to + ", from=" + from + ", heading=" + heading + ", publishDate=" + publishDate
				+ ", pen=" + pen + ", body=" + body + ", getPen()=" + getPen() +  "]";
	}
	

}
/**
 * 
 * <?xml version="1.0" encoding="ISO-8859-1" standalone="yes"?>
	<note heading="xml" publishDate="2016-10-04 13:45:41">
	    <to>wuhan</to>
	    <from>sahgnhai</from>
	    <body>hello</body>
	    <pen>
	        <color>red</color>
	        <name>dool</name>
	    </pen>
	    <address>
	        <address>janed@example.com</address>
	        <address>jdoe@example.org</address>
	    </address>
	</note>
 * */
