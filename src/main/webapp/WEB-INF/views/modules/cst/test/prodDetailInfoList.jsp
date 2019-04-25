<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>成本导入计算</title>
</head>
<body>
	<form id="fileUpload" action="${ctx}/cst/rest/entity/prodDetailInfo/excel" enctype="multipart/form-data" method="post">
		<input id="file" name="file" type="file" />
		<button type="submit" class="btn btn-primary" >成本计算批量上传</button>
	</form>
	<form id="fileUpload" action="${ctx}/cst/rest/entity/prodDetailInfo/excelThread" enctype="multipart/form-data" method="post">
		<input id="fileThread" name="fileThread" type="file" />
		<button type="submit" class="btn btn-primary" >成本计算批量上传-多线程</button>
	</form>
	<form id="fileUpload" action="${ctx}/cst/rest/entity/prodDetailInfo/singleExcel" enctype="multipart/form-data" method="post">
		<input id="singleFile" name="singleFile" type="file" />
		<button type="submit" class="btn btn-primary" >成本计算单个文件上传</button>
	</form>
	
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/rest/entity/prodDetailInfo/execBaseCost" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >基础表成本资源计算</button>
	</form:form>
	
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/rest/entity/prodDetailInfo/execBaseCostThread" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">基础表时间戳：</label>
			<div class="controls">
				<input type="text" id="createDate" name="createDate" readonly="readonly" 
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月dd日'})" />
			</div>
			<label class="control-label">项目号：</label>
			<div class="controls">
				<input type="text" class="input-xlarge required number" id="dcPrjId" name="dcPrjId"/>
			</div>
			<button type="submit" class="btn btn-primary" >基础表成本资源计算-多线程</button>
		</div>
	</form:form>
	
	
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/rest/entity/prodDetailInfo/checkBaseDataPara" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >基础表资源模型参数校验</button>
	</form:form>
	<hr>
	
	<form:form id="inputForm" action="${ctx}/cst/rest/entity/prodDetailInfo/calcBaseData" method="post" class="form-horizontal">
		<button type="submit" class="btn btn-primary" >基础表资源模型参数自动生成</button>
	</form:form>
	
</body>
</html>