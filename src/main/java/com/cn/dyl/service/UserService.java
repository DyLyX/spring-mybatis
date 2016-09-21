package com.cn.dyl.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cn.dyl.dao.UserDao;
import com.cn.dyl.pojo.User;
import com.cn.dyl.util.Page;
@Component
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly=false)
	public void add(User user)
	{
		userDao.add(user);
	}
	@Transactional(readOnly=false)
	public void update(User user)
	{
		userDao.update(user);
	}
	@Transactional(readOnly=true)
	public List<User> getUsersByString(String param){
		return userDao.getUsersByString(param);
	}
	public void delete(int id)
	{
		userDao.delete(id);
	}
	
	public User getById(int id)
	{
		return userDao.get(id);
	}
	public List<User> getAll()
	{
		return userDao.getAll();
	}
	@Transactional(readOnly=true)
	public Page  findUserList(Map<String,Object> param)
	{
		int currentPage = Integer.valueOf(param.get("currentPage").toString());
		int limit = Integer.valueOf(param.get("limit").toString());
		int resultCount = userDao.findCount(param);
		List<User> users= userDao.getUsersByParam(param);
		Page page = new Page(currentPage, limit);
		page.setData(users);
		page.setResultCount(resultCount);
		return page;
		
	}
}
