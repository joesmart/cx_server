<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>菜单管理</title>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#menucategory-tab").addClass("active");
		});
	</script>
</head>

<body>
	<h4>列表</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>	
	</c:if>
	
	<table id="table_menucategory" class="table table-striped table-bordered table-condensed">
		<tr><th>菜品名</th><th>操作</th></tr>
		<c:forEach items="${categoryList}" var="categoryItem">
			<tr>
				<td>${categoryItem.name}</td>
				<td>
					<shiro:hasPermission name="menucategory:edit">
						<a href="update/${categoryItem.id}" id="editLink-${categoryItem.id}">修改</a> <a href="delete/${categoryItem.id}">删除</a>
					</shiro:hasPermission>	
				</td>
			</tr>
		</c:forEach>
	</table>

	<shiro:hasPermission name="menucategory:edit">
		<a class="btn" href="create">添加菜品</a>
	</shiro:hasPermission> 
</body>
</html>
