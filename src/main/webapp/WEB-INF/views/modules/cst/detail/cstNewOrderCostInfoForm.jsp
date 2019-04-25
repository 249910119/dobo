<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源模型成本管理</title>
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
		<li><a href="${ctx}/cst/detail/cstNewOrderCostInfo/">资源模型成本列表</a></li>
		<li class="active"><a href="${ctx}/cst/detail/cstNewOrderCostInfo/form?id=${cstNewOrderCostInfo.id}">资源模型成本<shiro:hasPermission name="cst:detail:cstNewOrderCostInfo:edit">${not empty cstNewOrderCostInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:detail:cstNewOrderCostInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstNewOrderCostInfo" action="${ctx}/cst/detail/cstNewOrderCostInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目ID：</label>
			<div class="controls">
				<form:input path="dcPrjId" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品ID：</label>
			<div class="controls">
				<form:input path="prodId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">明细ID：</label>
			<div class="controls">
				<form:input path="pdId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类标识：</label>
			<div class="controls">
				<form:input path="keyId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线一级：</label>
			<div class="controls">
				<form:input path="manLine1level1" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线二级：</label>
			<div class="controls">
				<form:input path="manLine1level2" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线三级：</label>
			<div class="controls">
				<form:input path="manLine1level3" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线四级：</label>
			<div class="controls">
				<form:input path="manLine1level4" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线五级：</label>
			<div class="controls">
				<form:input path="manLine1level5" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线六级：</label>
			<div class="controls">
				<form:input path="manLine1level6" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外包资源（外援费用）：</label>
			<div class="controls">
				<form:input path="manOutFee" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-PM3级：</label>
			<div class="controls">
				<form:input path="manPm3" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-PM4级：</label>
			<div class="controls">
				<form:input path="manPm4" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-PM5级：</label>
			<div class="controls">
				<form:input path="manPm5" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-二线专家：</label>
			<div class="controls">
				<form:input path="manLine2expert" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-cmo：</label>
			<div class="controls">
				<form:input path="manCmo" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交付管理人工包：</label>
			<div class="controls">
				<form:input path="manDelivery" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风险：</label>
			<div class="controls">
				<form:input path="manRisk" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品线管理：</label>
			<div class="controls">
				<form:input path="manProdline" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-纯备件成本：</label>
			<div class="controls">
				<form:input path="bakBackcost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-备件人工成本：</label>
			<div class="controls">
				<form:input path="bakMancost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-备件运作：</label>
			<div class="controls">
				<form:input path="bakOpercost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">status：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mfr_name：</label>
			<div class="controls">
				<form:input path="mfrName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">resource_name：</label>
			<div class="controls">
				<form:input path="resourceName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">modelgroup_name：</label>
			<div class="controls">
				<form:input path="modelgroupName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">equip_type_name：</label>
			<div class="controls">
				<form:input path="equipTypeName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">resource_id：</label>
			<div class="controls">
				<form:input path="resourceId" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务开始时间：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cstNewOrderCostInfo.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cstNewOrderCostInfo.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cst:detail:cstNewOrderCostInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>