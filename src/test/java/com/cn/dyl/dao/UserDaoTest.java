package com.cn.dyl.dao;


import org.junit.Test;

public class UserDaoTest {

	@Test
	public void testAdd() {
		String fileRoot =this.getClass().getResource("/").getPath();
		System.out.println(fileRoot);
		System.out.println(System.getProperty("user.dir")); 
	}

}
