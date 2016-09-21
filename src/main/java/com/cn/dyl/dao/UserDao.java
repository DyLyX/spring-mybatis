package com.cn.dyl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cn.dyl.pojo.User;
import repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserDao {
	void add(User user);
	void update(User user);
	void delete(int id);
	User get(int id);
	List<User> getAll();
	List<User> getUsersByString(@Param(value="param") String param);
	List<User> getUsersByParam(Map<String,Object> param);
	int findCount(Map<String, Object> param);
}
