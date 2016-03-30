<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style> 
#content1 li a{
	font-size:13px;
	font: "Microsoft Yahei", "宋体", verdana;
	padding:4px;
}
#content2 li a{
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
  width: 22%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
#main p .lft {
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
            	<div class="form-group">
					<div class="col-lg-9" style="width: 100%; margin: 0 auto;">
						<div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content1"></div>
					</div>
				</div>
				<div id="transform">
					<label style="color:#778899;font: "Microsoft Yahei", "宋体", verdana;>选择转换报文:</label>
				</div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
				<div class="form-group">
					<div class="col-lg-9" style="width: 100%; margin: 0 auto;">
						<div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content2"></div>
					</div>
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
				$("#content2").empty();
				var code = $('input:radio:checked').val()
				var flag = $('input:radio:checked').attr('flag');
				switch(flag){
        			case "change":
        				var finanCode = $('#content1').find('[name="FinanCode"]').attr('value');
        				var cstCode = $('#content1').find('[name="CstCode"]').attr('value');
        				var data = [{ name: 'code', value: code },
        				            { name: 'finanCode', value: finanCode },
        	      					{ name: 'cstCode', value: cstCode },
        	     		];
        				$.get('${pageContext.request.contextPath}/MesgType/getEditHtmlOfChange.koala', data).done(function(data) {
        					$("#content2").append(data.data);
        				});
        	  			break;
        			case "deleteAll":
        				
        	  			break;
        			case "deleteBySeg":
        				dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+obj[key]+" flag="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>按段删除</span>");
        				break;
        			case "modify":
        				dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+obj[key]+" flag="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>修改</span>");
        				break;
        		}   
// 				$.get('${pageContext.request.contextPath}/MesgType/getEditHtmlByCode/' + $('input:radio:checked').val() + '.koala').done(function(data) {
// 					$("#content2").append(data.data);
// 				});
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
	$("#delete").click(function() {
		$("button[class='btn btn-failure']").toggle();
	});
	var transform = $('[name="transform"]');
	transform.on('click', function() {
		transform.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
</script>
</body>
</html>