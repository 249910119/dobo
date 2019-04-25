<%@ page contentType="text/html;charset=UTF-8" %>
<label>项目信息：</label>
<div class="input-append">
	<form:hidden id="costTypeCostType" path="costType" />
	<input type="text" id="costTypeTypeName" path="typeName" name="costTypeTypeName" maxlength="64" class="input-small" readonly="readonly"/>
	<a id="costTypeSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
	<script type="text/javascript">
		function setCostTypeSelectRetValue(id,title){
			$("#costTypeCostType").val(costType);
			$("#costTypeTypeName").val(typeName);
		}
		$("#costTypeSelect").click(function(){
			top.$.jBox.open("iframe:${ctx}/cst/model/costType/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		});
		$("#costTypeClear").click(function(){
			$("#costTypeCostType").val('');
			$("#costTypeTypeName").val('');
		});
	</script>
</div>