package com.cn.dyl.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AESbody extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -764066625464243500L;
	@JsonProperty("URL")
	private String url;
	private String name;
	private String marketmd5;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarketmd5() {
		return marketmd5;
	}
	public void setMarketmd5(String marketmd5) {
		this.marketmd5 = marketmd5;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	private int type;
	private float score;
}
