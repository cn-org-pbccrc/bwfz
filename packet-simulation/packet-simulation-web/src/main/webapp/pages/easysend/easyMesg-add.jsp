<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--     <link href="${contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet"/> --%>
    <style>
#content li a{
	font-size:13px;
	 font: "Microsoft Yahei", "宋体", verdana;
	padding:4px;
}
form p {
  clear: left;
  margin: 0;
  padding: 0;
  padding-top: 5px;
}
form p .rgt {
  float: left;
  width: 26%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
form p .lft {
  font-weight:100;
  width: 30%;
  text-align:left;
  color:#acacac;
  margin-left:10px;
}
fieldset {
  border: 1px bolid #61B5CF;
  margin-top: 16px;
  padding: 10px;
  font: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
legend {
  font: bold 1em Arial, Helvetica, sans-serif;
  color: #999999;
  background-color: #FFFFFF;
}
</style>
</head>
<body>
	<form class="form-horizontal">
		<div class="form-group">
			<label class="col-lg-3 control-label">用例名称:</label>
			<div class="col-lg-9">
				<input name="remark" style="display: inline; width: 40%;" class="form-control" type="text" id="remarkID" />
				<input name="mesgType"  class="form-control" type="hidden" id="mesgType" />
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  name="my-checkbox"  data-on-text="恢复" data-off-text="编辑" data-size="normal">
			</div>
		</div>
		<div class="form-group">
			<div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content"></div>
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
					var activeHref = $("#content").find($("li[class='active']")).children().attr('href')
					var activeClass = $("#content").find($("li[class='active']")).children().children().children().attr('class')
					if(activeClass=='glyphicon glyphicon-unchecked'){
						var firstCheckedHref = $("li:has(span[class='glyphicon glyphicon-check']):first").children().attr('href');
						var firstCheckedId = firstCheckedHref.substring(1,firstCheckedHref.length);
						var activeId = activeHref.substring(1,activeHref.length);
						$("#content").find($("li[class='active']")).removeClass('active');
						$('#' + activeId).removeClass('active in');
						$("li:has(span[class='glyphicon glyphicon-check']):first").addClass('active');
						$('#' + firstCheckedId).addClass('active in');
					}
				}
			}
		});
	});
	function addContent(id) {
		$("#content").empty();
		$.get('${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/'+ id + '.koala').done(function(data) {
			$("#content").append(data.data);
		});
	}
</script>
</body>
</html>