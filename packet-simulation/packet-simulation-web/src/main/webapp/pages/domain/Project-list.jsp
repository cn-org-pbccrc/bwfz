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
var grid;
var form;
var _dialog;
var projectManagerID = null;
var employeeId = null;
var userId = null;
var userName = null;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	        	         var startTimeVal = form.find('#projectstartDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#projectstartDateID_end');
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
	                           }).trigger('changeDate');//加载日期选择器
	                           startTime.trigger('changeDate');
	                     //startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                        //   endTime.datetimepicker('setDate',yesterday).trigger('changeDate');
	                	 var startTimeVal2 = form.find('#projectendDateID_start');
	                     var startTime2 = startTimeVal2.parent();
	                     var endTimeVal2 = form.find('#projectendDateID_end');
	                     var endTime2 = endTimeVal2.parent();
	                     startTime2.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime2.datetimepicker('setStartDate', startTimeVal2.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime2.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime2.datetimepicker('setEndDate', endTimeVal2.val());
	                           }).trigger('changeDate');//加载日期选择器
	                     startTime2.trigger('changeDate');
	                     //startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
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
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	                    ],
	                url:"${pageContext.request.contextPath}/Project/pageJson.koala",
	                columns: [
	                     	                         	                         { title: '项目名称', name: 'projectName', width: width},
	                         	                         	                         	                         { title: '项目编码', name: 'projectCode', width: width},
	                         	                         	                         	                         { title: '项目经理', name: 'projectManagerName', width: width},
	                         	                         	                         	                         { title: '项目开始时间', name: 'projectstartDate', width: width},
	                         	                         	                         	                         { title: '项目结束时间', name: 'projectendDate', width: width},
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
	                   },
	                   'search' : function() {						
	       				$("#projectQueryDiv").slideToggle("slow");						 
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
	        $.get('<%=path%>/Project-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            //employeeId = projectManagerID.find('input');
	            userId = projectManagerID.find('input');
	            //var a = getReposiory().get(User,userId).userName;
	            //alert(a);
	            
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){	        	  
	              //alert(userId.val());
	              if (!Validation.notNull(dialog, dialog.find('#projectNameID'), dialog.find('#projectNameID').val(), '请输入项目名称')) {
		    			return false;
		    	  }
	              if (!Validation.checkByRegExp(dialog, dialog.find('#projectNameID'), '^[^ ]{1,}$', dialog.find('#projectNameID').val(), '项目名称不能包含空格')) {
						return false;
				  }
		          if (!Validation.checkByRegExp(dialog, dialog.find('#projectNameID'), '^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$', dialog.find('#projectNameID').val(), '项目名称长度不能超过20个字符')) {
						return false;
				  }
		          if (!Validation.notNull(dialog, dialog.find('#projectCodeID'), dialog.find('#projectCodeID').val(), '请输入项目编码')) {
		    			return false;
		    	  }
		          if (!Validation.checkByRegExp(dialog, dialog.find('#projectCodeID'), '^[^ ]{1,}$', dialog.find('#projectCodeID').val(), '项目编码不能包含空格')) {
						return false;
				  }
		          if (!Validation.checkByRegExp(dialog, dialog.find('#projectCodeID'), '^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$', dialog.find('#projectCodeID').val(), '项目编码长度不能超过20个字符')) {
						return false;
				  }
		          var beginDate = dialog.find('#projectstartDateID').val();
		          var endDate = dialog.find('#projectendDateID').val();
		          if(Number(getDate(beginDate))<20100101 || Number(getDate(beginDate))>20991231){
		            	showErrorMessage(dialog, dialog.find('#projectstartDateID'), "项目开始时间有效输入范围只能是2010年1月1日-2099年12月31日");	            	
		            	return false;		
		          }
		          if(Number(getDate(endDate))<20100101 || Number(getDate(endDate))>20991231){
		            	showErrorMessage(dialog, dialog.find('#projectendDateID'), "项目结束时间有效输入范围只能是2010年1月1日-2099年12月31日");	            	
		            	return false;		
		          }
		          if(beginDate!='' && endDate!='' && (getDate(beginDate)-getDate(endDate)>0)){
		        	  showErrorMessage(dialog, dialog.find('#projectendDateID'), "结束时间不能小于开始时间");
		        	  dialog.find('#save').setAttr('disabled');
		        	  return false;
		          }
		          function getDate(date){
		        	  var dates = date.split("-");
		        	  var dateReturn = '';	        	  
		        	  for(var i=0; i<dates.length; i++){
		        	   dateReturn+=dates[i];
		        	  }
		        	  return dateReturn;
		          }		          
		          if (!Validation.notNull(dialog, dialog.find('#projectManagerID'), userId.val(), '请选择项目经理')) {
	    			    return false;
	    		  }
	              if(!Validator.Validate(dialog.find('form')[0],3))return;	       
	              $.post('${pageContext.request.contextPath}/Project/add.koala', dialog.find('form').serialize()).done(function(result){	     
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
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/Project-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               userId = projectManagerID.find('input');
	               $.get( '${pageContext.request.contextPath}/Project/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
 	                            if(elm.hasClass('select')){
 	                                //elm.setValue(json[index]);
 	                                elm.find('[data-toggle="item"]').html(json.projectManagerName); 
 	                                userId.val(json.projectManager);
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
	                  	if (!Validation.notNull(dialog, dialog.find('#projectNameID'), dialog.find('#projectNameID').val(), '请输入项目名称')) {
			    			return false;
			    	  	}
	                  	if (!Validation.checkByRegExp(dialog, dialog.find('#projectNameID'), '^[^ ]{1,}$', dialog.find('#projectNameID').val(), '项目名称不能包含空格')) {
							return false;
					    }
			         	if (!Validation.checkByRegExp(dialog, dialog.find('#projectNameID'), '^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$', dialog.find('#projectNameID').val(), '项目名称长度不能超过20个字符')) {
							return false;
					 	}
			         	if (!Validation.notNull(dialog, dialog.find('#projectCodeID'), dialog.find('#projectCodeID').val(), '请输入项目编码')) {
			    			return false;
			    	  	}
			         	if (!Validation.checkByRegExp(dialog, dialog.find('#projectCodeID'), '^[^ ]{1,}$', dialog.find('#projectCodeID').val(), '项目编码不能包含空格')) {
							return false;
					    }
			            if (!Validation.checkByRegExp(dialog, dialog.find('#projectCodeID'), '^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$', dialog.find('#projectCodeID').val(), '项目编码长度不能超过20个字符')) {
							return false;
					    }
			            var beginDate = dialog.find('#projectstartDateID').val();
			            var endDate = dialog.find('#projectendDateID').val();
			            if(Number(getDate(beginDate))<20100101 || Number(getDate(beginDate))>20991231){
			            	showErrorMessage(dialog, dialog.find('#projectstartDateID'), "项目开始时间有效输入范围只能是2010年1月1日-2099年12月31日");
			            	return false;		
			            }
			            if(Number(getDate(endDate))<20100101 || Number(getDate(endDate))>20991231){
			            	showErrorMessage(dialog, dialog.find('#projectendDateID'), "项目结束时间有效输入范围只能是2010年1月1日-2099年12月31日");	            	
			            	return false;		
			            }
			            if(beginDate!='' && endDate!='' && (getDate(beginDate)-getDate(endDate)>0)){
			        	    showErrorMessage(dialog, dialog.find('#projectendDateID'), "结束时间不能小于开始时间");
			        	    dialog.find('#save').setAttr('disabled');
			        	    return false;
			            }
			            function getDate(date){
			        	    var dates = date.split("-");
			        	    var dateReturn = '';	        	  
			        	    for(var i=0; i<dates.length; i++){
			        	        dateReturn+=dates[i];
			        	    }
			        	    return dateReturn;
			            }
			          if (!Validation.notNull(dialog, dialog.find('#projectManagerID'), userId.val(), '请选择项目经理')) {
		    			    return false;
		    		  }
	                  if(!Validator.Validate(dialog.find('form')[0],3))return;
	                  $.post('${pageContext.request.contextPath}/Project/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	               projectManagerID = form.find("#projectManagerID");
	               //alert(projectManagerID);
	               /*form.find('.select').each(function(){	            	   
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               });*/
	               
	              /*form.find('.select').each(function(){
	            	  var select = $(this);
	            	  $.get( contextPath + '/job/query-all.koala').done(function(data){
	                 
	                    
	                    var items = data;
	        			var contents = new Array();
	        			for(var i= 0, j=items.length; i<j; i++){
	        				var item = items[i];
	        				contents.push({value: item.id, title: item.name});
	        			}
	        			select.select({
	        				title: '选择职务',
	        				contents: contents
	        			});
	            	  })
	               });*/
// 	               if(projectManagerID.id != null){
// 	                   form.find('[data-toggle="item"]').html(projectManagerID.id);
// 	               }
	               
	               form.find('[data-toggle="dropdown"]').on('click', function(){

	            	   //selectEmployees();
	            	   selectUsers();

	               })
	               /**
	                * 选择员工
	                */
	               var selectEmployees = function() {	            
	                   $.get(contextPath + '/pages/auth/employee-select.jsp').done(function(data) {
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
	                                   { title:'姓名', name:'name' , width: 100},
	                                   { title:'员工编号', name:'sn', width: 100},
	                                   { title:'性别', name:'gender', width: 60,
	                                       render: function(item, name, index){
	                                           if(item[name] == 'FEMALE'){
	                                               return '女';
	                                           }else{
	                                               return '男';
	                                           }
	                                       }
	                                   },
	                                   { title:'入职日期', name:'entryDate', width: 100},
	                                   { title:'所属机构', name:'organizationName', width: 210},
	                                   { title:'岗位', name:'postName', width: 180},
	                                   { title:'兼职岗位', name:'additionalPostNames', width: 180}
	                               ];//<!-- definition columns end -->

	                               //查询出当前表单所需要得数据。
	                               dialog.find('.selectEmployeeGrid').grid({
	                                   identity: 'id',
	                                   columns: columns,
	                                   url: contextPath + '/employee/pagingquery.koala'
	                               });

	                           }
	                       });//<!-- 显示对话框数据结束-->

	                       dialog.find('#selectEmployeeGridSave').on('click',function(){
	                           var items = dialog.find('.selectEmployeeGrid').data('koala.grid').selectedRows();
	                           if(items.length == 0){
	                               dialog.find('.selectEmployeeGrid').message({
	                                   type: 'warning',
	                                   content: '请选择需要关联的员工！'
	                               });
	                           }
	                           if(items.length>1) {
	                               dialog.find('.selectEmployeeGrid').message({
	                                   type: 'warning',
	                                   content: '只能选择一个需要关联的员工！'
	                               });
	                           }
	                           var employeeId =  items[0].id;
	                           var employeeName = items[0].name;	                          
	                           dialog.modal('hide');
	                           if(employeeId != null){
	                        	   projectManagerID.find('[data-toggle="item"]').html(employeeName);
	                           }
	                           projectManagerID.find('input').val(employeeId);
	                           dialog.trigger('keydown');
	                       });

	                       //兼容IE8 IE9
	                       if(window.ActiveXObject){
	                           if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
	                               dialog.trigger('shown.bs.modal');
	                           }
	                       }

	                   });      //})
	               };//<!-- 选择员工结束-->
	               
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
	                        	   projectManagerID.find('[data-toggle="item"]').html(userName);
	                           }
	                           projectManagerID.find('input').val(userId);
	                           dialog.trigger('keydown');	                        
	                       });

	                       //兼容IE8 IE9
	                       if(window.ActiveXObject){
	                           if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
	                               dialog.trigger('shown.bs.modal');
	                           }
	                       }

	                   });      //})
	               };
	                   
	               
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Project/delete.koala', data).done(function(result){
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
	
	};
	    	
		
      
	
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
               $.get( '${pageContext.request.contextPath}/Project/get/' + id + '.koala').done(function(json){
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
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">


<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<div id="projectQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">项目名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="projectName" class="form-control" type="text" style="width:180px;" id="projectNameID"  dataType="Regex"  validateExpr="/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$/" require="false" />
        </div>
                      
           <label class="control-label" style="width:110px;float:left;">项目开始时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="projectstartDate" id="projectstartDateID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="projectstartDateEnd" id="projectstartDateID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
       </div>
            </div>
            
                  <div class="form-group">
                   
          
       <label class="control-label" style="width:100px;float:left;">项目编码:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="projectCode" class="form-control" type="text" style="width:180px;" id="projectCodeID"  dataType="Regex"  validateExpr="/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$/" require="false" />
        </div>
        <label class="control-label" style="width:110px;float:left;">项目结束时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="projectendDate" id="projectendDateID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="projectendDateEnd" id="projectendDateID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
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
