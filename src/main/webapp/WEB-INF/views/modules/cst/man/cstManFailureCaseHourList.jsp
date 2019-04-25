<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障CASE处理工时定义管理</title>
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
		<li class="active"><a href="${ctx}/cst/man/cstManFailureCaseHour/">故障CASE处理工时定义列表</a></li>
		<shiro:hasPermission name="cst:man:cstManFailureCaseHour:edit"><li><a href="${ctx}/cst/man/cstManFailureCaseHour/form">故障CASE处理工时定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cstManFailureCaseHour" action="${ctx}/cst/man/cstManFailureCaseHour/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label style="width: auto;">资源：</label>
				<form:hidden id="cstManFailureCaseHourId" path="resourceId" htmlEscape="false" maxlength="64" />
				<input type="text" id="cstManFailureCaseHourName" value="${cstManFailureCaseHour.resourceDesc}" maxlength="64" class="input-medium required" readonly="readonly" />
				<a id="cstManFailureCaseHourSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<script type="text/javascript">
					function setProjectSelectRetValue(id,title){
						$("#cstManFailureCaseHourId").val(id);
						$("#cstManFailureCaseHourName").val(title);
					}
					$("#cstManFailureCaseHourSelect").click(function(){
						top.$.jBox.open("iframe:${ctx}/cst/base/cstResourceBaseInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
				
			</li>
			<%-- <li><label>资源描述：</label>
				<form:input path="resourceDesc" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
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
				<th>年化故障率</th>
				<th>一线单次CASE<br />现场处理故障工时</th>
				<th>二线单次CASE<br />诊断、远程支持工时</th>
				<th>三线单次CASE<br />远程处理故障工时</th>
				<th>CMO单次CASE<br />处理工时</th>
				<th>资源岗单次CASE<br />处理工时</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cst:man:cstManFailureCaseHour:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cstManFailureCaseHour">
			<tr>
				<td><a href="${ctx}/cst/man/cstManFailureCaseHour/form?id=${cstManFailureCaseHour.id}">
					${cstManFailureCaseHour.resourceId}
				</a></td>
				<td>
					${cstManFailureCaseHour.resourceDesc}
				</td>
				<td>
					${cstManFailureCaseHour.yearFailureRate}
				</td>
				<td>
					${cstManFailureCaseHour.line1OnceHour}
				</td>
				<td>
					${cstManFailureCaseHour.line2OnceHour}
				</td>
				<td>
					${cstManFailureCaseHour.line3OnceHour}
				</td>
				<td>
					${cstManFailureCaseHour.cmoOnceHour}
				</td>
				<td>
					${cstManFailureCaseHour.resMgrOnceHour}
				</td>
				<td>
					${fns:getDictLabel(cstManFailureCaseHour.status, 'status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cstManFailureCaseHour.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cst:man:cstManFailureCaseHour:edit"><td>
    				<a href="${ctx}/cst/man/cstManFailureCaseHour/form?id=${cstManFailureCaseHour.id}">修改</a>
					<a href="${ctx}/cst/man/cstManFailureCaseHour/delete?id=${cstManFailureCaseHour.id}" onclick="return confirmx('确认要删除该故障CASE处理工时定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>