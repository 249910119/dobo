<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障级别配比定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManFailureSlaPara/">故障级别配比定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManFailureSlaPara:edit"><li><a href="${ctx}/cst/man/cstManFailureSlaPara/form">故障级别配比定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManFailureSlaPara" action="${ctx}/cst/man/cstManFailureSlaPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">资源：</label>
				<form:hidden id="cstManFailureSlaParaId" path="resourceId" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstManFailureSlaParaName" value="${cstManFailureSlaPara.resourceDesc}" maxlength="64" class="input-medium required" readonly="readonly" />
				<a id="cstManFailureSlaParaSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstManFailureSlaParaId").val(id);
						$("#cstManFailureSlaParaName").val(title);
					}
					$("#cstManFailureSlaParaSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/base/cstResourceBaseInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</li>
			<li><label style="width: auto;">服务级别：</label>
				<form:select path="slaId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${slaList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>资源标识</th>
				<th>资源描述</th>
				<th>服务级别标识</th>
				<th>服务级别</th>
				<th>一线1级配比</th>
				<th>一线2级配比</th>
				<th>一线3级配比</th>
				<th>一线4级配比</th>
				<th>一线5级配比</th>
				<th>二线4级配比</th>
				<th>二线5级配比</th>
				<th>二线6级配比</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManFailureSlaPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManFailureSlaPara">
			<tr>
				<td><a href="${ctx}/cst/man/cstManFailureSlaPara/form?id=${cstManFailureSlaPara.id}">
					${cstManFailureSlaPara.resourceId}
				</a></td>
				<td>
					${cstManFailureSlaPara.resourceDesc}
				</td>
				<td>
					${cstManFailureSlaPara.slaId}
				</td>
				<td>
					${cstManFailureSlaPara.slaName}
				</td>
				<td>
					${cstManFailureSlaPara.line1Level1Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line1Level2Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line1Level3Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line1Level4Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line1Level5Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line2Level4Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line2Level5Scale}
				</td>
				<td>
					${cstManFailureSlaPara.line2Level6Scale}
				</td>
				<td>
					${fns:getDictLabel(cstManFailureSlaPara.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManFailureSlaPara.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManFailureSlaPara:edit"><td>
    				<a href="${ctx}/cst/man/cstManFailureSlaPara/form?id=${cstManFailureSlaPara.id}">修改</a>
					<a href="${ctx}/cst/man/cstManFailureSlaPara/delete?id=${cstManFailureSlaPara.id}" onclick="return confirmx('确认要删除该故障级别配比定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>