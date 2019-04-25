<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本模型信息表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cst/model/cstModelModuleInfo/">成本模型信息表列表</a></li>
		<li class="active"><a href="${ctx}/cst/model/cstModelModuleInfo/form?id=${cstModelModuleInfo.id}">成本模型信息表<shiro:hasPermission name="cst:model:cstModelModuleInfo:edit">${not empty cstModelModuleInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:model:cstModelModuleInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstModelModuleInfo" action="${ctx}/cst/model/cstModelModuleInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">成本模型类型：</label>
			<div class="controls">
				<form:select path="moduleType" class="input-xlarge required">
					<form:option value="" label="请选择..."/>
					<form:options items="${fns:getDictList('module_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本模型名称：</label>
			<div class="controls">
				<form:input path="moduleName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本模型描述：</label>
			<div class="controls">
				<form:input path="moduleDesc" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="versionNo" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本时间：</label>
			<div class="controls">
				<input name="versionDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${cstModelModuleInfo.versionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstModelModuleInfo.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
					<div class="controls">
						${fns:getDictLabel(cstModelModuleInfo.status, 'status', '')}
					</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstModelModuleInfo.isNewRecord or cstModelModuleInfo.status eq 'A0'}">
				<shiro:hasPermission name="cst:model:cstModelModuleInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>