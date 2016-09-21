package com.cn.dyl.parser.wangyiyun.entity;

public class LyricMusic {

	private Lrc lrc;
	public Lrc getLrc() {
		return lrc;
	}


	public void setLrc(Lrc lrc) {
		this.lrc = lrc;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	private int code;
	
	
	public static class Lrc{
		private int version;
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		public String getLyric() {
			return lyric;
		}
		public void setLyric(String lyric) {
			this.lyric = lyric;
		}
		private String lyric;
	}
}
