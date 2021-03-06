<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本参数定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/model/cstModelParaDef/">成本参数定义列表</a></li>
		<shiro:hasPermission name="cst:model:cstModelParaDef:edit"><li><a href="${ctx}/cst/model/cstModelParaDef/form">成本参数定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstModelParaDef" action="${ctx}/cst/model/cstModelParaDef/" method="post" class="breadcrumb form-search">
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
			<li><label>参数名称：</label>
				<form:input path="paraName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>参数类型：</label>
				<form:select path="paraType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('para_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>参数名称</th>
				<th>成本模型</th>
				<th>参数类型</th>
				<th>参数计算公式</th>
				<th>参数计算类：对应的java类全名（含包路径）</th>
				<th>显示顺序</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:model:cstModelParaDef:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstModelParaDef">
			<tr>
				<td><a href="${ctx}/cst/model/cstModelParaDef/form?id=${cstModelParaDef.id}">
					${cstModelParaDef.paraName}
				</a></td>
				<td>
					${cstModelParaDef.cstModelModuleInfo.moduleName}
				</td>
				<td>
					${fns:getDictLabel(cstModelParaDef.paraType, 'para_type', '')}
				</td>
				<td>
					${cstModelParaDef.paraValue}
				</td>
				<td>
					${cstModelParaDef.paraCalcClass}
				</td>
				<td>
					${cstModelParaDef.displayOrder}
				</td>
				<td>
					${fns:getDictLabel(cstModelParaDef.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstModelParaDef.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:model:cstModelParaDef:edit"><td>
    				<a href="${ctx}/cst/model/cstModelParaDef/form?id=${cstModelParaDef.id}">修改</a>
					<a href="${ctx}/cst/model/cstModelParaDef/delete?id=${cstModelParaDef.id}" onclick="return confirmx('确认要删除该成本参数定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>