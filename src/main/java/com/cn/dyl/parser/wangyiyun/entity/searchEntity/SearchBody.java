package com.cn.dyl.parser.wangyiyun.entity.searchEntity;

import java.util.List;

public class SearchBody {
	private Result result;
	private int code;
	
	
	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public static class Result{
		
		public int getSongCount() {
			return songCount;
		}
		public void setSongCount(int songCount) {
			this.songCount = songCount;
		}
		public List<String> getQueryCorrected() {
			return queryCorrected;
		}
		public void setQueryCorrected(List<String> queryCorrected) {
			this.queryCorrected = queryCorrected;
		}
		public List<Song> getSongs() {
			return songs;
		}
		public void setSongs(List<Song> songs) {
			this.songs = songs;
		}
		private int songCount;
		private List<String> queryCorrected;
		private List<Song> songs;
	}
}
