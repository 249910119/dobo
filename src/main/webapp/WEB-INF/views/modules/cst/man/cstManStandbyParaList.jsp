<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>非工作时间比重定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManStandbyPara/">非工作时间比重定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManStandbyPara:edit"><li><a href="${ctx}/cst/man/cstManStandbyPara/form">非工作时间比重定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManStandbyPara" action="${ctx}/cst/man/cstManStandbyPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">服务产品编号：</label>
				<form:input path="prodId" htmlEscape="false" maxlength="60" class="input-medium"/>
			</li>
			<li><label style="width: auto;">服务产品名称：</label>
				<form:input path="prodName2" htmlEscape="false" maxlength="60" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li>
				<label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>服务产品编号</th>
				<th>服务产品名称</th>
				<th>生产角色</th>
				<th>非工作时间比重</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManStandbyPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManStandbyPara">
			<tr>
				<td><a href="${ctx}/cst/man/cstManStandbyPara/form?id=${cstManStandbyPara.id}">
					${cstManStandbyPara.prodId}
				</a></td>
				<td>
					${cstManStandbyPara.prodName2}
				</td>
				<td>
					${fns:getDictLabel(cstManStandbyPara.deliveryRole, 'delivery_role', '')}
				</td>
				<td>
					${cstManStandbyPara.standbyScale}
				</td>
				<td>
					${fns:getDictLabel(cstManStandbyPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManStandbyPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManStandbyPara:edit"><td>
    				<a href="${ctx}/cst/man/cstManStandbyPara/form?id=${cstManStandbyPara.id}">修改</a>
					<a href="${ctx}/cst/man/cstManStandbyPara/delete?id=${cstManStandbyPara.id}" onclick="return confirmx('确认要删除该非工作时间比重定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>