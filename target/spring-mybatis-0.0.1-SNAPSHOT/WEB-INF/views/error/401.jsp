<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, chrome=1">
<title>出错啦 - DyLzZz</title>
<link href="http://www.nduoa.com/cdn/styles/webv3/css/reset.css" rel="stylesheet" />
<link href="http://www.nduoa.com/cdn/styles/webv3/css/global.css" rel="stylesheet" />
<meta name="google-site-verification" content="wVV-NWU5kIPbASZdPKKhsuORjPifaUi4fiupDBdZVkg" />
<link rel="alternate" type="application/rss+xml" title="RSS 2.0" href="/feed/all" />
</head>
<body id="error-page">
  <div class="container">
    <div class="box">
      <h1 align="center">报歉！您访问的页面被管理员禁止访问.</h1>
      <div class="error-wrap">
        <h2>很抱歉！<br>您要访问的页面不存在</h2>
        <p>1. 请检查你输入的网址是否正确。</p>
        <p>2. 如果您不能确认您输入的网址，请访问<a href="<c:url value="/"/>" style="color:#63af00">返回首页</a>。</p>
        <p>3. 如需帮助，请<a href="mailto:service@nduoa.com" style="color:#0f81bc">联系管理员</a>。</p>
        <p>4. 您可以输入内容进行......</p>
      </div>
    </div>
  </div>
 
<script src="http://www.nduoa.com/cdn/styles/webv3/js/jquery.min.js"></script>
<script src="http://www.nduoa.com/cdn/styles/webv3/js/plugins.js"></script>
<script src='http://www.nduoa.com/cdn/js/autocomplete/jquery.autocomplete.js'></script>
<script>
var phpvars = {};
phpvars.offline = 1;
</script>
</body>
</html>
