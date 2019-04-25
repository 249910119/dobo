<%@page import="com.dobo.common.utils.DateUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目计划外财务费用计算</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		
		function clearDcDate(){
			$('[name="dcPrjDateBegin"]').val("");
			$('[name="dcPrjDateEnd"]').val("");
		}
		
		function clearPlanDate(){
			$('[name="planReceiptDateBegin"]').val("");
			$('[name="planReceiptDateEnd"]').val("");
		}
		
		function dcPrjBeginChange(){
			if($('[name="dcPrjDateEnd"]').val() == ""){
				$('[name="dcPrjDateEnd"]').val($('[name="dcPrjDateBegin"]').val());
			}
			if($('[name="dcPrjDateEnd"]').val() < $('[name="dcPrjDateBegin"]').val()){
				alert("项目立项开始日期不能大于结束日期！");
				$('[name="dcPrjDateBegin"]').val("");
			}
		}
		
		function dcPrjEndChange(){
			if($('[name="dcPrjDateBegin"]').val() == ""){
				$('[name="dcPrjDateBegin"]').val($('[name="dcPrjDateEnd"]').val());
			}
			if($('[name="dcPrjDateEnd"]').val() < $('[name="dcPrjDateBegin"]').val()){
				alert("项目立项开始日期不能大于结束日期！");
				$('[name="dcPrjDateEnd"]').val("");
			}
		}
		
		function planBeginChange(){
			if($('[name="planReceiptDateEnd"]').val() == ""){
				$('[name="planReceiptDateEnd"]').val($('[name="planReceiptDateBegin"]').val());
			}
			if($('[name="planReceiptDateEnd"]').val() < $('[name="planReceiptDateBegin"]').val()){
				alert("计划收款开始日期不能大于结束日期！");
				$('[name="planReceiptDateBegin"]').val("");
			}
		}
		
		function planEndChange(){
			if($('[name="planReceiptDateBegin"]').val() == ""){
				$('[name="planReceiptDateBegin"]').val($('[name="planReceiptDateEnd"]').val());
			}
			if($('[name="planReceiptDateEnd"]').val() < $('[name="planReceiptDateBegin"]').val()){
				alert("计划收款开始日期不能大于结束日期！");
				$('[name="planReceiptDateEnd"]').val("");
			}
		}
		
		function checkSubmit(){
			if($('[name="planReceiptDateBegin"]').val() == "" &&  $('[name="dcPrjDateBegin"]').val() == ""){
				alert("项目立项日期和计划收款日期不能同时为空，请至少选择一项！");
				return false;
			}
			return true;
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style type="text/css">
		#dcPrjDate, #planReceiptDate{
			width: 80px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="fc:fcPlanOuterFee:edit"><li><a href="${ctx}/fc/fcPlanOuterFee/">项目计划外财务费用列表</a></li></shiro:hasPermission>
		<shiro:hasPermission name="fc:fcPlanOuterFee:edit"><li><a href="${ctx}/fc/fcPlanOuterFee/form">项目计划外财务费用添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/fc/fcPlanOuterFee/count">项目计划外财务费用计算</a></li>
	</ul>
	<form:form id="inputForm" action="${ctx}/fc/fcPlanOuterFee/planCount" method="post" class="form-horizontal">
		<sys:message content="${message}"/>			
		<div class="control-group">
			<label class="control-label">项目号：</label>
			<div class="controls">
				<input type="text" class="input-xlarge required number" id="dcPrjId" name="dcPrjId"/>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">项目立项月份：</label>
			<div class="controls">
				<input type="text" id="dcPrjDate" name="dcPrjDateBegin" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月'})" class="dcPrjDate" readonly="readonly" onchange="dcPrjBeginChange()"/>
				至
				<input type="text" id="dcPrjDate" name="dcPrjDateEnd" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月'})" class="dcPrjDate" readonly="readonly" onchange="dcPrjEndChange()"/>
				<a href="#" onclick="clearDcDate()">清空</a>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">计算月份：</label>
			<div class="controls">
				<input type="text" id="planReceiptDate" name="planReceiptDateBegin" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月'})" class="planReceiptDate" readonly="readonly" onchange="planBeginChange()"/>
				至
				<input type="text" id="planReceiptDate" name="planReceiptDateEnd" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月'})" class="planReceiptDate" readonly="readonly" onchange="planEndChange()"/>
				<a href="#" onclick="clearPlanDate()">清空</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算结果：</label>
			<div class="controls">
				<input type="radio" id="planResult" name="planResult" value="A0" class="planReceiptDate" />结果入库
				<input type="radio" id="planResult" name="planResult" value="A1" class="planReceiptDate" checked="checked" />结果不入库
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fc:fcPlanOuterFee:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="开始计算" onclick="return checkSubmit()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<iframe src="${ctxStatic}/ckfinder/ckfinder.html" style="overflow:visible;" frameborder="no" height="550px" width="100%"></iframe>
				
</body>
</html>