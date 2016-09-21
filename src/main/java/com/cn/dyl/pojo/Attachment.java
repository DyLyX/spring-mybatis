package com.cn.dyl.pojo;

import java.util.Date;

/**
 * 
 * @author DengYinLei
 * @date 2016年8月14日 下午1:24:50
 */
public class Attachment extends IdEntity {

	private static final long serialVersionUID = 7095182195946057127L;

	private Long mailId;
	private String name;
	private String relativePath;
	private Mail mail;
	private Date createTime;
	public Long getMailId() {
		return mailId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	
}
