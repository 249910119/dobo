<%@ page contentType="text/html;charset=UTF-8" %>
<label>项目信息：</label>
<div class="input-append">
	<form:hidden id="fcProjectInfoId" path="fcProjectInfoId" />
	<input type="text" id="fcProjectInfoCode" path="fcProjectInfo.projectCode" name="fcProjectInfoCode" maxlength="64" class="input-small" readonly="readonly"/>
	<a id="fcProjectInfoSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
	<script type="text/javascript">
		function setProjectSelectRetValue(id,title){
			$("#fcProjectInfoId").val(id);
			$("#fcProjectInfoCode").val(title);
		}
		$("#fcProjectInfoSelect").click(function(){
			top.$.jBox.open("iframe:${ctx}/fc/fcProjectInfo/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
				buttons:{"确定":true,"清除":false}, 
				loaded:function(h){$(".jbox-content", top.document).css("overflow-y","hidden");},
				submit:function (v, h, f) {
				    if (v == false){
						$("#fcProjectInfoId").val('');
						$("#fcProjectInfoCode").val('');
				    }
				}
			});
		});
		$("#fcProjectInfoClear").click(function(){
			$("#fcProjectInfoId").val('');
			$("#fcProjectInfoCode").val('');
		});
	</script>
</div>