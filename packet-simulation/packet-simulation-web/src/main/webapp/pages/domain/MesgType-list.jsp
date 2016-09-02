<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp --> --%>
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var currentUserId;
$.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function(json) {
	json = json.data;
 	currentUserId = json['userAccount']; 	
 	initFun();
});
var grid;
var form;
var _dialog;
function initFun(){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){	        	            	                	            	                	            	                	            	        	     },
	    initGridPanel: function(){
	        var self = this;
	        var width = 180;
	        return grid.grid({
	        	identity:"id",
	        	buttons: [
	            	{content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	            	{content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	            	{content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
                    {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-up"><span>上移</button>', action: 'up'},
                    {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-down"><span>下移</button>', action: 'down'},
                    {content: '<button class="btn btn-warning" type="button"><span class="glyphicon glyphicon-refresh"><span>刷新</button>', action: 'fresh'},
	            	{content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	        	],
	        	url:"${pageContext.request.contextPath}/MesgType/pageJson.koala",
	        	columns: [
	            	{ title: '报文类型', name: 'mesgType', width: 240},
	            	{ title: '类型代码', name: 'code', width: 140},
	            	{ title: '业务类型', name: 'bizType', width: 140},
	            	{ title: '显示顺序', name: 'sort', width: 140},
	            	{ title: '创建人员', name: 'createdBy', width: 160},
	            	{ title: '操作', width: 120, render: function (rowdata, name, index)
	                	{
	                    	var param = '"' + rowdata.id + '"';
	                    	var h = "<a href='javascript:openDetailsPageOfMesgType(" + param + ")'>查看</a> ";
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
	        	'up':function(event, data){
                    var indexs = data.data;
                    var $this = $(this);
                    if(indexs.length == 0){
                        $this.message({
                            type: 'warning',
                            content: '请选择一条记录进行上移'
                        })
                        return;
                    }
                    if(indexs.length > 1){
                        $this.message({
                            type: 'warning',
                            content: '只能选择一条记录进行上移'
                        })
                        return;
                    }
                   self.up($this);
                },
               'down':function(event, data){
                    var indexs = data.data;
                    var $this = $(this);
                    if(indexs.length == 0){
                        $this.message({
                            type: 'warning',
                            content: '请选择一条记录进行下移'
                        })
                        return;
                    }
                    if(indexs.length > 1){
                        $this.message({
                            type: 'warning',
                            content: '只能选择一条记录进行下移'
                        })
                        return;
                    }
                   self.down($this);
                },
                'fresh' : function() {
                	grid.data('koala.grid').refresh();
                },
	        	'search' : function() {						
	            	$("#mesgTypeQueryDiv").slideToggle("slow");						 
	       		}
	    	});
		},
		add: function(grid){
			var self = this;
	    	var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	    	$.get('<%=path%>/MesgType-add.jsp').done(function(html){
	        	dialog.modal({
	            	keyboard:false
	        	}).on({
                	'hidden.bs.modal': function(){
                    	$(this).remove();
                	}
            	}).find('.modal-body').html(html);
            	self.initPage(dialog.find('form'));
        	});
        	dialog.find('#save').on('click',{grid: grid}, function(e){
            	if(!Validator.Validate(dialog.find('form')[0],3))return;
            	if (!Validation.notNull(dialog, dialog.find('#mesgTypeID'), dialog.find('#mesgTypeID').val(), '请输入报文类型')) {
	    			return false;
	    	    }
//             	if (!Validation.checkByRegExp(dialog, dialog.find('#codeID'), '^[0-9]{4}$', dialog.find('#codeID').val(), '类型代码应为4位数字')) {
//  			    	return false;
//  				}
            	if (!Validation.notNull(dialog, dialog.find('#bizTypeID'), dialog.find('#mesgTypeID').val(), '请输入业务类型')) {
	    			return false;
	    	    }
            	if (!Validation.checkByRegExp(dialog, dialog.find('#bizTypeID'), '^[0-9]{2}$', dialog.find('#bizTypeID').val(), '业务类型应为2位数字')) {
 			    	return false;
 				}
            	if (!Validation.notNull(dialog, dialog.find('#transformID'), dialog.find('#transformID').val(), '请输入转换模板')) {
	    			return false;
	    	    }
            	if (!Validation.notNull(dialog, dialog.find('#xmlID'), dialog.find('#xmlID').val(), '请输入基础模板')) {
	    			return false;
	    	    }
            	$.post('${pageContext.request.contextPath}/MesgType/add.koala?createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
                        	content: result.errorMessage
                    	});
                	}
            	});
        	});
    	},
    	modify: function(id, grid){
        	var self = this;
        	var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
        	$.get('<%=path%>/MesgType-update.jsp').done(function(html){
            	dialog.find('.modal-body').html(html);
            	self.initPage(dialog.find('form'));
            	$.get( '${pageContext.request.contextPath}/MesgType/get/' + id + '.koala').done(function(json){
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
                	if (!Validation.notNull(dialog, dialog.find('#mesgTypeID'), dialog.find('#mesgTypeID').val(), '请输入报文类型')) {
    	    			return false;
    	    	    }
//                 	if (!Validation.checkByRegExp(dialog, dialog.find('#codeID'), '^[0-9]{4}$', dialog.find('#codeID').val(), '类型代码应为4位数字')) {
//      			    	return false;
//      				}
                	if (!Validation.notNull(dialog, dialog.find('#bizTypeID'), dialog.find('#bizTypeID').val(), '请输入业务类型')) {
    	    			return false;
    	    	    }
                	if (!Validation.checkByRegExp(dialog, dialog.find('#bizTypeID'), '^[0-9]{2}$', dialog.find('#bizTypeID').val(), '业务类型应为2位数字')) {
     			    	return false;
     				}
                	if (!Validation.notNull(dialog, dialog.find('#transformID'), dialog.find('#transformID').val(), '请输入转换模板')) {
    	    			return false;
    	    	    }
                	if (!Validation.notNull(dialog, dialog.find('#xmlID'), dialog.find('#xmlID').val(), '请输入基础模板')) {
    	    			return false;
    	    	    }
                	$.post('${pageContext.request.contextPath}/MesgType/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
                            	content: result.errorMessage
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
    		$.post('${pageContext.request.contextPath}/MesgType/delete.koala', data).done(function(result){
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
    	},
    	up: function(grid){
	    	if(grid.getGrid().selectedRowsNo()==0){
	    		grid.message({
                     type: 'error',
                     content: "不能继续上移"
                 });
	    		return;
	    	}
	    	var sourceIndex = grid.getGrid().selectedRowsNo();
	    	var sourceId = grid.getGrid().getItemByIndex(sourceIndex).id;
	    	grid.getGrid().up(grid.getGrid().selectedRowsNo());
	    	var destId = grid.getGrid().getItemByIndex(sourceIndex).id;
	    	var data = [{ name: 'sourceId', value: sourceId },
	    	            { name: 'destId', value: destId }
	    	            ];
	    	$.post('${pageContext.request.contextPath}/MesgType/up.koala', data).done(function(result){
	        	if(result.success){
                    grid.message({
	                	type: 'success',
	                    content: '上移成功'
	                });
	            }else{
	                grid.message({
	                    type: 'error',
	                    content: result.errorMessage
	                });
	            }
	    	});
	    },
	    down: function(grid){
	    	if(grid.getGrid().selectedRowsNo()==grid.getGrid().getAllItems().length-1){
	    		grid.message({
                    type: 'error',
                    content: "不能继续下移"
                });
	    		return;
	    	}
	    	var sourceIndex = grid.getGrid().selectedRowsNo();
	    	var sourceId = grid.getGrid().getItemByIndex(sourceIndex).id;
	    	grid.getGrid().down(grid.getGrid().selectedRowsNo());
	    	var destId = grid.getGrid().getItemByIndex(sourceIndex).id;
	    	var data = [{ name: 'sourceId', value: sourceId },
	    	            { name: 'destId', value: destId }
	    	            ];
	    	$.post('${pageContext.request.contextPath}/MesgType/down.koala', data).done(function(result){
	        	if(result.success){
                    grid.message({
	                	type: 'success',
	                    content: '下移成功'
	                });
	            }else{
	                grid.message({
	                    type: 'error',
	                    content: result.errorMessage
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
}

var openDetailsPageOfMesgType = function(id){
	var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
    $.get('<%=path%>/MesgType-view.jsp').done(function(html){
        dialog.find('.modal-body').html(html);
        $.get( '${pageContext.request.contextPath}/MesgType/get/' + id + '.koala').done(function(json){
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
</script>
</head>
<body>
<div>
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<div id="mesgTypeQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>
        	<div class="form-group">
        	
          		<label class="control-label" style="width:100px;float:left;">报文类型:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="mesgType" class="form-control" type="text" style="width:180px;" id="mesgTypeID"  />
        		</div>
        		
                <label class="control-label" style="width:100px;float:left;">类型代码:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="code" class="form-control" type="text" style="width:180px;" id="codeID"  />
        		</div>

        	</div>
       </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
    </tr>
</table>
</div>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>
