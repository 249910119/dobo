<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本计算公式定义表管理</title>
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
		<li class="active"><a href="${ctx}/cst/model/cstModelCalculateFormula/">成本计算公式定义表列表</a></li>
		<shiro:hasPermission name="cst:model:cstModelCalculateFormula:edit"><li><a href="${ctx}/cst/model/cstModelCalculateFormula/form">成本计算公式定义表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstModelCalculateFormula" action="${ctx}/cst/model/cstModelCalculateFormula/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">成本模型标识：</label>
				<form:input path="moduleId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width: auto;">计算公式名称：</label>
				<form:input path="formulaName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<%-- <li><label style="width: auto;">是否成本参数：</label>
				<form:radiobuttons path="isModelPara" items="${fns:getDictList('is_model_para')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li> --%>
			<li><label style="width: auto;">是否成本参数：</label>
				<form:select path="isModelPara" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_model_para')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>指标代码</th>
				<th>成本模型标识</th>
				<th>成本参数</th>
				<th>成本类型代码</th>
				<th>返回类型</th>
				<th>计算公式名称</th>
				<!-- <th>计算公式描述</th> -->
				<th>显示顺序</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:model:cstModelCalculateFormula:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstModelCalculateFormula">
			<tr>
				<td><a href="${ctx}/cst/model/cstModelCalculateFormula/form?id=${cstModelCalculateFormula.id}">
					${cstModelCalculateFormula.measureDef.measureName}
				</a></td>
				<td>
					${cstModelCalculateFormula.cstModelModuleInfo.moduleName}
				</td>
				<td>
					${fns:getDictLabel(cstModelCalculateFormula.isModelPara, 'is_model_para', '')}
				</td>
				<td>
					${cstModelCalculateFormula.ctType.typeName}
				</td>
				<td>
					${fns:getDictLabel(cstModelCalculateFormula.returnType, 'return_type', '')}
				</td>
				<td>
					${cstModelCalculateFormula.formulaName}
				</td>
				<%-- <td>
					${cstModelCalculateFormula.formulaDesc}
				</td> --%>
				<td>
					${cstModelCalculateFormula.displayOrder}
				</td>
				<td>
					${fns:getDictLabel(cstModelCalculateFormula.status, 'status', '')}
				</td>
				<td style="width: 80px;">
					<fmt:formatDate value="${cstModelCalculateFormula.updateDate}" pattern="yyyy-MM-dd" />
				</td>
				<shiro:hasPermission name="cst:model:cstModelCalculateFormula:edit"><td>
    				<a href="${ctx}/cst/model/cstModelCalculateFormula/form?id=${cstModelCalculateFormula.id}">修改</a>
					<a href="${ctx}/cst/model/cstModelCalculateFormula/delete?id=${cstModelCalculateFormula.id}" onclick="return confirmx('确认要删除该成本计算公式定义表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>