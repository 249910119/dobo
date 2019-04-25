<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选择项目</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					top.mainFrame.setMeasureDefSelectRetValue(id, title);
				});
			});
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
	<div style="margin:10px;">
	<form:form id="searchForm" modelAttribute="measureDef" action="${ctx}/cst/model/measureDef/selectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>指标名称：</label><form:input path="measureName" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align:center;">选择</th><th>指标类型编码</th><th>指标名称</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="measureDef">
			<tr>
				<td style="text-align:center;"><input type="radio" name="id" value="${measureDef.measureId}" title="${measureDef.measureName}"/></td>
				<td>${measureDef.measureTypeId}</td>
				<td>${measureDef.measureName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>