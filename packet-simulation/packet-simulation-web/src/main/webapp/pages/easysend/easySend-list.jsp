<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<% 
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>

<style>
#contentID li a {
	font-size: 13px;
	font: "Microsoft Yahei", "宋体", verdana;
	padding: 4px;
}

form p {
	clear: left;
	margin: 0;
	padding: 0;
	padding-top: 5px;
}

form p .rgt {
	float: left;
	width: 26%;
	text-align: right;
	padding-top:6px;
	font: "Microsoft Yahei", "宋体", verdana;
}

form p .lft {
	font-weight: 100;
	width: 30%;
	text-align: left;
	color: #acacac;
	margin-left: 10px;
}

fieldset {
	border: 1px bolid #61B5CF;
	margin-top: 16px;
	padding: 10px;
	font: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

legend {
	font: bold 1em Arial, Helvetica, sans-serif;
	color: #999999;
	background-color: #FFFFFF;
}

.easySendPannel {
	border: 1px solid #dddddd !important;
    margin-bottom: 4px;
}

.easySendPannel .u-tree{
 		min-height:50px;
	}
	
.easySendPannel .table{
		margin-bottom:2px;
	}

.easySendPannel .row>.form-group {
	margin-left: 0;
	margin-right: 0;
	border: 1px solid #DDDDDD;
}

.easySendPannel .control-label {
	text-align: right;
}

.easySendPannel .base-info {
	border-width: 1px 0 0 1px;
	border-collapse: separate;
	margin-bottom: 10px;
}

.easySendPannel .grid-table-body {
	height: 308px !important
	
}

#taskPacketGrid .grid-table-body {
	height: 208px !important
	
}

.base-info {
	border-collapse: separate;
}

.base-info td {
	border-width: 0 1px 1px 0 !important;
	border-color: transparent #DDDDDD #DDDDDD transparent !important;
	border-style: solid
}
</style>
<div class="easySendPannel" id="departmentDetail">
	<ul class="u-tree tree" id="mesgTypeTree" oncontextmenu="return false"></ul>
	<div class="right-content" style="padding: 5px; width: 74%">
		<form id="mesgSearchForm" target="_self"
			class="form-horizontal searchCondition">
			<input name="mesgType" id="mesgType"  class="form-control"
				type="hidden" />
		</form>
		<div data-role="mesgGrid" id="mesgGrid"></div>

	</div>
	<div style="clear:both;"></div>
</div>
		<div data-role="taskPacketGrid" id="taskPacketGrid"></div>
<script type="text/javascript" src="<c:url value='/js/organisation/department.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/organisation/select-employee.js' />"></script>

