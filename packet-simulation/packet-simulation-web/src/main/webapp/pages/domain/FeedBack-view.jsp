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
	  				<li><a href="#tab1" data-toggle="tab">查看数据</a></li>
					<li><a href="#tab2" data-toggle="tab">模板转换</a></li>
					<li><a href="#tab3" data-toggle="tab">发送配置</a></li>
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
			<div class="tab-pane" id="tab3">
				<p>
					<label class='rgt'>任务名称:</label>
					<label class='lft'>
						<input name="taskName" style="display: inline; width: 94%;" class="form-control" type="text" dataType="Require" id="taskNameID" />
						<input name="selectedRecordType" class="form-control" type="hidden" id="selectedRecordTypeID" />
						<span class="required">*</span>
					</label>
				</p>
				<p>
					<label class='rgt'>发送渠道:</label>
					<label class='lft'>
						<input name="sendChannel" style="display: inline; width: 94%;" class="form-control" type="text" dataType="Require" id="sendChannelID" />
						<span class="required">*</span>
					</label>
				</p>
  				<p>
					<label class='rgt'>是否加密:</label>
					<div class="col-lg-9">
						<div class="radio">
							<span class="checked">
								<input value=1 type="radio" checked="checked" name="encryption" style="opacity: 0;">
							</span>
						</div>
						<span style="position: relative; top: 5px;">是</span>&nbsp;&nbsp;&nbsp;
						<div class="radio">
							<span>
								<input type="radio" value=0 name="encryption" style="opacity: 0;">
							</span>
						</div>
						<span style="position: relative; top: 5px;">否</span>
					</div>
				</p>			
				<p>
					<label class='rgt'>是否加压:</label>
					<div class="col-lg-9">
						<div class="radio">
							<span class="checked">
								<input value=1 type="radio" checked="checked" name="compression" style="opacity: 0;">
							</span>
						</div>
						<span style="position: relative; top: 5px;">是</span>&nbsp;&nbsp;&nbsp;
						<div class="radio">
							<span>
								<input type="radio" value=0 name="compression" style="opacity: 0;">
							</span>
						</div>
						<span style="position: relative; top: 5px;">否</span>
					</div>
				</p>
				<p>
					<label class='rgt'>文件内容:</label>
					<div class="col-lg-9">
						<textarea name="mesgContent" style="display: inline;width: 94%;height:300px;overflow-x:scroll;" class="form-control" id="mesgContentID"></textarea>
					</div>
				</p>
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
	<%String id = (String)request.getParameter("id");%>  
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
				var flag = $('input:radio:checked').attr('flag');
				var code = $('input:radio:checked').val();
				var catagory = $('input:radio:checked').attr('catagory');
				var map = eval("("+flag+")");				
				var data = [{ name: 'code', value: code }];
				$.get('${pageContext.request.contextPath}/MesgType/getEditHtmlByCode.koala', data).done(function(data){
                    $("#content2").append(data.data);   
                    $('[name = "InfRecTp"]').attr('value', code);
                    for (var key in map){
                    	var value = $('#content1').find('[name="' + key +'"]').attr('value');
        				$('[name="' + map[key] + '"]').attr('value', value);
    				}
                    switch(catagory){           			
            			case "deleteBySeg":
            				var del_Sgmt_Id = $('#content1').find('.active.in').attr('id');
            				$('[name = "Del_Sgmt_Id"]').attr('value', del_Sgmt_Id);
            				var rptDate = $('#content1').find('[name="RptDate"]').attr('value');
            				$('[name = "Del_Start_Date"]').attr('value', rptDate);
            				$('[name = "Del_End_Date"]').attr('value', rptDate);
            				break;
            			case "modify":
            				var modSegMark = $('#content1').find('.active.in').attr('id');
            				$('[name = "ModSegMark"]').attr('value', modSegMark);
            				var xml = '<' + modSegMark + '>';
            				$('#'+modSegMark).find($("[name]")).each(function(){
        						if($(this).parent()[0].tagName != 'FIELDSET'){        							
        							var name = ($(this).attr('name'));
            	    				var value = ($(this).attr('value'));
            						xml += '<' + name + '>' + value + '</' + name + '>';
        						}
        						else{
        							var name = ($(this).attr('name')); 
        							xml += '<' + name + '>';
        							$(this).parent().find($("[subName]")).each(function(){
        								var subName = ($(this).attr('subName'));
            							var value = ($(this).attr('value'));	
            							xml += '<' + subName + '>' + value + '</' + subName + '>';
        							});
        							xml += '</' + name + '>';
        						}
            				});
            				xml += '</' + modSegMark + '>';
            				$('[name = "MdfcSgmt"]').attr('value', xml);
            				break;
            		}
                    var data = [{ name: 'code', value: code}];
                    $.get( '${pageContext.request.contextPath}/FeedBack/initSend.koala',data).done(function(json){
    	            	var json = json.data;
    	            	var xml = json.packetHead;
    	            	var info = $("#content2").find($(".tab-content")).attr('id');
        				xml += '<?xml version="1.0" encoding="UTF-8"?><Document><'+info+'>';
        				$("#content2").find($(".true")).each(function(){   					
        					var id = $(this).attr('id');
        					xml += '<' + id + '>';
        					$('#'+id).find($("[name]")).each(function(){
        						if($(this).parent().parent()[0].tagName != 'FIELDSET'){
        							if($(this).attr('save')=='true'){
        								var name = ($(this).attr('name'));
        								var value = ($(this).val());
        								xml += '<' + name + '>' + value + '</' + name + '>';
        							}
        						}
        						else{
        							var name = ($(this).attr('name')); 
        							xml += '<' + name + '>';
        							$(this).parent().parent().find($("[subName]")).each(function(){
        								if($(this).attr('save')=='true'){
        									var subName = ($(this).attr('subName'));
        									var value = ($(this).val());	
        									xml += '<' + subName + '>' + value + '</' + subName + '>';
        								} 
        							});
        							xml += '</' + name + '>';
        						}
        					});
        					xml += '</' + id + '>';
        				});
        				xml += '</'+info+'></Document>';
        				$('#tab3').find("#mesgContentID").val(xml);
	                });                    
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
	var transform = $('[name="transform"]');
	transform.on('click', function() {
		transform.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
	var isEncryption = $('[name="encryption"]');
	var isCompression = $('[name="compression"]');
	isEncryption.on('click', function() {
		isEncryption.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
	isCompression.on('click', function() {
		isCompression.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
	$('body').unbind("keydown");
</script>
</body>
</html>