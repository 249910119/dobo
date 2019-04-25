<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-资源计划分类对应设备大类关系表管理</title>
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
		<li><a href="${ctx}/cst/check/cstCheckResstatSystemRel/">巡检-资源计划分类对应设备大类关系表列表</a></li>
		<li class="active"><a href="${ctx}/cst/check/cstCheckResstatSystemRel/form?id=${cstCheckResstatSystemRel.id}">巡检-资源计划分类对应设备大类关系表<shiro:hasPermission name="cst:check:cstCheckResstatSystemRel:edit">${not empty cstCheckResstatSystemRel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:check:cstCheckResstatSystemRel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstCheckResstatSystemRel" action="${ctx}/cst/check/cstCheckResstatSystemRel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源计划分类：</label>
			<div class="controls">
				<form:input path="resstattypeName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备大类：</label>
			<div class="controls">
				<form:input path="systemName" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源计划分类标识：</label>
			<div class="controls">
				<form:input path="resstattypeId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备大类标识：</label>
			<div class="controls">
				<form:input path="systemId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstCheckResstatSystemRel.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
					<div class="controls">
						${fns:getDictLabel(cstCheckResstatSystemRel.status, 'status', '')}
					</div>
			</div>
		</c:if>
		<c:if test="${!cstCheckResstatSystemRel.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstCheckResstatSystemRel.isNewRecord or cstCheckResstatSystemRel.status eq 'A0'}">
				<shiro:hasPermission name="cst:check:cstCheckResstatSystemRel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>