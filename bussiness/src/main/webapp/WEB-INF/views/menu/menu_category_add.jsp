<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="control-group">
		<form:form id="menu_category_add_form" action="${ctx}/registration/category/add" method="post">
			<label for="category" class="control-label">请输入菜品种类:</label>
			<div class="controls">
				<input type="text" id="menu_category_name" name="menu_category_name" size="50"
					class="required span2" placeholder="菜品类别名称"/>
				<input id="menu_category_add_but" class="btn menu_category_add_but" type="submit" value="添加"/>
			</div>
		</form:form>
		<label for="categorylist" class="control-label" class="menu_category_list_label">菜品种类类表</label>
		
		<table id="contentTable" class="menu_category_add_list table table-striped table-bordered table-condensed">
			<c:forEach items="${categoryList}" var="category">
				<tr>
					<td>${category.name}</td>
				</tr>
			</c:forEach>
			
		</table>
	
	</div>
</body>
</html>