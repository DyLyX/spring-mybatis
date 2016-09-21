<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<title>500 - 系统内部错误</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link rel="stylesheet" type="text/css" href="${ctx}/assets/plugins/bootstrap/css/bootstrap.min.css" />
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/assets/plugins/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
	<div id="container" style="width:1000px;margin-top:50px;margin:30px auto;">
		<table>
			<tr>
				<td></td>
				<td><div class="alert alert-error" style="margin-left:20px;height:100px;">
				<h2>500 - 系统产生了一个错误。</h2>
				<%=exception.toString()%>
				<%=exception.getMessage()%>
				<p><a href="<c:url value="/"/>">返回首页</a></p>
			</div></td>
			</tr>
		</table>
	</div>
</body>
</html>
