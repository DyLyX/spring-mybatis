package com.cn.dyl.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cn.dyl.commom.SystemParam;
import com.cn.dyl.dao.SystemParamDao;


/**
 * 系统参数管理业务层
 * @author dyl
 *
 */
@Component
public class SystemParamService {
	private static final Logger logger=LoggerFactory.getLogger(SystemParamService.class);

	@Autowired
	private SystemParamDao systemParamDao;
	
	public String findSystemParamByName(String paramName){
		return systemParamDao.findValueByName(paramName);
	}
	
	public void updateSystemParam(String name, String value){
		SystemParam sp = new SystemParam();
		sp.setName(name);
		sp.setValue(value);
		systemParamDao.updateByName(sp);
	}
	public void add(SystemParam param){
		systemParamDao.add(param);
	}

	public void updateSystemParam(SystemParam systemParam){
		this.systemParamDao.updateByName(systemParam);
	}
	public Map<String,String> commonFileUpload(CommonsMultipartFile[] files,String busiDir) throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		if(files!=null && files.length>0){
            String fileRoot ="fileroot";
            if(StringUtils.isNotBlank(fileRoot)){
                String fileName = "";
                String filePath = "";
                for(CommonsMultipartFile file : files){
                    if(file==null || file.isEmpty()){
                        continue;
                    }
                    String tempName = file.getOriginalFilename();
                    String tempPath = uploadFile(tempName, file.getInputStream(), fileRoot+busiDir);
                    if(StringUtils.isNotBlank(fileName)){
                        fileName += "," + tempName;
                    }else{
                        fileName = tempName;
                    }
                    if(StringUtils.isNotBlank(filePath)){
                        filePath += "," + busiDir +tempPath;
                    }else{
                        filePath = busiDir +tempPath;
                    }
                }
                if(StringUtils.isNotBlank(fileName)){
                	result.put("fileName", fileName.trim());
                }
                if(StringUtils.isNotBlank(filePath)){
                	result.put("attachment", filePath.trim());
                }
            }
        }
		
		return result;
	}
	/**
	 * 上传文件
	 * @param name 文件名（可为空）
	 * @param in 输入流 
	 * @param childPath 子目录名
	 * @author xdp
	 * @date 2013-11-21
	 */
	private  static String uploadFile(String name, InputStream in, String directory) {
		String filename = "";
		FileOutputStream out = null;
		try {
			filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			if (name != null && !name.trim().isEmpty()) {
				filename +=  "_" + name.trim();
			}
			File file = new File(directory);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			byte[] bytes = new byte[10240];
		    out = new FileOutputStream(new File(file, filename));
			int count = 0;
			while ((count = in.read(bytes)) != -1) {
				out.write(bytes, 0, count);
			}
		} catch (IOException ie) {
			logger.error("读取文件异常", ie);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
		return filename;
	}

}
