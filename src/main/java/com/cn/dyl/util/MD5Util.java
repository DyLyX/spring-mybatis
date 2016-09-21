package com.cn.dyl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	
	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
    public static String md5(String source) {
        if(StringUtils.isEmpty(source)){
            return source;
        }
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] array = MD5.digest(source.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                int m = (int) array[i];
                if(m < 0){
                    m += 256;
                }
                if(m < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(m));
            }
        } catch (NoSuchAlgorithmException ne) {
            logger.error("MD5 找不到算法异常", ne);
        } catch (UnsupportedEncodingException ue) {
            logger.error("MD5 不支持编码异常", ue);
        }
        return sb.toString();
    }	

    public static String digest(String source) {
	    StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			byte[] array = MD5.digest(source.getBytes("utf-8"));
			
			for (int i = 0; i < array.length; i++) {
				int m = (int) array[i];
				if(m < 0){
					m += 256;
				}
				if(m < 16){
					sb.append("0");
				}
				sb.append(Integer.toHexString(m));
			}
			
		} catch (NoSuchAlgorithmException ne) {
			logger.error("MD5 找不到算法异常", ne);
		} catch (UnsupportedEncodingException ue) {
			logger.error("MD5 不支持编码异常", ue);
		}
	    return sb.toString();
	}
    
    public static String getFileMD5(File file) {
    	String result = "";
    	if(file!=null){
    		FileInputStream in = null;
    		FileChannel ch = null;
    		try{
    			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
				in = new FileInputStream(file);
				ch = in.getChannel();
				MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
				messagedigest.update(byteBuffer);
			    result = bufferToHex(messagedigest.digest());
	    	}catch(Exception e){
	    		logger.error("文件md5失败！",e);
	    	}finally{
	    		if(ch!=null)try {ch.close();} catch (IOException e) {}
	    		if(in!=null)try {in.close();} catch (IOException e) {}
	    	}
    	}
		return result;
	}
    
    public static String getByteMd5(byte[] bytes){
    	String result = "";
    	if(bytes!=null){
			try {
				MessageDigest MD5 = MessageDigest.getInstance("MD5");
				MD5.update(bytes);
				result = bufferToHex(MD5.digest());
			} catch (NoSuchAlgorithmException e) {
				logger.error("byte md5 error！", e);
			}
    	}
    	return result;
    }
    
    private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}
    
    private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
}
