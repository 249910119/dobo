<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
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
		<li><a href="${ctx}/fc/fcProjectInfo/">项目信息列表</a></li>
		<li class="active"><a href="${ctx}/fc/fcProjectInfo/form?id=${fcProjectInfo.id}">项目信息<shiro:hasPermission name="fc:fcProjectInfo:edit">${not empty fcProjectInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fc:fcProjectInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fcProjectInfo" action="${ctx}/fc/fcProjectInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="custName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品线大类：</label>
			<div class="controls">
				<form:input path="fstSvcType" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品线小类：</label>
			<div class="controls">
				<form:input path="sndSvcType" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事业部：</label>
			<div class="controls">
				<form:input path="saleOrg" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务范围代码：</label>
			<div class="controls">
				<form:input path="businessCode" htmlEscape="false" maxlength="15" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售员：</label>
			<div class="controls">
				<form:input path="salesName" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有WBM订单：</label>
			<div class="controls">
				<form:radiobuttons path="hasWbmOrder" items="${fns:getDictList('has_wbm_order')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="false">
		<div class="control-group">
			<label class="control-label">保存标记：</label>
			<div class="controls">
				<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</c:if>
		<c:if test="${not fcProjectInfo.isNewRecord}">
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${fns:getDictLabel(fcProjectInfo.status, 'status', '')}
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${fcProjectInfo.isNewRecord or fcProjectInfo.status eq 'A0'}">
			<shiro:hasPermission name="fc:fcProjectInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>