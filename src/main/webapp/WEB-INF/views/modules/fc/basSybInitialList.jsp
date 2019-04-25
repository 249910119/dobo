<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>事业部初始现金流管理</title>
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
		<li class="active"><a href="${ctx}/fc/basSybInitial/">事业部初始现金流列表</a></li>
		<shiro:hasPermission name="fc:basSybInitial:edit"><li><a href="${ctx}/fc/basSybInitial/form">事业部初始现金流添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="basSybInitial" action="${ctx}/fc/basSybInitial/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="fc:basSybInitial:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="basSybInitial">
			<tr>
				<shiro:hasPermission name="fc:basSybInitial:edit"><td>
    				<a href="${ctx}/fc/basSybInitial/form?id=${basSybInitial.id}">修改</a>
					<a href="${ctx}/fc/basSybInitial/delete?id=${basSybInitial.id}" onclick="return confirmx('确认要删除该事业部初始现金流吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>