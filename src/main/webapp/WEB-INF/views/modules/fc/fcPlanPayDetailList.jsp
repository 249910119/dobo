<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目计划付款明细管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcPlanPayDetail/">项目计划付款明细列表</a></li>
		<shiro:hasPermission name="fc:fcPlanPayDetail:edit"><li><a href="${ctx}/fc/fcPlanPayDetail/form">项目计划付款明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcPlanPayDetail" action="${ctx}/fc/fcPlanPayDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><%@include file="projectSelectInclude.jsp"%></li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目信息</th>
				<th>期次</th>
				<th>计划收款时间</th>
				<th>计划收款金额</th>
				<th>计划收款比例</th>
				<th>支付类型</th>
				<th>支付币种</th>
				<th>收款时间间隔天数</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcPlanPayDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcPlanPayDetail">
			<tr>
				<td><a href="${ctx}/fc/fcPlanPayDetail/form?id=${fcPlanPayDetail.id}">
					${fcPlanPayDetail.fcProjectInfo.projectCode}
				</a></td>
				<td>
					${fcPlanPayDetail.displayOrder}
				</td>
				<td>
					<fmt:formatDate value="${fcPlanPayDetail.planPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fcPlanPayDetail.planPayAmount}
				</td>
				<td>
					${fcPlanPayDetail.planPayScale}
				</td>
				<td>
					${fns:getDictLabel(fcPlanPayDetail.payType, 'pay_type', '')}
				</td>
				<td>
					${fns:getDictLabel(fcPlanPayDetail.payCurrency, 'pay_currency', '')}
				</td>
				<td>
					${fcPlanPayDetail.planPayDays}
				</td>
				<td>
					${fcPlanPayDetail.status}
				</td>
				<td>
					<fmt:formatDate value="${fcPlanPayDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcPlanPayDetail:edit"><td>
    				<a href="${ctx}/fc/fcPlanPayDetail/form?id=${fcPlanPayDetail.id}">修改</a>
					<a href="${ctx}/fc/fcPlanPayDetail/delete?id=${fcPlanPayDetail.id}" onclick="return confirmx('确认要删除该项目计划付款明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>