package com.cn.dyl.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/**
 * 
 * @author DengYinLei
 * @date   2016年4月25日 下午4:01:30
 * @version 1.0
 * @since JDK 1.7
 */
public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory;
	
	static{
		InputStream is;
		try {
			is = Resources.getResourceAsStream("spring-mybatis.xml");
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static SqlSession createSession()
	{
		return sqlSessionFactory.openSession();
	}
	
	
	public static  void  closeSession( SqlSession session)
	{
		if(session!=null)
		{
			session.close();
		}
	}
}
