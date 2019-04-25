<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单信息管理</title>
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
		<li><a href="${ctx}/fc/fcOrderInfo/">订单信息列表</a></li>
		<li class="active"><a href="${ctx}/fc/fcOrderInfo/form?id=${fcOrderInfo.id}">订单信息<shiro:hasPermission name="fc:fcOrderInfo:edit">${not empty fcOrderInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fc:fcOrderInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fcOrderInfo" action="${ctx}/fc/fcOrderInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目信息：</label>
			<div class="controls">
				<form:hidden id="fcProjectInfoId" path="fcProjectInfoId" value="${fcOrderInfo.fcProjectInfo.id}" htmlEscape="false" maxlength="64" />
				<input type="text" id="fcProjectInfoCode" name="fcProjectInfoCode" value="${fcOrderInfo.fcProjectInfo.projectCode}" maxlength="64" class="input-medium required" readonly="readonly"/>
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
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品线大类：</label>
			<div class="controls">
				<form:input path="fstSvcType" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品线小类：</label>
			<div class="controls">
				<form:input path="sndSvcType" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务期开始：</label>
			<div class="controls">
				<input name="serviceDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${fcOrderInfo.serviceDateBegin}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务期结束：</label>
			<div class="controls">
				<input name="serviceDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${fcOrderInfo.serviceDateEnd}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约金额：</label>
			<div class="controls">
				<form:input path="signAmount" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自有产品成本：</label>
			<div class="controls">
				<form:input path="ownProdCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指定分包成本：</label>
			<div class="controls">
				<form:input path="specifySubCost" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${not fcOrderInfo.isNewRecord}">
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
				${fns:getDictLabel(fcOrderInfo.status, 'status', '')}
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${fcOrderInfo.isNewRecord or fcOrderInfo.status eq 'A0'}">
			<shiro:hasPermission name="fc:fcOrderInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>