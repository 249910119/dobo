<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理配比及饱和度表管理</title>
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
		<li><a href="${ctx}/cst/man/cstManManageDegreePara/">项目管理配比及饱和度表列表</a></li>
		<li class="active"><a href="${ctx}/cst/man/cstManManageDegreePara/form?id=${cstManManageDegreePara.id}">项目管理配比及饱和度表<shiro:hasPermission name="cst:man:cstManManageDegreePara:edit">${not empty cstManManageDegreePara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:man:cstManManageDegreePara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstManManageDegreePara" action="${ctx}/cst/man/cstManManageDegreePara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">服务产品编码：</label>
			<div class="controls">
				<form:input path="prodId" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务产品：</label>
			<div class="controls">
				<form:input path="prodName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务级别标识：</label>
			<div class="controls">
				<form:input path="serviceLevel" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务级别：</label>
			<div class="controls">
				<form:input path="serviceLevelName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">PM3级配比：</label>
			<div class="controls">
				<form:input path="pmLevel3Scale" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">PM4级配比：</label>
			<div class="controls">
				<form:input path="pmLevel4Scale" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">PM5级配比：</label>
			<div class="controls">
				<form:input path="pmLevel5Scale" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">PM饱和度：</label>
			<div class="controls">
				<form:input path="pmDegree" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstManManageDegreePara.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstManManageDegreePara.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<c:if test="${!cstManManageDegreePara.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstManManageDegreePara.isNewRecord or cstManManageDegreePara.status eq 'A0'}">、
				<shiro:hasPermission name="cst:man:cstManManageDegreePara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>