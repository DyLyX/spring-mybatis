package com.cn.dyl.dao;

import com.cn.dyl.commom.SystemParam;

import repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SystemParamDao {

	void updateByName(SystemParam param);

	void add(SystemParam param);

	String findValueByName(String name);

}
