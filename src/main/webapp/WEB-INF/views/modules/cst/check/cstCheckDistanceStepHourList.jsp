<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-路程工时阶梯系数表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckDistanceStepHour/">巡检-路程工时阶梯系数表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckDistanceStepHour:edit"><li><a href="${ctx}/cst/check/cstCheckDistanceStepHour/form">巡检-路程工时阶梯系数表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckDistanceStepHour" action="${ctx}/cst/check/cstCheckDistanceStepHour/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">同城同设备大类下设备数量上限：</label>
				<form:input path="systemResnumMax" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label style="width: auto;">同城同设备大类下设备数量下限：</label>
				<form:input path="systemResnumMin" htmlEscape="false" maxlength="10" class="input-medium"/>
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
				<th>同城同设备大类下设备数量下限</th>
				<th>同城同设备大类下设备数量上限</th>
				<th>设备大类下设备类型种类数</th>
				<th>路程工时系数</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:check:cstCheckDistanceStepHour:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckDistanceStepHour">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckDistanceStepHour/form?id=${cstCheckDistanceStepHour.id}">
					${cstCheckDistanceStepHour.systemResnumMin}
				</a></td>
				<td>
					${cstCheckDistanceStepHour.systemResnumMax}
				</td>
				<td>
					${cstCheckDistanceStepHour.typeResnum}
				</td>
				<td>
					${cstCheckDistanceStepHour.distanceHour}
				</td>
				<td>
					${fns:getDictLabel(cstCheckDistanceStepHour.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckDistanceStepHour.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:check:cstCheckDistanceStepHour:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckDistanceStepHour/form?id=${cstCheckDistanceStepHour.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckDistanceStepHour/delete?id=${cstCheckDistanceStepHour.id}" onclick="return confirmx('确认要删除该巡检-路程工时阶梯系数表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>