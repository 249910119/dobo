<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本计算公式定义表管理</title>
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
		<li><a href="${ctx}/cst/model/cstModelCalculateFormula/">成本计算公式定义表列表</a></li>
		<li class="active"><a href="${ctx}/cst/model/cstModelCalculateFormula/form?id=${cstModelCalculateFormula.id}">成本计算公式定义表<shiro:hasPermission name="cst:model:cstModelCalculateFormula:edit">${not empty cstModelCalculateFormula.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:model:cstModelCalculateFormula:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstModelCalculateFormula" action="${ctx}/cst/model/cstModelCalculateFormula/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">成本模型标识：</label>
			<div class="controls">
				<form:hidden id="cstModelModuleInfoId" path="moduleId" value="${cstModelCalculateFormula.cstModelModuleInfo.id}" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstModelModuleInfoModuleName" name="moduleName" value="${cstModelCalculateFormula.cstModelModuleInfo.moduleName}" maxlength="64" class="input-medium required" readonly="readonly"/>
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
						$("#cstModelModuleInfoId").val('');
						$("#cstModelModuleInfoModuleName").val('');
					});
				</script>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">成本类型代码：与订单成本明细一一对应：</label>
			<div class="controls">
				<form:hidden id="costTypeId" path="costType" value="${cstModelCalculateFormula.ctType.costType}" htmlEscape="false" maxlength="64" />
				<input type="text" id="costTypeTypeName" name="typeName" value="${cstModelCalculateFormula.ctType.typeName}" maxlength="64" class="input-medium required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<a id="costTypeSelect" href="javascript:" class="btn">选择</a>
				<a id="costTypeClear" href="javascript:" class="btn">清除</a>
				<script type="text/javascript">
					function setCostTypeSelectRetValue(id,title){
						$("#costTypeId").val(id);
						$("#costTypeTypeName").val(title);
					}
					$("#costTypeSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/model/costType/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
					$("#costTypeClear").click(function(){
						$("#costTypeId").val('');
						$("#costTypeTypeName").val('');
					});
				</script>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">指标代码：与订单成本明细一一对应：</label>
			<div class="controls">
				<form:hidden id="measureDefMeasureId" path="measureId" value="${cstModelCalculateFormula.measureDef.measureId}" htmlEscape="false" maxlength="64" />
				<input type="text" id="measureDefmeasureName" name="measureName" value="${cstModelCalculateFormula.measureDef.measureName}" maxlength="64" class="input-medium required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<a id="measureDefSelect" href="javascript:" class="btn">选择</a>
				<a id="measureDefClear" href="javascript:" class="btn">清除</a>
				<script type="text/javascript">
					function setMeasureDefSelectRetValue(id,title){
						$("#measureDefMeasureId").val(id);
						$("#measureDefmeasureName").val(title);
					}
					$("#measureDefSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/model/measureDef/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
					$("#measureDefClear").click(function(){
						$("#measureDefMeasureId").val('');
						$("#measureDefmeasureName").val('');
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否成本参数：</label>
			<div class="controls">
				<form:radiobuttons path="isModelPara" items="${fns:getDictList('is_model_para')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回类型：</label>
			<div class="controls">
				<form:radiobuttons path="returnType" items="${fns:getDictList('return_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算公式名称：</label>
			<div class="controls">
				<form:input path="formulaName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算公式描述：</label>
			<div class="controls">
				<form:input path="formulaDesc" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算公式：</label>
			<div class="controls">
				<form:input path="formula" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示顺序：</label>
			<div class="controls">
				<form:input path="displayOrder" htmlEscape="false" maxlength="18" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstModelCalculateFormula.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
					<div class="controls">
						${fns:getDictLabel(cstModelCalculateFormula.status, 'status', '')}
					</div>
			</div>
		</c:if>
		<c:if test="${!cstModelCalculateFormula.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstModelCalculateFormula.isNewRecord or cstModelCalculateFormula.status eq 'A0'}">
				<shiro:hasPermission name="cst:model:cstModelCalculateFormula:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>