<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#menu_dish_name").focus();
			//active tab
			$("#menu-tab").addClass("active");
			//为inputForm注册validate函数
			/*  $("#inputForm").validate({
			 	rules: {
			 		name: {
						remote: "${ctx}/menu/category/checkDishName?oldDishName=" + encodeURIComponent('${dish.name}')
					},
				},
				messages: {
					name: {
						remote: "菜品名称已经存在"
					}
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if ( element.is(":checkbox") )
						error.appendTo ( element.parent().next() );
					else
						error.insertAfter( element );
				} 
			});  */
		});
	</script>
	
</head>
<body>
	 <form:form id="inputForm" modelAttribute="dish" action="${ctx}/menu/category/save/dish/${dish.id}" method="post">
		<label  for="menu_dish_name" class="control-label">菜品种类:</label>
		<select name="categoryId" id="categoryId">
			<%-- <c:if test="${categoryList != null }">
        		<c:forEach items="${categoryList}" var="categoryItem">
        			<c:if test="${categoryItem.name} == ${dish.dishCategory.name}">
        			</c:if>
        			<option value="${categoryItem.name}" selected="selected">${categoryItem.name}</option>
        			<option value="${categoryItem.name}">${categoryItem.name}</option>
        		</c:forEach>
		    </c:if> --%>
		    <c:if test="${categoryList != null}">
		    	<c:forEach items="${categoryList}" var="categoryItem">
		    	 	<c:choose>  
       	 				<c:when test="${categoryItem.name == dish.dishCategory.name}">
       	 					<option value="${categoryItem.id}" selected="selected">${categoryItem.name}</option>  
        				</c:when>  
       					<c:otherwise>
       						<option value="${categoryItem.id}">${categoryItem.name}</option>
        				</c:otherwise>
        			</c:choose>
        		</c:forEach>
		    </c:if>
		        	
			<!-- <option value="未评估">未评价</option>
			<option value="差评">差评</option>
			<option value="中评">中评</option>
			<option value="好评">好评</option> -->
    	</select>
    	
		<label  for="menu_dish_name" class="control-label">名称:</label>
		<input  type="text" id="menu_dish_name" name="name" size="50"
				class="required span2" value="${dish.name}"/>
		
		<label for="menu_dish_price" class="control-label">价格:</label>
		<input type="text" id="menu_dish_price" name="price" size="50"
				class="required span2" value="${dish.price}"/>
		<div>
			<label for="menu_dish_description">描述:</label>
			<textarea rows="4" cols="20" name="description">${dish.description}</textarea>
		</div>	
		
		<label for="menu_dish_state" class="label_text_name control-label">状态:</label>
		<label for="menu_dish_state_value" class="label_text_value control-label">${dish_state}</label>
		
		<label for="menu_dish_png" class="label_next_line control-label">图片:</label>
		<img src="${dish.pictureurl}"  alt="${dish.name}" />
		<input type="hidden" name="pictureurl" value="">
		<label for="mene_dish_upload_png" class="control-label menu_main_operation">上传图片</label>
		
		<!-- <input id="menu_dish_modify_but" class="btn menu_category_add_but" type="submit" value="修改完成"/> -->
		<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		
	</form:form> 
</body>
</html>