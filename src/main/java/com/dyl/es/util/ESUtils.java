package com.dyl.es.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ESUtils {
		private static ObjectMapper objectMapper = new ObjectMapper();  
		public static String objectToJson(Object o)
		{
			try {
				return objectMapper.writeValueAsString(o);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
		@SuppressWarnings("unchecked")
		public static Object jsonToObject(String content,Class type) throws JsonParseException, JsonProcessingException, IOException
		{
			return objectMapper.readValue(content, type);
		}
		
}
