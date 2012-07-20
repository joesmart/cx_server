<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function changeFile(env) {
		var imgNode = document.getElementById("img");
		alert("imgeNode = " + imgNode + "  env = " + env);
		imgNode.setAttribute("src", env.value);
		alert("sdf2");
	}
</script>
<style type="text/css">
	#img {
		width: 100px;
		height: 100px;
	}
</style>

</head>
<body>
	<form name="form" action="fileupload.jsp" method="post"
		enctype="multipart/form-data">
		<img id="img"
			src="http://ttdesk.com/resources/upfile/ttdesk/nature/japan_hokkaido_furano_country_field_1920x1200/1280x1024/japan-hokkaido-landscape-wuxga_country_field_0149.jpg" />
		不加密上传文件： <br> 请选择要上传的文件：<input type="file" name="file"
			onchange="changeFile(this)"> <br> <input type="submit"
			value="确定">
	</form>
</body>
</html>