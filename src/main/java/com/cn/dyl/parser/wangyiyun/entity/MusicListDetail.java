package com.cn.dyl.parser.wangyiyun.entity;

import java.util.List;

public class MusicListDetail {
	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public List<MusicDetial> getTracks() {
		return tracks;
	}

	public void setTracks(List<MusicDetial> tracks) {
		this.tracks = tracks;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	public int getSubscribedCount() {
		return subscribedCount;
	}

	public void setSubscribedCount(int subscribedCount) {
		this.subscribedCount = subscribedCount;
	}

	public int getPlayCount() {
		return playCount;
	}

	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	//歌单创建者
	private Creator creator;
	//歌曲详情
	private List<MusicDetial> tracks;
	//类型标签
	private List<String> tags;
	//歌单图片
	private String coverImgUrl;
	
	//创建时间
	private long createTime;
	
	//更新时间
	
	private long updateTime;
	
	//表单名称
	
	private String name;
	
	//歌单id
	private long id;
	//歌单描述
	private String description;
	
	//歌单分享数
	
	private int shareCount;
	
	//歌单评论数
	
	private int commentCount;
	
	//歌单包含的歌曲数
	
	private int trackCount;
	//收藏数
	
	private int subscribedCount;
	
	//播放次数
	
	private int playCount;
	public static class Creator{
		private String nickname;
		private String description;
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}

