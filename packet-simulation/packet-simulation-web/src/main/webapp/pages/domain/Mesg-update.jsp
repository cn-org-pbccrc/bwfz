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
	<div class="form-group">
    	<label class="col-lg-3 control-label">备注:</label>
	    <div class="col-lg-9">
        	<input name="remark" style="display:inline; width:40%;" class="form-control"  type="text"  id="remarkID" />  
        	<button type="button" class="btn btn-danger" id="delete" style="float:right;">删除字段</button>     	
		</div>
	</div>
	<div class="form-group">
	    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="contentID">
		</div>
	</div>
</form>

<script type="text/javascript">
	$("#delete").click(function(){
		$("button[class='btn btn-failure']").toggle();
	});
</script>
</body>
</html>