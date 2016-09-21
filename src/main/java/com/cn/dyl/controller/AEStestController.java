package com.cn.dyl.controller;

import static com.google.common.collect.Maps.newHashMap;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import com.cn.dyl.pojo.AESbody;

//import net.sf.json.JSONObject;
@Controller
@RequestMapping("/aesTest")
public class AEStestController {
	
	private static final Logger logger=LoggerFactory.getLogger(AEStestController.class);
	private static JsonMapper jsonMapper=JsonMapper.nonDefaultMapper();
	private final String MESSGE = "messge";
	private final String STATE = "state";
	private final String DATA = "data";
	@RequestMapping(value="appApprove",method=RequestMethod.POST)
	@ResponseBody
	public String aesTest(@RequestBody String aesStringBody,
			@RequestParam(value = "sign") String sign,
            @RequestParam(value = "time") long time)
	{
		if(!StringUtils.isNotBlank(aesStringBody)){
//			JSONObject json = new JSONObject();
//        	json.element("error_code", "-1");
//    		json.element("data", "aseString can not be null");
//    		return json.toString();
		}
		Map<String,Object> param=newHashMap();
		AESbody aeSbody=null;
		aeSbody=jsonMapper.fromJson(aesStringBody, AESbody.class);
		param.put(MESSGE, "get success");
		param.put(STATE, 0);
		param.put(DATA, "success");
		return jsonMapper.toJson(param);
	}
	
/*//    private String checkAppInputParam(AESbody aes){
//    	String result = "";
//    	if(aes == null){
//    		result = "post body can not be null or you have forget encrypt the body";
//    		return result;
//    	}
//    	if(StringUtils.isBlank(aes.getMarketmd5()) || StringUtils.isBlank(aes.getMarketmd5())){
//    		result = "market info can not be null";
//    		return result;
//    	}
//    	if(aes.getAppList()==null||aes.getAppList().size()==0){
//    		result = "app list can not be null";
//    		return result;
//    	}
//    	if(aes.getAppList().size()>100){
//    		result = "the size of app list can not large than 100";
//    		return result;
//    	}
//    	//校验调用频率
//    	String lastCallTime = systemParamService.findSystemParamByName(aes.getMarketMd5());
//    	if(lastCallTime == null){
//    		SystemParam param = new SystemParam();
//    		param.setType(1);
//    		param.setDisplayName(aes.getMarket());
//    		param.setName(aes.getMarketMd5());
//    		param.setValue(TimeUtil.formatDateTime(new Date()));
//    		param.setDescription(aes.getMarket()+"市场的最后调用备案接口时间");
//    		systemParamService.add(param);
//    	}else{
//    		try {
//				long callTime = TimeUtil.formatStringDate(lastCallTime).getTime();
//				long currentTime = System.currentTimeMillis();
//				long expiresTime = currentTime - callTime;
//				if(expiresTime<Long.valueOf(time)){
//					result = "the time between two call should large than "+time+" seconds";
//		    		return result;
//				}
//				systemParamService.updateSystemParam(aes.getMarketMd5(), TimeUtil.formatDateTime(new Date(currentTime)));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//    	}
//    	return result;
//    }
//	
*/
}
