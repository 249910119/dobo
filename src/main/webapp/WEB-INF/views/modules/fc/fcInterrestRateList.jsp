<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利率定义管理</title>
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
		<li class="active"><a href="${ctx}/fc/fcInterrestRate/">利率定义列表</a></li>
		<shiro:hasPermission name="fc:fcInterrestRate:edit"><li><a href="${ctx}/fc/fcInterrestRate/form">利率定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fcInterrestRate" action="${ctx}/fc/fcInterrestRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>利息名称：</label>
				<form:input path="rateName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>发布时间：</label>
				<input name="ratePublishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${fcInterrestRate.ratePublishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>利息类型</th>
				<th>利息名称</th>
				<th>利率值</th>
				<th>发布时间</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="fc:fcInterrestRate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fcInterrestRate">
			<tr>
				<td><a href="${ctx}/fc/fcInterrestRate/form?id=${fcInterrestRate.id}">
					${fcInterrestRate.rateType}
				</a></td>
				<td>
					${fcInterrestRate.rateName}
				</td>
				<td>
					${fcInterrestRate.rateValue}
				</td>
				<td>
					<fmt:formatDate value="${fcInterrestRate.ratePublishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(fcInterrestRate.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${fcInterrestRate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fc:fcInterrestRate:edit"><td>
    				<a href="${ctx}/fc/fcInterrestRate/form?id=${fcInterrestRate.id}">修改</a>
					<a href="${ctx}/fc/fcInterrestRate/delete?id=${fcInterrestRate.id}" onclick="return confirmx('确认要删除该利率定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>