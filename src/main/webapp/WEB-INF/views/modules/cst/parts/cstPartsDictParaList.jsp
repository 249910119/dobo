<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件参数字典定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsDictPara/">备件参数字典定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsDictPara:edit"><li><a href="${ctx}/cst/parts/cstPartsDictPara/form">备件参数字典定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsDictPara" action="${ctx}/cst/parts/cstPartsDictPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>参数类型：</label>
				<form:select path="paraType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('para_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>参数名称：</label>
				<form:input path="paraName" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>参数类型</th>
				<th>参数标识</th>
				<th>参数名称</th>
				<th>参数值</th>
				<th>对应成本项</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:parts:cstPartsDictPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsDictPara">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsDictPara/form?id=${cstPartsDictPara.id}">
					${fns:getDictLabel(cstPartsDictPara.paraType, 'para_type', '')}
				</a></td>
				<td>
					${cstPartsDictPara.paraId}
				</td>
				<td>
					${cstPartsDictPara.paraName}
				</td>
				<td>
					${cstPartsDictPara.paraValue}
				</td>
				<td>
					${cstPartsDictPara.referCostItem}
				</td>
				<td>
					${fns:getDictLabel(cstPartsDictPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsDictPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstPartsDictPara.remarks}
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsDictPara:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsDictPara/form?id=${cstPartsDictPara.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsDictPara/delete?id=${cstPartsDictPara.id}" onclick="return confirmx('确认要删除该备件参数字典定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>