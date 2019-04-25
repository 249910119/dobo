<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备件事件故障参数定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/parts/cstPartsEventFailurePara/">备件事件故障参数定义列表</a></li>
		<shiro:hasPermission name="cst:parts:cstPartsEventFailurePara:edit"><li><a href="${ctx}/cst/parts/cstPartsEventFailurePara/form">备件事件故障参数定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstPartsEventFailurePara" action="${ctx}/cst/parts/cstPartsEventFailurePara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">资源：</label>
				<form:hidden id="cstPartsEventFailureParaId" path="resourceId" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstPartsEventFailureParaName" value="${cstPartsEventFailurePara.resourceDesc}" maxlength="64" class="input-medium required" readonly="readonly" />
				<a id="cstPartsEventFailureParaSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstPartsEventFailureParaId").val(id);
						$("#cstPartsEventFailureParaName").val(title);
					}
					$("#cstPartsEventFailureParaSelect").click(function(){
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
				<th>事件故障率</th>
				<th>1级事件占比</th>
				<th>2级事件占比</th>
				<th>3级事件占比</th>
				<th>4级事件占比</th>
				<th>备件故障率</th>
				<th>平均采购成本</th>
				<th>事件故障率</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:parts:cstPartsEventFailurePara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstPartsEventFailurePara">
			<tr>
				<td><a href="${ctx}/cst/parts/cstPartsEventFailurePara/form?id=${cstPartsEventFailurePara.id}">
					${cstPartsEventFailurePara.resourceId}
				</a></td>
				<td>
					${cstPartsEventFailurePara.resourceDesc}
				</td>
				<td>
					${cstPartsEventFailurePara.eventFailureRate}
				</td>
				<td>
					${cstPartsEventFailurePara.level1EventScale}
				</td>
				<td>
					${cstPartsEventFailurePara.level2EventScale}
				</td>
				<td>
					${cstPartsEventFailurePara.level3EventScale}
				</td>
				<td>
					${cstPartsEventFailurePara.level4EventScale}
				</td>
				<td>
					${cstPartsEventFailurePara.failureRate}
				</td>
				<td>
					${cstPartsEventFailurePara.averageCost}
				</td>
				<td>
					${cstPartsEventFailurePara.averageFailureCost}
				</td>
				<td>
					${fns:getDictLabel(cstPartsEventFailurePara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstPartsEventFailurePara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:parts:cstPartsEventFailurePara:edit"><td>
    				<a href="${ctx}/cst/parts/cstPartsEventFailurePara/form?id=${cstPartsEventFailurePara.id}">修改</a>
					<a href="${ctx}/cst/parts/cstPartsEventFailurePara/delete?id=${cstPartsEventFailurePara.id}" onclick="return confirmx('确认要删除该备件事件故障参数定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>