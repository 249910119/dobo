<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源模型成本管理</title>
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
		<li class="active"><a href="${ctx}/cst/detail/cstNewOrderCostInfo/">资源模型成本列表</a></li>
		<shiro:hasPermission name="cst:detail:cstNewOrderCostInfo:edit"><li><a href="${ctx}/cst/detail/cstNewOrderCostInfo/form">资源模型成本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstNewOrderCostInfo" action="${ctx}/cst/detail/cstNewOrderCostInfo/" method="post" class="breadcrumb form-search">
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
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="cst:detail:cstNewOrderCostInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstNewOrderCostInfo">
			<tr>
				<td><a href="${ctx}/cst/detail/cstNewOrderCostInfo/form?id=${cstNewOrderCostInfo.id}">
					<fmt:formatDate value="${cstNewOrderCostInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${cstNewOrderCostInfo.remarks}
				</td>
				<shiro:hasPermission name="cst:detail:cstNewOrderCostInfo:edit"><td>
    				<a href="${ctx}/cst/detail/cstNewOrderCostInfo/form?id=${cstNewOrderCostInfo.id}">修改</a>
					<a href="${ctx}/cst/detail/cstNewOrderCostInfo/delete?id=${cstNewOrderCostInfo.id}" onclick="return confirmx('确认要删除该资源模型成本吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>