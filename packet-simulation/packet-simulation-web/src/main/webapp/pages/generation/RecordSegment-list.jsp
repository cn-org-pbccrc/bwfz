<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var grid;
var form;
var tabData = $('.tab-pane.active').data();
var typeId = tabData.typeId;
var _dialog;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	        	            	                	            	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/RecordSegment/pageJson.koala?RecordTypeDTO.id="+typeId,
	                columns: [
        	                         { title: '段名称', name: 'segName', width: 100},
           	                         { title: '段标', name: 'segMark', width: 100},
           	                         { title: '出现次数', name: 'appearTimes', width: 100,
           	                        	 render: function(item, name, index){
   						                 if(item[name] == '0'){
   						                     return '1:1';
   						                 }else if(item[name] == '1'){
   						                     return '0：1';
   						                 }else if(item[name] == '2'){
   						                     return '0：n';
   						                 }else if(item[name] == '3'){
   						                     return '1:n';
   						                 }
   						             }},
           	                         { title: '状态', name: 'state', width: 50},
           	                         { title: '长度', name: 'segLength', width: 50},
            	                     { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
	                                     return h;
	                                 }
	                             }
	                ]
	         }).on({
	                   'add': function(){
	                       self.add($(this));
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
	    },
	    add: function(grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">新增段</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/RecordSegmentConfig.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
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
		        dialog.find('[data-role="addRow"]').on('click', function(e){
	            	addRow();
	            })
	            self.initPage(dialog.find('form'));
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
// 	        	$(this).attr('disabled', 'disabled');
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
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改段</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/RecordSegmentConfig.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
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
			        dialog.find('[data-role="addRow"]').on('click', function(e){
		            	addRow();
		            })
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/RecordSegment/get/' + id + '.koala').done(function(json){
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
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	    	        	if(!validate(dialog)){
	    	        		dialog.find('#save').removeAttr('disabled');
	            			return false;
	            		}
	    	              var params = getAllData(dialog);
	    	              params.version = dialog.find('#versionID').val();
	                    $.post('${pageContext.request.contextPath}/RecordSegment/update.koala?id='+id, params).done(function(result){
	                        if(result.success){
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
	        });
	    },
	    initPage: function(form){
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
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/RecordSegment/delete.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '删除成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	form.find('#search').on('click', function(){
            if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
            var params = {};
            form.find('input').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                if(name){
                    params[name] = $this.val();
                }
            });
            grid.getGrid().search(params);
        });
});

var getAllData = function(dialog){
	var data = {};
	data['segName'] = dialog.find("#segNameID").val();
	data['segMark'] = dialog.find("#segMarkID").val();
	data['segDesc'] = dialog.find("#segDescID").val();
	data['state'] = dialog.find("#stateID").getValue();
	data['appearTimes'] = dialog.find("#appearTimesID").getValue();
	data['recordTypeDTO.id'] = typeId;
	var segmentLength = 0;
	var itemTable = dialog.find("#itemTable");
	itemTable.find('tr').each(function(index,tr){
		var $tr = $(tr);
		data['RecordItems['+index+'].itemId'] = $tr.find('input[data-role="itemId"]').val();
		data['RecordItems['+index+'].itemName'] = $tr.find('input[data-role="itemName"]').val();
		data['RecordItems['+index+'].itemType'] = $tr.find('.select[data-role="itemType"]').getValue();
		data['RecordItems['+index+'].itemLength'] = $tr.find('input[data-role="itemLength"]').val();
		data['RecordItems['+index+'].itemLocation'] = $tr.find('input[data-role="itemLocation"]').val();
		segmentLength =segmentLength + parseInt($tr.find('input[data-role="itemLength"]').val());
		data['RecordItems['+index+'].itemDesc'] = $tr.find('input[data-role="itemDesc"]').val();
		data['RecordItems['+index+'].state'] = $tr.find('.select[data-role="state"]').getValue();
	});
	data['segLength'] = segmentLength;
	return data;
};

var calculateLocation = function(itemTable){
	var LocationStart = 1;
	itemTable.find('tr').each(function(index,tr){
		var $tr = $(tr);
		var itemLength = $tr.find('input[data-role="itemLength"]').val();
		var locationEnd =  parseInt(LocationStart) +  parseInt(itemLength) - 1;
		$tr.find('input[data-role="itemLocation"]').val(LocationStart+"-"+locationEnd);
		LocationStart = locationEnd + 1;
	});
}

/*
* 数据验证  成功返回true  失败返回false
 */
