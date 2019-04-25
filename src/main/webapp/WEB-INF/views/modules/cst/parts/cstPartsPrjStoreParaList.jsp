<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件项目储备成本系数定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsPrjStorePara/">备件项目储备成本系数定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsPrjStorePara:edit"><li><a href="${ctx}/cst/parts/cstPartsPrjStorePara/form">备件项目储备成本系数定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsPrjStorePara" action="${ctx}/cst/parts/cstPartsPrjStorePara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">成本系数级别：</label>
				<form:select path="costScaleLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cost_scale_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>省</th>
				<th>成本系数</th>
				<th>成本系数级别</th>
				<th>说明</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsPrjStorePara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsPrjStorePara">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsPrjStorePara/form?id=${cstPartsPrjStorePara.id}">
					${cstPartsPrjStorePara.province}
				</a></td>
				<td>
					${cstPartsPrjStorePara.costScale}
				</td>
				<td>
					${fns:getDictLabel(cstPartsPrjStorePara.costScaleLevel, 'cost_scale_level', '')}
				</td>
				<td>
					${cstPartsPrjStorePara.costDesc}
				</td>
				<td>
					${fns:getDictLabel(cstPartsPrjStorePara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsPrjStorePara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsPrjStorePara:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsPrjStorePara/form?id=${cstPartsPrjStorePara.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsPrjStorePara/delete?id=${cstPartsPrjStorePara.id}" onclick="return confirmx('确认要删除该备件项目储备成本系数定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>