package com.cn.dyl.parser.jsonParserTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   writeJsonObject();  
	        // readJsonObject();  
	}

	
	 // 直接写入一个对象(所谓序列化)  
    public static void writeJsonObject() {  
        ObjectMapper mapper = new ObjectMapper();  
        JsonObject person = new JsonObject("nomouse", 25, true, new Date(), "程序员",  2500.0);  
        try {  
            mapper.writeValue(new File("c:/person.json"), person);  
        } catch (JsonGenerationException e) {  
            e.printStackTrace();  
        } catch (JsonMappingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    // 直接将一个json转化为对象（所谓反序列化）  
    public static void readJsonObject() {  
        ObjectMapper mapper = new ObjectMapper();  
  
        try {  
        	JsonObject person = mapper.readValue(new File("c:/person.json"),  
        			JsonObject.class);  
            System.out.println(person.toString());  
        } catch (JsonParseException e) {  
            e.printStackTrace();  
        } catch (JsonMappingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    
    
    // 直接转化为map  
    public static void readJsonMap() {  
        ObjectMapper mapper = new ObjectMapper();  
        try {  
            // 需要注意的是这里的Map实际为一个LikedHashMap，即链式哈希表，可以按照读入顺序遍历  
			@SuppressWarnings("unchecked")
			Map<String, Object> map = mapper.readValue(new File("c:/person.json"), Map.class);  
            System.out.println(map.get("name") + ":" + map.get("age"));  
        } catch (JsonParseException e) {  
            e.printStackTrace();  
        } catch (JsonMappingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
