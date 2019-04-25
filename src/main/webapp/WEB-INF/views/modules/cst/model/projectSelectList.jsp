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
	<form:form id="searchForm" modelAttribute="cstModelModuleInfo" action="${ctx}/cst/model/cstModelModuleInfo/selectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>成本模型名称：</label><form:input path="moduleName" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align:center;">选择</th><th>成本模型类型</th><th>成本模型名称</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstModelModuleInfo">
			<tr>
				<td style="text-align:center;"><input type="radio" name="id" value="${cstModelModuleInfo.id}" title="${cstModelModuleInfo.moduleName}"/></td>
				<td>${fns:getDictLabel(cstModelModuleInfo.moduleType, 'module_type', '')}</td>
				<td>${cstModelModuleInfo.moduleName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>