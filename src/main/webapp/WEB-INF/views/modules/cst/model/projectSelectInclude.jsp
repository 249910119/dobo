<%@ page contentType="text/html;charset=UTF-8" %>
<label>成本模型：</label>
<div class="input-append">
	<form:hidden id="cstModelModuleInfoId" path="id" />
	<input type="text" id="cstModelModuleInfoModuleName" path="moduleName" name="cstModelModuleInfoModuleName" maxlength="64" class="input-small" readonly="readonly"/>
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
		$("#cstModelModuleInfoClear").click(function(){
			$("#cstModelModuleInfoId").val('');
			$("#cstModelModuleInfoModuleName").val('');
		});
	</script>
</div>