<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡检-首次巡检系数表管理</title>
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
		<li class="active"><a href="${ctx}/cst/check/cstCheckFirstcheckPara/">巡检-首次巡检系数表列表</a></li>
		<shiro:hasPermission name="cst:check:cstCheckFirstcheckPara:edit"><li><a href="${ctx}/cst/check/cstCheckFirstcheckPara/form">巡检-首次巡检系数表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstCheckFirstcheckPara" action="${ctx}/cst/check/cstCheckFirstcheckPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">巡检服务级别：</label>
				<form:input path="slaName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label style="width: auto;">首次巡检系数：</label>
				<form:input path="firstCheckScale" htmlEscape="false" class="input-medium"/>
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
				<th>巡检服务级别</th>
				<th>首次巡检系数</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cst:check:cstCheckFirstcheckPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstCheckFirstcheckPara">
			<tr>
				<td><a href="${ctx}/cst/check/cstCheckFirstcheckPara/form?id=${cstCheckFirstcheckPara.id}">
					${cstCheckFirstcheckPara.slaName}
				</a></td>
				<td>
					${cstCheckFirstcheckPara.firstCheckScale}
				</td>
				<td>
					${fns:getDictLabel(cstCheckFirstcheckPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstCheckFirstcheckPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cstCheckFirstcheckPara.remarks}
				</td>
				<shiro:hasPermission name="cst:check:cstCheckFirstcheckPara:edit"><td>
    				<a href="${ctx}/cst/check/cstCheckFirstcheckPara/form?id=${cstCheckFirstcheckPara.id}">修改</a>
					<a href="${ctx}/cst/check/cstCheckFirstcheckPara/delete?id=${cstCheckFirstcheckPara.id}" onclick="return confirmx('确认要删除该巡检-首次巡检系数表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>