package com.cn.dyl.parser.jinhuanews;

public interface Urls {
	//catid= 0 头条
	//http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=content&action=slide&catid=0&time=
	String slideListUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=content&action=slide&catid={0}&time=";
	//http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=content&action=index&catid=0&page=1&time=&keyword=
	//http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=content&action=index&catid=0&page=2&time=&keyword=
	String commonListUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=content&action=index&catid={0}&page={1}&time=&keyword=";
	//http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=article&action=content&contentid=48706
	//一般文章详情
	String detailUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=article&action=content&contentid={0}";
	//专题文章
	// http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=special&action=content&contentid=48573
	String specilaDetailUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=special&action=content&contentid={0}";
	//纯图片http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=picture&action=content&contentid=47990
	String picluteUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=picture&action=content&contentid={0}";
	//图片链接新闻(记得从网页中获取来源)
	// http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=link&action=content&contentid=48695
	String picturelinkUrl = "http://api.jhnews.com.cn/mobile/index.php?app=mobile&controller=link&action=content&contentid={0}";
	/*
	{
	    "state":true,
	    "data":{
	        "contentid":48103,
	        "modelid":3,
	        "topicid":608021,
	        "title":"不能忘怀的国家大典",
	        "description":"今天推出《国家》系列之五：不能忘怀的国家大典。",
	        "published":1475630677,
	        "sorttime":1475675807,
	        "allowcomment":1,
	        "comments":0,
	        "thumb":"",
	        "shareurl":"http://m.jhnews.com.cn/link/48103",
	        "linkto":"http://mp.weixin.qq.com/s?__biz=MzA4OTQ1OTgwNQ==&mid=2680377237&idx=1&sn=2018534ccb50b8458dd587b44dd2b0f7&chksm=8a2d72a7bd5afbb188b787e3eeffdd3222b1633656aca4437f17fd9ab96197b1fd09f83ca1c2&mpshare=1&scene=1&srcid=1005PbVOiZCmbGtSYW2kbX4W#wechat_redirect"
	    }
	}*/
	
	/*{
	    "state":true,
	    "data":{
	        "contentid":48695,
	        "modelid":3,
	        "topicid":611863,
	        "title":"图集：习近平的金边时间",
	        "description":"10月13日，国家主席习近平乘专机抵达金边国际机场，开始对柬埔寨王国进行国事访问。柬埔寨副首相兼王宫事务大臣贡桑奥亲王和夏卡朋亲王、阿伦公主等在舷梯旁热情迎接习近平主席。",
	        "published":1476503517,
	        "sorttime":1476511991,
	        "allowcomment":1,
	        "comments":0,
	        "thumb":"",
	        "shareurl":"http://m.jhnews.com.cn/link/48695",
	        "linkto":"http://www.xinhuanet.com/photo/zhuanti/2016xjpjpz/index.htm#page1"
	    }
	}*/
	/*{
	    "state":true,
	    "data":{
	        "contentid":48129,
	        "modelid":3,
	        "topicid":608219,
	        "title":"今天通车的这条铁路，“很中国”！",
	        "description":"具有中国铁路“纯正基因”的非洲第一条现代电气化铁路——亚吉铁路（埃塞俄比亚首都亚的斯亚贝巴至吉布提首都吉布提），今日正式通车。",
	        "published":1475675802,
	        "sorttime":1475675802,
	        "allowcomment":1,
	        "comments":0,
	        "thumb":"",
	        "shareurl":"http://m.jhnews.com.cn/link/48129",
	        "linkto":" http://mp.weixin.qq.com/s?__biz=MTI0MDU3NDYwMQ==&mid=2656545169&idx=1&sn=366bc1f2bdf6c277d0d96f03a3211dbe&chksm=7a6d54774d1add61df5b3a8c88508db272b022e95b1f3192d53fe5b02f4018cbd4e0a898a854&mpshare=1&scene=1&srcid=1005N0asTbS26t08hLUmlMSJ#wechat_redirect"
	    }
	}*/
	//评论功能未开放 detail详情为完整的html代码
	
}
