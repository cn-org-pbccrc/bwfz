<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="lotSubmissionDetail" id="lotSubmissionDetail">
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
var lotId = $('.lotSubmissionDetail').parent().attr('data-value');
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
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-cloud-upload"><span>上传</button>', action: 'upload'},
	                        {content: '<button class="btn btn-inverse" type="button"><span class="glyphicon glyphicon-export"><span>导出</button>', action: 'export'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-up"><span>上移</button>', action: 'up'},
	                        {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-arrow-down"><span>下移</button>', action: 'down'},
	                        {content: '<button class="btn btn-warning" type="button"><span class="glyphicon glyphicon-refresh"><span>刷新</button>', action: 'fresh'}
	                    ],
	                url:"${pageContext.request.contextPath}/LotSubmission/pageJson/" + lotId + ".koala",
	                columns: [
								{ title: '报文来源', name: 'submissionFrom', width: 2*width/3,
									render: function(item, name, index){
          								if(item[name] == '0'){
              								return '内部';
          								}else if(item[name] == '1'){
              								return '外部';
          								}
      								}
								},
	                     	                         	                         { title: '文件名称', name: 'name', width: 3*width/2},
	                         	                         	                         	                         { title: '加压', name: 'compression', width: 2*width/3,
	                     	                         	                        									render:function(item, name, index){
	                     	                       				            											return item[name] == 1 ? "是" : "否";
	                     	                       				            										}
	                     	                         	                         								 },
	                         	                         	                         	                         { title: '加密', name: 'encryption', width: 2*width/3,
	                     	                         	                         									render:function(item, name, index){
	                     	                         	                         										return item[name] == 1 ? "是" : "否";
	                     	   	                     	                       				            			}	 
	                         	                         	                         	                         },
	                         	                         	                         	                         { title: '顺序号', name: 'serialNumber', width: width},
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openLotSubmissionView(" + param + ")'>查看报文文件</a> ";
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
	                   'upload': function(){
		                   self.upload($(this));
		               },
		               'export': function(event, data){
	                   	   var indexs = data.data;
	                       var $this = $(this);
	                       if(indexs.length == 0){
	                           $this.message({
	                               type: 'warning',
	                               content: '请选择一条记录进行导出'
	                           })
	                           return;
	                       }
	                       if(indexs.length > 1){
	                           $this.message({
	                               type: 'warning',
	                               content: '只能选择一条记录进行导出'
	                           })
	                           return;
	                       }
	                       self.exportLotSubmission(indexs[0]);
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
	        $.get('<%=path%>/LotSubmission-add.jsp').done(function(data){
	        	var dialog = $(data);
	            dialog.modal({
	                keyboard:false,
	                backdrop: false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                },
	    	        'shown.bs.modal': function(){
	    	            var columns = [
	    	                { title: '报文名称', name: 'name' , width: 250},
	    	                { title: '报文类型', name: 'recordTypeStr', width: 250},
	    	                { title: '是否加压', width: 200, render: function (rowdata, name, index)
	                            {
	    	                        var param = rowdata.id;  	                    		
                                    var h = "<label><input id='compressionId" + param + "' name='compression' type='checkbox' value='' checked='checked'/></label>";
                                    return h;
                                }
	    	                },
	    	                { title: '是否加密', width: 200,  render: function (rowdata, name, index)
	                            {
	    	                    	var param = rowdata.id;
	    	                    	var h = "<label><input id='encryptionId" + param + "' name='encryption' type='checkbox' value='' checked='checked'/></label>";
                                    return h;
                                }
	    	                }
	    	            ];//<!-- definition columns end -->
	    	            //查询出当前表单所需要得数据。
	    	            dialog.find('.selectSubmissionGrid').grid({
	    	                identity: 'id',
	    	                columns: columns,
	    	                querys: [{title: '报文名称', value: 'name'}],
	    	                url: contextPath + '/Submission/pageJsonByType/' + lotId + '.koala'
	    	            });
	    	        }
	            });
	            dialog.find('#selectSubmissionGridSave').on('click',{grid: grid}, function(e){
		        	var items = dialog.find('.selectSubmissionGrid').data('koala.grid').selectedRows();
	    	    	var isComs = new Array;
	    	    	var isEncs = new Array;
	    	    	var flagIds = new Array();
	    	    	for(var i = 0; i < items.length; i++){
	    	    		flagIds[i] = items[i].id;
						isComs[i] = $("input[id='compressionId"+flagIds[i]+"']").is(':checked');
						if(isComs[i] == true){
							isComs[i] = 1;
						}else if(isComs[i] == false){
							isComs[i] = 0;
						}
						isEncs[i] = $("input[id='encryptionId"+flagIds[i]+"']").is(':checked');
						if(isEncs[i] == true){
							isEncs[i] = 1;
						}else if(isEncs[i] == false){
							isEncs[i] = 0;
						}
					}
	    	    	if(items.length == 0){
	    	    		dialog.find('.selectSubmissionGrid').message({
	    	        		type: 'warning',
	    	            	content: '请选择报文'
	    	        	});
	    	    	}                          
		    	    var data = [
						{ name: 'flagIds', value: flagIds.join(',')},
	     				{ name: 'compressions', value: isComs.join(',')},
	     				{ name: 'encryptions', value: isEncs.join(',')},
	     				{ name: 'lotId', value: lotId},
	     				{ name: 'submissionFrom', value: 0}
	     			];
		        	$.post('${pageContext.request.contextPath}/LotSubmission/add.koala', data).done(function(result){
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
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/LotSubmission-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/LotSubmission/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/LotSubmission/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	    upload: function(grid){
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">上传外部文件</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div></div>');	        
	        $.get('<%=path%>/LotSubmission-upload.jsp').done({ 
                'lotId' :  lotId
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
	            dialog.find("#input-id").on('fileselect', function(event, numFiles, label) {
	            	var fileName = dialog.find(".file-caption-name").attr('title');
	            	var data = [{ name: 'lotId', value: lotId },
	    	    	            { name: 'fileName', value: fileName }
	    	    	]; 
	            	$.post('${pageContext.request.contextPath}/LotSubmission/checkExisting.koala', data).done(function(result){
	            		if(result == 1){
	            			dialog.find(".file-error-message").attr('style','display:block');
		                	dialog.find(".file-error-message").append('<ul><li>已上传同名的文件，请确认是否继续上传覆盖原文件</li></ul>');
	            		}	            		
	            	});	                
	            });
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
	    exportLotSubmission: function(id){
	    	var date = new Date();
	    	window.open('${pageContext.request.contextPath}/LotSubmission/exportLotSubmission/' + id + '.koala?id=' + date.getTime() + '.txt');	    
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
	    	$.post('${pageContext.request.contextPath}/LotSubmission/up.koala', data).done(function(result){
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
	    	$.post('${pageContext.request.contextPath}/LotSubmission/down.koala', data).done(function(result){
	        	if(result.success){
	            	//grid.data('koala.grid').refresh();
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
	           	   contents.push({title:'是' , value:'1'});
	               contents.push({title:'否' , value:'0'});
	               selectItems['compressionID'] = contents;
	               var contents = [{title:'请选择', value: ''}];
	               contents.push({title:'是' , value:'1'});
	               contents.push({title:'否' , value:'0'});
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
	    	$.post('${pageContext.request.contextPath}/LotSubmission/delete.koala', data).done(function(result){
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

var openLotSubmissionView = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/LotSubmission-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get('${pageContext.request.contextPath}/LotSubmission/getLotSubmissionView/' + id + '.koala').done(function(json){
                   json = json.data;
            	   dialog.find("#lotSubmissionViewID").html("<div style='width:780px;overflow:auto;'><xmp>"+json+"</xmp></div>");
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
            	<label class="control-label" style="width:100px;float:left;">文件名称:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            		<input name="name" class="form-control" type="text" style="width:180px;" id="nameID"/>
            	</div>
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
</div>