package com.cn.dyl.dao;

import java.util.List;

import com.cn.dyl.parser.wangyiyun.musicListParser.MusicListParser;
import com.cn.dyl.pojo.Music;

public class BlogPost {

	public static void main(String[] args) {
		MusicListParser mp=new MusicListParser();
		List<Music> musics=mp.getMusicBySearch("庄心妍", 1, 0, 20);
		System.out.println(musics.size());
	}
}
