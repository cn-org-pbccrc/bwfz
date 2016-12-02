<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<%
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.recordItemConfig  .v-itemId {
	width: 7%;
}
.recordItemConfig  .v-itemName {
	width: 14%;
}
.recordItemConfig  .v-itemType {
	width: 10%;
}
.recordItemConfig .v-itemLength {
	width: 8%;
}
.recordItemConfig  .v-itemLocation {
	width: 9%;
}
.recordItemConfig  .v-itemDesc {
	width: 14%;
}
.recordItemConfig .v-state {
	width: 12%;
}
.recordItemConfig .v-itemValue {
	width: 13%;
}
.recordItemConfig .v-itemPrompt {
	width: 13%;
}
</style>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/js/generation/recordEdit.js' />"></script>
<body>	
<form class="form-horizontal" id="itemForm">
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
            	<div class="selectRecordGrid"></div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
				<div class="form-group row">					
					<div class="col-lg-6 form-group">
						<label class="col-lg-3 control-label">报文名称:</label>
						<div class="col-lg-6">
							<input name="name" class="form-control" type="text" dataType="Require" id="nameID" />
							<span class="required">*</span>
						</div>
					</div>
					<div id="mark" class="col-lg-6 form-group"></div>
				</div>
				<div class="panel panel-default table-responsive">
					<div class="panel-heading">数据项</div>
					<table	class="table table-responsive table-bordered grid recordItemConfig">
						<tr>
							<td>
								<div class="grid-body">
									<div class="grid-table-head"  style="width:1150px">
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
												<th class="delete-btn">提示</th>
											</tr>
										</table>
									</div>
									<div class="grid-table-body" style="overflow-x: hidden;width:1150px">
										<table class="table table-bordered table-hover table-striped staticQueryRightTable" style="width:1150px" id="itemTable"></table>
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
				<li class="finish save" id="sub"><a href="javascript:;">完成</a></li>
			</ul>
		</div>
	</div>
</div>
</form>
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
			'onPrevious' : function(tab, navigation, index){
				$('#main').find('[data-toggle="item"]').attr('disabled', false);
				$('#main').find('[data-toggle="button"]').attr('disabled', false);
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
				$('#mark').empty();
				if(items[0].type == 2){
					$('#mark').append('<label class="col-lg-3 control-label">非银填充位:</label><div class="col-lg-6"><input name=padding class="form-control" type="text" dataType="Require" id="paddingID" /><span class="required">*</span></div>');
				}
				$('#main').find("#itemTable").empty();
		        $('#main').find('[data-toggle="item"]').attr('disabled', true);
		        $('#main').find('[data-toggle="button"]').attr('disabled', true);
		        if($('#itemTable').find("tr").length <= 0){
		        	$.get('${pageContext.request.contextPath}/RecordType/findHeaderItemByRecordType/' + items[0].id + '.koala').done(function(data) {					
						var recordType = data;
						var headerItems = recordType.headerItems;
						var inputs = [];
			            var rules = {
							"notnull" : {
								"rule" : function(value, formData){
									return value ? true : false;
								},
								"tip" : "不能为空"
							}
						};
						for(var i=0;i<headerItems.length;i++){
	             			var data = {};
	             			var value = {};
	             			addRow($('#main').find("#itemTable"),headerItems[i]);
	             			value["rule"] = new RegExp("^.{1," + headerItems[i].itemLength + "}$");
	             			value["tip"] = "长度大于" + headerItems[i].itemLength;
	             			rules[headerItems[i].itemId] = value;
	             			data["name"] = headerItems[i].itemId;
	             			data["rules"] = ["notnull", headerItems[i].itemId];
	                 		data["focusMsg"] = "必填";
	                 		data["rightMsg"] = "正确";
	             			inputs.push(data);
	             		}
						$('#itemForm').validateForm({
					    	inputs : inputs,
					        button : ".save",
					        rules : rules,
					        beforeSubmit : function(e,result,form){
					        	if(!result){ //如果表单验证不通过,则阻止表单提交
					        		e.preventDefault();
					        	}	
					        },
					        onButtonClick:function(result, button, form){
				            	if(result){
				            		var content = getAllData($('#main'));
				        	        var data = [{ name: 'name', value: $('#main').find('#nameID').val()},
				        	 				    { name: 'content', value: content},
				        	 				    { name: 'recordTypeId', value: items[0].id},
				        	 				    { name: 'padding', value: $('#main').find('#paddingID').val()}
				        	 		];
				                    $.post('${pageContext.request.contextPath}/Submission/add.koala', data).done(function(result){
				                    	if(result.success){
				                    		$('#itemForm').parent().parent().parent().parent().modal('hide');
				                            grid.data('koala.grid').refresh();
				                            grid.message({
				                                type: 'success',
				                                content: '保存成功'
				                            });
				                        }else{
				                        	$('#itemForm').parent().parent().parent().parent().find('.modal-content').message({
				                                type: 'error',
				                                content: result.errorMessage
				                            });
				                        }
				                    });
				            	}
					        }
						});
					});
		        }
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
	var addRow = function(itemTable, variable, insertRow){
		var row = $('<tr data-toggle="context" data-target="#context-menu"><td class="v-itemId"><input data-role="itemId" required="true" readonly="true" style="display: inline; " class="form-control" type="text" /></td>'
			+'<td class="v-itemName"><input data-role="itemName" required="true" readonly="true" style="display: inline; " class="form-control" type="text" /></td>'
			+'<td class="v-itemType"><div class="btn-group select" id="itemTypeSelect" data-role="itemType"></div></td>'
			+'<td class="v-itemLength"><input data-role="itemLength" class="form-control" required="true" readonly="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字"/></td>'
			+'<td class="v-itemLocation"><input data-role="itemLocation" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
			+'<td class="v-itemDesc"><input data-role="itemDesc" required="true" readonly="true" style="display: inline; " class="form-control" type="text" /></td>'
			+'<td class="v-state"><div class="btn-group select" id="itemStateSelect" data-role="state"></div></td>'
			+'<td class="v-itemValue"><input data-role="itemValue" style="display: inline;" class="form-control" type="text" /></td>'
			+'<td class="delete-btn"></td>');

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
			row.find('input[data-role="itemValue"]').attr('name', variable.itemId)
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
	
	var getAllData = function(dialog){
		var itemId;
		var itemValue;
		var data = {};
		var itemTable = dialog.find("#itemTable");
		itemTable.find('tr').each(function(index,tr){
			var $tr = $(tr);
			itemId = $tr.find('input[data-role="itemId"]').val();
			itemValue = $tr.find('input[data-role="itemValue"]').val();
			data[itemId] = itemValue;
		});
		return JSON.stringify(data);
	};
</script>
</body>
</html>