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

<link rel="stylesheet" href="${ctx}/static/menu/css/jquery.wijmo-open.2.0.8.css" type="text/css"/>
 <link rel="stylesheet" href="${ctx}/static/menu/css/jquery-ui-1.8.20.custom.css" type="text/css" media="all"/> 
 <link rel="stylesheet" href="${ctx}/static/menu/css/app.css" type="text/css"/>
 <link href="${ctx}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet" />
 <link rel="stylesheet" href="${ctx}/static/menu/css/demo_table_jui.css" type="text/css" media="all">
 <link rel="stylesheet" href="${ctx}/static/menu/css/jquery.dataTables_themeroller.css" type="text/css"/>
 <link rel="stylesheet" href="${ctx}/static/menu/css/jquery.dataTables.css" type="text/css"/>
 
 <link href="${ctx}/static/bootstrap/2.0.4/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bussiness.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/menu/css/menu_category_add.css" type="text/css" rel="stylesheet" />
       
<script src="${ctx}/static/bootstrap/2.0.4/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.js" type="text/javascript"></script>
<script src="${ctx}/static/menu/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${ctx}/static/menu/js/app.js" type="text/javascript"></script>

<script src="${ctx}/static/menu/js/jquery.wijmo-open.all.2.0.8.min.js" type="text/javascript"></script> 
<script src="${ctx}/static/menu/js/json2.js" type="text/javascript"></script>
<script src="${ctx}/static/menu/js/httputil.js" type="text/javascript"></script>


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