package com.cn.dyl.parser.jinhuanews.special;

import java.util.List;

import com.cn.dyl.parser.jinhuanews.list.JinHuaListItem;

public class JinHuaSpecialDetail {
	
	private String catname;//子专题模块名称
	
	private List<JinHuaListItem> data; //各个专题子模块对应的新闻列表

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public List<JinHuaListItem> getData() {
		return data;
	}

	public void setData(List<JinHuaListItem> data) {
		this.data = data;
	}

}
