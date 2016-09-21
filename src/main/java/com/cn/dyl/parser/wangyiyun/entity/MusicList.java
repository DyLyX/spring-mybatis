package com.cn.dyl.parser.wangyiyun.entity;

public class MusicList {
	private String author;
	private String name;
	private String playTimes;
	private long id;
	private String introUrl;
	private String imageUrl;
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}

	public String getIntroUrl() {
		return introUrl;
	}

	public void setIntroUrl(String introUrl) {
		this.introUrl = introUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(String playTimes) {
		this.playTimes = playTimes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "MusicList [author=" + author + ", name=" + name + ", playTimes=" + playTimes + ", id=" + id
				+ ", introUrl=" + introUrl + ", imageUrl=" + imageUrl + "]";
	}
	
}
