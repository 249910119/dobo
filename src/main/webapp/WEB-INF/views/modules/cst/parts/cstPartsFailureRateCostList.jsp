<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件故障率与采购成本定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsFailureRateCost/">备件故障率与采购成本定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsFailureRateCost:edit"><li><a href="${ctx}/cst/parts/cstPartsFailureRateCost/form">备件故障率与采购成本定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsFailureRateCost" action="${ctx}/cst/parts/cstPartsFailureRateCost/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">资源：</label>
				<form:hidden id="cstPartsFailureRateCostId" path="resourceId" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstPartsFailureRateCostName" value="${cstPartsFailureRateCost.resourceDesc}" maxlength="64" class="input-medium required" readonly="readonly" />
				<a id="cstPartsFailureRateCostSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstPartsFailureRateCostId").val(id);
						$("#cstPartsFailureRateCostName").val(title);
					}
					$("#cstPartsFailureRateCostSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/base/cstResourceBaseInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</li>
			<li><label>备件类型：</label>
				<form:select path="partsTypeName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('parts_type_name')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>资源标识</th>
				<th>资源描述</th>
				<th>备件类型</th>
				<th>备件故障率</th>
				<th>采购平均成本</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsFailureRateCost:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsFailureRateCost">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsFailureRateCost/form?id=${cstPartsFailureRateCost.id}">
					${cstPartsFailureRateCost.resourceId}
				</a></td>
				<td>
					${cstPartsFailureRateCost.resourceDesc}
				</td>
				<td>
					${fns:getDictLabel(cstPartsFailureRateCost.partsTypeName, 'parts_type_name', '')}
				</td>
				<td>
					${cstPartsFailureRateCost.failureRate}
				</td>
				<td>
					${cstPartsFailureRateCost.averageCost}
				</td>
				<td>
					${fns:getDictLabel(cstPartsFailureRateCost.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsFailureRateCost.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsFailureRateCost:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsFailureRateCost/form?id=${cstPartsFailureRateCost.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsFailureRateCost/delete?id=${cstPartsFailureRateCost.id}" onclick="return confirmx('确认要删除该备件故障率与采购成本定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>