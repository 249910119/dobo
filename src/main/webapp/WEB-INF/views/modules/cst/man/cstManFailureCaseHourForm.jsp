<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障CASE处理工时定义管理</title>
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
		<li><a href="${ctx}/cst/man/cstManFailureCaseHour/">故障CASE处理工时定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/man/cstManFailureCaseHour/form?id=${cstManFailureCaseHour.id}">故障CASE处理工时定义<shiro:hasPermission name="cst:man:cstManFailureCaseHour:edit">${not empty cstManFailureCaseHour.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:man:cstManFailureCaseHour:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstManFailureCaseHour" action="${ctx}/cst/man/cstManFailureCaseHour/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源标识：</label>
			<div class="controls">
				<input type="text" id="cstResourceBaseInfoName" name="resourceId" value="${cstManFailureCaseHour.resourceId}" maxlength="64" class="input-medium required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<a id="cstResourceBaseInfoSelect" href="javascript:" class="btn">选择</a>
				<a id="cstResourceBaseInfoClear" href="javascript:" class="btn">清除</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstResourceBaseInfoId").val(id);
						$("#cstResourceBaseInfoName").val(id);
						$("#resourceDescId").val(title);
					}
					$("#cstResourceBaseInfoSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/base/cstResourceBaseInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
					$("#cstResourceBaseInfoClear").click(function(){
						$("#cstResourceBaseInfoId").val('');
						$("#cstResourceBaseInfoName").val('');
					});
				</script>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">资源描述：</label>
			<div class="controls">
				<form:textarea id="resourceDescId" path="resourceDesc" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化故障率：</label>
			<div class="controls">
				<form:input path="yearFailureRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线单次CASE现场处理故障工时：</label>
			<div class="controls">
				<form:input path="line1OnceHour" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二线单次CASE诊断、远程支持工时：</label>
			<div class="controls">
				<form:input path="line2OnceHour" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">三线单次CASE远程处理故障工时：</label>
			<div class="controls">
				<form:input path="line3OnceHour" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">CMO单次CASE处理工时：</label>
			<div class="controls">
				<form:input path="cmoOnceHour" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源岗单次CASE处理工时：</label>
			<div class="controls">
				<form:input path="resMgrOnceHour" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${!cstManFailureCaseHour.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstManFailureCaseHour.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<c:if test="${!cstManFailureCaseHour.isNewRecord and user.isAdmin(user.id)}">
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
			<c:if test="${cstManFailureCaseHour.isNewRecord or cstManFailureCaseHour.status eq 'A0'}">
				<shiro:hasPermission name="cst:man:cstManFailureCaseHour:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>