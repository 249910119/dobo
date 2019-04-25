<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检级别配比定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckSlaPara/">巡检级别配比定义列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckSlaPara:edit"><li><a href="${ctx}/cst/check/cstCheckSlaPara/form">巡检级别配比定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckSlaPara" action="${ctx}/cst/check/cstCheckSlaPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>型号组：</label>
				<form:input path="resModelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width: auto;">型号组描述：</label>
				<form:input path="resModelDesc" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label style="width: auto;">巡检服务级别：</label>
				<form:input path="slaName" htmlEscape="false" maxlength="30" class="input-medium"/>
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
				<th>型号组</th>
				<th>型号组描述</th>
				<th>巡检服务级别</th>
				<th>一线1级配比</th>
				<th>一线2级配比</th>
				<th>一线3级配比</th>
				<th>一线4级配比</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:check:cstCheckSlaPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckSlaPara">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckSlaPara/form?id=${cstCheckSlaPara.id}">
					${cstCheckSlaPara.resModelId}
				</a></td>
				<td>
					${cstCheckSlaPara.resModelDesc}
				</td>
				<td>
					${cstCheckSlaPara.slaName}
				</td>
				<td>
					${cstCheckSlaPara.line1Level1Scale}
				</td>
				<td>
					${cstCheckSlaPara.line1Level2Scale}
				</td>
				<td>
					${cstCheckSlaPara.line1Level3Scale}
				</td>
				<td>
					${cstCheckSlaPara.line1Level4Scale}
				</td>
				<td>
					${fns:getDictLabel(cstCheckSlaPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckSlaPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:check:cstCheckSlaPara:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckSlaPara/form?id=${cstCheckSlaPara.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckSlaPara/delete?id=${cstCheckSlaPara.id}" onclick="return confirmx('确认要删除该巡检级别配比定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>