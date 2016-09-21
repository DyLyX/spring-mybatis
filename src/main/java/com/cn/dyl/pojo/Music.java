package com.cn.dyl.pojo;

import java.util.Date;

/**
 * 采集的歌曲
 * 
 * @author DengYinLei
 * @date 2016年8月27日 上午11:15:11
 */
public class Music {
	private String musicListName;// 所属歌单名称
	private long musicListId;//歌单id
	private String size;
	// 歌曲名称
	private String name;
	// 歌曲id
	private int id;
	// 歌手名
	private String artistsName;
	// 专辑名
	private String albumName;
	// 热度
	private int popularity;
	// 得分
	private int score;
	// 播放地址
	private String mp3Url;
	// mv地址
	private int mvid;
	// 播放时长
	private int duration;
	//文件大小
	private int musicSize;
	
	//歌手id
	private int artistId;
	
	//歌词
	private String lyrics;
	
	//发布时间
	private String publishDate;
	//mvurl
	private String mvUrl;
	
	private String introUrl;
	private String musicListUrl;

	public String getMusicListUrl() {
		return musicListUrl;
	}

	public void setMusicListUrl(String musicListUrl) {
		this.musicListUrl = musicListUrl;
	}

	public String getIntroUrl() {
		return introUrl;
	}

	public void setIntroUrl(String introUrl) {
		this.introUrl = introUrl;
	}

	public String getMvUrl() {
		return mvUrl;
	}

	public void setMvUrl(String mvUrl) {
		this.mvUrl = mvUrl;
	}

	public String getMusicListName() {
		return musicListName;
	}

	public void setMusicListName(String musicListName) {
		this.musicListName = musicListName;
	}

	public long getMusicListId() {
		return musicListId;
	}

	public void setMusicListId(long musicListId) {
		this.musicListId = musicListId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

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

	public String getArtistsName() {
		return artistsName;
	}

	public void setArtistsName(String artistsName) {
		this.artistsName = artistsName;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
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

	public int getMusicSize() {
		return musicSize;
	}

	public void setMusicSize(int musicSize) {
		this.musicSize = musicSize;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	@Override
	public String toString() {
		return "Music [musicListName=" + musicListName + ", musicListId=" + musicListId + ", size=" + size + ", name="
				+ name + ", id=" + id + ", artistsName=" + artistsName + ", albumName=" + albumName + ", popularity="
				+ popularity + ", score=" + score + ", mp3Url=" + mp3Url + ", mvid=" + mvid + ", duration=" + duration
				+ ", musicSize=" + musicSize + ", artistId=" + artistId + ", lyrics=" + lyrics + ", publishDate="
				+ publishDate + "]";
	}

	
	

}
