package com.cn.dyl.util.aes;

import java.util.List;
import java.util.Map;

import org.springside.modules.mapper.JsonMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TestEncrypt {

	private static JsonMapper jsonMapper=new JsonMapper();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = Maps.newHashMap();
		List<Map<String,Object>> lists=Lists.newArrayList();
		Map<String,Object> appLists = Maps.newHashMap();
		appLists.put("name", "梦幻西游");
		appLists.put("package", "网易游戏");
		appLists.put("type", 1);
		appLists.put("version", "1.3");
		lists.add(appLists);
		resultMap.put("market", "3g门户");
		resultMap.put("marketMd5", "d4f5a4dfdcfcdd531a4c2a4d2f44affa");
		resultMap.put("appList", lists);
		String jsonStr=jsonMapper.toJson(resultMap);
		Encrypt encrypt=new Encrypt();
		String encryptJson=encrypt.textEncrypt(jsonStr, "appApproveTransfer2016");
		System.out.println("==========="+jsonStr);
		System.out.println("======加密后===="+encryptJson);
		Decrypt decrypt=new Decrypt();
		String decrypJson=decrypt.textDecrypt(encryptJson, "appApproveTransfer2016");
		System.out.println("=======解密后======"+decrypJson);
		
	}

}
