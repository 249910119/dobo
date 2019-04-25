<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>cst_parts_equip_type_rate管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsEquipTypeRate/">cst_parts_equip_type_rate列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsEquipTypeRate:edit"><li><a href="${ctx}/cst/parts/cstPartsEquipTypeRate/form">cst_parts_equip_type_rate添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsEquipTypeRate" action="${ctx}/cst/parts/cstPartsEquipTypeRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>技术方向标识：</label>
				<form:input path="equiptypeId" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>技术方向描述：</label>
				<form:input path="equiptypeDesc" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>技术方向标识</th>
				<th>技术方向描述</th>
				<th>技术方向备件人工单件成本系数</th>
				<th>技术方向备件激励单件成本系数</th>
				<th>故障成本SLA采购成本系数</th>
				<th>技术方向历史备件更换量</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsEquipTypeRate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsEquipTypeRate">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsEquipTypeRate/form?id=${cstPartsEquipTypeRate.id}">
					${cstPartsEquipTypeRate.equiptypeId}
				</a></td>
				<td>
					${cstPartsEquipTypeRate.equiptypeDesc}
				</td>
				<td>
					${cstPartsEquipTypeRate.equipManRate}
				</td>
				<td>
					${cstPartsEquipTypeRate.equipUrgeRate}
				</td>
				<td>
					${cstPartsEquipTypeRate.slaCostRate}
				</td>
				<td>
					${cstPartsEquipTypeRate.hisPartsAmount}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsEquipTypeRate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsEquipTypeRate:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsEquipTypeRate/form?id=${cstPartsEquipTypeRate.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsEquipTypeRate/delete?id=${cstPartsEquipTypeRate.id}" onclick="return confirmx('确认要删除该cst_parts_equip_type_rate吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>