<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
	p {
  		clear: left;
  		margin: 0;
  		padding: 0;
  		padding-top: 5px;
	}
	p .rgt {
  		float: left;
  		width: 30%;
  		text-align:right;
  		font: "Microsoft Yahei", "宋体", verdana;
	}
	p .lft {
  		font-weight:100;
  		width: 30%;
  		text-align:left;
  		color:#acacac;
  		margin-left:10px;
  		float:left;
	}
</style>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="packetIdID" name="packetId" />
	<input type="hidden" id="mesgTypeID" name="mesgType" />
	<input type="hidden" id="mesgFromID" name="mesgFrom" />
	<input type="hidden" id="createByID" name="createBy" />
	<div class="form-group">
		<p>
			<label class="rgt">用例名称 :</label> 
			<label class="lft"> 
				<input name="remark" class="form-control" type="text" id="remarkID" />
			</label>
		</p>
		<input type="checkbox"  name="my-checkbox"  data-on-text="恢复" data-off-text="编辑" data-size="normal">
	</div>
	<div class="form-group">
	    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="contentID">
		</div>
	</div>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		$("[name='my-checkbox']").bootstrapSwitch({
			onSwitchChange:function(event, state) {
				$("button[class='btn btn-failure']").toggle();
			}
		});
	});
	$("#delete").click(function(){
		$("button[class='btn btn-failure']").toggle();
	});
</script>
</body>
</html>