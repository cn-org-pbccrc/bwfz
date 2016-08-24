<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp --> --%>
<%@include file="/commons/taglibs.jsp"%>
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
var selectMesg;
var mesgTree;
var mesgInput;
var mesgId;
var mesgName;
var mesgCode;

function initFun(){
	grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");

	PageLoader = {
	    initSearchPanel:function(){
	    	
	        	         var startTimeVal = form.find('#origSendDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#origSendDateID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd hh:ii:ss",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        //minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd hh:ii:ss",
	                             autoclose: true,
	                             todayBtn: true,
	                             //minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                     var contents = [{title:'请选择', value: ''}];
 	                     contents.push({title:'正常' , value:'0'});
 	                     contents.push({title:'删除且不需重报' , value:'1'});
 	                     contents.push({title:'删除且需重报' , value:'2'});
 	                     form.find('#dataType_SELECT').select({
                        title: '请选择',
                        contents: contents
                   }).on('change',function(){
                       form.find('#dataTypeID_').val($(this).getValue());
                   });
	    },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         //alert(currentUserId)
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-cloud-upload"><span>加载外部报文</button>', action: 'load'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	                    ],
	                url:"${pageContext.request.contextPath}/Packet/pageJson/" + currentUserId + ".koala",
	                columns: [
	                     	                         	                         								 { title: '文件名称', name: 'packetName', width: 100},
	                     	                         	                         								 { title: '版本号', name: 'fileVersion', width: 50},
	                         	                         	                         	                         { title: '数据提供机构代码', name: 'origSender', width: 100},	                     	                
	                         	                         	                         	                         { title: '文件生成时间', name: 'origSendDate', width: 100,
	                         	                         	                         	                        	 render: function(item, name, index){
	                     	                         	                         						                var d = new Date(item[name]);    //根据时间戳生成的时间对象
																														var date = (d.getFullYear()) + "-" + 
           																													(d.getMonth() + 1) + "-" +
           																													(d.getDate()) + " " + 
           																													(d.getHours()) + ":" + 
           																													(d.getMinutes()) + ":" + 
           																													(d.getSeconds());
	                     	                         	                         						                return date;
	                     	                         	                         						             }	
	                         	                         	                         	                         },
	                         	                         	                         	                      	{ title: '业务类型', name: 'bizType', width: 60},
	                         	                         	                         	                         { title: '记录类型', name: 'recordType', width: 100},
	                         	                         	                         	                         { title: '数据类型', name: 'dataType', width: 60,
	                         	                         	                         	                 		 render: function(item, name, index){
                     	                         	                         						                 if(item[name] == '0'){
                     	                         	                         						                     return '正常';
                     	                         	                         						                 }else if(item[name] == '1'){
                     	                         	                         						                     return '删除且不需重报';
                     	                         	                         						                 }else{
                     	                         	                         						                	 return '删除且需重报';
                     	                         	                         						                 }
                     	                         	                         						             }	
	                         	                         	                         	                         },
	                         	                         	                         	                      	 { title: '信息记录数', name: 'mesgNum', width: width/2},
	                         	                         	                             { title: '操作', width: 200, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var packId = '"' + rowdata.packId + '"';
	                                     var h = "<a href='javascript:openPacket(" + param + ")'><span class='glyphicon glyphicon glyphicon-edit'></span>&nbsp记录管理</a> "
	                                     +"&nbsp;&nbsp;<a href='javascript:downloadCSV(" + param + ")'><span class='glyphicon glyphicon glyphicon-export'></span>&nbsp导出csv文件</a> "
	                                     +"&nbsp;&nbsp;<a href='javascript:downloadENC(" + param + ")'><span class='glyphicon glyphicon glyphicon-export'></span>&nbsp导出enc文件</a>"
	                                     +"&nbsp;&nbsp;<a href='javascript:openPacketView(" + param + ")'><span class='glyphicon glyphicon glyphicon-eye-open'></span>&nbsp显示报文</a>";
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
	                   'load':function(){
	                	   self.load($(this));
	                   },
	                   'search' : function() {						
	       					$("#packetQueryDiv").slideToggle("slow");						 
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
	        $.get('<%=path%>/Packet-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false,
	                backdrop: 'static'
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            selectMesg = dialog.find('#select-mesg');
	            mesgInput = selectMesg.find('input');
				selectMesg.find('[data-toggle="dropdown"]').on('click', function(){
					selectMesgTypes();
				});
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
 		        if(!Validator.Validate(dialog.find('form')[0],3))return;
// 		        if (!Validation.notNull(dialog, dialog.find('#packetNameID'), dialog.find('#packetNameID').val(), '报文名称不能为空')) {
//     			    return false;
//     		    }
// 		        if (!Validation.checkByRegExp(dialog, dialog.find('#fileVersionID'), '^[0-9]+[.][0-9]+$', dialog.find('#fileVersionID').val(), '文件格式版本号应该为N.N格式数字')) {
// 					return false;
// 				}
// 		        if (!Validation.checkByRegExp(dialog, dialog.find('#origSenderID'), '^[0-9]{14}$', dialog.find('#origSenderID').val(), '数据提供机构代码应为14位数字')) {
// 					return false;
// 			    }
// 		        if (!Validation.checkByRegExp(dialog, dialog.find('#recordTypeID'), '^[0-9]{4}$', dialog.find('#recordTypeID').val(), '记录类型只能为四位数字')) {
// 					return false;
// 			    }
// 		        if (!Validation.notNull(dialog, dialog.find('#dataTypeID'), dialog.find('#dataTypeID').getValue(), '请选择数据类型')) {
//     			    return false;
//     		    }
				if(!CheckDateTime(dialog.find('#origSendDateID').val())){
					showErrorMessage(dialog, dialog.find('#origSendDateID'), '日期格式不正确');
					return false;
				}
	            $.post('${pageContext.request.contextPath}/Packet/add.koala?createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
	    load: function(grid){
			var self = this;
    		var dialog = $('<div class="modal fade"><div class="modal-dialog">'
    			+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    			+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    			+'<h4 class="modal-title">上传外部文件</h4></div><div class="modal-body">'
    			+'<p>One fine body&hellip;</p></div></div>');
    		$.get('<%=path%>/Packet-load.jsp').done({ 
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
    		});
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button><button type="button" class="btn btn-warning" id="saveAs">另存为</button></div></div></div></div>');
	        $.get('<%=path%>/Packet-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/Packet/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
// 	                            }else if(elm.hasClass('date')){
// 	                            	elm.val("mimi");
	                            }else{
	                                elm.val(json[index]);
	                            }	                            
	                        }
	                        var d = new Date(json['origSendDate']);    //根据时间戳生成的时间对象
							var date = (d.getFullYear()) + "-" + 
									(d.getMonth() + 1) + "-" +
									(d.getDate()) + " " + 
									(d.getHours()) + ":" + 
									(d.getMinutes()) + ":" + 
									(d.getSeconds());
	                        dialog.find('#origSendDateID').val(date);
	                        //dialog.find('#packetNameID').val(json['packetName']).attr('disabled', 'disabled');
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
// 	                    if (!Validation.checkByRegExp(dialog, dialog.find('#fileVersionID'), '^[0-9]+[.][0-9]+$', dialog.find('#fileVersionID').val(), '文件格式版本号应该为N.N格式')) {
// 	    					return false;
// 	    				}
// 	    		        if (!Validation.checkByRegExp(dialog, dialog.find('#origSenderID'), '^[0-9]{14}$', dialog.find('#origSenderID').val(), '数据提供机构代码应为14位')) {
// 	    					return false;
// 	    			    }
// 	    		        if (!Validation.checkByRegExp(dialog, dialog.find('#recordTypeID'), '^[0-9]{4}$', dialog.find('#recordTypeID').val(), '记录类型只能为四位数字')) {
// 	    					return false;
// 	    			    }
// 	    		        if (!Validation.notNull(dialog, dialog.find('#dataTypeID'), dialog.find('#dataTypeID').getValue(), '请选择数据类型')) {
// 	        			    return false;
// 	        		    }
	                    if(!CheckDateTime(dialog.find('#origSendDateID').val())){
	    					showErrorMessage(dialog, dialog.find('#origSendDateID'), '日期类型不正确');
	    					return false;
	    				}
	                    $.post('${pageContext.request.contextPath}/Packet/update.koala?id='+id+'&createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
	                
	                dialog.find('#saveAs').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;	                         
	                    $.post('${pageContext.request.contextPath}/Packet/saveAs.koala?idOfPacket='+id+'&createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
	                        if(result.success){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            	type: 'success',
	                            	content: '另存为成功'
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
	                   format: "yyyy-mm-dd hh:ii:ss",
	                   autoclose: true,
	                   todayBtn: true,
	                   //minView: 2,
	                   pickerPosition: 'bottom-left'
	               }).datetimepicker('setDate', new Date());//加载日期选择器
	               /* var selectItems = {};
	               var contents = [{title:'请选择', value: ''}];
	               contents.push({title:'正常' , value:'0'});
	               contents.push({title:'删除且不需重报' , value:'1'});
	               contents.push({title:'删除且需重报' , value:'2'});
	               selectItems['dataTypeID'] = contents;
	               form.find('.select').each(function(){
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               }); */
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Packet/delete.koala', data).done(function(result){
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
}

function CheckDateTime(str){
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if(r == null) return false;  
	r[2] = r[2] - 1;  
	var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);  
	if (d.getFullYear() != r[1]) return false;  
	if (d.getMonth() != r[2]) return false;  
	if (d.getDate() != r[3]) return false;  
	if (d.getHours() != r[4]) return false;  
	if (d.getMinutes() != r[5]) return false;  
	if (d.getSeconds() != r[6]) return false;  
	return true;  
}  

var openPacketView = function(id){
	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看源数据</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
    $.get('<%=path%>/Packet-view.jsp').done(function(html){
          dialog.find('.modal-body').html(html);
          $.get('${pageContext.request.contextPath}/Packet/getPacketView/' + id + '.koala').done(function(json){
                   json = json.data;
       	   dialog.find("#packetViewID").html("<div style='width:780px;overflow:auto;'><xmp>"+json+"</xmp></div>");
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

var mark;
function openPacket(id){
    var thiz 	= $(this);
    var  mark 	= thiz.attr('mark');
    mark = openTab("/pages/domain/Mesg-list.jsp", "增加报文明细 ",'OpenMesgList',id);
    if(mark){
        thiz.attr("mark",mark);
    }
}

function downloadCSV(id){
	var date = new Date();
	window.open('${pageContext.request.contextPath}/Packet/downloadCSV/' + id + '.koala?id='+date.getTime()+'.csv');
}

function downloadENC(id){
	var date = new Date();
	window.open('${pageContext.request.contextPath}/Packet/downloadENC/' + id + '.koala');
}

var selectMesgTypes = function(){
	$.get( contextPath + '/pages/organisation/select-mesg-template.jsp').done(function(data){
		var mesgTreeDialog = $(data);
		mesgTreeDialog.find('.modal-body').css({height:'325px'});
		mesgTree = mesgTreeDialog.find('.tree');
        loadMesgTree();
		mesgTreeDialog.find('#confirm').on('click',function(){
			mesgInput.val(mesgCode);
			selectMesg.find('[data-toggle="item"]').text(mesgName);
			mesgTreeDialog.modal('hide');
			selectMesg.trigger('keydown');
		}).end().modal({
			backdrop: false,
			keyboard: false
		}).on({
			'hidden.bs.modal': function(){
				$(this).remove();
			}
		});
	});
};

var loadMesgTree = function(){
	mesgTree.parent().loader({
		opacity: 0
	});
    $.get(contextPath  + '/MesgType/findMesgTypes.koala').done(function(data){
        mesgTree.parent().loader('hide');
        var zNodes = new Array();
		var cNodes = new Array();
		if (!data) {
			return;
		}
		$.each(data, function() {
			var cNode = {};
			this.title = this.mesgType;
			cNode.menu = this;
			cNodes.push(cNode);
		});
		var zNode = {};
		var menu = {};
		zNode.type = 'parent';
		menu.title = '个人';
		menu.open = true;
		zNode.children = cNodes;
		zNode.menu = menu;
		zNodes.push(zNode);
		var dataSourceTree = {
			data : zNodes,
			delay : 400
		};
        mesgTree.tree({
            dataSource: dataSourceTree,
            loadingHTML: '<div class="static-loader">Loading...</div>',
            multiSelect: false,
            useChkBox : false,
            cacheItems: true
        }).on({
            'selectChildren': function(event, data){
                mesgId = data.id;
                mesgName = data.mesgType;
                mesgCode = data.code;
            }
        });
    });
};
</script>
</head>
<body>
<div>
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<div id="packetQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>
        	<div class="form-group">         
            	<label class="control-label" style="width:160px;float:left;">报文名称:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="packetName" class="form-control" type="text" style="width:160px;" id="packetNameID"  />
        		</div>
          
<!--             	<label class="control-label" style="width:160px;float:left;">文件格式版本号:&nbsp;</label> -->
<!--             	<div style="margin-left:15px;float:left;"> -->
<!--             		<input name="fileVersion" class="form-control" type="text" style="width:160px;" id="fileVersionID"  /> -->
<!--         		</div> -->
                      
            	<label class="control-label" style="width:160px;float:left;">数据提供机构代码:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="origSender" class="form-control" type="text" style="width:160px;" id="origSenderID"  />
        		</div>
            </div>
                  
            <div class="form-group">
            	<label class="control-label" style="width:160px;float:left;">文件生成时间:&nbsp;</label>
           		<div style="margin-left:15px;float:left;">
            		<div class="input-group date form_datetime" style="width:160px;float:left;" >
                		<input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID_start" >
                		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            		</div>
            		<div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            		<div class="input-group date form_datetime" style="width:160px;float:left;" >
                		<input type="text" class="form-control" style="width:160px;" name="origSendDateEnd" id="origSendDateID_end" >
                		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            		</div>
       			</div>
       			<label class="control-label" style="width:160px;float:left;">记录类型:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="recordType" class="form-control" type="text" style="width:160px;" id="recordID"  />
        		</div>
       		</div>
       		
<!--             <div class="form-group"> -->
<!--             	<label class="control-label" style="width:160px;float:left;">记录类型:&nbsp;</label> -->
<!--             	<div style="margin-left:15px;float:left;"> -->
<!--             		<input name="recordType" class="form-control" type="text" style="width:160px;" id="recordID"  /> -->
<!--         		</div> -->

<!--                 <label class="control-label" style="width:160px;float:left;">数据类型:&nbsp;</label> -->
<!--     	  		<div style="margin-left:15px;float:left;"> -->
<!-- 	      		<div class="btn-group select" id="dataType_SELECT"></div> -->
<!-- 	        		<input type="hidden" id="dataTypeID_" name="dataType" /> -->
<!-- 	      		</div> -->
<!-- 	  		</div> -->
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
