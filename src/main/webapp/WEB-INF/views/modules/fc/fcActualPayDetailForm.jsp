<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实际付款明细管理</title>
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
		<li><a href="${ctx}/fc/fcActualPayDetail/">实际付款明细列表</a></li>
		<li class="active"><a href="${ctx}/fc/fcActualPayDetail/form?id=${fcActualPayDetail.id}">实际付款明细<shiro:hasPermission name="fc:fcActualPayDetail:edit">${not empty fcActualPayDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fc:fcActualPayDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fcActualPayDetail" action="${ctx}/fc/fcActualPayDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">fk_id：</label>
			<div class="controls">
				<form:input path="fkId" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购订单代码：</label>
			<div class="controls">
				<form:input path="cgdddm" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目代码：</label>
			<div class="controls">
				<form:input path="xmdm" htmlEscape="false" maxlength="108" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商代码：</label>
			<div class="controls">
				<form:input path="gysdm" htmlEscape="false" maxlength="108" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商名称：</label>
			<div class="controls">
				<form:input path="gysmc" htmlEscape="false" maxlength="240" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司代码：</label>
			<div class="controls">
				<form:input path="gsdm" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SBU名称：</label>
			<div class="controls">
				<form:input path="sbumc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">BU名称：</label>
			<div class="controls">
				<form:input path="bumc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事业部名称：</label>
			<div class="controls">
				<form:input path="sybmc" htmlEscape="false" maxlength="240" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务范围代码：</label>
			<div class="controls">
				<form:input path="ywfwdm" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务范围名称：</label>
			<div class="controls">
				<form:input path="ywfwmc" htmlEscape="false" maxlength="240" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款凭证代码：</label>
			<div class="controls">
				<form:input path="fkpzdm" htmlEscape="false" maxlength="120" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款金额：</label>
			<div class="controls">
				<form:input path="fkje" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款日期：</label>
			<div class="controls">
				<form:input path="fkrq" htmlEscape="false" maxlength="48" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款方式：</label>
			<div class="controls">
				<form:input path="pays" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开票日：</label>
			<div class="controls">
				<form:input path="invsdate" htmlEscape="false" maxlength="48" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到期日：</label>
			<div class="controls">
				<form:input path="invedate" htmlEscape="false" maxlength="48" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单号：</label>
			<div class="controls">
				<form:input path="formid" htmlEscape="false" maxlength="60" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="240" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fc:fcActualPayDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>