<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售员账户使用明细表管理</title>
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
		<li class="active"><a href="${ctx}/cop/detail/copSalesUseDetail/">销售员账户使用明细表列表</a></li>
		<shiro:hasPermission name="cop:detail:copSalesUseDetail:edit"><li><a href="${ctx}/cop/detail/copSalesUseDetail/form">销售员账户使用明细表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="copSalesUseDetail" action="${ctx}/cop/detail/copSalesUseDetail/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="cop:detail:copSalesUseDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="copSalesUseDetail">
			<tr>
				<td><a href="${ctx}/cop/detail/copSalesUseDetail/form?id=${copSalesUseDetail.id}">
					<fmt:formatDate value="${copSalesUseDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${copSalesUseDetail.remarks}
				</td>
				<shiro:hasPermission name="cop:detail:copSalesUseDetail:edit"><td>
    				<a href="${ctx}/cop/detail/copSalesUseDetail/form?id=${copSalesUseDetail.id}">修改</a>
					<a href="${ctx}/cop/detail/copSalesUseDetail/delete?id=${copSalesUseDetail.id}" onclick="return confirmx('确认要删除该销售员账户使用明细表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>