<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件加权平均在保量定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsWeightAmount/">备件加权平均在保量定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsWeightAmount:edit"><li><a href="${ctx}/cst/parts/cstPartsWeightAmount/form">备件加权平均在保量定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsWeightAmount" action="${ctx}/cst/parts/cstPartsWeightAmount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">资源：</label>
				<form:hidden id="cstPartsWeightAmountId" path="resourceId" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstPartsWeightAmountName" value="${cstPartsWeightAmount.resourceDesc}" maxlength="64" class="input-medium required" readonly="readonly" />
				<a id="cstPartsWeightAmountSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstPartsWeightAmountId").val(id);
						$("#cstPartsWeightAmountName").val(title);
					}
					$("#cstPartsWeightAmountSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/base/cstResourceBaseInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
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
				<th>加权平均在保量</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsWeightAmount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsWeightAmount">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsWeightAmount/form?id=${cstPartsWeightAmount.id}">
					${cstPartsWeightAmount.resourceId}
				</a></td>
				<td>
					${cstPartsWeightAmount.resourceDesc}
				</td>
				<td>
					${cstPartsWeightAmount.weightCost}
				</td>
				<td>
					${fns:getDictLabel(cstPartsWeightAmount.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsWeightAmount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsWeightAmount:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsWeightAmount/form?id=${cstPartsWeightAmount.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsWeightAmount/delete?id=${cstPartsWeightAmount.id}" onclick="return confirmx('确认要删除该备件加权平均在保量定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>