var validate = function(dialog){
	var flag = true;
	dialog.find('.recordItemConfig').find('input[required=true],input[rgExp]').each(function(){
		var $this = $(this);
		var value = $this.val();
		if($this.attr('required') && !checkNotNull(value)){
			showErrorMessage(dialog, $this, '请填入该选项内容');
			flag = false;
			return false;
		}
		var rgExp = $this.attr('rgExp');
		if(rgExp && !value.match(eval(rgExp))){
			showErrorMessage(dialog, $this, $this.data('content'));
			flag = false;
			return false;
		}
	});
	dialog.find('.recordItemConfig').find('.select').each(function(){
		var $this = $(this);
		var value = $this.getValue();
		if(!checkNotNull(value)){
			showErrorMessage(dialog, $this, '请选择该选项内容');
			flag = false;
			return false;
		}
		
	});
	return flag;
};


/**
 * 显示提示信息
 */
var showErrorMessage = function(container, $element, content){
	$element.popover({
		content: content,
		trigger: 'manual',
		container: container
	}).popover('show').on({
			'blur': function(){
				$element.popover('destroy');
				$element.parent().removeClass('has-error');
			},
			'keydown': function(){
				$element.popover('destroy');
				$element.parent().removeClass('has-error');
			}
		}).focus().parent().addClass('has-error');
};

/**
 * 检查变量是否不为空  true:不空   false:空
 */
var checkNotNull = function(item){
	//不能为空和空格
	if(item==null || item=="" || item.replace(/(^\s*)|(\s*$)/g, "")=="" ){
		return false;
	}else{
		return true;
	}
};

var addRow = function(itemTable, variable, insertRow){
	var row = $('<tr data-toggle="context" data-target="#context-menu"><td class="v-itemId"><input  data-role="itemId" required="true" style="display: inline; " class="form-control" type="text" /></td>'
	+'<td class="v-itemName"><input  data-role="itemName" required="true" style="display: inline; " class="form-control" type="text" /></td>'
	+'<td class="v-itemType"><div class="btn-group select" id="itemTypeSelect" data-role="itemType"></div></td>'
	+'<td class="v-itemLength"><input data-role="itemLength" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字"/></td>'
	+'<td class="v-itemLocation"><input  data-role="itemLocation" readonly="true" required="true" style="display: inline; " class="form-control" type="text" /></td>'
	+'<td class="v-itemDesc"><input  data-role="itemDesc" required="true" style="display: inline; " class="form-control" type="text" /></td>'
	+'<td class="v-state"><div class="btn-group select" id="itemStateSelect" data-role="state"></div></td>'
	+'<td class="delete-btn"><a data-role="delete"><span class="glyphicon glyphicon-remove">删除</span></a></td>');
	
	row.find("[data-role='itemLength']").on('change', function(){
		calculateLocation(itemTable);
	})
	row.contextmenu({
		onItem: function (context, e) {
			addRow(null,null,row);
	        }
	});
	
	if(!itemTable){
		itemTable=$('#itemTable')
	}
	var itemType = row.find('[data-role="itemType"]')
	var itemState = row.find('[data-role="state"]')
	fillVTypeSelect(itemType);
	fillStateSelect(itemState);
	if(variable){
		row.find('input[data-role="itemId"]').val(variable.itemId);
		row.find('input[data-role="itemName"]').val(variable.itemName);
		row.find('input[data-role="itemLength"]').val(variable.itemLength);
		row.find('input[data-role="itemLocation"]').val(variable.itemLocation);
		row.find('input[data-role="itemDesc"]').val(variable.itemDesc);
		row.find('.select[data-role="itemType"]').setValue(variable.itemType);
		row.find('.select[data-role="state"]').setValue(variable.state);
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

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/RecordSegment-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/RecordSegment/get/' + id + '.koala').done(function(json){
                       json = json.data;
                        var elm;
                        for(var index in json){
                        if(json[index]+"" == "false"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        	}else if(json[index]+"" == "true"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                        	}else if(index+"" == "appearTimes"){
                        	var appearTimes = "";
                        		switch (parseInt(json[index])){
                        	  case 0: appearTimes ="1:1";
                        	    break;
                        	  case 1: appearTimes ="0:1";
                        	    break;
                        	  case 2: appearTimes ="0:n";
                        	    break;
                        	  case 3: appearTimes ="1:n";
                        	    break;
                        		}
                        	   dialog.find('#'+ index + 'ID').html(appearTimes);
                        	}else{
                          		 dialog.find('#'+ index + 'ID').html(json[index]+"");
                        	}
                        }
               });
                dialog.modal({
                    keyboard:false
                }).on({
                    'hidden.bs.modal': function(){
                        $(this).remove();
                    }
                });
        });
}
</script>
</head>
<body>
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">段名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="segName" class="form-control" type="text" style="width:180px;" id="segNameID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">段标:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="segMark" class="form-control" type="text" style="width:180px;" id="segMarkID"  />
        </div>
            </div>
            </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</body>
</html>