<script>
  
	PageLoader = {
			//初始化记录模板列表
			initGridPanel : function() {
				var self = this;
				var columns = [
						{
							title : '用例名称',
							name : 'remark',
							width : 200
						},
						{
							title : '创建人',
							name : 'createBy',
							width : 100
						},
						{
							title : '操作',
							width : 180,
							render : function(rowdata, name, index) {
								var param = '"' + rowdata.id + '"';
								var h = "<a href='javascript:openDetailsPageOfMesg("
										+ param + ")'>查看</a> ";
								return h;
							}
						} ];
				var buttons = [
						{
							content : '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>',
							action : 'add'
						},
						{
							content : '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>',
							action : 'modify'
						},
						{
							content : '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>',
							action : 'delete'
						},
						{
							content : '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-log-out"><span>发送</button>',
							action : 'send'
						}
						,
						{
							CONTENT : '<KS:HASSECURITYRESOURCE IDENTIFIER="JOBMANAGERQUERY"><BUTTON CLASS="BTN BTN-SUCCESS" TYPE="BUTTON"><SPAN CLASS="GLYPHICON GLYPHICON-SEARCH"></SPAN>&NBSP;高级搜索<SPAN CLASS="CARET"></SPAN></BUTTON></KS:HASSECURITYRESOURCE>',
							ACTION : 'SEARCH'
						} 
						];
				var url = contextPath + '/Mesg/pageJson.koala';
				return $('[data-role="mesgGrid"]').grid({
					identity : 'id',
					columns : columns,
					buttons : buttons,
					autoLoad:false,
					querys: [{title: '用例名称', value: 'remark'},{title: '创建人', value: 'createBy'}],
					url : url
				}).on({
					'add' : function() {
						self.add($(this));
					},
					'modify' : function(event, data) {
						var indexs = data.data;
						var $this = $(this);
						if (indexs.length == 0) {
							$this.message({
								type : 'warning',
								content : '请选择一条记录进行修改'
							})
							return;
						}
						if (indexs.length > 1) {
							$this.message({
								type : 'warning',
								content : '只能选择一条记录进行修改'
							})
							return;
						}
						self.modify(indexs[0], $this);
					},
					'delete' : function(event, data) {
						var indexs = data.data;
						var $this = $(this);
						if (indexs.length == 0) {
							$this.message({
								type : 'warning',
								content : '请选择要删除的记录'
							});
							return;
						}
						var remove = function() {
							self.remove(indexs, $this);
						};
						$this.confirm({
							content : '确定要删除所选记录吗?',
							callBack : remove
						});
					},
				   'send': function(event, data){
	                       	var indexs = data.data;
	                       	var $this = $(this);
	                       	if(indexs.length == 0){
	                           	$this.message({
	                              		type: 'warning',
	                                	content: '请选择一条记录进行发送'
	                           	})
	                           	return;
	                      	 	}
	                      	self.send(indexs, $this);
	                   	},
					'search' : function() {
						$("#mesgQueryDiv").slideToggle("slow");
					}
				});
			},
			//初始化任务列表
			initTaskPacketGridPanel : function() {
				var self = this;
				var columns = [
				            { title: '报文名称', name: 'task', width: 150,
				            	render:function(item, name, index){
				            		return item[name].taskName
				            	}	
				            },
	                        { title: '文件名', name: 'selectedPacketName', width: 185},
	                        { title: '记录类型', name: 'selectedRecordType', width: 60},
	                        { title: '发送渠道', name: 'task', width: 100,
				            	render:function(item, name, index){
				            		return item[name].sendChannel
				            	}	
	                        },
                         	{ title: '加压', name: 'compression', width: 40,
	                        	render:function(item, name, index){
				            		return item[name]==1?"是":"否";
				            		}
				            	},
                         	{ title: '加密', name: 'encryption', width: 40,
				            		render:function(item, name, index){
			            		return item[name]==1?"是":"否";
		            		}
		            		},
                         	{ title: '发送时间', name: 'selectedOrigSendDate', width: 100,
	                        	render: function(item, name, index){
					                //alert(item[name].getMonth());
					                if(item[name]){
					                var d = new Date(item[name]);    //根据时间戳生成的时间对象
								var date = (d.getFullYear()) + "-" + 
										(d.getMonth() + 1) + "-" +
										(d.getDate()) + " " + 
										(d.getHours()) + ":" + 
										(d.getMinutes()) + ":" + 
										(d.getSeconds());
					                return date;
					             }	
					                }
	                        },
	                        { title: '反馈时间', name: 'receiveDate', width: 100,
	                        	render: function(item, name, index){
					                if(item[name]){
					                var d = new Date(item[name]);    //根据时间戳生成的时间对象
								var date = (d.getFullYear()) + "-" + 
										(d.getMonth() + 1) + "-" +
										(d.getDate()) + " " + 
										(d.getHours()) + ":" + 
										(d.getMinutes()) + ":" + 
										(d.getSeconds());
					                return date;
					             }	else{
					            	 return "-";
					             }
					                }
	                        },
                         	{ title: '状态', name: 'sendState', width: 100,
	                        	render: function(item, name, index){
					                 if(item[name] == '0'){
					                     return '失败';
					                 }else if(item[name] == '1'){
					                     return '已发送';
					                 }else if(item[name] == '2'){
					                	 return '已反馈';
					                 }else{
					                	 return '-';
					                 }
					             }		
	                        },
						{
							title : '操作',
							render : function(rowdata, name, index) {
								var param = '"' + rowdata.id + '"';
								var h = "<a href='javascript:openDetailsPageOfMesg("+ param + ")'>查看文件</a>&nbsp&nbsp&nbsp&nbsp<a href='javascript:openDetailsPageOfMesg("
										+ param + ")'>查看反馈</a>&nbsp&nbsp&nbsp&nbsp<a href='javascript:deleteTaskPacket("+param+")'>删除</a> ";
								return h;
							}
						} ];
				var buttons = [];
				var url = contextPath + '/TaskPacket/pageJson.koala';
				return $('[data-role="taskPacketGrid"]').grid({
					identity : 'id',
					columns : columns,
					buttons : buttons,
					isShowIndexCol:false,
					
					//querys: [{title: '用例名称', value: 'remark'},{title: '创建人', value: 'createBy'}],
					url : url
				}).on({
					'add' : function() {
						self.add($(this));
					},
					'modify' : function(event, data) {
						var indexs = data.data;
						var $this = $(this);
						if (indexs.length == 0) {
							$this.message({
								type : 'warning',
								content : '请选择一条记录进行修改'
							})
							return;
						}
						if (indexs.length > 1) {
							$this.message({
								type : 'warning',
								content : '只能选择一条记录进行修改'
							})
							return;
						}
						self.modify(indexs[0], $this);
					},
					'delete' : function(event, data) {
						var indexs = data.data;
						var $this = $(this);
						if (indexs.length == 0) {
							$this.message({
								type : 'warning',
								content : '请选择要删除的记录'
							});
							return;
						}
						var remove = function() {
							self.remove(indexs, $this);
						};
						$this.confirm({
							content : '确定要删除所选记录吗?',
							callBack : remove
						});
					},
				   'send': function(event, data){
	                       	var indexs = data.data;
	                       	var $this = $(this);
	                       	if(indexs.length == 0){
	                           	$this.message({
	                              		type: 'warning',
	                                	content: '请选择一条记录进行发送'
	                           	})
	                           	return;
	                      	 	}
	                      	self.send(indexs, $this);
	                   	}
				});
			},
			
    	    add: function(grid){
    	        var self = this;
    	        var mesgType=$("#mesgType").val();
    	        if(!mesgType){
    	        	grid.message({
                		type: 'warning',
                    	content: "请先选择记录类型"
                	});
    	        	return;
    	        }
    	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;">'
    	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
    	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
    	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
    	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
    	        	+'</div></div>');
    	        $.get('<%=path%>/easyMesg-add.jsp').done(function(html){
    	            dialog.modal({
    	                keyboard:false
    	            }).on({
    	                'hidden.bs.modal': function(){
    	                    $(this).remove();
    	                }
    	            }).find('.modal-body').html(html);
    	            self.initPage(dialog.find('form'));
    				dialog.find("#content").empty();
    				$.get(
    						'${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/'
    								+ mesgType+ '.koala').done(function(data) {
    					//alert(data.data)
    					dialog.find("#content").append(data.data);
    				});
    	            dialog.find("#mesgType").val(mesgType);
    	        });
    	        dialog.find('#save').on('click',{grid: grid}, function(e){
					var info = dialog.find($(".tab-content")).attr('id');
					var xml = '<?xml version="1.0" encoding="UTF-8"?><Document><'+info+'>';
					dialog.find($(".tab-pane")).each(function(){   					
    					var id = $(this).attr('id');
    					xml += '<' + id + '>';
    					var flag = '';
    					$('#'+id).find($("[name]")).each(function(){
							if($(this).parent().parent()[0].tagName != 'FIELDSET'){
								var name = ($(this).attr('name'));
	    						var value = ($(this).val());
								xml += '<' + name + '>' + value + '</' + name + '>';
							}
							else{
								var name = ($(this).attr('name')); 
								xml += '<' + name + '>';
								$(this).parent().parent().find($("[subName]")).each(function(){
									var subName = ($(this).attr('subName'));
									var value = ($(this).val());	
									xml += '<' + subName + '>' + value + '</' + subName + '>'; 
								});
								xml += '</' + name + '>';
							}
    					});
    					xml += '</' + id + '>';
  					});
					xml += '</'+info+'></Document>';
 				if(!Validator.Validate(dialog.find('form')[0],3))return;
 				var data = [{ name: 'nodeValues', value: xml },
 				            { name: 'mesgType', value: dialog.find("#mesgType").val()},
 				            { name: 'remark', value: dialog.find("#remarkID").val()}
 				           ];
    	        $.post('${pageContext.request.contextPath}/Mesg/add.koala', data).done(function(result){
     	        	if(result.success ){
     	            	dialog.modal('hide');
     	                e.data.grid.data('koala.grid').refresh();
     	                e.data.grid.message({
     	                	type: 'success',
     	                	content: '保存成功'
                		});
     	            }else{
   	                    dialog.find('.modal-dialog').append('<div class="errMsg" style="float:left;position:absolute;max-width:500px;min-height:200px;bottom:20px;left:20px;background-color:#4cae4c;color:white;">'+result.errorMessage+ '</div>');
  				    }
      	        });    	        
    	        });
   	    },
    	    send: function(ids,grid){
    	        var self = this;
    	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
    	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    	        	+'<h4 class="modal-title">发送配置</h4></div><div class="modal-body">'
    	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
    	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
    	        	+'<button type="button" class="btn btn-success" id="send">发送</button></div></div>' 
    	        	+'</div></div>');
    	        $.get('<%=path%>/easySend-config.jsp').done(function(html){
    	            dialog.modal({
    	                keyboard:false
    	            }).on({
    	                'hidden.bs.modal': function(){
    	                    $(this).remove();
    	                }
    	            }).find('.modal-body').html(html);
    	            self.initPage(dialog.find('form'));
    	            var data = [{ name: 'ids', value: ids.join(',') },{ name: 'mesgType', value: $("#mesgType").val() }];
    	            $.get( '${pageContext.request.contextPath}/Mesg/initSend.koala',data).done(function(json){
    	            	var json=json.data;
    	            	dialog.find("#mesgContentID").val(json.mesgContent);
    	            	dialog.find("#selectedRecordTypeID").val(json.mesgType);
	                       json = json.data;
	                });
    	            
    	        });
    	        dialog.find('#send').on('click',{grid: grid}, function(e){
     		        if(!Validator.Validate(dialog.find('form')[0],3))return;
    	              $.post('${pageContext.request.contextPath}/Mesg/send.koala', dialog.find('form').serialize()).done(function(result){
    	                   if(result.success ){
    	                        dialog.modal('hide');
    	                        e.data.grid.data('koala.grid').refresh();
    	                        $("#taskPacketGrid").getGrid().refresh()
    	                        e.data.grid.message({
    	                            type: 'success',
    	                            content: '发送成功'
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
    	    	alert(ids)
    	    	var data = [{ name: 'ids', value: ids.join(',') }];
    	    	$.post('${pageContext.request.contextPath}/Mesg/delete.koala', data).done(function(result){
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
		PageLoader.initGridPanel();
		PageLoader.initTaskPacketGridPanel();
		$(".easySendPannel .u-tree").css("height",$("#mesgGrid").height());
		//窗口大小变化调节各组建大小
		window.onresize=function(){
			$("#mesgGrid").getGrid()._reSize();
			$("#taskPacketGrid").getGrid()._reSize();
			$(".easySendPannel .u-tree").css("height",$("#mesgGrid").height());
		}
		//删除文件
		var deleteTaskPacket= function(id){
			var remove = function() {
				var data = [{ name: 'ids', value: id }];
				var grid=$('#taskPacketGrid');
    	    	$.post('${pageContext.request.contextPath}/TaskPacket/delete.koala', data).done(function(result){
    	                        if(result.success){
    	                            grid.getGrid().refresh();
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
			};
			$('#taskPacketGrid').confirm({
				content : '确定要删除所选记录吗?',
				callBack : remove
			});
		}
		 var openDetailsPageOfMesg = function(id){
            var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/easyMesg-view.jsp').done(function(html){
                   dialog.find('.modal-body').html(html);
                   $.get( '${pageContext.request.contextPath}/Mesg/get/' + id + '.koala').done(function(json){
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
		  $(function(){
		var form = $("#mesgSearchForm");
		form.find('#mesgSearchBtn').on('click', function() {
			var params = {};
			form.find('.form-control').each(function() {
				var $this = $(this);
				var name = $this.attr('name');
				if (name) {
					params[name] = $this.val();
				}
			});
			$('[data-role="mesgGrid"]').getGrid().search(params);
		});
		
	
	    
		/**
		 * 生成模板类型树
		 */
		$('#mesgTypeTree').loader({
			opacity : 0
		});
		$
				.get(
						contextPath
								+ '/MesgType/findMesgTypesByCreateUser.koala?userName=root')
				.done(
						function(data) {
							$('#mesgTypeTree').loader('hide');
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
							$('#mesgTypeTree')
									.tree(
											{
												dataSource : dataSourceTree,
												loadingHTML : '<div class="static-loader">Loading...</div>',
												multiSelect : false,
												useChkBox : false,
												cacheItems : true
											}).on(
											{
												'selectChildren' : function(
														event, data) {
													showMesgTempletes(data.id);
												}
											});
						});
		
	})

	var showMesgTempletes = function(id) {
		var form = $("#mesgSearchForm");
		$("#mesgType").val(id);
		var params = {};
		form.find('.form-control').each(function() {
			var $this = $(this);
			var name = $this.attr('name');
			if (name) {
				params[name] = $this.val();
			}
		});
		//             params['mesgType']=id;
		$('[data-role="mesgGrid"]').getGrid().search(params);
	};
	  function cloneHtml(obj,countTagId){
	    	var brother = $($(obj).parent().next().children("FIELDSET:first").get(0));
	    	if(brother.get(0).tagName=="FIELDSET"){
	    		var countNum = $("#"+countTagId).val();
	        	$("#"+countTagId).val(parseInt(countNum) + 1);
	        	var html = $($(obj).parent().next().children("FIELDSET:first").get(0)).clone();
	        	$(obj).parent().next().append(html);
	        	html.find($('[data-toggle="tooltip"]')).tooltip();
	    	}
	    }
	    
	    function removeHtml(obj,countTagId){
	    	var brother = $($("#"+countTagId+"_div").children("FIELDSET").get(1));
	    	if(brother.get(0)!=null && brother.get(0).tagName=="FIELDSET"){
	    		var countNum = $("#"+countTagId).val();
	        	$("#"+countTagId).val(parseInt(countNum) - 1);
	        	$(obj).parent().parent().remove();
	        	return;
	    	}else{
	    		alert("不能删除，资料记录必须大于一条！");
	    	}
	    }
	    function removeTab(obj,tabId){
	    	$(obj).parent().parent().remove();
	    	var nextElement = $('#'+tabId).next();
	    	$('#'+tabId).remove();
	    	nextElement.attr("class","tab-pane fade active in");
	    }
</script>
