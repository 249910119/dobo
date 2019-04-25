<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-单台路程工时表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckDistanceUnitHour/">巡检-单台路程工时表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckDistanceUnitHour:edit"><li><a href="${ctx}/cst/check/cstCheckDistanceUnitHour/form">巡检-单台路程工时表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckDistanceUnitHour" action="${ctx}/cst/check/cstCheckDistanceUnitHour/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">巡检服务级别：</label>
				<form:input path="slaName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>设备大类：</label>
				<form:input path="systemName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label style="width: auto;">设备大类标识：</label>
				<form:input path="systemId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>巡检服务级别</th>
				<th>设备大类</th>
				<th>单台路程工时</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:check:cstCheckDistanceUnitHour:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckDistanceUnitHour">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckDistanceUnitHour/form?id=${cstCheckDistanceUnitHour.id}">
					${cstCheckDistanceUnitHour.slaName}
				</a></td>
				<td>
					${cstCheckDistanceUnitHour.systemName}
				</td>
				<td>
					${cstCheckDistanceUnitHour.unitDistanceHour}
				</td>
				<td>
					${fns:getDictLabel(cstCheckDistanceUnitHour.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckDistanceUnitHour.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstCheckDistanceUnitHour.remarks}
				</td>
				<shiro:hasPermission name="cst:check:cstCheckDistanceUnitHour:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckDistanceUnitHour/form?id=${cstCheckDistanceUnitHour.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckDistanceUnitHour/delete?id=${cstCheckDistanceUnitHour.id}" onclick="return confirmx('确认要删除该巡检-单台路程工时表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>