<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="taskPacketDetail" id="taskPacketDetail">
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String formId2 = "form2_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">

var tabData = $('.tab-pane.active').data();
//var taskId = tabData.taskId;
var taskId = $('.taskPacketDetail').parent().attr('data-value');
var grid;
var form;
var _dialog;
var selectedPacketNameID = null;
var selectedPacketName = null;
var packetFrom = null;
var selectedFileVersion = null;
var selectedOrigSender = null;
var selectedOrigSendDate = null;
var selectedDataType = null;
var selectedRecordType = null;
var packetId2 = null;
var packetName2 = null;
var currentUserId;

$.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function(json) {
	json = json.data;
 	currentUserId = json['userAccount']; 	
 	initFun();
});

function initFun(){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
    form2 = $("#<%=formId2%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	    	var startTimeVal = form.find('#selectedOrigSendDateID_start');
            var startTime = startTimeVal.parent();
            var endTimeVal = form.find('#selectedOrigSendDateID_end');
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
             contents.push({title:'-' , value:'-'});
             form.find('#selectedDataType_SELECT').select({
           title: '请选择',
           contents: contents
      }).on('change',function(){
          form.find('#selectedDataTypeID_').val($(this).getValue());
      });
             var contentsOfCompression = [{title:'请选择', value: ''}];
             contentsOfCompression.push({title:'是' , value:'是'});
             contentsOfCompression.push({title:'否' , value:'否'});
             contentsOfCompression.push({title:'-' , value:'-'});
             form.find('#compression_SELECT').select({
           title: '请选择',
           contents: contentsOfCompression
      }).on('change',function(){
          form.find('#compressionID_').val($(this).getValue());
      });
             var contentsOfEncryption = [{title:'请选择', value: ''}];
             contentsOfEncryption.push({title:'是' , value:'是'});
             contentsOfEncryption.push({title:'否' , value:'否'});             
             contentsOfEncryption.push({title:'-' , value:'-'});
             form.find('#encryption_SELECT').select({
           title: '请选择',
           contents: contentsOfEncryption
      }).on('change',function(){
          form.find('#encryptionID_').val($(this).getValue());
      });},
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改加压加密方式</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-cloud-upload"><span>上传外部文件</button>', action: 'upload'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-up"><span>上移</button>', action: 'up'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-down"><span>下移</button>', action: 'down'},
	                        {content: '<button class="btn btn-warning" type="button"><span class="glyphicon glyphicon-refresh"><span>刷新</button>', action: 'fresh'},
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-log-out"><span>发送</button>', action: 'send'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-search"><span>高级搜索<span class="caret"></span></button>', action: 'search'}
	                    ],
	                url:"${pageContext.request.contextPath}/TaskPacket/pageJson/" + taskId + ".koala",
	                columns: [
																				{ title: '报文来源', name: 'packetFrom', width: width/2,
																					render: function(item, name, index){
                    	                         						                 if(item[name] == '0'){
                    	                         						                     return '内部报文';
                    	                         						                 }else if(item[name] == '1'){
                    	                         						                     return '外部报文';
                    	                         						                 }else if(item[name] == '2'){
                    	                         						                	 return '快速报文';
                    	                         						                 }
                    	                         						             }
																				},
	                     	                         	                        { title: '报文名称', name: 'selectedPacketName', width: width/2},
	                     	                         	                        { title: '版本号', name: 'selectedFileVersion', width: 5*width/12},
	                     	                         	                        { title: '数据提供机构代码', name: 'selectedOrigSender', width: 3*width/4},
	                     	                         	                        { title: '生成时间', name: 'selectedOrigSendDate', width: width/2,
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
	                     	                         	                        { title: '数据类型', name: 'selectedDataType', width: 3*width/4,
	                     	                         	                        	render: function(item, name, index){
                     	                         						                 if(item[name] == '0'){
                     	                         						                     return '正常';
                     	                         						                 }else if(item[name] == '1'){
                     	                         						                     return '删除且不需重报';
                     	                         						                 }else if(item[name] == '2'){
                     	                         						                	 return '删除且需重报';
                     	                         						                 }else{
                     	                         						                	 return '-';
                     	                         						                 }
                     	                         						             }
	                     	                         	                        },
	                     	                         	                        { title: '记录类型', name: 'selectedRecordType', width: width/2},
	                         	                         	                         	                         { title: '加压', name: 'compression', width: width/3},
	                         	                         	                         	                         { title: '加密', name: 'encryption', width: width/3},
	                         	                         	                         	                         { title: '顺序号', name: 'serialNumber', width: 5*width/12},
	                         	                         	                             { title: '操作', width: 100, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';	 
	                                     var h = "<a href='javascript:openTaskPacketView(" + param + ")'>查看发送报文</a> ";
	                                     return h;
	                                 }	                         	                         	         
	                             }
	                ]
	         }).on({
	                   'add': function(){
	                	   self.selectPackets($(this));
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
	                   'upload': function(){
	                	   self.upload($(this));
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
	                    'send' : function() {
	                    	self.send($(this));
	                    },
	                    'search' : function() {						
	       					$("#taskPacketQueryDiv").slideToggle("slow");						 
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
	        $.get('<%=path%>/TaskPacket-add.jsp').done(function(html){
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
	              $.post('${pageContext.request.contextPath}/TaskPacket/add.koala', dialog.find('form').serialize()).done(function(result){
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
	    selectPackets:function(grid){
	    	$.get(contextPath + '/pages/auth/packet-select.jsp').done(function(data) {	    		
	    	    var dialog = $(data);
<%-- 	    	dialog.find('.modal-title').html(document.getElementById("<%=formId2%>").innerHTML); --%>
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
	    	                { title:'报文名称', name:'packetName' , width: 120},
	    	                { title:'文件格式版本号', name:'fileVersion', width: 120},
	    	                { title:'数据提供机构代码', name:'origSender', width: 130},
	    	                { title:'文件生成时间', name:'origSendDate', width: 100,
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
	    	                { title:'记录类型', name:'recordType', width: 90},
	    	                { title:'数据类型', name:'dataType', width: 120,
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
	    	                { title:'创建人员', name:'createdBy', width: 90},
	    	                { title:'是否加压', width: 80, name: "haha", render: function (rowdata, name, index)
	                            {
	    	                        var param = rowdata.id;  	                    		
                                    var h = "<label><input id='compressionId" + param + "' name='compression' type='checkbox' value='' checked='checked'/></label>";
                                    return h;
                                }
	    	                },
	    	                { title:'是否加密', width: 80,  render: function (rowdata, name, index)
	                            {
	    	                    	var param = rowdata.id;
	    	                    	var h = "<label><input id='encryptionId" + param + "' name='encryption' type='checkbox' value='' checked='checked'/></label>";
                                    return h;
                                }
	    	                }
	    	            ];//<!-- definition columns end -->
	    	            //查询出当前表单所需要得数据。
	    	            dialog.find('.selectPacketGrid').grid({
	    	                identity: 'id',
	    	                columns: columns,
	    	                url: contextPath + '/Packet/pageJson/' + currentUserId + '.koala'
	    	                //url: contextPath + '/Packet/pageJson.koala'
	    	            });
	    	        }
	    	    });//<!-- 显示对话框数据结束-->
	    	    function initSearch(){	    		    	
	        	    var startTimeVal = dialog.find('#origSendDateID_start');
	                var startTime = startTimeVal.parent();
	                var endTimeVal = dialog.find('#origSendDateID_end');
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
 	                dialog.find('#dataType_SELECT').select({
                    	title: '请选择',
                        contents: contents
                    }).on('change',function(){
                        dialog.find('#dataTypeID_').val($(this).getValue());
                    });
	            }
	    	    initSearch(); 
	    	    dialog.find('#search2').on('click', function(){
<%-- 	    		if(!Validator.Validate(document.getElementById("<%=formId2%>"),3))return; --%>
	    	    	var params = {};
	    	    	dialog.find('input').each(function(){
	    	    		var $this = $(this);
	    	        	var name = $this.attr('name');
	    	        	if(name){
	    	        		params[name] = $this.val();
	    	        	}
	    	    	});
	    	   	    dialog.find('.selectPacketGrid').getGrid().search(params);
	    	    });
				dialog.find('#selectPacketGridSave').on('click',{grid: grid}, function(e){
	    			var items = dialog.find('.selectPacketGrid').data('koala.grid').selectedRows();
	    	    	var selectedPacketNames = new Array();
					for(var i = 0; i < items.length; i++){
						selectedPacketNames[i] = items[i].packetName;
					}
	    	    	var data2 = [{ name: 'selectedPacketNames', value: selectedPacketNames.join(',')},
	    	    		{ name: 'taskId', value: taskId}
	    	    	];
	    	    	var isComs = new Array;
	    	    	var isEncs = new Array;
	    	    	var flagIds = new Array();
	    	    	for(var i = 0; i < items.length; i++){
	    	    		flagIds[i] = items[i].id;
						isComs[i] = $("input[id='compressionId"+flagIds[i]+"']").is(':checked');
						if(isComs[i] == true){
							isComs[i] = '是';
						}else if(isComs[i] == false){
							isComs[i] = '否';
						}
						isEncs[i] = $("input[id='encryptionId"+flagIds[i]+"']").is(':checked');
						if(isEncs[i] == true){
							isEncs[i] = '是';
						}else if(isEncs[i] == false){
							isEncs[i] = '否';
						}
					}
	    	    	if(items.length == 0){
	    	    		dialog.find('.selectPacketGrid').message({
	    	        		type: 'warning',
	    	            	content: '请选择需要关联的报文！'
	    	        	});
	    	    	}                          
		    	    var data = [
						{ name: 'flagIds', value: flagIds.join(',')},
	     				{ name: 'compressions', value: isComs.join(',')},
	     				{ name: 'encryptions', value: isEncs.join(',')},
	     				{ name: 'taskId', value: taskId},
	     				{ name: 'packetFrom', value: 0}
	     			];
	    	    	$.post('${pageContext.request.contextPath}/TaskPacket/add.koala', data).done(function(result){
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
		    	//兼容IE8 IE9
		    	if(window.ActiveXObject){
		    		if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
		    			dialog.trigger('shown.bs.modal');
		    		}
		    	}
	    	});   
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/TaskPacket-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/TaskPacket/get/' + id + '.koala').done(function(json){
	                       json = json.data;                     
	                        var elm;
	                        packetFrom = json['packetFrom'];	                                                
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
	                        var d = new Date(json['selectedOrigSendDate']);    //根据时间戳生成的时间对象
							var date = (d.getFullYear()) + "-" + 
									(d.getMonth() + 1) + "-" +
									(d.getDate()) + " " + 
									(d.getHours()) + ":" + 
									(d.getMinutes()) + ":" + 
									(d.getSeconds());
							selectedOrigSendDate = date;
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                	if(packetFrom == '外部报文'){
	                		dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: "不能对外部报文进行修改！"
	                        });
	                		return false;
	                	}
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $.post('${pageContext.request.contextPath}/TaskPacket/update.koala?selectedOrigSendDate='+selectedOrigSendDate, dialog.find('form').serialize()).done(function(result){
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
	                   format: "yyyy-mm-dd hh:ii:ss",
	                   autoclose: true,
	                   todayBtn: true,
	                   //minView: 2,
	                   pickerPosition: 'bottom-left'
	               }).datetimepicker('setDate', new Date());//加载日期选择器
	               
                   var selectItems = {};
                   var contents = [{title:'请选择', value: ''}];
                   selectItems['selectedPacketNameID'] = contents;
                   var contents = [{title:'请选择', value: ''}];
                   contents.push({title:'是' , value:'是'});
                   contents.push({title:'否' , value:'否'});
                   
                   selectItems['compressionID'] = contents;
                   var contents = [{title:'请选择', value: ''}];
                   contents.push({title:'是' , value:'是'});
        		   contents.push({title:'否' , value:'否'});
        		   

        		   selectItems['encryptionID'] = contents;
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
	    	$.post('${pageContext.request.contextPath}/TaskPacket/delete.koala', data).done(function(result){
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
	    upload: function(grid){
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">上传外部文件</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'	        	
	        	+'<button type="button" class="btn btn-default" id="back" data-dismiss="modal">返回</button>'
	        	+'<button type="button" class="btn btn-success" id="upload">上传</button>'
	        	+'<button type="button" class="btn btn-warning" id="cancelUpload">取消上传</button></div></div>'
	        	+'</div></div>');
	        
	        $.get('<%=path%>/TaskPacket-upload.jsp').done({ 
                'taskId' :  taskId, 'grid' : grid
            },function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            dialog.find('#upload').on('click',{grid: grid}, function(e){
	            	$('#uploadify').uploadify('upload');
		        });
	            dialog.find('#cancelUpload').on('click',{grid: grid}, function(e){
	            	$('#uploadify').uploadify('cancel');
	            });
	        });
	    },
	    up: function(grid){
	    	if(grid.getGrid().selectedRowsNo()==0){
	    		grid.message({
                     type: 'error',
                     content: "不能再往上移啦"
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
	    	$.post('${pageContext.request.contextPath}/TaskPacket/up.koala', data).done(function(result){
	        	if(result.success){
                    grid.message({
	                	type: 'success',
	                    content: '上移成功'
	                });
	            }else{
	                grid.message({
	                    type: 'error',
	                    content: result.result
	                });
	            }
	    	});
	    },
	    down: function(grid){
	    	if(grid.getGrid().selectedRowsNo()==grid.getGrid().getAllItems().length-1){
	    		grid.message({
                    type: 'error',
                    content: "不能再往下移啦"
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
	    	$.post('${pageContext.request.contextPath}/TaskPacket/down.koala', data).done(function(result){
	        	if(result.success){
	            	//grid.data('koala.grid').refresh();
                    grid.message({
	                	type: 'success',
	                    content: '下移成功'
	                });
	            }else{
	                grid.message({
	                    type: 'error',
	                    content: result.result
	                });
	            }
	    	});
	    },
	    send: function(grid){
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">发送</button></div></div></div></div>');
	        $.get('<%=path%>/TaskPacket-send.jsp').done(function(html){
	        	dialog.find('.modal-body').html(html);
	            dialog.modal({
	            	keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                	$(this).remove();
	                }
	            });
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){	
	        	if(!Validator.Validate(dialog.find('form')[0],3))return;
	            $.post('${pageContext.request.contextPath}/TaskPacket/send.koala', dialog.find('form').serialize()).done(function(result){
	            	if(result.success){
	                	dialog.modal('hide');
	                    e.data.grid.data('koala.grid').refresh();
	                    e.data.grid.message({
	                    	type: 'success',
	                        content: '发送成功'
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
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
<%-- 	document.getElementById("<%=formId2%>").style.display="none"; --%>
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
//});
}

var openTaskPacketView = function(id){
	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看源数据</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
    $.get('<%=path%>/TaskPacket-view.jsp').done(function(html){
          dialog.find('.modal-body').html(html);
          $.get('${pageContext.request.contextPath}/TaskPacket/getTaskPacketView/' + id + '.koala').done(function(json){
              json = json.data;
       	  	  dialog.find("#taskPacketViewID").html("<div style='width:780px;overflow:auto;'><xmp>"+json+"</xmp></div>");
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

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/TaskPacket-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/TaskPacket/get/' + id + '.koala').done(function(json){
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
                        var d = new Date(json['selectedOrigSendDate']);    //根据时间戳生成的时间对象
						var date = (d.getFullYear()) + "-" + 
								(d.getMonth() + 1) + "-" +
								(d.getDate()) + " " + 
								(d.getHours()) + ":" + 
								(d.getMinutes()) + ":" + 
								(d.getSeconds());
						dialog.find('#selectedOrigSendDateID').html(date);
						if(json['selectedDataType'] == '0'){
							dialog.find('#selectedDataTypeID').html('正常');
			            }else if(json['selectedDataType'] == '1'){
			            	dialog.find('#selectedDataTypeID').html('删除且不需重报');
			            }else if(json['selectedDataType'] == '2'){
			            	dialog.find('#selectedDataTypeID').html('删除且需重报');
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
<input type="hidden" id="pagesize" name="pagesize" value="10">
<div id="taskPacketQueryDiv" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:160px;float:left;">内外部报文:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="packetFrom" class="form-control" type="text" style="width:160px;" id="packetFromID"  />
        </div>
          <label class="control-label" style="width:160px;float:left;">报文名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="selectedPacketName" class="form-control" type="text" style="width:160px;" id="selectedPacketNameID"  />
        </div>
        <label class="control-label" style="width:160px;float:left;">文件格式版本号:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="selectedFileVersion" class="form-control" type="text" style="width:160px;" id="selectedFileVersionID"  />
        </div>
        </div>

        <div class="form-group">
                      <label class="control-label" style="width:160px;float:left;">数据提供机构代码:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="selectedOrigSender" class="form-control" type="text" style="width:160px;" id="selectedOrigSenderID"  />
        </div>
                  <label class="control-label" style="width:160px;float:left;">文件生成时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="selectedOrigSendDate" id="selectedOrigSendDateID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="selectedOrigSendDateEnd" id="selectedOrigSendDateID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
       </div>
       </div>

       <div class="form-group">
       <label class="control-label" style="width:160px;float:left;">记录类型:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="selectedRecordType" class="form-control" type="text" style="width:160px;" id="selectedRecordID"  />
        </div>
                <label class="control-label" style="width:160px;float:left;">数据类型:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="selectedDataType_SELECT"></div>
	        <input type="hidden" id="selectedDataTypeID_" name="selectedDataType" />
	      </div>

	      		<label class="control-label" style="width:100px;float:left;">加压:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="compression_SELECT"></div>
	        <input type="hidden" id="compressionID_" name="compression" />
	      </div>

	      <label class="control-label" style="width:100px;float:left;">加密:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="encryption_SELECT"></div>
	        <input type="hidden" id="encryptionID_" name="encryption" />
	      </div>
</div>
</td>
<!--        <div class="form-group"> -->
<!--                       <label class="control-label" style="width:160px;float:left;">加压:&nbsp;</label> -->
<!--             <div style="margin-left:15px;float:left;"> -->
<!--             <input name="compression" class="form-control" type="text" style="width:160px;" id="compressionID"  /> -->
<!--         </div> -->

<!--           <label class="control-label" style="width:160px;float:left;">加密:&nbsp;</label> -->
<!--             <div style="margin-left:15px;float:left;"> -->
<!--             <input name="encryption" class="form-control" type="text" style="width:160px;" id="encryptionID"  /> -->
<!--         </div> -->
<!--         </div> -->
       <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>
</div>	
</form>

<%-- <form name=<%=formId2%> style="display:none;" id=<%=formId2%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<button class="btn btn-success" type="button" style="margin-top:10px" onclick="$('#searchQueryDiv').slideToggle('slow')"><span class="glyphicon glyphicon-flash"></span>高级搜索<span class="caret"></span></button>
<div id="searchQueryDiv" style="margin-top:5px" hidden="true">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          
           <label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报文名称:</label>
            <div style="margin-left:1px;float:left;">
            <input name="packetName" class="form-control" type="text" style="width:160px;" id="packetNameID"  />
        </div>
          
          <label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件格式版本号:</label>
            <div style="margin-left:1px;float:left;">
            <input name="fileVersion" class="form-control" type="text" style="width:160px;" id="fileVersionID"  />
        </div>
                      <label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据提供机构代码:</label>
            <div style="margin-left:1px;float:left;">
            <input name="origSender" class="form-control" type="text" style="width:160px;" id="origSenderID"  />
        </div>
            </div>
  </td>
  </tr>
  <tr>
  <td>
                 <label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记录类型:</label>
            <div style="margin-left:1px;float:left;">
            <input name="recordType" class="form-control" type="text" style="width:160px;" id="recordID"  />
        </div>
<label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据类型:</label>
    	  <div style="margin-left:1px;float:left;">
	      <div class="btn-group select" id="dataType_SELECT"></div>
	        <input type="hidden" id="dataTypeID_" name="dataType" />
	      </div>
  </td>
  </tr>
  <tr>
  <td>      
                        <div class="form-group">
          <label class="control-label" style="width:160px;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件生成时间:</label>
           <div style="margin-left:1px;float:left;">
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
        
        
            </div>
            </td>
       <td style="vertical-align: bottom;"><button id="search2" type="button" style="position:relative; margin-left:15px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>
</div>	
</form> --%>

<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>
</div>
