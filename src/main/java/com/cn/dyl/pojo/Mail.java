package com.cn.dyl.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 一封邮件（Mail）可以有0个或多个附件（Attachment），附件（Attachment）仅对应一封邮件。
 * 
 * @author DengYinLei
 * @date 2016年8月14日 下午1:20:19
 */
public class Mail extends IdEntity {
	private static final long serialVersionUID = -1693681573426666009L;
	private String sender;
	private String subject;
	private String content;
	private String formAddress;
	private String sendAddress;
	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	private List<Attachment> attachments = new ArrayList<Attachment>();
	private Integer[] attachmentIds;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormAddress() {
		return formAddress;
	}

	public void setFormAddress(String formAddress) {
		this.formAddress = formAddress;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Integer[] getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(Integer[] attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

}
