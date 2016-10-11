<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<div class="missionDetail" id="missionDetail">
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
var projectId = $('.missionDetail').parent().attr('data-value');
var directorID;
var userId;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	        	            	                	            	                	                     var contents = [{title:'请选择', value: ''}];
	                     	                     form.find('#director_SELECT').select({
	                                            title: '请选择',
	                                            contents: contents
	                                       }).on('change',function(){
	                                           form.find('#directorID_').val($(this).getValue());
	                                       });
	                	            	                	                     var startTimeVal = form.find('#startDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#startDateID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	                	                     var contents = [{title:'请选择', value: ''}];
	                     	                     contents.push({title:'未开始' , value:'0'});
	                     	                     contents.push({title:'进行中20%' , value:'1'});
	                     	                     contents.push({title:'进行中40%' , value:'2'});
	                     	                     contents.push({title:'进行中60%' , value:'3'});
	                     	                     contents.push({title:'进行中80%' , value:'4'});
	                     	                     contents.push({title:'完成100%' , value:'5'});
	                     	                     contents.push({title:'中断' , value:'6'});
	                     	                     contents.push({title:'推迟' , value:'7'});
	                     	                     contents.push({title:'其它' , value:'8'});
	                     	                     form.find('#status_SELECT').select({
	                                            title: '请选择',
	                                            contents: contents
	                                       }).on('change',function(){
	                                           form.find('#statusID_').val($(this).getValue());
	                                       });
	                	            	                	                     var startTimeVal = form.find('#endDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#endDateID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         if(projectId == 'undefined'){
	        	 projectId = "";
	         }
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},	                      
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-wrench"></span>&nbsp;禁用</button>', action : 'forbidden'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-wrench"></span>&nbsp;激活</button>', action : 'available'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	                    ],
	                url:"${pageContext.request.contextPath}/Mission/pageJson/" + projectId + ".koala",
	                columns: [
	                     	                         	                         { title: '任务名称', name: 'name', width: 110},
	                         	                         	                         	                         { title: '任务负责人', name: 'directorName', width: 80},
	                         	                         	                         	                         { title: '任务开始时间', name: 'startDate', width: 110},
	                         	                         	                         	                         { title: '任务结束时间', name: 'endDate', width: 110},
	                         	                         	                         	                         { title: '任务状态', name: 'status', width: 80},
	                         	                         	                         	                         { title: '任务描述', name: 'description', width: 130},
	                         	                         	                         	                     	 { title: '任务建立人', name: 'taskCreator', width: 80},
	                         	                         	                         	                   		 { title: '任务建立时间', name: 'taskCreatedTime', width: 110},
	                         	                         	                         	                   		 { title : "是否有效", name : "disabled", width : 80,
	                         	                         	                         								render : function(item, name, index) {
	                         	                         	                         									return item[name]?  '<span class="glyphicon glyphicon-remove" style="color:#D9534F;margin-left:15px;"></span>':'<span class="glyphicon glyphicon-ok" style="color:#5CB85C;margin-left:15px;"></span>';
	                         	                         	                         								}
	                         	                         	                         	                   		 },
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看所属项目</a> ";
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
	                            content: '若有关联报文,删除任务也将删除该任务下所有关联报文,确定要删除吗?',
	                            callBack: remove
	                        });
	                   },
	                   'forbidden' : function(event, data) {
							var indexs = data.data;
							var $this = $(this);
							if (indexs.length == 0) {
								$this.message({
									type : 'warning',
									content : '请选择一条记录进行修改'
								});
								return;
							}
							if (indexs.length > 1) {
								$this.message({
									type : 'warning',
									content : '只能选择一条记录进行修改'
								});
								return;
							}							
							self.forbidden(indexs[0] , $this);
						},
						'available' : function(event, data) {
							var indexs = data.data;
							var $this = $(this);
							if (indexs.length == 0) {
								$this.message({
									type : 'warning',
									content : '请选择一条记录进行修改'
								});
								return;
							}
							if (indexs.length > 1) {
								$this.message({
									type : 'warning',
									content : '只能选择一条记录进行修改'
								});
								return;
							} 
							self.available(indexs[0] , $this);
					   },
	                   'search' : function() {						
	       					$("#missionQueryDiv").slideToggle("slow");						 
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
	        $.get('<%=path%>/Mission-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false,
	                backdrop: 'static'
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            userId = directorID.find('input');
	            dialog.find('#save').on('click',{grid: grid}, function(e){
		              if(!Validator.Validate(dialog.find('form')[0],3))return;
		              $.post('${pageContext.request.contextPath}/Mission/add.koala?projectId=' + projectId, dialog.find('form').serialize()).done(function(result){
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
	        });
	        
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/Mission-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               userId = directorID.find('input');
	               $.get( '${pageContext.request.contextPath}/Mission/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        var director = json.director;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(index == 'status'){
	                                elm.setValue(json[index]);
	                            }else if(index == 'director'){
	                            	elm.find('[data-toggle="item"]').html(json.directorName);
	                            	userId.val(director);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
	                });
	                dialog.modal({
	                    keyboard:false,
		                backdrop: 'static'
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $.post('${pageContext.request.contextPath}/Mission/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	               var selectItems = {};
				   var contents = [{title:'请选择', value: ''}];
				   contents.push({title:'未开始' , value:'未开始'});
				   contents.push({title:'进行中20%' , value:'进行中20%'});
				   contents.push({title:'进行中40%' , value:'进行中40%'});
				   contents.push({title:'进行中60%' , value:'进行中60%'});
				   contents.push({title:'进行中80%' , value:'进行中80%'});
				   contents.push({title:'完成100%' , value:'完成100%'});
				   contents.push({title:'中断' , value:'中断'});
				   contents.push({title:'推迟' , value:'推迟'});
				   contents.push({title:'其它' , value:'其它'});
				   selectItems['statusID'] = contents;
	               form.find('#statusID').each(function(){
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#statusID_').val($(this).getValue());
	                    });
	               });
	               
	               directorID = form.find("#directorID");
	               form.find('[data-toggle="dropdown"]').on('click', function(){
	            	   selectUsers();
	               });
	               var selectUsers = function() {	
	                   $.get(contextPath + '/pages/auth/user-select.jsp').done(function(data) {
	                	   var dialog = $(data);
	                       //显示对话框数据
	                       dialog.modal({
	                           keyboard: false,
	                           backdrop: false // 指定当前页为静态页面，如果不写后台页面就是黑色。
	                       }).on({
	                           'hidden.bs.modal': function(){
	                               $(this).remove();
	                           },
	                           'shown.bs.modal': function(){
	                               var columns = [
	                                   { title:'用户名称', name:'name' , width: 100},
	                                   { title:'用户账号', name:'userAccount', width: 100},
	                                   //{ title:'关联员工', name:'employeeName', width: 100},
	                                   { title:'创建人', name:'createOwner', width: 100},
	                                   { title:'创建时间', name:'createDate', width: 100},
	                                   { title:'最后更改时间', name:'lastModifyTime', width: 100},
	                                   { title:'用户描述', name:'description', width: 100},
	                                   { title:'是否有效', name:'disabled', width: 100},
	                               ];//<!-- definition columns end -->

	                               //查询出当前表单所需要得数据。
	                               dialog.find('.selectUserGrid').grid({
	                                   identity: 'id',
	                                   columns: columns,
	                                   url: contextPath + '/auth/user/pagingQuery.koala'
	                               });

	                           }
	                       });//<!-- 显示对话框数据结束-->

	                       dialog.find('#selectUserGridSave').on('click',function(){
	                           var items = dialog.find('.selectUserGrid').data('koala.grid').selectedRows();
	                           if(items.length == 0){
	                               dialog.find('.selectUserGrid').message({
	                                   type: 'warning',
	                                   content: '请选择需要关联的用户！'
	                               });
	                           }
	                           if(items.length>1) {
	                               dialog.find('.selectUserGrid').message({
	                                   type: 'warning',
	                                   content: '只能选择一个需要关联的用户！'
	                               });
	                           }
	                           var userId =  items[0].id;
	                           var userName = items[0].name;	                          
	                           dialog.modal('hide');
	                           if(userId != null){
	                        	   directorID.find('[data-toggle="item"]').html(userName);
	                           }
	                           directorID.find('input').val(userId);
	                           dialog.trigger('keydown');	                        
	                       });

	                       //兼容IE8 IE9
	                       if(window.ActiveXObject){
	                           if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
	                               dialog.trigger('shown.bs.modal');
	                           }
	                       }

	                   });
	               };
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Mission/delete.koala', data).done(function(result){
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
 		/*
		 禁用
		 */
		forbidden: function(id, grid) {
			var dataGrid = grid;
			$.post(contextPath + '/Mission/suspend.koala?missionId=' + id).done(function(data) {			    
				if (data.success == true) {				
					dataGrid.message({
						type : 'success',
						content : '任务禁用成功！'
					});
					grid.grid('refresh');
				} else {
					dataGrid.message({
						type : 'error',
						content : data.errorMessage
					});
				}
			}).fail(function(data) {
				dataGrid.message({
					type : 'error',
					content : '禁用失败'
				});
			});
		},
	    /*
		 * 激活
		 */
		available: function(id, grid) {
			var dataGrid = grid;
			$.post(contextPath + '/Mission/activate.koala?missionId=' + id).done(function(data) {		    
				if (data.success == true) {				
					dataGrid.message({
						type : 'success',
						content : '任务已经激活！'
					});
					grid.grid('refresh');
				} else {
					dataGrid.message({
						type : 'error',
						content : data.errorMessage
					});
				}
			}).fail(function(data) {
				dataGrid.message({
					type : 'error',
					content : '激活失败'
				});
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
        $.get('<%=path%>/Project-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/Mission/getProject/' + id + '.koala').done(function(json){
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
                    keyboard:false,
	                backdrop: 'static'
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
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<div id="missionQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">任务名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="name" class="form-control" type="text" style="width:180px;" id="nameID"  />
        </div>
<!--                       <label class="control-label" style="width:100px;float:left;">任务负责人:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="director_SELECT"></div>
	        <input type="hidden" id="directorID_" name="director" />
	      </div>
	  </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">任务开始时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="startDate" id="startDateID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="startDateEnd" id="startDateID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
       </div>
                      <label class="control-label" style="width:100px;float:left;">任务状态:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="status_SELECT"></div>
	        <input type="hidden" id="statusID_" name="status" />
	      </div>
	  </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">任务结束时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="endDate" id="endDateID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="endDateEnd" id="endDateID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div> -->
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
</div>