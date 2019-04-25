<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcProjectInfo/">项目信息列表</a></li>
		<shiro:hasPermission name="fc:fcProjectInfo:edit"><li><a href="${ctx}/fc/fcProjectInfo/form">项目信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcProjectInfo" action="${ctx}/fc/fcProjectInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目编号：</label>
				<form:input path="projectCode" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>项目编号</th>
				<th>项目名称</th>
				<th>客户名称</th>
				<th>产品线大类</th>
				<th>产品线小类</th>
				<th>事业部</th>
				<th>业务范围代码</th>
				<th>销售员</th>
				<th>是否有WBM订单</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcProjectInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcProjectInfo">
			<tr>
				<td><a href="${ctx}/fc/fcProjectInfo/form?id=${fcProjectInfo.id}">
					${fcProjectInfo.projectCode}
				</a></td>
				<td>
					${fcProjectInfo.projectName}
				</td>
				<td>
					${fcProjectInfo.custName}
				</td>
				<td>
					${fcProjectInfo.fstSvcType}
				</td>
				<td>
					${fcProjectInfo.sndSvcType}
				</td>
				<td>
					${fcProjectInfo.saleOrg}
				</td>
				<td>
					${fcProjectInfo.businessCode}
				</td>
				<td>
					${fcProjectInfo.salesName}
				</td>
				<td>
					${fns:getDictLabel(fcProjectInfo.hasWbmOrder, 'has_wbm_order', '')}
				</td>
				<td>
					${fns:getDictLabel(fcProjectInfo.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${fcProjectInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcProjectInfo:edit"><td>
    				<a href="${ctx}/fc/fcProjectInfo/form?id=${fcProjectInfo.id}">修改</a>
					<a href="${ctx}/fc/fcProjectInfo/delete?id=${fcProjectInfo.id}" onclick="return confirmx('确认要删除该项目信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>