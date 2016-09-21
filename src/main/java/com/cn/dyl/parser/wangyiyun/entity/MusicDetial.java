package com.cn.dyl.parser.wangyiyun.entity;

import java.util.List;

public class MusicDetial {
	//歌曲名称
	private String name;
	//歌曲id
	private int id;
	//歌手
	private List<Artists> artists;
	//专辑名
	private Album album;
	//热度
	private int popularity;
	//得分
	private int score;
	
	//播放地址
	private String mp3Url;
	//mv地址
	private int mvid;
	//播放时长
	private int duration;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Artists> getArtists() {
		return artists;
	}
	public void setArtists(List<Artists> artists) {
		this.artists = artists;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getMp3Url() {
		return mp3Url;
	}
	public void setMp3Url(String mp3Url) {
		this.mp3Url = mp3Url;
	}
	public int getMvid() {
		return mvid;
	}
	public void setMvid(int mvid) {
		this.mvid = mvid;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public static class Artists{
		
		//歌手名称
		private String name;
		
		private int id;
		private int musicSize;
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getMusicSize() {
			return musicSize;
		}

		public void setMusicSize(int musicSize) {
			this.musicSize = musicSize;
		}

	}
	
	public static class Album{
		private String name;
		private int id;
		private String type;
		private int size;
		private String blurPicUrl;
		private long publishTime;
		private String description;
		private String tags;
		private String company;
		private String briefDesc;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public String getBlurPicUrl() {
			return blurPicUrl;
		}
		public void setBlurPicUrl(String blurPicUrl) {
			this.blurPicUrl = blurPicUrl;
		}
		public long getPublishTime() {
			return publishTime;
		}
		public void setPublishTime(long publishTime) {
			this.publishTime = publishTime;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getBriefDesc() {
			return briefDesc;
		}
		public void setBriefDesc(String briefDesc) {
			this.briefDesc = briefDesc;
		}
	}
}



