<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单清单成本明细管理</title>
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
		<li class="active"><a href="${ctx}/cst/detail/cstDetailCostInfo/">订单清单成本明细列表</a></li>
		<shiro:hasPermission name="cst:detail:cstDetailCostInfo:edit"><li><a href="${ctx}/cst/detail/cstDetailCostInfo/form">订单清单成本明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstDetailCostInfo" action="${ctx}/cst/detail/cstDetailCostInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>detail_id：</label>
				<form:input path="detailId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>key_id：</label>
				<form:input path="keyId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>prod_id：</label>
				<form:input path="prodId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>detail_id</th>
				<th>key_id</th>
				<th>res_plan</th>
				<th>man_cost</th>
				<th>fee_cost</th>
				<th>urge_cost</th>
				<th>travl_fee</th>
				<th>status</th>
				<th>update_date</th>
				<th>remarks</th>
				<th>prod_id</th>
				<shiro:hasPermission name="cst:detail:cstDetailCostInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstDetailCostInfo">
			<tr>
				<td><a href="${ctx}/cst/detail/cstDetailCostInfo/form?id=${cstDetailCostInfo.id}">
					${cstDetailCostInfo.detailId}
				</a></td>
				<td>
					${cstDetailCostInfo.keyId}
				</td>
				<td>
					${cstDetailCostInfo.resPlan}
				</td>
				<td>
					${cstDetailCostInfo.manCost}
				</td>
				<td>
					${cstDetailCostInfo.feeCost}
				</td>
				<td>
					${cstDetailCostInfo.urgeCost}
				</td>
				<td>
					${cstDetailCostInfo.travlFee}
				</td>
				<td>
					${cstDetailCostInfo.status}
				</td>
				<td>
					<fmt:formatDate value="${cstDetailCostInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstDetailCostInfo.remarks}
				</td>
				<td>
					${cstDetailCostInfo.prodId}
				</td>
				<shiro:hasPermission name="cst:detail:cstDetailCostInfo:edit"><td>
    				<a href="${ctx}/cst/detail/cstDetailCostInfo/form?id=${cstDetailCostInfo.id}">修改</a>
					<a href="${ctx}/cst/detail/cstDetailCostInfo/delete?id=${cstDetailCostInfo.id}" onclick="return confirmx('确认要删除该订单清单成本明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>