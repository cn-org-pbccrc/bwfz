<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
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
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/Submission/pageJson.koala",
	                columns: [
	                     	                         	                         { title: '报文名称', name: 'name', width: width},
	                         	                         	                         	                         { title: '记录数', name: 'recordNum', width: width},
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'><span class='glyphicon glyphicon glyphicon-eye-open'></span>&nbsp查看报文头</a> "
	                                     +"&nbsp;&nbsp;<a href='javascript:recordEdit(" + param + ")'><span class='glyphicon glyphicon glyphicon-edit'></span>&nbsp编辑记录</a> ";
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
	                        console.log(data)
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
	    	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1040px;">'
	    			+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	    		    +'data-dismiss="modal" aria-hidden="true">&times;</button>'
	    		    +'<h4 class="modal-title">编辑报文</h4></div><div class="modal-body">'
	    		    +'<p>One fine body&hellip;</p></div></div>'
	    		    +'</div></div>');
	    	$.get('<%=path%>/Submission-add.jsp').done(function(html){
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
	    	 	            { title:'创建人员', name:'createdBy'}	    	                    
	    	 	    	];//<!-- definition columns end -->
	    	 	    	//查询出当前表单所需要得数据。
	    	 	    	dialog.find('.selectRecordGrid').grid({
	    	 	    		identity: 'id',
	    	 	        	columns: columns,
	    	 	        	url: contextPath + '/RecordType/pageJson.koala'
	    	 	    	});
	         		}
	    	    }).find('.modal-body').html(html);
				dialog.find('#sub').on('click',{grid: grid}, function(e){
					var content = getAllData(dialog);
			        var data = [{ name: 'name', value: dialog.find('#nameID').val()},
			 				    { name: 'content', value: content}
			 		];
		            $.post('${pageContext.request.contextPath}/Submission/add.koala', data).done(function(result){
		            	if(result.success ){
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
		        });
	    	});
	        
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/Submission-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/Submission/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
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
	                    $.post('${pageContext.request.contextPath}/Submission/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/Submission-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/Submission/get/' + id + '.koala').done(function(json){
                       json = json.data;
                        var elm;
                        for(var index in json){
                        if(json[index]+"" == "false"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        	}else if(json[index]+"" == "true"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
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

var getAllData = function(dialog){
	var itemId;
	var itemValue;
	var itemType;
	var itemLength;
	
	var data = '{';
	var itemTable = dialog.find("#itemTable");
	itemTable.find('tr').each(function(index,tr){
		var $tr = $(tr);
		itemId = $tr.find('input[data-role="itemId"]').val();
		itemValue = $tr.find('input[data-role="itemValue"]').val();
		itemType = $tr.find('.select[data-role="itemType"]').getValue();
		itemLength = $tr.find('input[data-role="itemLength"]').val();
		
		var len = itemValue.length;
		if(len < itemLength){
			if(itemType == 0){
			    while(len < itemLength) {  
			    	itemValue = "0" + itemValue;  
			        len++;  
			    }  
			}else{
				while(len < itemLength) {  
			    	itemValue = itemValue + " ";  
			        len++;  
			    }
			}
		}else{
			
		}
		data += '"' + itemId + '" : "' + itemValue + '",';
	});
	
	data = data.substring(0, data.length - 1) + '}';
	return data;
};

var mark;
function recordEdit(id){
    var thiz = $(this);
    var  mark = thiz.attr('mark');
    mark = openTab("/pages/generation/Record-list.jsp", "编辑记录",'OpenRecordList',id);
    if(mark){
        thiz.attr("mark",mark);
    }
}
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