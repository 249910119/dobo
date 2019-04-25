<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单成本明细管理</title>
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
		<li class="active"><a href="${ctx}/cst/detail/cstOrderCostInfo/">订单成本明细列表</a></li>
		<shiro:hasPermission name="cst:detail:cstOrderCostInfo:edit"><li><a href="${ctx}/cst/detail/cstOrderCostInfo/form">订单成本明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstOrderCostInfo" action="${ctx}/cst/detail/cstOrderCostInfo/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="cst:detail:cstOrderCostInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstOrderCostInfo">
			<tr>
				<td><a href="${ctx}/cst/detail/cstOrderCostInfo/form?id=${cstOrderCostInfo.id}">
					<fmt:formatDate value="${cstOrderCostInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${cstOrderCostInfo.remarks}
				</td>
				<shiro:hasPermission name="cst:detail:cstOrderCostInfo:edit"><td>
    				<a href="${ctx}/cst/detail/cstOrderCostInfo/form?id=${cstOrderCostInfo.id}">修改</a>
					<a href="${ctx}/cst/detail/cstOrderCostInfo/delete?id=${cstOrderCostInfo.id}" onclick="return confirmx('确认要删除该订单成本明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>