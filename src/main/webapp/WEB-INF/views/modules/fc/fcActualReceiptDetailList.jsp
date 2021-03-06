<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目实际到款明细管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcActualReceiptDetail/">项目实际到款明细列表</a></li>
		<shiro:hasPermission name="fc:fcActualReceiptDetail:edit"><li><a href="${ctx}/fc/fcActualReceiptDetail/form">项目实际到款明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcActualReceiptDetail" action="${ctx}/fc/fcActualReceiptDetail/" method="post" class="breadcrumb form-search">
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
				<th>期次</th>
				<th>计划收款时间</th>
				<th>计划收款金额</th>
				<th>支付类型</th>
				<th>支付币种</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcActualReceiptDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcActualReceiptDetail">
			<tr>
				<td><a href="${ctx}/fc/fcActualReceiptDetail/form?id=${fcActualReceiptDetail.id}">
					${fcActualReceiptDetail.fcProjectInfo.projectCode}
				</a></td>
				<td>
					${fcActualReceiptDetail.displayOrder}
				</td>
				<td>
					<fmt:formatDate value="${fcActualReceiptDetail.actualReceiptTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fcActualReceiptDetail.actualReceiptAmount}
				</td>
				<td>
					${fns:getDictLabel(fcActualReceiptDetail.payType, 'pay_type', '')}
				</td>
				<td>
					${fns:getDictLabel(fcActualReceiptDetail.payCurrency, 'pay_currency', '')}
				</td>
				<td>
					${fns:getDictLabel(fcActualReceiptDetail.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${fcActualReceiptDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcActualReceiptDetail:edit"><td>
    				<a href="${ctx}/fc/fcActualReceiptDetail/form?id=${fcActualReceiptDetail.id}">修改</a>
					<a href="${ctx}/fc/fcActualReceiptDetail/delete?id=${fcActualReceiptDetail.id}" onclick="return confirmx('确认要删除该项目实际到款明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>