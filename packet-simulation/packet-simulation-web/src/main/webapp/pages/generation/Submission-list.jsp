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
var _dialog;

var selectMesg;
var mesgInput;
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
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-export"><span>导出报文</button>', action: 'export'}
	                    ],
	                url:"${pageContext.request.contextPath}/Submission/pageJson.koala",
	                columns: [
	                     	                         	                         { title: '报文名称', name: 'name', width: 240},
	                     	                         	                       		{ title: '报文类型', name: 'recordTypeStr', width: 200},
	                         	                         	                         	                         { title: '记录数', name: 'recordNum', width: 200},
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'><span class='glyphicon glyphicon glyphicon-eye-open'></span>&nbsp查看报文头</a> "
	                                     +"&nbsp;&nbsp;<a href='javascript:editRecord(" + param + ")'><span class='glyphicon glyphicon glyphicon-edit'></span>&nbsp编辑记录</a> ";
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
	                   },
	                   'export': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请至少选择一条记录进行导出'
	                            })
	                            return;
	                        }
	                       self.exportSubmission(indexs, $this);
	                    }
	         });
	    },
	    add: function(grid){
	    	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1040px;">'
	    			+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	    		    +'data-dismiss="modal" aria-hidden="true">&times;</button>'
	    		    +'<h4 class="modal-title">编辑报文</h4></div><div class="modal-body">'
	    		    +'<p>One fine body&hellip;</p></div></div>'
	    		    +'</div></div>');
	    	$.get('<%=path%>/Submission-add.jsp').done({'grid' : grid}, function(html){
	    	    dialog.modal({
	    	        keyboard:false
	    	    }).on({
	    	        'hidden.bs.modal': function(){
	    	            $(this).remove();
	    	        },
	    	    	'shown.bs.modal': function(){
	    	    		var columns = [
	    	 	        	{ title:'报文类型', name:'recordType' , width: 250},
	    	 	            { title:'类型代码', name:'code', width: 120},
	    	 	            { title:'创建人员', name:'createdBy', width: 120},
 	    	 	            { title:'文件类型', name: 'fileType', width: 130},
       	                    { title: '类别', name: 'type', width: 150,
       	                    	render: function(item, name, index){
						        	if(item[name] == '0'){
						            	return '一代个人';
						            }else if(item[name] == '1'){
						               	return '一代企业';
						            }else if(item[name] == '2'){
						               	return '非银';
						            }
						        } 	  
       	                    }
	    	 	    	];//<!-- definition columns end -->
	    	 	    	//查询出当前表单所需要得数据。
	    	 	    	dialog.find('.selectRecordGrid').grid({
	    	 	    		identity: 'id',
	    	 	        	columns: columns,
	    	 	        	querys: [{title: '报文类型', value: 'recordType'},
	    	 	        	         {title: '类型代码', value: 'code'}
	    	 	        			],
	    	 	        	url: contextPath + '/RecordType/pageJson.koala'
	    	 	    	});
	         		}
	    	    }).find('.modal-body').html(html);
	    	    dialog.find('#search').on('click', function(){
		            var params = {};
		            dialog.find('input').each(function(){
		            	var $this = $(this);
		            	var name = $this.attr('name');
		            	if(name){
		            		params[name] = $this.val();
		            	}
		            });
		            dialog.find('.selectRecordGrid').getGrid().search(params);
		        });
			});
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1040px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div></div></div></div>');
	        $.get('<%=path%>/Submission-update.jsp').done(function(html){
	        	dialog.find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            $.get('${pageContext.request.contextPath}/Submission/get/' + id + '.koala').done(function(json){
                	json = json.data;
                    var elm;
                    var inputs = [];
			        var rules = {
						"notnull" : {
							"rule" : function(value, formData){
								return value ? true : false;
							},
							"tip" : "不能为空"
						}
					};
			        if(json['recordType'].type == 2){
						dialog.find('#mark').append('<label class="col-lg-3 control-label">非银填充位:</label><div class="col-lg-9"><input name=padding class="form-control" type="text" dataType="Require" id="paddingID" /><span class="required">*</span></div>');
					}
                    for(var index in json){
                        elm = dialog.find('#'+ index + 'ID');
                        if(index == 'recordType'){
                        	var recordType = json['recordType'];
                            dialog.find('#recordTypeIdID').val(recordType.id);
                            var headerItems = recordType.headerItems;
        					for(var i=0;i<headerItems.length;i++){
        						var data = {};
        	             		var value = {};
        	             		addRow(dialog.find("#itemTable"),headerItems[i]);
        	             		value["rule"] = new RegExp("^.{1," + headerItems[i].itemLength + "}$");
        	             		value["tip"] = "长度大于" + headerItems[i].itemLength;
        	             		rules[headerItems[i].itemId] = value;
        	             		data["name"] = headerItems[i].itemId;
        	             		data["rules"] = ["notnull", headerItems[i].itemId];
        	                	data["focusMsg"] = "必填";
        	                	data["rightMsg"] = "正确";
        	             		inputs.push(data);
        					}
        	            }else if(elm.hasClass('select')){
                            elm.setValue(json[index]);
                        }else{
                            elm.val(json[index]);
                        }
                    }
    				dialog.find('#itemForm').validateForm({
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
			            		var content = getAllData(dialog);
			                    var data = [{ name: 'name', value: dialog.find('#nameID').val()},
						 				    { name: 'content', value: content},
						 				    { name: 'recordTypeId', value: dialog.find('#recordTypeIdID').val()},
						 				    { name: 'version', value: dialog.find('#versionID').val()},
						 				    { name: 'createdBy', value: dialog.find('#createdByID').val()},
						 				    { name: 'recordNum', value: dialog.find('#recordNumID').val()},
						 				    { name: 'padding', value: dialog.find('#paddingID').val()}
						 		];
			                    $.post('${pageContext.request.contextPath}/Submission/update.koala?id='+id, data).done(function(result){
			                        if(result.success){
			                            dialog.modal('hide');
			                            grid.data('koala.grid').refresh();
			                            grid.message({
			                            	type: 'success',
			                            	content: '保存成功'
			                            });
			                        }else{
			                            dialog.find('.modal-content').message({
			                            	type: 'error',
			                            	content: result.errorMessage
			                            });
			                        }
			                    });
			            	}	
    				    }
    				});
                });               
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
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
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               });
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Submission/delete.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '删除成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.errorMessage
	                            });
	                        }
	    	});
	    },
	    exportSubmission: function(ids, grid){
	    	var ids = ids.join(',');
	    	var date = new Date();
	    	window.open('${pageContext.request.contextPath}/Submission/exportSubmissions.koala?ids=' + ids + '&id=' + date.getTime() + '.txt');	    
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

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1040px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/Submission-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/Submission/get/' + id + '.koala').done(function(json){
                   json = json.data;
                    var elm;
                    for(var index in json){
                        elm = dialog.find('#'+ index + 'ID');
                        if(index == 'recordType'){
                        	var recordType = json['recordType'];
                            dialog.find('#recordTypeIdID').val(recordType.id);
                            var headerItems = recordType.headerItems;
    						for(var i=0;i<headerItems.length;i++){
    	             			addRow(dialog.find("#itemTable"),headerItems[i]);
    	             		}
    						dialog.find("#itemTable").find(".delete-btn").remove();
	             			dialog.find("#itemTable").find('[data-role="itemValue"]').attr('disabled', true);
	                    }else if(elm.hasClass('select')){
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
        });
}

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

var mark;
function editRecord(id){
    var thiz = $(this);
    var  mark = thiz.attr('mark');
    mark = openTab("/pages/generation/Record-list.jsp", "编辑记录", 'editRecordList', id);
    if(mark){
        thiz.attr("mark", mark);
    }
}

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
</script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">报文名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="Name" class="form-control" type="text" style="width:180px;" id="NameID"  />
        </div>
                </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>