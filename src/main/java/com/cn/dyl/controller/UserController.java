package com.cn.dyl.controller;

import static com.google.common.collect.Maps.newHashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.mapper.JsonMapper;
import com.cn.dyl.pojo.User;
import com.cn.dyl.service.SystemParamService;
import com.cn.dyl.service.UserService;
import com.cn.dyl.util.ESManager;
import com.cn.dyl.util.MD5Util;
import com.cn.dyl.util.Page;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Maps;
/**
 * 
 * @author DengYinLei
 * @date   2016年7月31日 上午12:57:31
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	private static JsonMapper jsonMapper = new JsonMapper();
	private final String MESSGE = "messge";
	private final String STATE = "state";
	private final String DATA = "data";
	private static final String userCheck_DIR = "\\user\\";
	@Value("${es.index.type}")
	private String indexType;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("ESManager")
	private ESManager ESManager;
	@Autowired
	private SystemParamService systemParamService;
	
	private static Map<String,Object> param=Maps.newHashMap();
	// 便利字符匹配类 CharMatcher
    // 判断匹配结果
    boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K');
    // 保留数字文本
    String s1 = CharMatcher.DIGIT.retainFrom("abc 123 efg");
    // 删除数字文本
    String s2 = CharMatcher.DIGIT.removeFrom("abc 123 efg");
    Map<String,Object> param1=newHashMap();
    
    @RequestMapping(value = "list")
	@ResponseBody
	public String findList(HttpServletRequest request){
		Page page = null;
		boolean state = true;
		String messge = "";
		try{
				Map<String, Object> param = buidlerQuery(request);
				page = userService.findUserList(param);
			
		}catch(Exception e){
			logger.error("获取用户信息异常！",e);
			state = false;
			messge +="获取用户信息异常！";
		}
		if(page==null){
			page = new Page(1,10);
		}
		Map<String,Object> resultMap = Maps.newHashMap();
		resultMap.put(DATA, page);
		resultMap.put(MESSGE, messge);
		resultMap.put(STATE, state);
		return jsonMapper.toJson(resultMap);
	}
	@RequestMapping(value="download")
	public String download(@RequestParam String path, @RequestParam(required=false) String name, HttpServletResponse response){
		InputStream input = null;
		OutputStream output = null;
		try {
			//获得当前项目的根路径
			String fileRoot =System.getProperty("user.dir");
			File file = new File(fileRoot, path);
			input = new FileInputStream(file);
			output = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setContentLength((int)file.length());
			if(StringUtils.isNotBlank(name)){
				response.addHeader("Content-Disposition", "attachment;filename=" + name);
			}
			IOUtils.copyLarge(input, output);
		} catch (Exception e) {
			
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		return null;
	}
	/**
	 * 构建 list 方法查询参数
	 * @param request
	 * @return
	 */
	private Map<String,Object> buidlerQuery(HttpServletRequest request){
		Map<String,Object> param = Maps.newHashMap();
		try{
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String beginTime = request.getParameter("beginTime");
			String limit = request.getParameter("limit");
			String currentPage = request.getParameter("currentPage");
			String endTime = request.getParameter("endTime");
			endTime=endTime+" 23:59:59";
			
			if(StringUtils.isNotBlank(name)){
				param.put("name", name.trim());
			}
			if(StringUtils.isNotBlank(sex)){
				param.put("sex", Integer.parseInt(sex.trim()));
			}
			if(StringUtils.isNotBlank(beginTime)){
				param.put("beginTime", beginTime.trim());
			}if(StringUtils.isNotBlank(endTime)){
				param.put("endTime", endTime.trim());
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
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public String add(User user, @RequestParam(required = false) MultipartFile file){
		if(file!=null && !file.isEmpty()){
			String fileRoot =System.getProperty("user.dir");
			String name = UUID.randomUUID().toString().replace("-", "");
			File dest = new File(fileRoot, userCheck_DIR+name);
			dest.getParentFile().mkdirs();
			try {
				file.transferTo(dest);
			} catch (Exception e) {
				logger.error("保存复核附件失败！", e);
			}
			//user.setAttachment(userCheck_DIR+name);
			//user.setFileSize(file.getSize());
			//user.setFileName(file.getOriginalFilename());
		}
		//设置orgId，方便查询同一组织下的下架任务
		//查询对应组织的数据
	
		//已经复核过，是更新操作
//		if(user.getCheckStatus()==1)
//		{   
//			userService.update(user);
//		}else{
//			user.setCheckStatus(1);
//			userService.add(user);
//		}
		return jsonMapper.toJson("success");
	}
	
	/*用户数据存在数据库的同时，也存到es中
	 *@responsebody表示该方法的返回结果直接写入HTTP response body中
	 *一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，
	 *加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。
	 *比如异步获取json数据，加上@responsebody后，会直接返回json数据。
	 */
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	@ResponseBody
	public String addUser(User user){
		Map<String, Object> source=new HashMap<String,Object>();
		source.put("keyId", MD5Util.md5(user.getName()));
		source.put("name", user.getName());
		source.put("nickname", user.getNickname());
		source.put("email", user.getEmail());
		String dataFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		source.put("createTime", dataFormat);
		if(user.getSex()==1)
		source.put("sex", "男");
		else
		source.put("sex", "女");
		//ESManager.insertOrUpdate(IndexType.userInfo, MD5Util.md5(user.getName()), source);
		userService.add(user);
		return jsonMapper.toJson("success");
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public String update(User user){
		System.out.println(user);
		userService.update(user);
		return jsonMapper.toJson("success");
	}
	@RequestMapping(value="getAll",method=RequestMethod.GET)
	@ResponseBody
	public String getUsersByParam(HttpServletRequest request){
		String param=request.getParameter("q_name");
		if(!StringUtils.isNotBlank(param)){
			return "";
		}
		List<User> users=userService.getUsersByString(param);
		StringBuilder sb=new StringBuilder();
		for(User u:users){
			sb.append(u.getName()).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return jsonMapper.toJson(sb.toString());
	}
}
