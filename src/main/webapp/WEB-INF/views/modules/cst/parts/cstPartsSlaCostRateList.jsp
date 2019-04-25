<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障成本SLA采购成本系数管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsSlaCostRate/">故障成本SLA采购成本系数列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsSlaCostRate:edit"><li><a href="${ctx}/cst/parts/cstPartsSlaCostRate/form">故障成本SLA采购成本系数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsSlaCostRate" action="${ctx}/cst/parts/cstPartsSlaCostRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>服务级别：</label>
				<form:input path="slaName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>服务级别id：</label>
				<form:input path="slaId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>服务级别</th>
				<th>服务级别id</th>
				<th>故障成本SLA采购成本系数</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsSlaCostRate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsSlaCostRate">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsSlaCostRate/form?id=${cstPartsSlaCostRate.id}">
					${cstPartsSlaCostRate.slaName}
				</a></td>
				<td>
					${cstPartsSlaCostRate.slaId}
				</td>
				<td>
					${cstPartsSlaCostRate.slaCostRate}
				</td>
				<td>
					${fns:getDictLabel(cstPartsSlaCostRate.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsSlaCostRate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsSlaCostRate:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsSlaCostRate/form?id=${cstPartsSlaCostRate.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsSlaCostRate/delete?id=${cstPartsSlaCostRate.id}" onclick="return confirmx('确认要删除该故障成本SLA采购成本系数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>