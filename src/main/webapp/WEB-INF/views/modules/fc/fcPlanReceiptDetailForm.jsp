<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目计划收款明细管理</title>
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
		<li><a href="${ctx}/fc/fcPlanReceiptDetail/">项目计划收款明细列表</a></li>
		<li class="active"><a href="${ctx}/fc/fcPlanReceiptDetail/form?id=${fcPlanReceiptDetail.id}">项目计划收款明细<shiro:hasPermission name="fc:fcPlanReceiptDetail:edit">${not empty fcPlanReceiptDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fc:fcPlanReceiptDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fcPlanReceiptDetail" action="${ctx}/fc/fcPlanReceiptDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>				
		<div class="control-group">
			<label class="control-label">项目信息：</label>
			<div class="controls">
				<form:hidden id="fcProjectInfoId" path="fcProjectInfoId" value="${fcPlanReceiptDetail.fcProjectInfo.id}" htmlEscape="false" maxlength="64" />
				<input type="text" id="fcProjectInfoCode" name="fcProjectInfoCode" value="${fcPlanReceiptDetail.fcProjectInfo.projectCode}" maxlength="64" class="input-medium required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<a id="fcProjectInfoSelect" href="javascript:" class="btn">选择</a>
				<a id="fcProjectInfoClear" href="javascript:" class="btn">清除</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#fcProjectInfoId").val(id);
						$("#fcProjectInfoCode").val(title);
					}
					$("#fcProjectInfoSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/fc/fcProjectInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
					$("#fcProjectInfoClear").click(function(){
						$("#fcProjectInfoId").val('');
						$("#fcProjectInfoCode").val('');
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期次：</label>
			<div class="controls">
				<form:input path="displayOrder" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划收款时间：</label>
			<div class="controls">
				<input name="planReceiptTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${fcPlanReceiptDetail.planReceiptTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划收款金额：</label>
			<div class="controls">
				<form:input path="planReceiptAmount" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划收款比例：</label>
			<div class="controls">
				<form:input path="planReceiptScale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				<form:radiobuttons path="payType" items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付币种：</label>
			<div class="controls">
				<form:radiobuttons path="payCurrency" items="${fns:getDictList('pay_currency')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款时间间隔天数：</label>
			<div class="controls">
				<form:input path="planReceiptDays" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${not fcPlanReceiptDetail.isNewRecord}">
		<div class="control-group">
			<label class="control-label">保存标记：</label>
			<div class="controls">
				<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${fns:getDictLabel(fcPlanReceiptDetail.status, 'status', '')}
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${fcPlanReceiptDetail.isNewRecord or fcPlanReceiptDetail.status eq 'A0'}">
			<shiro:hasPermission name="fc:fcPlanReceiptDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>