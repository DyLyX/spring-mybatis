package com.cn.dyl.parser.jinhuanews.detail;

import java.util.List;

public class JinHuaDetailBody {
	
	private boolean state;
	private JinHuaDetailItem data;
	
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public JinHuaDetailItem getData() {
		return data;
	}

	public void setData(JinHuaDetailItem data) {
		this.data = data;
	}

	public static class JinHuaDetailItem{
		
		private int contentid;
		private int modelid;
		private int topicid;
		private int allowcomment;
		private int comments;
		private Long published;// "published":1476511990,
		private String title;
		private String description;
		private String thumb;
		private String source;
		private String shareurl;
		private String content;//文本新闻对应的内容
		private String linkto; // 图片新闻对应链接地址，通过链接中的网页获取图片信息
		private List<Images> images;
		public static class Images{
			private String image;
			private String thumb;
			private String note;
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			public String getThumb() {
				return thumb;
			}
			public void setThumb(String thumb) {
				this.thumb = thumb;
			}
			public String getNote() {
				return note;
			}
			public void setNote(String note) {
				this.note = note;
			}
			
		}
		public List<Images> getImages() {
			return images;
		}
		public void setImages(List<Images> images) {
			this.images = images;
		}
		public String getLinkto() {
			return linkto;
		}
		public void setLinkto(String linkto) {
			this.linkto = linkto;
		}
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
		public int getTopicid() {
			return topicid;
		}
		public void setTopicid(int topicid) {
			this.topicid = topicid;
		}
		public int getAllowcomment() {
			return allowcomment;
		}
		public void setAllowcomment(int allowcomment) {
			this.allowcomment = allowcomment;
		}
		public int getComments() {
			return comments;
		}
		public void setComments(int comments) {
			this.comments = comments;
		}
		public Long getPublished() {
			return published;
		}
		public void setPublished(Long published) {
			this.published = published;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getThumb() {
			return thumb;
		}
		public void setThumb(String thumb) {
			this.thumb = thumb;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getShareurl() {
			return shareurl;
		}
		public void setShareurl(String shareurl) {
			this.shareurl = shareurl;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	}

}
