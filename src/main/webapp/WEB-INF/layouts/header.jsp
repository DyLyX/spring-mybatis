<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--  
为了防止导航栏与页面主体中的其他内容的顶部相交错，请向 <body> 标签添加至少 50 像素的内边距（padding），内边距的值可以根据您的需要进行设置.
创建一个带有黑色背景白色文本的倒置的导航栏
如果您想要让导航栏固定在页面的顶部，请向 .navbar class 添加 class .navbar-fixed-top。下面的实例演示了这
您可以使用实用工具 class .navbar-left 或 .navbar-right 向左或向右对齐导航栏中的 导航链接、表单、按钮或文本 这些组件。这两个 class 都会在指定的方向上添加 CSS 浮动。
导航栏中的表单不是使用 Bootstrap 表单 章节中所讲到的默认的 class，它是使用 .navbar-form class。这确保了表单适当的垂直对齐和在较窄的视口中折叠的行为。
使用对齐方式选项（这将在组件对齐方式部分进行详细讲解）来决定导航栏中的内容放置在哪里。
-->
     <nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx }">Project name</a>
        </div>
        <div>
          <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="${ctx }"><span class="glyphicon glyphicon-home">Home</span></a></li>
            <li><a href="#shop"><span class="glyphicon glyphicon-shopping-cart">Shop</span></a></li>
            <li><a href="#support"><span class="glyphicon glyphicon-headphones">Support</span></a></li>
          </ul>
     
     		 <form class="navbar-form navbar-right" role="search">
	   				<div class="form-group">
	         				<input type="text" class="form-control" id="q_globalSearch" placeholder="模糊查询11...">
	      					 <button type="button" class="btn btn-default" onclick="">
	      						<span class="glyphicon glyphicon-search" style="color: rgb(255, 140, 60);"></span>
	   						 </button>
	   			  </div>
   			  </form>
      </div>
    </nav>