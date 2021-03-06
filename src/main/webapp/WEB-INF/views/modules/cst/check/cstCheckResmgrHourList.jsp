<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-资源岗巡检安排工时表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckResmgrHour/">巡检-资源岗巡检安排工时表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckResmgrHour:edit"><li><a href="${ctx}/cst/check/cstCheckResmgrHour/form">巡检-资源岗巡检安排工时表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckResmgrHour" action="${ctx}/cst/check/cstCheckResmgrHour/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">PMO单次巡检安排工时：</label>
				<form:input path="pmoCheckHour" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label style="width: auto;">资源岗单次巡检安排工时：</label>
				<form:input path="resMgrCheckHour" htmlEscape="false" class="input-medium"/>
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
				<th>PMO单次巡检安排工时</th>
				<th>资源岗单次巡检安排工时</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:check:cstCheckResmgrHour:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckResmgrHour">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckResmgrHour/form?id=${cstCheckResmgrHour.id}">
					${cstCheckResmgrHour.pmoCheckHour}
				</a></td>
				<td>
					${cstCheckResmgrHour.resMgrCheckHour}
				</td>
				<td>
					${fns:getDictLabel(cstCheckResmgrHour.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckResmgrHour.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstCheckResmgrHour.remarks}
				</td>
				<shiro:hasPermission name="cst:check:cstCheckResmgrHour:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckResmgrHour/form?id=${cstCheckResmgrHour.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckResmgrHour/delete?id=${cstCheckResmgrHour.id}" onclick="return confirmx('确认要删除该巡检-资源岗巡检安排工时表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>