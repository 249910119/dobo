<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>成本模型参数缓存刷新</title>
</head>
<body>
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/cache/cstParaCache/cstParaCacheFresh" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >刷新参数缓存</button>
	</form:form>
	
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/cache/cstParaCache/calcBaseCasePara" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >故障CASE样本获取故障率</button>
	</form:form>
	
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/cache/cstParaCache/calcCaseLineOnePara" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >故障一线各级别配比</button>
	</form:form>
	
</body>
</html>