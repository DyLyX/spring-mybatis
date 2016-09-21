package com.cn.dyl.dao;

import java.util.List;
import java.util.Map;
import com.cn.dyl.parser.wangyiyun.entity.Singer;
import repository.mybatis.MyBatisRepository;
@MyBatisRepository
public interface SingerDao {
	void add(Singer singer);
	void update(Singer singer);
	void delete(Long id);
	Singer get(Long id);
	Singer getBySingId(Long singId);
	Singer getByName(String name);
	List<Singer> getAll();
	List<Singer> getSingerByParam(Map<String,Object> param);
	int findCount(Map<String, Object> param);
	

}
