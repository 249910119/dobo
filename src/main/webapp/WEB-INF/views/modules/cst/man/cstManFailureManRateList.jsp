<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障人工费率定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManFailureManRate/">故障人工费率定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManFailureManRate:edit"><li><a href="${ctx}/cst/man/cstManFailureManRate/form">故障人工费率定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManFailureManRate" action="${ctx}/cst/man/cstManFailureManRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>生产角色：</label>
				<form:select path="deliveryRole" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_role')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否驻场：</label>
				<form:select path="isResident" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li>
				<label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生产角色</th>
				<th>是否驻场</th>
				<th>人工年包</th>
				<th>费用年包</th>
				<th>激励年包</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManFailureManRate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManFailureManRate">
			<tr>
				<td><a href="${ctx}/cst/man/cstManFailureManRate/form?id=${cstManFailureManRate.id}">
					<%-- ${cstManFailureManRate.deliveryRole} --%>
					${fns:getDictLabel(cstManFailureManRate.deliveryRole, 'delivery_role', '')}
				</a></td>
				<td>
					${fns:getDictLabel(cstManFailureManRate.isResident, 'is_resident', '')}
				</td>
				<td>
					${cstManFailureManRate.manCostYear}
				</td>
				<td>
					${cstManFailureManRate.feeCostYear}
				</td>
				<td>
					${cstManFailureManRate.urgeCostYear}
				</td>
				<td>
					${fns:getDictLabel(cstManFailureManRate.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManFailureManRate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManFailureManRate:edit"><td>
    				<a href="${ctx}/cst/man/cstManFailureManRate/form?id=${cstManFailureManRate.id}">修改</a>
					<a href="${ctx}/cst/man/cstManFailureManRate/delete?id=${cstManFailureManRate.id}" onclick="return confirmx('确认要删除该故障人工费率定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>