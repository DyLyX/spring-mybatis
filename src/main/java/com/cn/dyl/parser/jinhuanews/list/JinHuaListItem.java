package com.cn.dyl.parser.jinhuanews.list;

public class JinHuaListItem {

	private int contentid;
	
	private int modelid; //新闻类型   "modelid":3,（图片链接）       "modelid":1,（普通新闻）     "modelid":10,（专题） "modelid":2,（图片）
	
	private String title;
	
	private String thumb;//image
	
	private String description; //summary
	
	private int comments;
	
	private Long sorttime; //   "sorttime":1476505195

	public int getContentid() {
		return contentid;
	}

	public void setContentid(int contentid) {
		this.contentid = contentid;
	}

	public int getModelid() {
		return modelid;
	}

	public void setModelid(int modelid) {
		this.modelid = modelid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public Long getSorttime() {
		return sorttime;
	}

	public void setSorttime(Long sorttime) {
		this.sorttime = sorttime;
	}
	

}
