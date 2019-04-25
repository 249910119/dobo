<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单成本明细管理</title>
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
		<li><a href="${ctx}/cst/detail/cstOrderCostInfo/">订单成本明细列表</a></li>
		<li class="active"><a href="${ctx}/cst/detail/cstOrderCostInfo/form?id=${cstOrderCostInfo.id}">订单成本明细<shiro:hasPermission name="cst:detail:cstOrderCostInfo:edit">${not empty cstOrderCostInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:detail:cstOrderCostInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstOrderCostInfo" action="${ctx}/cst/detail/cstOrderCostInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目ID：</label>
			<div class="controls">
				<form:input path="dcPrjId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
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
				<form:input path="detailId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类标识：</label>
			<div class="controls">
				<form:input path="keyId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
			<label class="control-label">人工-一线一级-折减：</label>
			<div class="controls">
				<form:input path="manLine1level1Des" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线二级-折减：</label>
			<div class="controls">
				<form:input path="manLine1level2Des" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线三级-折减：</label>
			<div class="controls">
				<form:input path="manLine1level3Des" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线四级-折减：</label>
			<div class="controls">
				<form:input path="manLine1level4Des" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线五级-折减：</label>
			<div class="controls">
				<form:input path="manLine1level5Des" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线一级-差旅：</label>
			<div class="controls">
				<form:input path="manLine1level1Travl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线二级-差旅：</label>
			<div class="controls">
				<form:input path="manLine1level2Travl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线三级-差旅：</label>
			<div class="controls">
				<form:input path="manLine1level3Travl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线四级-差旅：</label>
			<div class="controls">
				<form:input path="manLine1level4Travl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-一线五级-差旅：</label>
			<div class="controls">
				<form:input path="manLine1level5Travl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-二线四级：</label>
			<div class="controls">
				<form:input path="manLine2level4" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-二线五级：</label>
			<div class="controls">
				<form:input path="manLine2level5" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-二线六级：</label>
			<div class="controls">
				<form:input path="manLine2level6" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-三线六级：</label>
			<div class="controls">
				<form:input path="manLine3level6" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-cmo：</label>
			<div class="controls">
				<form:input path="manCmo" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-pmo：</label>
			<div class="controls">
				<form:input path="manPmo" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-资源岗：</label>
			<div class="controls">
				<form:input path="manZyg" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-总控管理：</label>
			<div class="controls">
				<form:input path="manZkgl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-区域管理：</label>
			<div class="controls">
				<form:input path="manQygl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人工-区域管理_折减：</label>
			<div class="controls">
				<form:input path="manQyglDes" htmlEscape="false" class="input-xlarge  number"/>
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
			<label class="control-label">备件-故障成本：</label>
			<div class="controls">
				<form:input path="bakGzcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-项目储备成本：</label>
			<div class="controls">
				<form:input path="bakXmcbcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-高频储备成本：</label>
			<div class="controls">
				<form:input path="bakGpcbcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-备件人工成本：</label>
			<div class="controls">
				<form:input path="bakBjrgcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-总控管理：</label>
			<div class="controls">
				<form:input path="bakZkgl" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-仓库租赁成本：</label>
			<div class="controls">
				<form:input path="bakCkzlcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-包材成本：</label>
			<div class="controls">
				<form:input path="bakBccb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-回收取件运输成本：</label>
			<div class="controls">
				<form:input path="bakHsqjyscb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-故障件发货运输成本：</label>
			<div class="controls">
				<form:input path="bakGzjfhyscb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-物业、水电成本：</label>
			<div class="controls">
				<form:input path="bakWysdcb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备件-调拨运输成本：</label>
			<div class="controls">
				<form:input path="bakDbyscb" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理：</label>
			<div class="controls">
				<form:input path="managerCost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工具：</label>
			<div class="controls">
				<form:input path="toolCost" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风险：</label>
			<div class="controls">
				<form:input path="riskCost" htmlEscape="false" class="input-xlarge  number"/>
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
		<div class="form-actions">
			<shiro:hasPermission name="cst:detail:cstOrderCostInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>