<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障人工费率定义管理</title>
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
		<li><a href="${ctx}/cst/man/cstManFailureManRate/">故障人工费率定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/man/cstManFailureManRate/form?id=${cstManFailureManRate.id}">故障人工费率定义<shiro:hasPermission name="cst:man:cstManFailureManRate:edit">${not empty cstManFailureManRate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:man:cstManFailureManRate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstManFailureManRate" action="${ctx}/cst/man/cstManFailureManRate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">生产角色：</label>
			<div class="controls">
				<form:select path="deliveryRole" class="input-xlarge required">
					<form:option value="" label="请选择..."/>
					<form:options items="${fns:getDictList('delivery_role')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否驻场：</label>
			<div class="controls">
				<form:select path="isResident" class="input-xlarge required">
					<form:option value="" label="请选择..."/>
					<form:options items="${fns:getDictList('is_resident')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工年包：</label>
			<div class="controls">
				<form:input path="manCostYear" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用年包：</label>
			<div class="controls">
				<form:input path="feeCostYear" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">激励年包：</label>
			<div class="controls">
				<form:input path="urgeCostYear" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstManFailureManRate.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstManFailureManRate.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<c:if test="${!cstManFailureManRate.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstManFailureManRate.isNewRecord or cstManFailureManRate.status eq 'A0'}">
				<shiro:hasPermission name="cst:man:cstManFailureManRate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>