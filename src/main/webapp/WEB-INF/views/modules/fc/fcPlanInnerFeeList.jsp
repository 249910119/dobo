<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目计划内财务费用管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcPlanInnerFee/">项目计划内财务费用列表</a></li>
		<shiro:hasPermission name="fc:fcPlanInnerFee:edit"><li><a href="${ctx}/fc/fcPlanInnerFee/form">项目计划内财务费用添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="fc:fcPlanInnerFee:edit"><li><a href="${ctx}/fc/fcPlanInnerFee/count">项目计划内财务费用计算</a></li></shiro:hasPermission>
		
	</ul>
	<form:form id="searchForm" modelAttribute="fcPlanInnerFee" action="${ctx}/fc/fcPlanInnerFee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><%@include file="projectSelectInclude.jsp"%></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目信息</th>
				<th>财务费用</th>
				<th>计算时间</th>
				<th>贷息利率</th>
				<th>存息利率</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcPlanInnerFee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcPlanInnerFee">
			<tr>
				<td><a href="${ctx}/fc/fcPlanInnerFee/form?id=${fcPlanInnerFee.id}">
					${fcPlanInnerFee.fcProjectInfo.projectCode}
				</a></td>
				<td>
					${fcPlanInnerFee.financialCost}
				</td>
				<td>
					<fmt:formatDate value="${fcPlanInnerFee.calculateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fcPlanInnerFee.loanRate}
				</td>
				<td>
					${fcPlanInnerFee.depositRate}
				</td>
				<td>
					${fns:getDictLabel(fcPlanInnerFee.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${fcPlanInnerFee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcPlanInnerFee:edit"><td>
    				<a href="${ctx}/fc/fcPlanInnerFee/form?id=${fcPlanInnerFee.id}">修改</a>
					<a href="${ctx}/fc/fcPlanInnerFee/delete?id=${fcPlanInnerFee.id}" onclick="return confirmx('确认要删除该项目计划内财务费用吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>