<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统人员测评计算结果管理</title>
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
		<li><a href="${ctx}/cp/result/cpStaffResultReview/">系统人员测评计算结果列表</a></li>
		<li class="active"><a href="${ctx}/cp/result/cpStaffResultReview/form?id=${cpStaffResultReview.id}">系统人员测评计算结果<shiro:hasPermission name="cp:result:cpStaffResultReview:edit">${not empty cpStaffResultReview.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cp:result:cpStaffResultReview:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cpStaffResultReview" action="${ctx}/cp/result/cpStaffResultReview/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">ITCode(被评人)：</label>
			<div class="controls">
				<form:input path="staffId" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名(被评人)：</label>
			<div class="controls">
				<form:input path="staffName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评委人数：</label>
			<div class="controls">
				<form:input path="raterNum" htmlEscape="false" maxlength="18" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评委类型(自评	\他评\	上级评\	下级评	\同事评)：</label>
			<div class="controls">
				<form:input path="raterLevel" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权重：</label>
			<div class="controls">
				<form:input path="percents" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="jobLevel" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位级别：</label>
			<div class="controls">
				<form:input path="jobLevelId" htmlEscape="false" maxlength="4" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板号：</label>
			<div class="controls">
				<form:input path="templateId" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号1-得分：</label>
			<div class="controls">
				<form:input path="number1" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号2：</label>
			<div class="controls">
				<form:input path="number2" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号3：</label>
			<div class="controls">
				<form:input path="number3" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号4：</label>
			<div class="controls">
				<form:input path="number4" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号5：</label>
			<div class="controls">
				<form:input path="number5" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号6：</label>
			<div class="controls">
				<form:input path="number6" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号7：</label>
			<div class="controls">
				<form:input path="number7" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号8：</label>
			<div class="controls">
				<form:input path="number8" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号9：</label>
			<div class="controls">
				<form:input path="number9" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号10：</label>
			<div class="controls">
				<form:input path="number10" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号11：</label>
			<div class="controls">
				<form:input path="number11" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号12：</label>
			<div class="controls">
				<form:input path="number12" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号13：</label>
			<div class="controls">
				<form:input path="number13" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号14：</label>
			<div class="controls">
				<form:input path="number14" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号15：</label>
			<div class="controls">
				<form:input path="number15" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号16：</label>
			<div class="controls">
				<form:input path="number16" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号17：</label>
			<div class="controls">
				<form:input path="number17" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号18：</label>
			<div class="controls">
				<form:input path="number18" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号19：</label>
			<div class="controls">
				<form:input path="number19" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号20：</label>
			<div class="controls">
				<form:input path="number20" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号21：</label>
			<div class="controls">
				<form:input path="number21" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号22：</label>
			<div class="controls">
				<form:input path="number22" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号23：</label>
			<div class="controls">
				<form:input path="number23" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号24：</label>
			<div class="controls">
				<form:input path="number24" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remark1：</label>
			<div class="controls">
				<form:input path="remark1" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remark2：</label>
			<div class="controls">
				<form:input path="remark2" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remark3：</label>
			<div class="controls">
				<form:input path="remark3" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态（A0:有效/A1:无效）：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
					value="<fmt:formatDate value="${cpStaffResultReview.statusChangeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保存标记（0：加时间戳新增保存；1：原纪录直接更新；）：</label>
			<div class="controls">
				<form:input path="saveFlag" htmlEscape="false" maxlength="1" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效截止日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cpStaffResultReview.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cp:result:cpStaffResultReview:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>