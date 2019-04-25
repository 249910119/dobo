<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障级别配比定义管理</title>
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
		<li><a href="${ctx}/cst/man/cstManFailureSlaPara/">故障级别配比定义列表</a></li>
		<li class="active"><a href="${ctx}/cst/man/cstManFailureSlaPara/form?id=${cstManFailureSlaPara.id}">故障级别配比定义<shiro:hasPermission name="cst:man:cstManFailureSlaPara:edit">${not empty cstManFailureSlaPara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cst:man:cstManFailureSlaPara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cstManFailureSlaPara" action="${ctx}/cst/man/cstManFailureSlaPara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源标识：</label>
			<div class="controls">
				<input type="text" id="cstResourceBaseInfoName" name="resourceId" value="${cstManFailureSlaPara.resourceId}" maxlength="64" class="input-medium required" readonly="readonly"/>
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
			<label class="control-label">服务级别：</label>
			<div class="controls">
				<form:select path="slaName" class="input-xlarge required" onchange="selectSla()" id="slaName">
					<form:option value="" label="请选择..." />
					<form:options items="${slaList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<script type="text/javascript">
					function selectSla(){
						var selectObj = document.getElementById("slaName");
						var name = selectObj.options[selectObj.selectedIndex].value;
						$("#slaId").val(name);
					};
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务级别标识：</label>
			<div class="controls">
				<form:input path="slaId" htmlEscape="false" maxlength="30" class="input-xlarge required" id="slaId" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线1级配比：</label>
			<div class="controls">
				<form:input path="line1Level1Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线2级配比：</label>
			<div class="controls">
				<form:input path="line1Level2Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线3级配比：</label>
			<div class="controls">
				<form:input path="line1Level3Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线4级配比：</label>
			<div class="controls">
				<form:input path="line1Level4Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一线5级配比：</label>
			<div class="controls">
				<form:input path="line1Level5Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二线4级配比：</label>
			<div class="controls">
				<form:input path="line2Level4Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二线5级配比：</label>
			<div class="controls">
				<form:input path="line2Level5Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二线6级配比：</label>
			<div class="controls">
				<form:input path="line2Level6Scale" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${!cstManFailureSlaPara.isNewRecord}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					${fns:getDictLabel(cstManFailureSlaPara.status, 'status', '')}
				</div>
			</div>
		</c:if>
		<c:if test="${!cstManFailureSlaPara.isNewRecord and user.isAdmin(user.id)}">
			<div class="control-group">
				<label class="control-label">保存标记：</label>
				<div class="controls">
					<form:radiobuttons path="saveFlag" items="${fns:getDictList('save_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${cstManFailureSlaPara.isNewRecord or cstManFailureSlaPara.status eq 'A0'}">
				<shiro:hasPermission name="cst:man:cstManFailureSlaPara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>