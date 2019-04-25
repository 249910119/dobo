<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>包名归属字典---主要用于维护主数据参考用管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsPackInfo/">包名归属字典---主要用于维护主数据参考用列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsPackInfo:edit"><li><a href="${ctx}/cst/parts/cstPartsPackInfo/form">包名归属字典---主要用于维护主数据参考用添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsPackInfo" action="${ctx}/cst/parts/cstPartsPackInfo/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="cst:parts:cstPartsPackInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsPackInfo">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsPackInfo/form?id=${cstPartsPackInfo.id}">
					<fmt:formatDate value="${cstPartsPackInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${cstPartsPackInfo.remarks}
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsPackInfo:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsPackInfo/form?id=${cstPartsPackInfo.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsPackInfo/delete?id=${cstPartsPackInfo.id}" onclick="return confirmx('确认要删除该包名归属字典---主要用于维护主数据参考用吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>