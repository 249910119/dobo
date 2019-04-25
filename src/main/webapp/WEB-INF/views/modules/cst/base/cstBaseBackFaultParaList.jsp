<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件故障率参数获取管理</title>
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
		<li class="active"><a href="${ctx}/cst/base/cstBaseBackFaultPara/">备件故障率参数获取列表</a></li>
		<shiro:hasPermission name="cst:base:cstBaseBackFaultPara:edit"><li><a href="${ctx}/cst/base/cstBaseBackFaultPara/form">备件故障率参数获取添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstBaseBackFaultPara" action="${ctx}/cst/base/cstBaseBackFaultPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:base:cstBaseBackFaultPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstBaseBackFaultPara">
			<tr>
				<td><a href="${ctx}/cst/base/cstBaseBackFaultPara/form?id=${cstBaseBackFaultPara.id}">
					<fmt:formatDate value="${cstBaseBackFaultPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${cstBaseBackFaultPara.remarks}
				</td>
				<shiro:hasPermission name="cst:base:cstBaseBackFaultPara:edit"><td>
    				<a href="${ctx}/cst/base/cstBaseBackFaultPara/form?id=${cstBaseBackFaultPara.id}">修改</a>
					<a href="${ctx}/cst/base/cstBaseBackFaultPara/delete?id=${cstBaseBackFaultPara.id}" onclick="return confirmx('确认要删除该备件故障率参数获取吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>