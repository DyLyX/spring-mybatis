package com.cn.dyl.dao;

import com.cn.dyl.pojo.Attachment;

import repository.mybatis.MyBatisRepository;

//@MyBatisRepository
public interface AddressDao {
	void insert(Attachment attachment);
	Attachment selectByMailId(Long id);
	
}
