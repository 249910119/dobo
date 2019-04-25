<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>事业部对应新事业部名称管理</title>
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
		<li><a href="${ctx}/fc/basSybNewsybRel/">事业部对应新事业部名称列表</a></li>
		<li class="active"><a href="${ctx}/fc/basSybNewsybRel/form?id=${basSybNewsybRel.id}">事业部对应新事业部名称<shiro:hasPermission name="fc:basSybNewsybRel:edit">${not empty basSybNewsybRel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fc:basSybNewsybRel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="basSybNewsybRel" action="${ctx}/fc/basSybNewsybRel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">ID：</label>
			<div class="controls">
				<form:input path="relId" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事业部名称：</label>
			<div class="controls">
				<form:input path="sybmc" htmlEscape="false" maxlength="360" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新事业部名称：</label>
			<div class="controls">
				<form:input path="newSybmc" htmlEscape="false" maxlength="360" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财年：</label>
			<div class="controls">
				<form:input path="fiscalYear" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="360" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fc:basSybNewsybRel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>