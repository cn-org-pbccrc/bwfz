<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="feedBackDetail" id="feedBackDetail">
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp --> --%>
<%@ include file="/commons/taglibs.jsp"%>
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
var pathOfMesg=null;
var taskPacketId = $('.feedBackDetail').parent().attr('data-value');
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
	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 220;
	         return grid.grid({
	                identity:"id",
	                url:"${pageContext.request.contextPath}/FeedBack/pageJson/" + taskPacketId + ".koala",
	                columns: [
	                     	                         	                         { title: '行号', name: 'id', width: 100},
	                     	                         	                         { title: '状态', name: 'status', width: 100},
	                     	                         	                         { title: '反馈结果', name: 'feedBack', width: 550},                                	                        
	                         	                         	                     { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看解析数据</a> &nbsp&nbsp  <a href='javascript:openXml(" + param + ")'>查看源数据</a>";
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
	            userId = projectManagerID.find('input');
	            
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
	               form.find('[data-toggle="dropdown"]').on('click', function(){
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

	                   }); 
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
});

function getPath(obj){	
	pathOfMesg=$("#fileID").val();
	var params = {};
    form.find('input').each(function(){
        var $this = $(this);
        var name = $this.attr('name');
        if(name){
            params[name] = $this.val();
        }
    });
    grid.getGrid().search(params);
}
var openDetailsPage = function(id){
	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看解析数据</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div></div></div></div>');
	$.get('<%=path%>/FeedBack-view.jsp?id='+id).done(function(html){
	    dialog.find('.modal-body').html(html);		
        $.get( '${pageContext.request.contextPath}/FeedBack/get/' + id + '.koala?taskPacketId='+taskPacketId).done(function(json){
        	json = json.data;
            dialog.find("#content1").html(json['html']);
            if(json['transform']!=''){
            	var obj = eval("("+json['transform']+")");
                for (var key in obj){
                	switch(key){
                		case "change":
                			var code = obj['change']['code'];
    						var map = obj.change.map
    						var flag = JSON.stringify(map);
                			dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+code+" flag="+flag+" catagory="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>标识变更</span>");
                	  		break;
                		case "deleteAll":
                			var code = obj['deleteAll']['code'];
    						var map = obj.deleteAll.map
    						var flag = JSON.stringify(map);
                			dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+code+" flag="+flag+" catagory="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>整笔删除</span>");
                	  		break;
                		case "deleteBySeg":
                			var code = obj['deleteBySeg']['code'];
    						var map = obj.deleteBySeg.map
    						var flag = JSON.stringify(map);
                			dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+code+" flag="+flag+" catagory="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>按段删除</span>");
                			break;
                		case "modify":
                			var code = obj['modify']['code'];
    						var map = obj.modify.map
    						var flag = JSON.stringify(map);
                			dialog.find('#transform').append("&nbsp;&nbsp;&nbsp;<div class='radio'><span><input value="+code+" flag="+flag+" catagory="+key+" type='radio'  name='transform' style='opacity: 0;'></span></div><span>修改</span>");
                			break;
                	}
    			}
                var transform = dialog.find('[name="transform"]').first();
             	transform.parent().addClass("checked");
             	transform.attr("checked","checked");
            }else{
            	dialog.find('#transform').append("<a>无</a>");
            	dialog.find("#next").attr('class','disabled');
            }         	
			dialog.find('#sub').click(function(){
				if (!Validation.notNull(dialog, dialog.find('#taskNameID'), dialog.find('#taskNameID').val(), '请输入任务名称')) {
	    			return false;
	    	  	}
				if (!Validation.notNull(dialog, dialog.find('#sendChannelID'), dialog.find('#sendChannelID').val(), '请输入发送渠道')) {
	    			return false;
	    	  	}
				var data = [{ name: 'taskName', value: dialog.find("#taskNameID").val()},
				            { name: 'selectedRecordType', value: code},
				            { name: 'sendChannel', value: dialog.find("#sendChannelID").val()},
				            { name: 'encryption', value: dialog.find('[name="encryption"]').val()},
				            { name: 'compression', value: dialog.find('[name="compression"]').val()},
				            { name: 'mesgContent', value: dialog.find("#mesgContentID").val()}
				            ];
     	    	$.post('${pageContext.request.contextPath}/Mesg/send.koala', data).done(function(result){	     
     	        	if(result.success ){
     	            	dialog.modal('hide');
     	                grid.message({
	                    	type: 'success',
	                        content: '转换成功'
	                    });
     	            }else{
     	                grid.message({
     	                	type: 'error',
     	                    content: result.errorMessage
     	                });
     	            }
     	        });
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
}
var openXml = function(id){
	 var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看源数据</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
    $.get('<%=path%>/xml-view.jsp').done(function(html){
           dialog.find('.modal-body').html(html);
           $.get( '${pageContext.request.contextPath}/FeedBack/getXml/' + id + '.koala?taskPacketId='+taskPacketId).done(function(json){
               json = json.data;
        	   dialog.find("#xmlID").html("<xmp>"+json+"</xmp>");
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


<div id="analyzeQueryDiv">
<%-- <table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="vertical-align: bottom;">
          <div class="form-group">
          <label class="control-label" style="width:300px;float:left;">请选择报文文件:&nbsp;</label>
            <input type="file" id="fileID" name="pathOfMesg"/>
            </div>
            <input id="lefile" type="file" style="display:none">
            <div class="input-append" style="position:relative; margin-left:10px; top: -15px">
             <label class="control-label" style="width:120px;float:left;">请选择接口文件:&nbsp;</label>
   <input id="fileID" name="pathOfMesg" onclick="$('input[id=lefile]').click();" class="form-control" style="width:300px;" type="text">
</div>
        </td>
                      
          
       <td style="vertical-align: bottom;"><button id="search" type="button" onclick="getPath()" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;解析</button></td>
  </tr>
</table> --%>

</div>
	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>
</div>
