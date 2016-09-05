<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/js/generation/recordEdit.js' />"></script>
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
  padding-top:6px;
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
		<div class="navbar" style="margin-bottom:5px;">
	    	<div class="navbar-inner">
				<ul>
	  				<li><a href="#tab1" data-toggle="tab">选择模板</a></li>
					<li><a href="#tab2" data-toggle="tab">编辑记录</a></li>
				</ul>
	  		</div>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab1">
				<div id="searchQueryDiv" style="margin-bottom:10px;">
					<table border="0" cellspacing="0" cellpadding="0"> 							
  						<tr>
  							<td>
  								<div class="form-group">
  									<label class="control-label" style="width:130px;float:left;text-align:right;margin-top:6px;">报文类型:</label>
            						<div style="margin-left:5px;margin-right: 25px;float:left;">
            							<input name="mesgType" class="form-control" type="text" style="width:160px;" id="mesgTypeID"  />
        							</div>
  									<label class="control-label" style="width:130px;float:left;text-align:right;margin-top:6px;">类型代码:</label>
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
				
				
				var record = {};
				var segment = [];
				var item ={};
				item.name="姓名";
				item.length="10";
				item.value="张三";
				segment.push(item);
				segment.push(item);
				$.get('${pageContext.request.contextPath}/RecordSegment/findRecordSegmentByRecordType/' + items[0].id + '.koala').done(function(data) {
					alert(1);
					
					var result = "<ul class='nav nav-tabs' style='font-size:5px' id='myTab'>";
					var contentStr="<div class='tab-content info' id='myTabContent'>";
					var tabClass="active";
					var TAB_TABLE_TAG = "<table class='table'>";
					var TR_TAG = "<tr>";
					var TABLE_ENDTAG = "</table>";
					var DIV_ENDTAG = "</div>"
					var UL_ENDTAG = "</ul>"
					var contentClass="tab-pane fade active in";
						for(var i=0; i<data.length; i++){
							var segment = data[i];
							result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+segment.segMark+"'>"+segment.segName+"</a></li>";
							tabClass="";
// 							if(null!= childNode && childNode.size()>0){
								contentStr=contentStr+"<div id='"+segment.segMark+"' class='"+contentClass+"'>";
								contentClass="tab-pane fade";
								contentStr=contentStr+"<div  class='contentGrid' id='contentGrid_"+segment.segMark+"'></div></div>";
							
// // 								contentStr=contentStr+getTabContents(childNode,null,templateName);
// 								contentStr=contentStr+TABLE_ENDTAG+DIV_ENDTAG;
// 							}
						}
					result=result + UL_ENDTAG;
					contentStr=contentStr+DIV_ENDTAG;
					$("#content").html(result+contentStr);
					for(var i=0; i<data.length; i++){
						var segment =data[i];
						var recordItems = segment.recordItems;
						var columns = new Array();
						for(var j=0; j<recordItems.length; j++){
							var recordItem =recordItems[j];
							columns.push({
								title : recordItem.itemName,
								name : recordItem.itemId,
								width : '200px'
							});
							
						}
						$('#contentGrid_A').grid({
							isShowButtons : false,
							isShowIndexCol : false,
							columns : columns
						});
					}
					
				});
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
</script>
</body>
</html>