<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>无需计算计划内财务费用项目管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcProjectInfoNocalc/">无需计算计划内财务费用项目列表</a></li>
		<shiro:hasPermission name="fc:fcProjectInfoNocalc:edit"><li><a href="${ctx}/fc/fcProjectInfoNocalc/form">无需计算计划内财务费用项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcProjectInfoNocalc" action="${ctx}/fc/fcProjectInfoNocalc/" method="post" class="breadcrumb form-search">
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
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="fc:fcProjectInfoNocalc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcProjectInfoNocalc">
			<tr>
				<td><a href="${ctx}/fc/fcProjectInfoNocalc/form?id=${fcProjectInfoNocalc.id}">
					<fmt:formatDate value="${fcProjectInfoNocalc.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${fcProjectInfoNocalc.remarks}
				</td>
				<shiro:hasPermission name="fc:fcProjectInfoNocalc:edit"><td>
    				<a href="${ctx}/fc/fcProjectInfoNocalc/form?id=${fcProjectInfoNocalc.id}">修改</a>
					<a href="${ctx}/fc/fcProjectInfoNocalc/delete?id=${fcProjectInfoNocalc.id}" onclick="return confirmx('确认要删除该无需计算计划内财务费用项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>