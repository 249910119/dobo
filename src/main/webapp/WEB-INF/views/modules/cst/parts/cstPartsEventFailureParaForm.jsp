<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件事件故障参数定义管理</title>
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
		<li><a href="${ctx}/cst/parts/cstPartsEventFailurePara/">备件事件故障参数定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/parts/cstPartsEventFailurePara/form?id=${cstPartsEventFailurePara.id}">备件事件故障参数定义<shiro:hasPermission name="cst:parts:cstPartsEventFailurePara:edit">${not empty cstPartsEventFailurePara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:parts:cstPartsEventFailurePara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstPartsEventFailurePara" action="${ctx}/cst/parts/cstPartsEventFailurePara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<%-- <div class="control-group">
			<label class="control-label">资源标识：</label>
			<div class="controls">
				<form:input path="resourceId" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源描述：</label>
			<div class="controls">
				<form:input path="resourceDesc" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">资源标识：</label>
			<div class="controls">
				<input type="text" id="cstResourceBaseInfoName" name="resourceId" value="${cstPartsEventFailurePara.resourceId}" maxlength="64" class="input-medium required" readonly="readonly"/>
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
			<label class="control-label">事件故障率：</label>
			<div class="controls">
				<form:input path="eventFailureRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">1级事件占比：</label>
			<div class="controls">
				<form:input path="level1EventScale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2级事件占比：</label>
			<div class="controls">
				<form:input path="level2EventScale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">3级事件占比：</label>
			<div class="controls">
				<form:input path="level3EventScale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">4级事件占比：</label>
			<div class="controls">
				<form:input path="level4EventScale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件故障率：</label>
			<div class="controls">
				<form:input path="failureRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平均采购成本：</label>
			<div class="controls">
				<form:input path="averageCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事件故障率：</label>
			<div class="controls">
				<form:input path="averageFailureCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstPartsEventFailurePara.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
					<div class="controls">
						${fns:getDictLabel(cstPartsEventFailurePara.status, 'status', '')}
					</div>
			</div>
		</c:if>
		<c:if test="${!cstPartsEventFailurePara.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstPartsEventFailurePara.isNewRecord or cstPartsEventFailurePara.status eq 'A0'}">
				<shiro:hasPermission name="cst:parts:cstPartsEventFailurePara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>