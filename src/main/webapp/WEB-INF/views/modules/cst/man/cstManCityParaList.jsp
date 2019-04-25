<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>地域系数定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManCityPara/">地域系数定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManCityPara:edit"><li><a href="${ctx}/cst/man/cstManCityPara/form">地域系数定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManCityPara" action="${ctx}/cst/man/cstManCityPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>城市名称：</label>
				<sys:treeselect id="city" name="cityId" value="${cstManCityPara.cityId}" labelName="cityName" labelValue="${cstManCityPara.cityName}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label style="width: auto;">省会城市名称：</label>
				<sys:treeselect id="capitalCity" name="capitalCityId" value="${cstManCityPara.capitalCityId}" labelName="capitalCityName" labelValue="${cstManCityPara.capitalCityName}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>城市名称</th>
				<th>省会城市名称</th>
				<th>市内路途工时</th>
				<th>差旅工时</th>
				<th>差旅费</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManCityPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManCityPara">
			<tr>
				<td><a href="${ctx}/cst/man/cstManCityPara/form?id=${cstManCityPara.id}">
					${cstManCityPara.cityName}
				</a></td>
				<td>
					${cstManCityPara.capitalCityName}
				</td>
				<td>
					${cstManCityPara.cityHour}
				</td>
				<td>
					${cstManCityPara.travelHour}
				</td>
				<td>
					${cstManCityPara.travelFee}
				</td>
				<td>
					${fns:getDictLabel(cstManCityPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManCityPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManCityPara:edit"><td>
    				<a href="${ctx}/cst/man/cstManCityPara/form?id=${cstManCityPara.id}">修改</a>
					<a href="${ctx}/cst/man/cstManCityPara/delete?id=${cstManCityPara.id}" onclick="return confirmx('确认要删除该地域系数定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>