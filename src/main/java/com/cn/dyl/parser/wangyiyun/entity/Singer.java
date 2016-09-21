package com.cn.dyl.parser.wangyiyun.entity;

import java.io.Serializable;

public class Singer implements Serializable {

	private static final long serialVersionUID = -769760681388186857L;
	private String imageUrl;
	private int id;
	private int type ;
	private Long singId;
	private String name;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getSingId() {
		return singId;
	}
	public void setSingId(Long singId) {
		this.singId = singId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
