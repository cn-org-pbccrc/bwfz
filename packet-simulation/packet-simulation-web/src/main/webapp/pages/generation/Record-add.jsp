<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<%
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
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
            	<div class="selectRecordGrid"></div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
				<div class="form-group">
					<p>
						<label class="rgt">用例名称 :</label> 
						<label class="lft"> 
							<input name="remark" class="form-control" type="text" id="remarkID" />
						</label>
					</p>
				</div>
				<div id="grid">
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
				$.get('${pageContext.request.contextPath}/RecordSegment/findRecordSegmentByRecordType/' + items[0].id + '.koala').done(function(data) {					
					for(var i=0; i<data.length; i++){
						var segment = data[i];
						var segmentId = segment.id;
						var recordItems = segment.recordItems;
						var columns = new Array();
						for(var j=0; j<recordItems.length; j++){
							var recordItem = recordItems[j];
							columns.push({
								title : recordItem.itemName,
								name : recordItem.itemName,
								width : '200px'
							});
						}
						var subGrid = "grid" + i;
						if($('#' + subGrid).length <= 0){
							$('#grid').append("<div id=" + subGrid + " segmentId=" + segmentId + "></div>")
							$('#' + subGrid).grid({
								identity: 'id',
								buttons: [
								    {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
								    {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
								    {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
								],
								columns : columns,
								//url: contextPath + '/Record/pageJson.koala'
								localData : [{"信息类别" : "haha", "变更类型" : "haha", "业务标识号" : "haha"}],
								isUserLocalData : true
							}).on({
						    	'add': function(){
						    		add($(this));
						    	},
						    	'modify': function(event, data){
				   	            	var indexs = data.data;
				   	                var $this = $(this);
				   	                if(indexs.length == 0){
				   	                	$this.message({
				   	                    	type: 'warning',
				   	                        content: '请选择一条记录进行修改'
				   	                    })
				   	                    return;
				   	                }
				   	                if(indexs.length > 1){
				   	                	$this.message({
				   	                    	type: 'warning',
				   	                        content: '只能选择一条记录进行修改'
				   	                    })
				   	                    return;
				   	                }
				   	                self.modify(indexs[0], $this);
				   	            },
				    	        'delete': function(event, data){
				    	        	var indexs = data.data;
				    	            var $this = $(this);
				    	            if(indexs.length == 0){
				    	            	$this.message({
				    	                	type: 'warning',
				    	                    content: '请选择要删除的记录'
				    	                });
				    	                return;
				    	            }
				    	            var remove = function(){
				    	            	self.remove(indexs, $this);
				    	            };
				    	            $this.confirm({
				    	            	content: '确定要删除所选记录吗?',
				    	                callBack: remove
				    	            });
				    	        }        
							});
						}
						
					}		
				});
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
	//填充数据项状态
	var fillStateSelect = function(select){
		  var contents = [{title:'必选', value: 'M',selected: true}];
	       contents.push({title:'条件选择' , value:'C'});
	       contents.push({title:'可选' , value:'O'});
		select.select({
			contents: contents
		});
	};
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
// 		row.contextmenu({
// 			onItem: function (context, e) {
// 				addRow(null,null,row);
// 		    }
// 		});
		
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
	
	var add = function(grid){
		var self = this;
		var segmentId = grid.attr("segmentId")
        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">新增段</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
        $.get('<%=path%>/RecordSegmentEditConfig.jsp').done(function(html){
            dialog.modal({
                keyboard:false,
                backdrop:false
            }).on({
                'hidden.bs.modal': function(){
                    $(this).remove();
                }
            }).find('.modal-body').html(html);
            var stateSelect = dialog.find('#stateID');
            fillStateSelect(stateSelect);
           	var contents = [{title:'1:1', value: '0',selected: true}];
            contents.push({title:'0：1' , value:'1'});
            contents.push({title:'0：n' , value:'2'});
            contents.push({title:'1：n' , value:'3'});
            var appearTimesSelect = dialog.find('#appearTimesID');
            appearTimesSelect.select({
        		contents: contents
       		});
            initPage(dialog.find('form'));
            dialog.find('[data-toggle="item"]').attr('disabled', true);
            dialog.find('[data-toggle="button"]').attr('disabled', true);
	        $.get( '${pageContext.request.contextPath}/RecordSegment/get/' + segmentId + '.koala').done(function(json){
            	json = json.data;
                var elm;
                for(var index in json){
                	if(index=='recordItems'){
                 		var items = json[index];
                 		for(var i=0;i<items.length;i++){
                 			addRow(dialog.find("#itemTable"),items[i]);
                 		}
                 	}
                    elm = dialog.find('#'+ index + 'ID');
                    if(elm.hasClass('select')){
                    	elm.setValue(json[index]);
                    }else{
                        elm.val(json[index]);
                    }
                }
            });
        });
        dialog.find('#save').on('click',{grid: grid}, function(e){
        	if(!Validator.Validate(dialog.find('form')[0],3))return;
        	if(!validate(dialog)){
        		dialog.find('#save').removeAttr('disabled');
    			return false;
    		}
            var params = getAllData(dialog);
            $.post('${pageContext.request.contextPath}/RecordSegment/add.koala', params).done(function(result){
            	if(result.success ){
                	dialog.modal('hide');
                    e.data.grid.data('koala.grid').refresh();
                    e.data.grid.message({
                    	type: 'success',
                        content: '保存成功'
                    });
                }else{
                    dialog.find('.modal-content').message({
                        type: 'error',
                        content: result.actionError
                    });
                }
            });
        });
	}
	var initPage = function(form){
        form.find('.form_datetime').datetimepicker({
             language: 'zh-CN',
             format: "yyyy-mm-dd",
             autoclose: true,
             todayBtn: true,
             minView: 2,
             pickerPosition: 'bottom-left'
         }).datetimepicker('setDate', new Date());//加载日期选择器
         form.find('.select').each(function(){
              var select = $(this);
              var idAttr = select.attr('id');
              form.find('#'+ idAttr + '_').val($(this).getValue());
              select.on('change', function(){
                  form.find('#'+ idAttr + '_').val($(this).getValue());
              });
         });
	}
</script>
</body>
</html>