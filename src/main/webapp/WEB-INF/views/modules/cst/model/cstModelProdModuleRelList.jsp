<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品成本模型使用定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/model/cstModelProdModuleRel/">产品成本模型使用定义列表</a></li>
		<shiro:hasPermission name="cst:model:cstModelProdModuleRel:edit"><li><a href="${ctx}/cst/model/cstModelProdModuleRel/form">产品成本模型使用定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstModelProdModuleRel" action="${ctx}/cst/model/cstModelProdModuleRel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">成本模型：</label>
				<form:hidden id="cstModelModuleInfoId" path="moduleId" htmlEscape="false" maxlength="64" />
				<form:input type="text" id="cstModelModuleInfoModuleName" path="cstModelModuleInfo.moduleName" disabled="false" maxlength="64" class="input-medium required" readonly="true"/>
				<a id="cstModelModuleInfoSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstModelModuleInfoId").val(id);
						$("#cstModelModuleInfoModuleName").val(title);
					}
					$("#cstModelModuleInfoSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/model/cstModelModuleInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</li>
			<li><label style="width: auto;">成本模型使用名称：</label>
				<form:input path="cpmName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>产品标识：</label>
				<form:input path="prodId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>成本模型使用名称</th>
				<th>成本模型</th>
				<th>产品标识</th>
				<th>模型计算类</th>
				<th>计算顺序</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:model:cstModelProdModuleRel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstModelProdModuleRel">
			<tr>
				<td><a href="${ctx}/cst/model/cstModelProdModuleRel/form?id=${cstModelProdModuleRel.id}">
					${cstModelProdModuleRel.cpmName}
				</a></td>
				<td>
					${cstModelProdModuleRel.cstModelModuleInfo.moduleName}
				</td>
				<td>
					${cstModelProdModuleRel.prodId}
				</td>
				<td>
					${cstModelProdModuleRel.className}
				</td>
				<td>
					${cstModelProdModuleRel.calOrder}
				</td>
				<td>
					${fns:getDictLabel(cstModelProdModuleRel.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstModelProdModuleRel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:model:cstModelProdModuleRel:edit"><td>
    				<a href="${ctx}/cst/model/cstModelProdModuleRel/form?id=${cstModelProdModuleRel.id}">修改</a>
					<a href="${ctx}/cst/model/cstModelProdModuleRel/delete?id=${cstModelProdModuleRel.id}" onclick="return confirmx('确认要删除该产品成本模型使用定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>