package com.cn.dyl.dao;
import java.util.ArrayList;
import java.util.List;

import com.cn.dyl.parser.wangyiyun.entity.MusicList;
import com.cn.dyl.parser.wangyiyun.musicListParser.MusicListParser;
import com.cn.dyl.util.DownLoadUtil;

public class TestParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://music.163.com/discover/playlist";
		List<MusicList> musicLists=new ArrayList<MusicList>();
		MusicListParser mp=new MusicListParser();
		while(!"".equals(url)){
			String htmlString = new DownLoadUtil().getByCallBack(url);
			List<MusicList> musicLists1=mp.getMusiclist(url,htmlString);
			musicLists.addAll(musicLists1);
			url=mp.getNextPageUrl(url, htmlString);
			System.out.println(url);
		}
		for(MusicList ml:musicLists){
			System.out.println(ml);
		}
	}

}
