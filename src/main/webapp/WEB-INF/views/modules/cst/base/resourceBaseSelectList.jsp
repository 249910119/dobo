<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选择项目</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					top.mainFrame.setProjectSelectRetValue(id, title);
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<div style="margin:10px;">
	<form:form id="searchForm" modelAttribute="cstResourceBaseInfo" action="${ctx}/cst/base/cstResourceBaseInfo/selectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标识名称：</label><form:input path="resourceId" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<label>厂商名称：</label><form:input path="mfrName" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<label>型号名称：</label><form:input path="resourceName" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center;">选择</th>
				<th>资源标识</th>
				<th>厂商</th>
				<th>型号</th>
				<th>型号组</th>
				<th>技术方向</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstResourceBaseInfo">
			<tr>
				<td style="text-align:center;"><input type="radio" name="id" onclick="ready()" value="${cstResourceBaseInfo.resourceId}" 
				title="${cstResourceBaseInfo.mfrName}|${cstResourceBaseInfo.resourceName}|${cstResourceBaseInfo.modelGroupName}|${cstResourceBaseInfo.equipTypeName}" /></td>
				<td>${cstResourceBaseInfo.resourceId}</td>
				<td id="mfrNameId" >${cstResourceBaseInfo.mfrName}</td>
				<td id="resourceNameId">${cstResourceBaseInfo.resourceName}</td>
				<td id="modelGroupNameId">${cstResourceBaseInfo.modelGroupName}</td>
				<td id="equipTypeNameId">${cstResourceBaseInfo.equipTypeName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>