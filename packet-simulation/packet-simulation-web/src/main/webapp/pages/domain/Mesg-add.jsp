<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style> 
#content li a{
  font-size:13px;
  font: "Microsoft Yahei", "宋体", verdana;
  padding:4px;
}
#main p {
  clear: left;
  margin: 0;
  padding: 0;
  padding-top: 5px;
}
#main p .rgt {
  float: left;
  width: 30%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
#main p .lft {
  font-weight:100;
  width: 30%;
  text-align:left;
  color:#acacac;
  margin-left:10px;
  float:left;
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
<body>
<div id="main">
	<div id="rootwizard">
		<div class="navbar">
	    	<div class="navbar-inner">
				<ul>
	  				<li><a href="#tab1" data-toggle="tab">选择模板</a></li>
					<li><a href="#tab2" data-toggle="tab">编辑记录</a></li>
				</ul>
	  		</div>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab1">
				<div id="searchQueryDiv" style="margin:5px">
					<table border="0" cellspacing="0" cellpadding="0"> 							
  						<tr>
  							<td>
  								<div class="form-group">
  									<label class="control-label" style="width:130px;float:left;">报文类型:</label>
            						<div style="margin-left:5px;margin-right: 25px;float:left;">
            							<input name="mesgType" class="form-control" type="text" style="width:160px;" id="mesgTypeID"  />
        							</div>
  									<label class="control-label" style="width:130px;float:left;">类型代码:</label>
            						<div style="margin-left:5px;margin-right: 25px;float:left;">
            							<input name="code" class="form-control" type="text" style="width:160px;" id="codeID"  />
        							</div>                        			
        						</div>
            				</td>
       						<td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:15px;" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  						</tr>
					</table>
				</div>
            	<div class="selectPacketGrid"></div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
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
					<div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content"></div>
				</div>
			</div>
			<ul class="pager wizard">
				<li class="previous"><a href="javascript:;">上一步</a></li>
		  		<li class="next" id="next"><a href="javascript:;">下一步</a></li>
				<li class="finish" id="sub"><a href="javascript:;">完成</a></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("[name='my-checkbox']").bootstrapSwitch({
			onSwitchChange:function(event, state) {
				$("button[class='btn btn-failure']").toggle();
			}
		});
		$('#rootwizard').bootstrapWizard({
			'tabClass' : 'nav nav-tabs', 
			onTabShow : function(tab, navigation, index) {
				var $total = navigation.find('li').length;
				var $current = index + 1;
				var $percent = ($current / $total) * 100;
				$('#rootwizard').find('.bar').css({
					width : $percent + '%'
				});
			},
			'onNext' : function(tab, navigation, index) {
				var items = $('#main').find('.selectPacketGrid').data('koala.grid').selectedRows();
				if (items.length == 0) {
					$('#main').find('.selectPacketGrid').message({
						type : 'warning',
						content : '请选择报文模板！'
					});
					return false;
				}
				if (items.length > 1) {
					$('#main').find('.selectPacketGrid').message({
						type : 'warning',
						content : '只能选择一个报文模板！'
					});
					return false;
				}
				$("#content").empty();
				$.get('${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/' + items[0].id + '.koala').done(function(data) {
					$("#content").append(data.data);
				});
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
	$("#delete").click(function() {
		$("button[class='btn btn-failure']").toggle();
	});
</script>
</body>
</html>