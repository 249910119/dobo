<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件发货运输平均成本定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsTransportCost/">备件发货运输平均成本定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsTransportCost:edit"><li><a href="${ctx}/cst/parts/cstPartsTransportCost/form">备件发货运输平均成本定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsTransportCost" action="${ctx}/cst/parts/cstPartsTransportCost/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>服务级别：</label>
				<form:select path="slaLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SLA_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发货类型：</label>
				<form:select path="deliveryType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DELIVERY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>城市：</label>
				<form:input path="city" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label style="width: auto;">发货运输级别：</label>
				<form:select path="transportType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('TRANSPORT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>城市级别：</label>
				<form:select path="cityLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('CITY_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>服务级别</th>
				<th>发货类型</th>
				<th>本地备件满足率</th>
				<th>城市</th>
				<th>省</th>
				<th>发货运输级别</th>
				<th>城市级别</th>
				<th>跨省专送运输平均成本</th>
				<th>本市专送运输平均成本</th>
				<th>第三方物流发货运输平均成本</th>
				<th>第三方物流回收取件运输平均成本</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsTransportCost:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsTransportCost">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsTransportCost/form?id=${cstPartsTransportCost.id}">
					${cstPartsTransportCost.slaLevel}
				</a></td>
				<td>
					${fns:getDictLabel(cstPartsTransportCost.deliveryType, 'DELIVERY_TYPE', '')}
				</td>
				<td>
					${cstPartsTransportCost.localFillRate}
				</td>
				<td>
					${cstPartsTransportCost.city}
				</td>
				<td>
					${cstPartsTransportCost.province}
				</td>
				<td>
					${fns:getDictLabel(cstPartsTransportCost.transportType, 'TRANSPORT_TYPE', '')}
				</td>
				<td>
					${fns:getDictLabel(cstPartsTransportCost.cityLevel, 'CITY_LEVEL', '')}
				</td>
				<td>
					${cstPartsTransportCost.transProvinceCost}
				</td>
				<td>
					${cstPartsTransportCost.localCityCost}
				</td>
				<td>
					${cstPartsTransportCost.thirdDeliveryCost}
				</td>
				<td>
					${cstPartsTransportCost.thirdPickCost}
				</td>
				<td>
					${fns:getDictLabel(cstPartsTransportCost.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsTransportCost.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsTransportCost:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsTransportCost/form?id=${cstPartsTransportCost.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsTransportCost/delete?id=${cstPartsTransportCost.id}" onclick="return confirmx('确认要删除该备件发货运输平均成本定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>