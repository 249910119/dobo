<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单信息管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcOrderInfo/">订单信息列表</a></li>
		<shiro:hasPermission name="fc:fcOrderInfo:edit"><li><a href="${ctx}/fc/fcOrderInfo/form">订单信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcOrderInfo" action="${ctx}/fc/fcOrderInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><%@include file="projectSelectInclude.jsp"%></li>
			<li><label>订单编号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="30" class="input-medium"/>
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
				<th>项目信息</th>
				<th>订单编号</th>
				<th>产品线小类</th>
				<th>服务期开始</th>
				<th>服务期结束</th>
				<th>签约金额</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcOrderInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcOrderInfo">
			<tr>
				<td><a href="${ctx}/fc/fcOrderInfo/form?id=${fcOrderInfo.id}">
					${fcOrderInfo.fcProjectInfo.projectCode}
				</a></td>
				<td>
					${fcOrderInfo.orderId}
				</td>
				<td>
					${fcOrderInfo.sndSvcType}
				</td>
				<td>
					<fmt:formatDate value="${fcOrderInfo.serviceDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${fcOrderInfo.serviceDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fcOrderInfo.signAmount}
				</td>
				<td>
					${fns:getDictLabel(fcOrderInfo.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${fcOrderInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcOrderInfo:edit"><td>
    				<a href="${ctx}/fc/fcOrderInfo/form?id=${fcOrderInfo.id}">修改</a>
					<a href="${ctx}/fc/fcOrderInfo/delete?id=${fcOrderInfo.id}" onclick="return confirmx('确认要删除该订单信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>