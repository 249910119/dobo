<%@ page contentType="text/html;charset=UTF-8" %>
<label>项目信息：</label>
<div class="input-append">
	<form:hidden id="measureDefMeasureId" path="measureId" />
	<input type="text" id="measureDefMeasureName" path="measureName" name="measureDefMeasureName" maxlength="64" class="input-small" readonly="readonly"/>
	<a id="measureDefSelect" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>
	<script type="text/javascript">
		function setMeasureDefSelectRetValue(id,title){
			$("#measureDefMeasureId").val(measureId);
			$("#measureDefMeasureName").val(measureName);
		}
		$("#measureDefSelect").click(function(){
			top.$.jBox.open("iframe:${ctx}/cst/model/measureDef/selectList", "选择项目",$(top.document).width()*0.6,$(top.document).height()*0.7,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		});
		$("#measureDefClear").click(function(){
			$("#measureDefMeasureId").val('');
			$("#measureDefMeasureName").val('');
		});
	</script>
</div>