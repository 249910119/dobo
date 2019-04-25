<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本模型信息表管理</title>
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
		<li class="active"><a href="${ctx}/cst/model/cstModelModuleInfo/">成本模型信息表列表</a></li>
		<shiro:hasPermission name="cst:model:cstModelModuleInfo:edit"><li><a href="${ctx}/cst/model/cstModelModuleInfo/form">成本模型信息表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstModelModuleInfo" action="${ctx}/cst/model/cstModelModuleInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">成本模型类型：</label>
				<form:select path="moduleType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('module_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: auto;">成本模型名称：</label>
				<form:input path="moduleName" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>成本模型名称</th>
				<th>成本模型类型</th>
				<th>成本模型描述</th>
				<th>版本号</th>
				<th>版本时间</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:model:cstModelModuleInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstModelModuleInfo">
			<tr>
				<td>
					<a href="${ctx}/cst/model/cstModelModuleInfo/form?id=${cstModelModuleInfo.id}">
					${cstModelModuleInfo.moduleName}</a>
				</td>
				<td>
					${fns:getDictLabel(cstModelModuleInfo.moduleType, 'module_type', '')}
				</td>
				<td>
					${cstModelModuleInfo.moduleDesc}
				</td>
				<td>
					${cstModelModuleInfo.versionNo}
				</td>
				<td>
					<fmt:formatDate value="${cstModelModuleInfo.versionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(cstModelModuleInfo.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstModelModuleInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:model:cstModelModuleInfo:edit"><td>
    				<a href="${ctx}/cst/model/cstModelModuleInfo/form?id=${cstModelModuleInfo.id}">修改</a>
					<a href="${ctx}/cst/model/cstModelModuleInfo/delete?id=${cstModelModuleInfo.id}" onclick="return confirmx('确认要删除该成本模型信息表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>