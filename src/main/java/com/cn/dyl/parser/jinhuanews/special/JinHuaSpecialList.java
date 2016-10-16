package com.cn.dyl.parser.jinhuanews.special;

import java.util.List;

public class JinHuaSpecialList {
	private JinHuaSpecilaItem data;
	private boolean state;

	public JinHuaSpecilaItem getData() {
		return data;
	}

	public void setData(JinHuaSpecilaItem data) {
		this.data = data;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public static class JinHuaSpecilaItem{
		
		private int contentid;
		
		private int modelid; //新闻类型   "modelid":3,（图片）       "modelid":1,（普通新闻）
		
		private String title;
		
		private String thumb;//image 列表图片
		
		private String image;//专题详情中的图片
		private String description; //summary
		
		private int comments;
		
		private Long sorttime; //   "sorttime":1476505195
		
		private List<JinHuaSpecialDetail> data;

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

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
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

		public List<JinHuaSpecialDetail> getData() {
			return data;
		}

		public void setData(List<JinHuaSpecialDetail> data) {
			this.data = data;
		}
	}

}
