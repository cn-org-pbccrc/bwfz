<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
var taskId = tabData.taskId;
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
	        	            	                	            	                	            	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改加压加密方式</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>上传外部文件</button>', action: 'upload'}
	                    ],
	                url:"${pageContext.request.contextPath}/TaskPacket/pageJson/" + taskId + ".koala",
	                columns: [
																				{ title: '内外部报文', name: 'packetFrom', width: width/2},
	                     	                         	                        { title: '报文名称', name: 'selectedPacketName', width: 3*width/4},
	                     	                         	                        { title: '版本号', name: 'selectedFileVersion', width: width/2},
	                     	                         	                        { title: '数据提供机构代码', name: 'selectedOrigSender', width: 3*width/4},
	                     	                         	                        { title: '生成时间', name: 'selectedOrigSendDate', width: width/2},
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
	                	   //selectPackets();
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
	    	 	   dialog.find('.modal-query').html(document.getElementById("<%=formId2%>").innerHTML);

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
	    	                    { title:'文件生成时间', name:'origSendDate', width: 100},
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
                                    	var h = "<label><input id='compressionId" + param + "' name='compression' type='checkbox' value='' /></label>";
                                    	return h;
                                	}
	    	                    },
	    	                    { title:'是否加密', width: 80,  render: function (rowdata, name, index)
	                                {
	    	                    		var param = rowdata.id;
	    	                    		var h = "<label><input id='encryptionId" + param + "' name='encryption' type='checkbox' value='' /></label>";
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
	    }
	    	        initSearch();
	    	    	dialog.find('#search2').on('click', function(){
	    	            if(!Validator.Validate(document.getElementById("<%=formId2%>"),3))return;
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
	    	            var isComs = new Array;
	    	            var isEncs = new Array;
	    	            var flagId;
	    	            for(var i = 0; i < items.length; i++){
	    	            	flagId = items[i].id;
							isComs[i] = $("input[id='compressionId"+flagId+"']").is(':checked');
							if(isComs[i] == true){
								isComs[i] = '是';
							}else if(isComs[i] == false){
								isComs[i] = '否';
							}
							isEncs[i] = $("input[id='encryptionId"+flagId+"']").is(':checked');
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
// 	    	            if(items.length>1) {
// 	    	                dialog.find('.selectPacketGrid').message({
// 	    	                    type: 'warning',
// 	    	                    content: '只能选择一个需要关联的报文！'
// 	    	                });
// 	    	            }

						var packetNames = new Array();
						var fileVersions = new Array();
						var origSenders = new Array();
						var origSendDates = new Array();
						var dataTypes = new Array();
						var recordTypes = new Array();
						for(var i = 0; i < items.length; i++){
							packetNames[i] = items[i].packetName;							
							fileVersions[i] = items[i].fileVersion;
							origSenders[i] = items[i].origSender;
							origSendDates[i] = items[i].origSendDate;
							dataTypes[i] = items[i].dataType;
							recordTypes[i] = items[i].recordType;
						}
// 	    	            var packetId2 =  items[0].id;
// 	    	            var packetName2 = items[0].packetName;	                          
	    	            var data = [{ name: 'packetNames', value: packetNames.join(',')},
	    	                        { name: 'fileVersions', value: fileVersions.join(',')},
	    	                        { name: 'origSenders', value: origSenders.join(',')},
	    	                        { name: 'origSendDates', value: origSendDates.join(',')},
	    	                        { name: 'dataTypes', value: dataTypes.join(',')},
	    	                        { name: 'recordTypes', value: recordTypes.join(',')},
	     				            { name: 'compressions', value: isComs.join(',')},
	     				            { name: 'encryptions', value: isEncs.join(',')},
	     				            { name: 'taskId', value: taskId},
	     				            { name: 'packetFrom', value: '内部报文'}
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
	                        selectedPacketName = json['selectedPacketName'];
	                        packetFrom = json['packetFrom'];
							selectedFileVersion = json['selectedFileVersion'];
	                		selectedOrigSender = json['selectedOrigSender'];
	                		selectedOrigSendDate = json['selectedOrigSendDate'];
	                		selectedDataType = json['selectedDataType'];
	                		selectedRecordType = json['selectedRecordType'];
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
	                	if(packetFrom == '外部报文'){
	                		alert("不能对外部报文进行修改！")
	                		dialog.modal('hide');
	                		dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                	}
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $.post('${pageContext.request.contextPath}/TaskPacket/update.koala?id='+id+'&taskId='+taskId+'&selectedPacketName='+selectedPacketName+'&packetFrom='+packetFrom+'&selectedFileVersion='+selectedFileVersion+'&selectedOrigSender='+selectedOrigSender+'&selectedOrigSendDate='+selectedOrigSendDate+'&selectedDataType='+selectedDataType+'&selectedRecordType='+selectedRecordType, dialog.find('form').serialize()).done(function(result){
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

// 	               selectedPacketNameID = form.find("#selectedPacketNameID");
// 	               form.find('[data-toggle="dropdown"]').on('click', function(){
// 	            	   selectPackets();
// 	               })
// 	               var selectPackets = function() {	
// 	                   $.get(contextPath + '/pages/auth/packet-select.jsp').done(function(data) {
// 	                	   var dialog = $(data);
// 	                       //显示对话框数据
// 	                       dialog.modal({
// 	                           keyboard: false,
// 	                           backdrop: false // 指定当前页为静态页面，如果不写后台页面就是黑色。
// 	                       }).on({
// 	                           'hidden.bs.modal': function(){
// 	                               $(this).remove();
// 	                           },
// 	                           'shown.bs.modal': function(){
// 	                               var columns = [
// 	                                   { title:'报文名称', name:'packetName' , width: 100},
// 	                                   { title:'文件格式版本号', name:'fileVersion', width: 100},
// 	                                   { title:'数据提供机构代码', name:'origSender', width: 100},
// 	                                   { title:'文件生成时间', name:'origSendDate', width: 100},
// 	                                   { title:'记录类型', name:'recordType', width: 100},
// 	                                   { title:'数据类型', name:'dataType', width: 100},
// 	                                   { title:'创建人员', name:'createdBy', width: 100},
// 	                               ];//<!-- definition columns end -->

// 	                               //查询出当前表单所需要得数据。
// 	                               dialog.find('.selectPacketGrid').grid({
// 	                                   identity: 'id',
// 	                                   columns: columns,
// 	                                   url: contextPath + '/Packet/pageJson/' + currentUserId + '.koala'
// 	                                   //url: contextPath + '/Packet/pageJson.koala'
// 	                               });

// 	                           }
// 	                       });//<!-- 显示对话框数据结束-->

// 	                       dialog.find('#selectPacketGridSave').on('click',function(){                    	   
// 	                           var items = dialog.find('.selectPacketGrid').data('koala.grid').selectedRows();
// 	                           if(items.length == 0){
// 	                               dialog.find('.selectPacketGrid').message({
// 	                                   type: 'warning',
// 	                                   content: '请选择需要关联的报文！'
// 	                               });
// 	                           }
// 	                           if(items.length>1) {
// 	                               dialog.find('.selectPacketGrid').message({
// 	                                   type: 'warning',
// 	                                   content: '只能选择一个需要关联的报文！'
// 	                               });
// 	                           }
// 	                           var packetId2 =  items[0].id;
// 	                           var packetName2 = items[0].packetName;	                          
// 	                           dialog.modal('hide');
// 	                           if(packetId2 != null){
// 	                        	   selectedPacketNameID.find('[data-toggle="item"]').html(packetName2);
// 	                           }
// 	                           selectedPacketNameID.find('input').val(packetId2);
// 	                           dialog.trigger('keydown');	                        
// 	                       });

// 	                       //兼容IE8 IE9
// 	                       if(window.ActiveXObject){
// 	                           if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
// 	                               dialog.trigger('shown.bs.modal');
// 	                           }
// 	                       }

// 	                   });   
// 	               };
                   var selectItems = {};
                   var contents = [{title:'请选择', value: ''}];
                   selectItems['selectedPacketNameID'] = contents;
                   var contents = [{title:'请选择', value: ''}];

                   contents.push({title:'不加压' , value:'否'});
                   contents.push({title:'加压' , value:'是'});
                   selectItems['compressionID'] = contents;
                   var contents = [{title:'请选择', value: ''}];
        		   contents.push({title:'不加密' , value:'否'});
        		   contents.push({title:'加密' , value:'是'});

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
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>'
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
	            //alert(dialog.html())
	            self.initPage(dialog.find('form'));
// 	            $(document).ready(function(){
// 	        		$("#uploadify").uploadify({
// 	        			'uploader':'${pageContext.request.contextPath}/TaskPacket/uploadFile.koala?taskId='+taskId,
// 	        			'swf':'/lib/uploadify.swf',
// 	        			'cancelImg':'/lib/uploadify-cancel.png',
// 	        			'folder':'uploads',
// 	        			'queueID':'fileQueue',
// 	        			'queueSizeLimit':1,
// 	        			'auto':false,
// 	        			'fileObjName':'file',
// 	        			'multi':false,
// 	        			'simUploadLimit':1,
// 	        			'buttonText':'选择文件',
// 	        			'onUploadSuccess':function(file,data,response){
// 	        				alert(file.name+'上传成功!');
// 	        				dialog.modal('hide');
//     						grid.data('koala.grid').refresh(); 
//     						grid.message({
//     							type: 'success',
//     							content: '上传成功'
//     					    });
// 	        			},
// 	        		    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
// 	                        alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
// 	                    } 
// 	        		});
// 	            });
	            dialog.find('#upload').on('click',{grid: grid}, function(e){
	            	
	            	$('#uploadify').uploadify('upload');
	            	//dialog.modal('hide');
	            	//e.data.grid.data('koala.grid').refresh(); 

			
	            	   /*  $.ajaxFileUpload({  
	            	        url : '${pageContext.request.contextPath}/TaskPacket/upload.koala?packetFrom='+'外部报文'+'&taskId='+taskId,   //submit to UploadFileServlet  
	            	        secureuri : false,  
	            	        fileElementId : 'fileToUpload',  
	            	        dataType : 'text', //or json xml whatever you like~  
	            	        success : function(data, status) {
	            	        	alert(status);
	            	            $("#result").append(data);
	            	            dialog.modal('hide');
	    						e.data.grid.data('koala.grid').refresh(); 
	    						e.data.grid.message({
	    							type: 'success',
	    							content: '上传成功'
	    					    });
	            	            //$("#result").parent().parent().parent().parent().parent().parent().parent().modal('hide');

	            	        },  
	            	        error : function(data, status, e) {  
	            	            $("#result").append(data);  
	            	            return false;  
	            	        }  
	            	    });  */ 

		        });
	            dialog.find('#cancelUpload').on('click',{grid: grid}, function(e){
	            	$('#uploadify').uploadify('cancel');
	            });
	        });
	    }       	
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	document.getElementById("<%=formId2%>").style.display="none";
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
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">报文名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="selectedPacketName" class="form-control" type="text" style="width:180px;" id="selectedPacketNameID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">加压:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="compression" class="form-control" type="text" style="width:180px;" id="compressionID"  />
        </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">加密:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="encryption" class="form-control" type="text" style="width:180px;" id="encryptionID"  />
        </div>
                </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>	
</form>

<form name=<%=formId2%> id=<%=formId2%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
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
            <input name="dataType" class="form-control" type="text" style="width:160px;" id="dataTypeID"  />
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
</form>

<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>
