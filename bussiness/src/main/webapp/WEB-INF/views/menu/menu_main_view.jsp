<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ page import="com.server.cx.entity.menu.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜品种类</title>
<script type="text/javascript" src="${ctx}/static/menu/js/util.js"></script>
<script type="text/javascript" src="${ctx}/static/menu/js/menu_main.js"></script>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#menu-tab").addClass("active");
		});
	</script>
</head>
<body>
<div class="main">
 	<div id="operate_menu">
    <!-- <div class="left_menu">
        <button id="all_food">所有菜品</button>
        <button id="all_drinks">所有饮品</button>
    </div> -->
</div>
	
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>	
	</c:if>
	
	<div id="splitter">
		<div class="left_panel">
		    <div class="food_type_button">
		        <span><h1>菜品种类</h1></span>
		        <ul>
		        	<c:if test="${categoryList != null }">
		        		<c:forEach items="${categoryList}" var="categoryItem">
		        			<li><a onclick="changeCategory(this);" id="${categoryItem.id}">${categoryItem.name}</a></li>
		        		</c:forEach>
		        	</c:if>
		        </ul>
		        
		    </div>
		</div>
		
		<div class="ui-corner-all">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
			<thead>	
			<tr>
			    <th>选择</th>
			    <th>图片</th>
			    <th>菜品名称</th>
			    <th>价格</th>
			    <th>操作</th>
			</tr>
			</thead>
			
			<tbody>
			<c:if test="${dishCategory != null }">
        		<c:forEach items="${dishCategory.dishes}" var="dish">
        			<tr class="gradeX">
        			<td>选择</td>
        			<td>${dish.pictureurl}</td>
        			<td>${dish.name}</td>
        			<td class="center">${dish.price}</td>
        			<td class="center">
        				<shiro:hasPermission name="menu:edit">
	        				<a href="update/dish/${dish.id}" class="menu_main_operation">修改</a>
	        				<a href="delete/dish/${dish.id}" class="menu_main_operation">删除</a>
        				</shiro:hasPermission>
        			</td>
        		</c:forEach>
		    </c:if>
			</tbody>
		</table>

	</div>
</div>
</div>

<%-- <shiro:hasPermission name="table:edit">
		<a class="btn" href="create">添加菜品种类</a>
</shiro:hasPermission> --%>
	

<%-- <div id="dialog-form" title="新建菜品分类">
    <p class="validateTips">菜品种类创建</p>
    <form>
        <fieldset>
            <label for="name">名称</label>
            <input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" />
            <label for="description">描述</label>
            <textarea name="description" id="description"
                      class="text ui-widget-content ui-corner-all"></textarea>
        </fieldset>
    </form>
</div> --%>

</body>
</html>