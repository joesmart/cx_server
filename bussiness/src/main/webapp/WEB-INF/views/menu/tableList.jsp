<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>台桌管理</title>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#table-tab").addClass("active");
		});
	</script>
</head>

<body>
	<h4>列表</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>	
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>台桌号</th><th>名称</th><th>操作</th></tr>
		<c:forEach items="${tables}" var="table">
			<tr>
				<td>${table.number}</td>
				<td>${table.name}</td>
				<td>
					<shiro:hasPermission name="table:edit">
						<a href="update/${table.id}" id="editLink-${table.id}">修改</a> <a href="delete/${table.id}">删除</a>
					</shiro:hasPermission>	
				</td>
			</tr>
		</c:forEach>
	</table>

	<shiro:hasPermission name="table:edit">
		<a class="btn" href="create">创建台桌</a>
	</shiro:hasPermission>
</body>
</html>
