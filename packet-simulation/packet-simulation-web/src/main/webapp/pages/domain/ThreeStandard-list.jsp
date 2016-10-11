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
	    initSearchPanel:function(){
	        	            	                	            	                	            	                	                     var startTimeVal = form.find('#createdDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#createdDateID_end');
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
	                	            	         contents.push({title:'身份证' , value:'0'});
	                     	                     contents.push({title:'军官证' , value:'1'});
	                     	                     contents.push({title:'护照' , value:'2'});
	                     	                     form.find('#credentialType_SELECT').select({
	                                            title: '请选择',
	                                            contents: contents
	                                       }).on('change',function(){
	                                           form.find('#credentialTypeID_').val($(this).getValue());
	                                       });
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
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-import"><span>导入三标信息</button>', action: 'importFile'},
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-export"><span>导出三标信息</button>', action: 'exportFile'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-expand"><span>随机生成三标</button>', action: 'expand'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-trash"><span>全部删除</button>', action: 'trash'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	                    ],
	                url:"${pageContext.request.contextPath}/ThreeStandard/pageJson/" + currentUserId + ".koala",
	                isShowIndexCol:true,
	                columns: [
	                     	                         	                         { title: '姓名', name: 'name', width: 2*width/3},
	                         	                         	                         	                         { title: '证件类型', name: 'credentialType', width: width/2,
	                     	                         	                        								 	render: function(item, name, index){
                     	                         						                 								if(item[name] == '0'){
                     	                         						                     								return '身份证';
                     	                         						                 								}else if(item[name] == '1'){
                     	                         						                     								return '军官证';
                     	                         						                 								}else if(item[name] == '2'){
                     	                         						                	 								return '护照';
                     	                         						                 								}
                     	                         						             								}	
	                     	                         	                         								 },
	                         	                         	                         	                         { title: '证件号', name: 'credentialNumber', width: width},
	                         	                         	                         	                         { title: '机构代码', name: 'organizationCode', width: width},
	                         	                         	                         	                         { title: '客户资料标识号', name: 'customerCode', width: 2*width/3},
	                         	                         	                         	                      	 { title: '账户标识号', name: 'acctCode', width: 3*width/4},
	                         	                         	                         	                   		 { title: '合同标识号', name: 'conCode', width: 2*width/3},
	                         	                         	                         	                		 { title: '抵质押合同标识号', name: 'ccc', width: width},
	                         	                         	                         	                         { title: '创建日期', name: 'createdDate', width: 2*width/3},
	                         	                         	                         	                         { title: '创建者', name: 'createdBy', width: width/2},
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPageOfThreeStandard(" + param + ")'>查看</a> ";
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
	                   'expand': function(){
	                	   self.expand($(this));
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
	                   'importFile':function(){
	                	   self.importFile($(this));
	                   },
	                   'exportFile':function(){
	                       var date = new Date();
	                	   window.open('${pageContext.request.contextPath}/ThreeStandard/downloadCSV.koala?id='+date.getTime()+'.csv'+'&createdBy='+currentUserId);
	                   },
	                   'trash': function(){
	                       var $this = $(this);
	                       var trash = function(){
	                           self.trash($this);
	                       };
	                       $this.confirm({
	                           content: '确定要删除全部三标信息吗?',
	                           callBack: trash
	                       });
	                   },
	                   'search' : function() {						
	       			       $("#threeStandardQueryDiv").slideToggle("slow");						 
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
	        $.get('<%=path%>/ThreeStandard-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false,
	                backdrop: 'static'
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;	             
	              $.post('${pageContext.request.contextPath}/ThreeStandard/add.koala?createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
	    importFile: function(grid){
			var self = this;
    		var dialog = $('<div class="modal fade"><div class="modal-dialog">'
    			+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    			+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    			+'<h4 class="modal-title">导入三标信息</h4></div><div class="modal-body">'
    			+'<p>One fine body&hellip;</p></div></div>');
    		$.get('<%=path%>/ThreeStandard-importFile.jsp').done({ 
        		'createdBy' :  currentUserId
    			},function(html){
        			dialog.modal({
           			keyboard:false,
	                backdrop: 'static'
        		}).on({
            		'hidden.bs.modal': function(){
                		$(this).remove();
            		}
        		}).find('.modal-body').html(html);
        		self.initPage(dialog.find('form'));
        		dialog.find("#input-id").on("fileuploaded", function(event, data, previewId, index) {
        			dialog.modal('hide');
                    grid.data('koala.grid').refresh();
                    grid.message({
                        type: 'success',
                        content: '保存成功'
                     });
        		});
        		dialog.find("#input-id").on('fileuploaderror', function(event, data, msg) {
                    //alert(111);
        		});
    		});
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/ThreeStandard-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/ThreeStandard/get/' + id + '.koala').done(function(json){
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
	                    keyboard:false,
		                backdrop: 'static'
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;                   
	                    $.post('${pageContext.request.contextPath}/ThreeStandard/update.koala?id='+id+'&createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
	    expand: function(){
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">随机生成三标</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');   	    	      
	        $.get('<%=path%>/ThreeStandard-generate.jsp').done(function(html){
	        	dialog.find('.modal-body').html(html);
				dialog.modal({
	            	keyboard:false,
	                backdrop: 'static'
	            }).on({
	                'hidden.bs.modal': function(){
	                	$(this).remove();
	                }
	            });
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
                if(!Validator.Validate(dialog.find('form')[0],3))return;
                dialog.find('.modal-footer').html("<html><body><img src='${pageContext.request.contextPath}/images/loading.gif'  alt='上海鲜花港 - 郁金香' /></body></html>");
                $.post('${pageContext.request.contextPath}/ThreeStandard/generate.koala?createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
				   contents.push({title:'护照' , value:'2'});
				   contents.push({title:'军官证' , value:'1'});
				   contents.push({title:'身份证' , value:'0'});
				   selectItems['credentialTypeID'] = contents;
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
	    	$.post('${pageContext.request.contextPath}/ThreeStandard/delete.koala', data).done(function(result){
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
	    trash: function(grid){
	    	var data = [{ name: 'createdBy', value: currentUserId}];
	    	$.post('${pageContext.request.contextPath}/ThreeStandard/trash.koala', data).done(function(result){
	        	if(result.success){
	            	grid.data('koala.grid').refresh();
	                grid.message({
	                	type: 'success',
	                    content: '清除成功'
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
	/*
	* 清除菜单效果
	*/
	var clearMenuEffect = function(){
	   $('.first-level-menu').find('li').each(function(){
	       var $menuLi = $(this);
	       $menuLi.hasClass('active') && $menuLi.removeClass('active').parent().parent().removeClass('active');
	   });
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
}

var openDetailsPageOfThreeStandard = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/ThreeStandard-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/ThreeStandard/get/' + id + '.koala').done(function(json){
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
<div>
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<div id="threeStandardQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <div class="form-group">
            	<label class="control-label" style="width:120px;float:left;">姓名:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
                	<input name="name" class="form-control" type="text" style="width:180px;" id="nameID"  />
        		</div>
        		
       			<label class="control-label" style="width:120px;float:left;">机构代码:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="organizationCode" class="form-control" type="text" style="width:180px;" id="organizationCodeID"  />
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
