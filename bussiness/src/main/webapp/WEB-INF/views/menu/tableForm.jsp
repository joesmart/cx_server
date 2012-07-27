<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>帐号管理</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#number").focus();
			//active tab
			$("#table-tab").addClass("active");
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					number: {
						remote: "${ctx}/menu/table/checktableNum?oldTableNum=" + encodeURIComponent('${table.number}')
					}
				},
				messages: {
					number: {
						remote: "台桌号码已存在"
					},
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if ( element.is(":checkbox") )
						error.appendTo ( element.parent().next() );
					else
						error.insertAfter( element );
				}
			});
		});
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="table" action="${ctx}/menu/table/save/${table.id}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${table.id}"/>
		<fieldset>
			<legend><small>管理台桌</small></legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
	
			<div class="control-group">
				<label for="loginName" class="control-label">台桌号:</label>
				<div class="controls">
					<input type="text" id="number" name="number" size="2" value="${table.number}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">台桌名:</label>
				<div class="controls">
					<input type="text" id="name" name="name" size="50" value="${table.name}"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>

	</form:form>
</body>
</html>
