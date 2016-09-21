package com.cn.dyl.dao;

import java.util.List;

import com.cn.dyl.pojo.Mail;

import repository.mybatis.MyBatisRepository;

//@MyBatisRepository
public interface MailDao {
	void insert(Mail mail);
	Mail selectById(Long id);
	List<Mail> selectAllMails();

}
