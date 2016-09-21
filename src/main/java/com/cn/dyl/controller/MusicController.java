package com.cn.dyl.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import com.cn.dyl.parser.wangyiyun.musicListParser.MusicListParser;
import com.cn.dyl.pojo.Music;
import com.cn.dyl.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 前端通过搜索访问网易云接口
 * 
 * @author DengYinLei
 * @date 2016年9月7日 下午10:21:40
 */

@Controller
@RequestMapping("/music")
public class MusicController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static JsonMapper jsonMapper = new JsonMapper();
	private final String MESSGE = "messge";
	private final String STATE = "state";
	private final String DATA = "data";
	@Autowired
	private MusicListParser musicListParser;

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseBody
	public String searchByKeyWords(@RequestParam("keywords") String keywords) {
		Page page = null;
		boolean state = true;
		String messge = "";
		List<Music> musics = Lists.newArrayList();
		if (StringUtils.isNotBlank(keywords)) {
			try {
				musics=musicListParser.getMusicBySearch(keywords, 1, 0, 50);
			} catch (Exception e) {
				logger.error("查询歌曲异常！！！", e);
				state = false;
				messge +="获取歌曲信息异常！";
			}
		}
		if(page==null){
			page = new Page(1,10);
		}
		Map<String,Object> resultMap = Maps.newHashMap();
		resultMap.put(DATA, page);
		resultMap.put(MESSGE, messge);
		resultMap.put(STATE, state);
		return jsonMapper.toJson(musics);
	}
	
	/**
	 * 构建 list 方法查询参数
	 * @param request
	 * @return
	 */
	private Map<String,Object> buidlerQuery(HttpServletRequest request){
		Map<String,Object> param = Maps.newHashMap();
		try{
			String name = request.getParameter("keywords");
			String limit = request.getParameter("limit");
			String currentPage = request.getParameter("currentPage");
			
			if(StringUtils.isNotBlank(name)){
				param.put("name", name.trim());
			}
			int size = 10;
			int currenPage = 1;
			if(StringUtils.isNotBlank(limit) || StringUtils.isNotBlank(currentPage)){
				int tempPage = Integer.valueOf(currentPage.trim());
				int tempLimit = Integer.valueOf(limit.trim());
				if(tempPage > 0){
					currenPage = tempPage;
				}
				if(tempLimit > 0){
					size = tempLimit;
				}
			}
			Page page = new Page(currenPage, size);
			param.put("limit", page.getLimit());
			param.put("start", page.getStart());
			param.put("currentPage", currenPage);
			
		}catch(Exception e){
			logger.error("构建 list 方法查询参数异常！",e);
		}
		return param;
	}

}
