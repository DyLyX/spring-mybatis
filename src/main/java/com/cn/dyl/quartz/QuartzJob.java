package com.cn.dyl.quartz;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 只要把spring的配置文件加载了就可以看到定时任务运行了
 * 启动你的应用即可，即将工程部署至tomcat或其他容器
 * @author DengYinLei
 * @date   2016年8月1日 下午9:42:07
 */
@Component(value="quartzJob")
public class QuartzJob {
	private static final Logger logger=LoggerFactory.getLogger(QuartzJob.class);
	
	public void work(){
		try {
			logger.info("处理任务开始>........");
			//业务逻辑代码
			System.out.println("时间【"+new Date().toLocaleString()+"】--->大家好！");
			logger.info("处理任务结束>........");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("处理任务出现异常",e);
		}
		
	}
	
}
