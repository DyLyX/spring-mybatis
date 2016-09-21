package com.cn.dyl.pojo;

import java.io.Serializable;
import java.util.Date;

public class User  implements Serializable{
	private static final long serialVersionUID = 4618542573515859366L;
	private int id;
	private String name;
	private String nickname;
	private String email;
	private Date createTime;
	private int sex;
	public int getId() {
		return id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", nickname=" + nickname
				+ ", email=" + email + ", createTime=" + createTime + ", sex="
				+ sex + "]";
	}
}
