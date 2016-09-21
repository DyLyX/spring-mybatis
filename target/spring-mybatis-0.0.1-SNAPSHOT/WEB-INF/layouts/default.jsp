<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>七友DyLzZz：<sitemesh:title/></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/styles/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/res-tag.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.item_selected{background-color:#D8E8ED;}
</style>
<script type="text/javascript" src="${ctx}/static/jquery/1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
<sitemesh:head/>
</head>
<body class="main_bg">
	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	<div id="mainBody" class="main_content">
	<sitemesh:body />
	</div>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	<div class="return-top">
		<div id="return" class="com-return">
			<a href="javascript:void(0);"></a>
		</div>
	</div>
	<script type="text/javascript">
		function footer(){
			var h = $(window).height();
			var b = $(document.body).height();
			if(h>b){
				$('#mainBody').css(
					"min-height",function(index, value){
						return $(this).height()+h-b;
					}
				);
			}
		}
		window.onload = function(){
			footer();
		}
		window.onresize = function(){
			$('#mainBody').css("min-height",'');
			footer();
		};
	</script>
</body>
</html>