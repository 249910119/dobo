<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品成本模型使用定义管理</title>
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
		<li><a href="${ctx}/cst/model/cstModelProdModuleRel/">产品成本模型使用定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/model/cstModelProdModuleRel/form?id=${cstModelProdModuleRel.id}">产品成本模型使用定义<shiro:hasPermission name="cst:model:cstModelProdModuleRel:edit">${not empty cstModelProdModuleRel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:model:cstModelProdModuleRel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstModelProdModuleRel" action="${ctx}/cst/model/cstModelProdModuleRel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">成本模型使用名称：</label>
			<div class="controls">
				<form:input path="cpmName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本模型标识：</label>
			<div class="controls">
				<form:hidden id="cstModelModuleInfoId" path="moduleId" value="${cstModelProdModuleRel.cstModelModuleInfo.id}" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstModelModuleInfoModuleName" name="moduleName" value="${cstModelProdModuleRel.cstModelModuleInfo.moduleName}" maxlength="64" class="input-medium required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<a id="cstModelModuleInfoSelect" href="javascript:" class="btn">选择</a>
				<a id="cstModelModuleInfoClear" href="javascript:" class="btn">清除</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstModelModuleInfoId").val(id);
						$("#cstModelModuleInfoModuleName").val(title);
					}
					$("#cstModelModuleInfoSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/model/cstModelModuleInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
					$("#cstModelModuleInfoClear").click(function(){
						$("#cstModelModuleInfo").val('');
						$("#cstModelModuleInfoModuleName").val('');
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品标识：</label>
			<div class="controls">
				<form:input path="prodId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型计算类：</label>
			<div class="controls">
				<form:input path="className" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算顺序：</label>
			<div class="controls">
				<form:input path="calOrder" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstModelProdModuleRel.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstModelProdModuleRel.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstModelProdModuleRel.isNewRecord or cstModelProdModuleRel.status eq 'A0'}">
				<shiro:hasPermission name="cst:model:cstModelProdModuleRel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>