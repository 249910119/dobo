<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件发货运输平均成本定义管理</title>
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
		<li><a href="${ctx}/cst/parts/cstPartsTransportCost/">备件发货运输平均成本定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/parts/cstPartsTransportCost/form?id=${cstPartsTransportCost.id}">备件发货运输平均成本定义<shiro:hasPermission name="cst:parts:cstPartsTransportCost:edit">${not empty cstPartsTransportCost.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:parts:cstPartsTransportCost:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstPartsTransportCost" action="${ctx}/cst/parts/cstPartsTransportCost/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">服务级别：</label>
			<div class="controls">
				<form:select path="slaLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SLA_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货类型：</label>
			<div class="controls">
				<form:select path="deliveryType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DELIVERY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本地备件满足率：</label>
			<div class="controls">
				<form:input path="localFillRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">
				<form:input path="city" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省：</label>
			<div class="controls">
				<form:input path="province" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货运输级别：</label>
			<div class="controls">
				<form:select path="transportType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('TRANSPORT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市级别：</label>
			<div class="controls">
				<form:select path="cityLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('CITY_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跨省专送运输平均成本：</label>
			<div class="controls">
				<form:input path="transProvinceCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本市专送运输平均成本：</label>
			<div class="controls">
				<form:input path="localCityCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第三方物流发货运输平均成本：</label>
			<div class="controls">
				<form:input path="thirdDeliveryCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第三方物流回收取件运输平均成本：</label>
			<div class="controls">
				<form:input path="thirdPickCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!cstPartsTransportCost.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
					<div class="controls">
						${fns:getDictLabel(cstPartsTransportCost.status, 'status', '')}
					</div>
			</div>
		</c:if>
		<c:if test="${!cstPartsTransportCost.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${cstPartsTransportCost.isNewRecord or cstPartsTransportCost.status eq 'A0'}">
				<shiro:hasPermission name="cst:parts:cstPartsTransportCost:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>