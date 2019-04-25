<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-资源计划分类对应设备大类关系表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckResstatSystemRel/">巡检-资源计划分类对应设备大类关系表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckResstatSystemRel:edit"><li><a href="${ctx}/cst/check/cstCheckResstatSystemRel/form">巡检-资源计划分类对应设备大类关系表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckResstatSystemRel" action="${ctx}/cst/check/cstCheckResstatSystemRel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">资源计划分类：</label>
				<form:input path="resstattypeName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label style="width: auto;">设备大类：</label>
				<form:input path="systemName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label style="width: auto;">资源计划分类标识：</label>
				<form:input path="resstattypeId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>资源计划分类</th>
				<th>设备大类</th>
				<th>资源计划分类标识</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:check:cstCheckResstatSystemRel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckResstatSystemRel">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckResstatSystemRel/form?id=${cstCheckResstatSystemRel.id}">
					${cstCheckResstatSystemRel.resstattypeName}
				</a></td>
				<td>
					${cstCheckResstatSystemRel.systemName}
				</td>
				<td>
					${cstCheckResstatSystemRel.resstattypeId}
				</td>
				<td>
					${fns:getDictLabel(cstCheckResstatSystemRel.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckResstatSystemRel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstCheckResstatSystemRel.remarks}
				</td>
				<shiro:hasPermission name="cst:check:cstCheckResstatSystemRel:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckResstatSystemRel/form?id=${cstCheckResstatSystemRel.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckResstatSystemRel/delete?id=${cstCheckResstatSystemRel.id}" onclick="return confirmx('确认要删除该巡检-资源计划分类对应设备大类关系表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>