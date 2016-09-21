package com.cn.dyl.parser.wangyiyun.entity.response;

import java.util.Map;

public class StatEntry {
	
	private String key;
	private String name;
	private double value;
	private Map<?,?> data;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
    public Map<?,?> getData() {
        return data;
    }
    public void setData(Map<?,?> data) {
        this.data = data;
    }
}
