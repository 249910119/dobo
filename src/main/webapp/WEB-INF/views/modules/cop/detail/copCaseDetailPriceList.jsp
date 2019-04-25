<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单次、先行支持服务报价明细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cop/detail/copCaseDetailPrice/">单次、先行支持服务报价明细列表</a></li>
		<shiro:hasPermission name="cop:detail:copCaseDetailPrice:edit"><li><a href="${ctx}/cop/detail/copCaseDetailPrice/form">单次、先行支持服务报价明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="copCaseDetailPrice" action="${ctx}/cop/detail/copCaseDetailPrice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cop:detail:copCaseDetailPrice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="copCaseDetailPrice">
			<tr>
				<td><a href="${ctx}/cop/detail/copCaseDetailPrice/form?id=${copCaseDetailPrice.id}">
					<fmt:formatDate value="${copCaseDetailPrice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${copCaseDetailPrice.remarks}
				</td>
				<shiro:hasPermission name="cop:detail:copCaseDetailPrice:edit"><td>
    				<a href="${ctx}/cop/detail/copCaseDetailPrice/form?id=${copCaseDetailPrice.id}">修改</a>
					<a href="${ctx}/cop/detail/copCaseDetailPrice/delete?id=${copCaseDetailPrice.id}" onclick="return confirmx('确认要删除该单次、先行支持服务报价明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>