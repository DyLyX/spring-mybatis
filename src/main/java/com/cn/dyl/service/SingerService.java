package com.cn.dyl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cn.dyl.dao.SingerDao;
import com.cn.dyl.parser.wangyiyun.entity.Singer;
import com.cn.dyl.util.Page;

@Component
public class SingerService {
	private final ConcurrentHashMap<String, Singer> singerNameData = new ConcurrentHashMap<String, Singer>();
	private final ConcurrentHashMap<String, Singer> singerSingIDData = new ConcurrentHashMap<String, Singer>();
	private static final Logger logger = LoggerFactory.getLogger(SingerService.class);
	@Autowired
	private SingerDao singerDao;
	public ConcurrentHashMap<String, Singer> getSingerNameData() {
		return singerNameData;
	}
	public ConcurrentHashMap<String, Singer> getSingerSingIDData() {
		return singerSingIDData;
	}
	@Transactional(readOnly=false)
	public void saveSinger(Singer singer){
		if(singer!=null){
			try {
				singerDao.add(singer);
				singerNameData.put(singer.getName(), singer);
				singerSingIDData.put(String.valueOf(singer.getSingId()), singer);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加数据失败", e);
			}
		}else{
			logger.error("singer 不能为空");
		}

	}
	@Transactional(readOnly=false)
	public void updateSinger(Singer singer){
		singerDao.update(singer);
		singerSingIDData.put(String.valueOf(singer.getSingId()), singer);
		singerNameData.put(singer.getName(), singer);
	}
	@Transactional(readOnly=true)
	public Singer getById(long id){
		return singerDao.get(id);
	}
	@Transactional(readOnly=true)
	public Singer getSingerByName(String name){
		return singerDao.getByName(name);
	}
	@Transactional(readOnly=true)
	public Singer getSingerBySingId(Long singerId){
		return singerDao.getBySingId(singerId);
	}
	@Transactional(readOnly=true)
	public List<Singer> gerAllSingers(){
		return singerDao.getAll();
	}
	@Transactional(readOnly=true)
	public void delete(Long id){
		Singer singer =this.getById(id);
		if(singer!=null){
			singerDao.delete(id);
			singerSingIDData.remove(String.valueOf(singer.getSingId()));
			singerNameData.remove(singer.getName());
		}else{
			logger.error("singer 不能为空！！！");
		}

	}
	
	public Page fingSingerList(Map<String,Object> param){
		int currentPage = Integer.valueOf(param.get("currentPage").toString());
		int limit = Integer.valueOf(param.get("limit").toString());
        int fountCount = singerDao.findCount(param);
        List<Singer> singers = singerDao.getSingerByParam(param);
        Page page = new Page(currentPage, limit);
        page.setData(singers);
        page.setResultCount(fountCount);
        return page;
        
		
	}
}
