<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单明细信息管理</title>
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
		<li class="active"><a href="${ctx}/cst/detail/cstOrderDetailInfo/">订单明细信息列表</a></li>
		<shiro:hasPermission name="cst:detail:cstOrderDetailInfo:edit"><li><a href="${ctx}/cst/detail/cstOrderDetailInfo/form">订单明细信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstOrderDetailInfo" action="${ctx}/cst/detail/cstOrderDetailInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>order_id：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>prod_id：</label>
				<form:input path="prodId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>detail_id：</label>
				<form:input path="detailId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>order_id</th>
				<th>prod_id</th>
				<th>detail_id</th>
				<th>amount</th>
				<th>sla_id</th>
				<th>city_id</th>
				<th>status</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="cst:detail:cstOrderDetailInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstOrderDetailInfo">
			<tr>
				<td><a href="${ctx}/cst/detail/cstOrderDetailInfo/form?id=${cstOrderDetailInfo.id}">
					${cstOrderDetailInfo.orderId}
				</a></td>
				<td>
					${cstOrderDetailInfo.prodId}
				</td>
				<td>
					${cstOrderDetailInfo.detailId}
				</td>
				<td>
					${cstOrderDetailInfo.amount}
				</td>
				<td>
					${cstOrderDetailInfo.slaId}
				</td>
				<td>
					${cstOrderDetailInfo.cityId}
				</td>
				<td>
					${cstOrderDetailInfo.status}
				</td>
				<td>
					<fmt:formatDate value="${cstOrderDetailInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstOrderDetailInfo.remarks}
				</td>
				<shiro:hasPermission name="cst:detail:cstOrderDetailInfo:edit"><td>
    				<a href="${ctx}/cst/detail/cstOrderDetailInfo/form?id=${cstOrderDetailInfo.id}">修改</a>
					<a href="${ctx}/cst/detail/cstOrderDetailInfo/delete?id=${cstOrderDetailInfo.id}" onclick="return confirmx('确认要删除该订单明细信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>