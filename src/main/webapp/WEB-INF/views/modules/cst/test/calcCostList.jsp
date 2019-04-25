<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>财务费用计算</title>
</head>
<body>
	<form id="fileUpload" action="${ctx}/cst/rest/entity/calcCost/inPlanExcel" enctype="multipart/form-data" method="post">
		<input id="file" name="file" type="file" />
		<button type="submit" class="btn btn-primary" >上传财务费用计划内</button>
	</form>
	<form id="fileUpload" action="${ctx}/cst/rest/entity/calcCost/outPlanExcel" enctype="multipart/form-data" method="post">
		<input id="file" name="file" type="file" />
		<button type="submit" class="btn btn-primary" >上传财务费用计划外</button>
	</form>
</body>
</html>