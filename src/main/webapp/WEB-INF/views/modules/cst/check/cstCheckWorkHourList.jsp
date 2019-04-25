<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检工时定义表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckWorkHour/">巡检工时定义表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckWorkHour:edit"><li><a href="${ctx}/cst/check/cstCheckWorkHour/form">巡检工时定义表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckWorkHour" action="${ctx}/cst/check/cstCheckWorkHour/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>型号组标识：</label>
				<form:input path="resModelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>型号组描述：</label>
				<form:input path="resModelDesc" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>型号组标识</th>
				<th>型号组描述</th>
				<th>单台设备远程巡检一线工时</th>
				<th>单台设备现场巡检一线工时</th>
				<th>单台设备深度巡检一线工时</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:check:cstCheckWorkHour:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckWorkHour">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckWorkHour/form?id=${cstCheckWorkHour.id}">
					${cstCheckWorkHour.resModelId}
				</a></td>
				<td>
					${cstCheckWorkHour.resModelDesc}
				</td>
				<td>
					${cstCheckWorkHour.line1RemoteHour}
				</td>
				<td>
					${cstCheckWorkHour.line1LocalHour}
				</td>
				<td>
					${cstCheckWorkHour.line1DepthHour}
				</td>
				<td>
					${fns:getDictLabel(cstCheckWorkHour.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckWorkHour.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:check:cstCheckWorkHour:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckWorkHour/form?id=${cstCheckWorkHour.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckWorkHour/delete?id=${cstCheckWorkHour.id}" onclick="return confirmx('确认要删除该巡检工时定义表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>