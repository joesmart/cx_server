<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Mini-Web示例:<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />


 <link href="${ctx}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet" />
 <link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/mini-web.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.js" type="text/javascript"></script>




<!--Theme-->
<!--    <link href="http://cdn.wijmo.com/themes/aristo/jquery-wijmo.css" rel="stylesheet" type="text/css"
       title="redmond-jqueryui"/>  -->
 <!--Wijmo Widgets CSS-->
 
 
 

<sitemesh:head/>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div id="content" class="span12">
			<sitemesh:body/>
		</div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	
</body>
</html>