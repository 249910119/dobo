<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理配比及饱和度表管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManManageDegreePara/">项目管理配比及饱和度表列表</a></li>
		<shiro:hasPermission name="cst:man:cstManManageDegreePara:edit"><li><a href="${ctx}/cst/man/cstManManageDegreePara/form">项目管理配比及饱和度表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManManageDegreePara" action="${ctx}/cst/man/cstManManageDegreePara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">服务产品编码：</label>
				<form:input path="prodId" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>服务产品：</label>
				<form:input path="prodName" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>服务产品编码</th>
				<th>服务产品</th>
				<th>服务级别标识</th>
				<th>服务级别</th>
				<th>PM3级配比</th>
				<th>PM4级配比</th>
				<th>PM5级配比</th>
				<th>PM饱和度</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManManageDegreePara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManManageDegreePara">
			<tr>
				<td><a href="${ctx}/cst/man/cstManManageDegreePara/form?id=${cstManManageDegreePara.id}">
					${cstManManageDegreePara.prodId}
				</a></td>
				<td>
					${cstManManageDegreePara.prodName}
				</td>
				<td>
					${cstManManageDegreePara.serviceLevel}
				</td>
				<td>
					${cstManManageDegreePara.serviceLevelName}
				</td>
				<td>
					${cstManManageDegreePara.pmLevel3Scale}
				</td>
				<td>
					${cstManManageDegreePara.pmLevel4Scale}
				</td>
				<td>
					${cstManManageDegreePara.pmLevel5Scale}
				</td>
				<td>
					${cstManManageDegreePara.pmDegree}
				</td>
				<td>
					${fns:getDictLabel(cstManManageDegreePara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManManageDegreePara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManManageDegreePara:edit"><td>
    				<a href="${ctx}/cst/man/cstManManageDegreePara/form?id=${cstManManageDegreePara.id}">修改</a>
					<a href="${ctx}/cst/man/cstManManageDegreePara/delete?id=${cstManManageDegreePara.id}" onclick="return confirmx('确认要删除该项目管理配比及饱和度表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>