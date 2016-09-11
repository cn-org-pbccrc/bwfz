<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<%
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/js/generation/recordEdit.js' />"></script>
<body>
<div id="main">
	<div id="rootwizard">
		<div class="navbar" style="margin-bottom:5px;">
	    	<div class="navbar-inner">
				<ul>
	  				<li><a href="#tab1" data-toggle="tab">选择模板</a></li>
					<li><a href="#tab2" data-toggle="tab">编辑报文头</a></li>
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
            	<div class="selectRecordGrid"></div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
<!-- 				<div class="form-group"> -->
<!-- 					<p> -->
<!-- 						<label class="rgt">报文名称 :</label>  -->
<!-- 						<label class="lft">  -->
<!-- 							<input name="name" class="form-control" type="text" id="nameID" /> -->
<!-- 						</label> -->
<!-- 					</p> -->
<!-- 				</div> -->
				<div class="form-group row">
					<div class="col-lg-6 form-group">
						<label class="col-lg-3 control-label">报文名称:</label>
						<div class="col-lg-9">
							<input name="name" class="form-control" type="text" dataType="Require" id="nameID" />
							<span class="required">*</span>
						</div>
					</div>
				</div>
				<div class="panel panel-default table-responsive">
					<div class="panel-heading">数据项</div>
					<table	class="table table-responsive table-bordered grid recordItemConfig">
						<tr>
							<td>
								<div class="grid-body">
									<div class="grid-table-head"  style="width:950px">
										<table class="table table-bordered" >
											<tr>
												<th class="v-itemId">标识符</th>
												<th class="v-itemName">数据项名称</th>
												<th class="v-itemType">类型</th>
												<th class="v-itemLength">长度</th>
												<th class="v-itemLocation">位置</th>
												<th class="v-itemDesc">描述及代码表</th>
												<th class="v-state">状态</th>
												<th class="v-itemValue">值</th>
											</tr>
										</table>
									</div>
									<div class="grid-table-body" style="overflow-x: hidden;width:950px">
										<table class="table table-bordered table-hover table-striped staticQueryRightTable" style="width:950px" id="itemTable">
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
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
				var items = $('#main').find('.selectRecordGrid').data('koala.grid').selectedRows();
				if (items.length == 0) {
					$('#main').find('.selectRecordGrid').message({
						type : 'warning',
						content : '请选择报文模板！'
					});
					return false;
				}
				if (items.length > 1) {
					$('#main').find('.selectRecordGrid').message({
						type : 'warning',
						content : '只能选择一个报文模板！'
					});
					return false;
				}
				
		        $('#main').find('[data-toggle="item"]').attr('disabled', true);
		        $('#main').find('[data-toggle="button"]').attr('disabled', true);
		        if($('#itemTable').find("tr").length <= 0){
		        	$.get('${pageContext.request.contextPath}/RecordType/findHeaderItemByRecordType/' + items[0].id + '.koala').done(function(data) {					
						var recordType = data;
						var headerItems = recordType.headerItems;
						for(var i=0;i<headerItems.length;i++){
	             			addRow($('#main').find("#itemTable"),headerItems[i]);
	             		}
							
					});
		        }
				
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
	
	var addRow = function(itemTable, variable, insertRow){
		var row = $('<tr data-toggle="context" data-target="#context-menu"><td class="v-itemId"><input  data-role="itemId" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
		+'<td class="v-itemName"><input  data-role="itemName" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
		+'<td class="v-itemType"><div class="btn-group select" id="itemTypeSelect" data-role="itemType"></div></td>'
		+'<td class="v-itemLength"><input data-role="itemLength" readonly="true" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字"/></td>'
		+'<td class="v-itemLocation"><input data-role="itemLocation" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
		+'<td class="v-itemDesc"><input data-role="itemDesc" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
		+'<td class="v-state"><div class="btn-group select" id="itemStateSelect" data-role="state" ></div></td>'
		+'<td class="v-itemValue"><input data-role="itemValue" style="display: inline;" class="form-control" type="text" /></td>');

		row.find("[data-role='itemLength']").on('change', function(){
			calculateLocation(itemTable);
		})
		if(!itemTable){
			itemTable=$('#itemTable')
		}
		var itemType = row.find('[data-role="itemType"]')
		var itemState = row.find('[data-role="state"]')
		fillVTypeSelect(itemType);
		fillStateSelect(itemState);
		row.find('[data-toggle="item"]').attr('disabled', true);
		row.find('[data-toggle="button"]').attr('disabled', true);
		if(variable){
			row.find('input[data-role="itemId"]').val(variable.itemId);
			row.find('input[data-role="itemName"]').val(variable.itemName);
			row.find('input[data-role="itemLength"]').val(variable.itemLength);
			row.find('input[data-role="itemLocation"]').val(variable.itemLocation);
			row.find('input[data-role="itemDesc"]').val(variable.itemDesc);
			row.find('.select[data-role="itemType"]').setValue(variable.itemType);
			row.find('.select[data-role="state"]').setValue(variable.state);
			row.find('input[data-role="itemValue"]').val(variable.itemValue);
		}
		row.find('[data-role="delete"]').on('click', function(){
			removeRow($(this));
		});
		if(insertRow){
			row.insertAfter(insertRow);
		}else{
			row.appendTo(itemTable);
		}
		
	}
	
	//删除行
	var removeRow = function($this){
		$this.closest('tr').remove();
	};
	
	//填充数据项类型
	var fillVTypeSelect = function(select){
		select.select({
			contents: [
						{value: '0', title: 'N',selected: true},
						{value: '1', title: 'AN'},
						{value: '2', title: 'ANC'}
					]
		});
	};

	//填充数据项状态
	var fillStateSelect = function(select){
		  var contents = [{title:'必选', value: 'M',selected: true}];
	       contents.push({title:'条件选择' , value:'C'});
	       contents.push({title:'可选' , value:'O'});
		select.select({
			contents: contents
		});
	};
</script>
</body>
</html>