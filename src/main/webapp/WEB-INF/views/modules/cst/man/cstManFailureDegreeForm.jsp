<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障饱和度定义管理</title>
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
		<li><a href="${ctx}/cst/man/cstManFailureDegree/">故障饱和度定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/man/cstManFailureDegree/form?id=${cstManFailureDegree.id}">故障饱和度定义<shiro:hasPermission name="cst:man:cstManFailureDegree:edit">${not empty cstManFailureDegree.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:man:cstManFailureDegree:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstManFailureDegree" action="${ctx}/cst/man/cstManFailureDegree/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">服务级别：</label>
			<div class="controls">
				<form:select path="slaName" class="input-xlarge required" onchange="selectSla()" id="slaName">
					<form:option value="" label="请选择..." />
					<form:options items="${slaList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<script type="text/javascript">
					function selectSla(){
						var selectObj = document.getElementById("slaName");
						var name = selectObj.options[selectObj.selectedIndex].value;
						$("#slaId").val(name);
					};
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务级别标识：</label>
			<div class="controls">
				<form:input path="slaId" htmlEscape="false" maxlength="30" class="input-xlarge required" id="slaId" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线饱和度：</label>
			<div class="controls">
				<form:input path="line1Degree" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二线饱和度：</label>
			<div class="controls">
				<form:input path="line2Degree" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">三线饱和度：</label>
			<div class="controls">
				<form:input path="line3Degree" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">CMO饱和度：</label>
			<div class="controls">
				<form:input path="cmoDegree" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源岗饱和度：</label>
			<div class="controls">
				<form:input path="resMgrDegree" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${!cstManFailureDegree.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstManFailureDegree.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<c:if test="${!cstManFailureDegree.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${cstManFailureDegree.isNewRecord or cstManFailureDegree.status eq 'A0'}">
				<shiro:hasPermission name="cst:man:cstManFailureDegree:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>