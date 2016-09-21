package com.dyl.es.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LogModel {

	private Long id;
	private int subId;
	private String systemName;
	private String host;
	private String desc;
	private List<Integer> catids;
	public LogModel(Long id, int subId, String systemName, String host,
			String desc, List<Integer> catids) {
		super();
		this.id = id;
		this.subId = subId;
		this.systemName = systemName;
		this.host = host;
		this.desc = desc;
		this.catids = catids;
	}
	public LogModel()
	{
		Random random=new Random();
		this.id=Math.abs(random.nextLong());
		this.subId=Math.abs(random.nextInt());
		List<Integer> lists=new ArrayList<Integer>();
		for(int i=0;i<5;i++)
		{
			lists.add(Math.abs(random.nextInt()));
		}
		this.catids=lists;
		this.systemName = subId%1 == 0?"oa":"cms";  
        this.host = subId%1 == 0?"10.0.0.1":"10.2.0.1";  
        this.desc = "中文" + UUID.randomUUID().toString();  
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
		this.subId = subId;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Integer> getCatids() {
		return catids;
	}
	public void setCatids(List<Integer> catids) {
		this.catids = catids;
	}
}
