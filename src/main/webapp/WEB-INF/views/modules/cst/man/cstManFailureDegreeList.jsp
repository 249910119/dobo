<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障饱和度定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManFailureDegree/">故障饱和度定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManFailureDegree:edit"><li><a href="${ctx}/cst/man/cstManFailureDegree/form">故障饱和度定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManFailureDegree" action="${ctx}/cst/man/cstManFailureDegree/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>服务级别：</label>
				<form:input path="slaName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li> --%>
			<li><label style="width: auto;">服务级别：</label>
				<form:select path="slaId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${slaList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>服务级别</th>
				<th>一线饱和度</th>
				<th>二线饱和度</th>
				<th>三线饱和度</th>
				<th>CMO饱和度</th>
				<th>资源岗饱和度</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManFailureDegree:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManFailureDegree">
			<tr>
				<td><a href="${ctx}/cst/man/cstManFailureDegree/form?id=${cstManFailureDegree.id}">
					${cstManFailureDegree.slaName}
				</a></td>
				<td>
					${cstManFailureDegree.line1Degree}
				</td>
				<td>
					${cstManFailureDegree.line2Degree}
				</td>
				<td>
					${cstManFailureDegree.line3Degree}
				</td>
				<td>
					${cstManFailureDegree.cmoDegree}
				</td>
				<td>
					${cstManFailureDegree.resMgrDegree}
				</td>
				<td>
					${fns:getDictLabel(cstManFailureDegree.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManFailureDegree.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManFailureDegree:edit"><td>
    				<a href="${ctx}/cst/man/cstManFailureDegree/form?id=${cstManFailureDegree.id}">修改</a>
					<a href="${ctx}/cst/man/cstManFailureDegree/delete?id=${cstManFailureDegree.id}" onclick="return confirmx('确认要删除该故障饱和度定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>