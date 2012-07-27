<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
		 $(document).ready(function() {
			//聚焦第一个输入框
			$("#name").focus();
			//active tab
			$("#menucategory-tab").addClass("active");
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					name: {
						remote: "${ctx}/menu/menucategory/checkCategory?oldCategoryName=" + encodeURIComponent('${dishCategory.name}')
					},
					/* groupList:"required" */
				},
				messages: {
					name: {
						remote: "菜品种类已经存在"
					}
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
	<form:form id="inputForm" modelAttribute="dishCategory" action="${ctx}/menu/menucategory/save/${dishCategory.id }" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${dishCategory.id}"/>
		<fieldset>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
	
			<div class="control-group">
				<label for="name" class="control-label">菜品种类:</label>
				<div class="controls">
					<input type="text" id="name" name="name" size="50" value="${dishCategory.name}" class="required"/>
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