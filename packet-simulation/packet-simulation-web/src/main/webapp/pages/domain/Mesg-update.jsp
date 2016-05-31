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
	<input type="hidden" id="mesgNumID" name="mesgNum" />
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
				$("button[class='btn btn-link']").toggle();
				$("li:has(span[class='glyphicon glyphicon-unchecked'])").toggle();
				$("p:has(span[class='glyphicon glyphicon-unchecked'])").toggle();
				if(state==false){
					var activeHref = $("#contentID").find($("li[class='active']")).children().attr('href')
					var activeClass = $("#contentID").find($("li[class='active']")).children().children().children().attr('class')
					if(activeClass=='glyphicon glyphicon-unchecked'){
						var firstCheckedHref = $("li:has(span[class='glyphicon glyphicon-check']):first").children().attr('href');
						var firstCheckedId = firstCheckedHref.substring(1,firstCheckedHref.length);
						var activeId = activeHref.substring(1,activeHref.length);
						$("#contentID").find($("li[class='active']")).removeClass('active');
						$('#' + activeId).removeClass('active in');
						$("li:has(span[class='glyphicon glyphicon-check']):first").addClass('active');
						$('#' + firstCheckedId).addClass('active in');
					}
				}
			}
		});
	});
</script>
</body>
</html>