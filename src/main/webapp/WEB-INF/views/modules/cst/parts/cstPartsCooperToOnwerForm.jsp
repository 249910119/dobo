<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件合作转自有时间清单管理</title>
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
		<li><a href="${ctx}/cst/parts/cstPartsCooperToOnwer/">备件合作转自有时间清单列表</a></li>
		<li class="active"><a href="${ctx}/cst/parts/cstPartsCooperToOnwer/form?id=${cstPartsCooperToOnwer.id}">备件合作转自有时间清单<shiro:hasPermission name="cst:parts:cstPartsCooperToOnwer:edit">${not empty cstPartsCooperToOnwer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:parts:cstPartsCooperToOnwer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstPartsCooperToOnwer" action="${ctx}/cst/parts/cstPartsCooperToOnwer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源标识：</label>
			<div class="controls">
				<form:input path="resourceId" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源描述（格式：厂商|型号|型号组|技术方向）：</label>
			<div class="controls">
				<form:input path="resourceDesc" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合作包ID：</label>
			<div class="controls">
				<form:input path="packId" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cstPartsCooperToOnwer.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cstPartsCooperToOnwer.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态（A0:有效/A1:无效）：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新前状态（A0:有效/A1:无效）：</label>
			<div class="controls">
				<form:input path="preStatus" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态更新时间：</label>
			<div class="controls">
				<input name="statusChangeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cstPartsCooperToOnwer.statusChangeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保存标记（0：加时间戳新增保存；1：原纪录直接更新；）：</label>
			<div class="controls">
				<form:input path="saveFlag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cst:parts:cstPartsCooperToOnwer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>