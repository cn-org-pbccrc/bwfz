<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<style>
#contentID li a{
	font-size:13px;
	 font: "Microsoft Yahei", "宋体", verdana;
	padding:4px;
}
form p {
  clear: left;
  margin: 0;
  padding: 0;
  padding-top: 5px;
}
form p .rgt {
  float: left;
  width: 22%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
form p .lft {
  font-weight:100;
  width: 30%;
  text-align:left;
  color:#acacac;
  margin-left:10px;
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
</style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>

</head>
<body>

<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">

<!-- <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">流水号:&nbsp;&nbsp;<span id="packIdSpan" ></span> -->
<!--                     <input type="hidden" id="pacId" /> -->
<!--                     </label> -->
	                    
<!-- 			   </div>	 -->
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>

<script type="text/javascript">

    var selectItems = {};
    var tabData = $('.tab-pane.active').data();
    var packetId = tabData.packetId;
    var packId = tabData.packId;
    
    var mesgTypeOption = '';
    var grid;
    var form;
    var _dialog;
    
    $(function (){
    	$.get( '${pageContext.request.contextPath}/MesgType/findAllMesgType.koala').done(function(data){
            json = data;
            for(var index in json){
            	mesgTypeOption=mesgTypeOption+'<option value="'+json[index].id+'"> '+json[index].mesgType +' ：'+json[index].filePath + '</option>';
            }
     	});
    	$('#packIdSpan').text(packId);
    	$('#pacId').val(packetId);
    	//alert(mesgTypeOption)
        grid = $("#<%=gridId%>");
        form = $("#<%=formId%>");
    	PageLoader = {
    	   //
    	    initGridPanel: function(){
    	         var self = this;
    	         var width = 180;
    	         return grid.grid({
    	                identity:"id",
    	                buttons: [
    	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
    	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
    	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
    	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>批量添加</button>', action: 'batch'}
    	                    ],
    	                url:"${pageContext.request.contextPath}/Mesg/pageJson/" + packetId + ".koala",
    	                columns: [
																							//{ title: '流水号', name: 'mesgId', width: width},
																							 { title: '备注', name: 'remark', width: width},
    	                         	                         	                         	 { title: '报文类型', name: 'mesgTypeStr', width: width},
    	                         	                         	                         	 
//     	                         	                         	                         	{ title: '优先级', name: 'mesgPriority', width: width},
//     	                         	                         	                         	{ title: '传输方向', name: 'mesgDirection', width: width},
//     	                         	                         	                         	{ title: '保留域', name: 'reserve', width: width},
    	                         	                         	                         	
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
    	                   'batch': function(event, data){
  	                        	var indexs = data.data;
  	                        	var $this = $(this);
  	                        	if(indexs.length == 0){
  	                            	$this.message({
  	                               		type: 'warning',
  	                                 	content: '请选择一条记录进行批量添加'
  	                            	})
  	                            	return;
  	                       	 	}
  	                        	if(indexs.length > 1){
  	                            	$this.message({
  	                                	type: 'warning',
  	                                	content: '只能选择一条记录进行批量添加'
  	                            	})
  	                            	return;
  	                        	}
  	                       		self.batch(indexs[0], $this);
  	                    	}
    	         });
    	    },
    	    add: function(grid){
    	        var self = this;
    	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;">'
    	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
    	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
    	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
    	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
    	        	+'</div></div>');
    	        
    	        $.get('<%=path%>/Mesg-add.jsp').done(function(html){
    	            dialog.modal({
    	                keyboard:false
    	            }).on({
    	                'hidden.bs.modal': function(){
    	                    $(this).remove();
    	                }
    	            }).find('.modal-body').html(html);
    	            
    	            self.initPage(dialog.find('form'));
    	            
    	            dialog.find("#mesgTypeID").append(mesgTypeOption);
    	            //alert(mesgTypeOption)
    	            dialog.find("#packetIdID").val(packetId);
    	        });
    	     
    	        dialog.find('#save').on('click',{grid: grid}, function(e){
    	        	//alert(1);
//     	        	var reg = /^[0-9a-zA-Z_-]{6,20}$/;
//     	        	var mesgPriorityID = $("#mesgPriorityID").val();
//     	        	if(!reg.test(mesgPriorityID)){
//     	        		alert("no");
//     	        	}
// var haha = $("input[name='Nm']").parent().parent().parent().attr('id')
// alert(haha);


					var xml = '<?xml version="1.0" encoding="UTF-8"?><Document><AcctInf>';
					dialog.find($(".tab-pane")).each(function(){
    					
    					var id = $(this).attr('id');
    					xml += '<' + id + '>';
    					var flag = '';
    					$('#'+id).find($("[name]")).each(function(){
    						
    						
//     						alert($(this).parent().parent()[0].tagName == 'FIELDSET')
    					
//     						if(value != ''){
//     							xml += '<' + name + '>' + value + '</' + name + '>';	
//     						}
//     						else{
//     							xml += flag + '<' + name + '>';
//     							flag = '</' + name + '>';
//     						}
    						
//   					});
//     					xml += flag;
//     					xml += '</' + id + '>';
    					
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
					xml += '</AcctInf></Document>';
					//alert(xml);
// 					dialog.find($("[name]")).each(function(){
//     					alert($(this).attr('value'))
//   				});
// 				var div=$("#AcctBsSgmt");
// 				div.find($('[name]')).each(function(){
// 					alert($(this).attr('name'))
// 				});
    	        

// 				$.ajax({
// 					      type : "post",
// 						  url : '${pageContext.request.contextPath}/Mesg/add.koala',
// 						  async : false,
// 						  data : {
// 						  	  "xml" : xml
// 		                  },
// 						  cache : false,
// 						  dataType : "json",
// 						  success : function(msg) {
// 						      alert("保存成功")	  
// 						  },
// 						  error : function(msg) {
// 							  alert("保存失败")
// 						  }
// 				});
 				if(!Validator.Validate(dialog.find('form')[0],3))return;
 				var data = [{ name: 'nodeValues', value: xml },
 				            { name: 'mesgType', value: dialog.find("#mesgTypeID").val()},
 				            { name: 'packetId', value: dialog.find("#packetIdID").val()},
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
						alert(result.errorMessage);
   	                    dialog.find('.modal-dialog').append('<div class="errMsg" style="float:left;position:absolute;max-width:500px;min-height:200px;bottom:20px;left:20px;background-color:#4cae4c;color:white;">'+result.errorMessage+ '</div>');
  				    }
      	        });
    	        	
//     	              if(!Validator.Validate(dialog.find('form')[0],3))return;
//     	              $.post('${pageContext.request.contextPath}/Mesg/add.koala', dialog.find('form').serialize()).done(function(result){
//     	                   if(result.success ){
//     	                        dialog.modal('hide');
//     	                        e.data.grid.data('koala.grid').refresh();
//     	                        e.data.grid.message({
//     	                            type: 'success',
//     	                            content: '保存成功'
//     	                         });
//     	                    }else{
    	                    	
    	                    	//alert(result.errorMessage);
     	                    	//dialog.find('.modal-dialog').append('<div class="errMsg" style="float:left;position:absolute;max-width:500px;min-height:200px;bottom:20px;left:20px;background-color:#4cae4c;color:white;">'+result.errorMessage+ '</div>');
// //     	                    	e.data.grid.message({
// //     	                            type: 'error',
// //     	                            content: result.errorMessage
// //     	                         });
    	                    	
// //     	                    	dialog.find('.errMsg').text(result.errorMessage);
// //     	                    	dialog.find('.errMsg').css("display","inline");
// //     	                        dialog.find('.modal-content').message({
// //     	                            type: 'error',
// //     	                            content: result.errorMessage
// //     	                        });
//      	                     }
//      	              });
    	        });
   	    },
    	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	      
	        $.get('<%=path%>/Mesg-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/Mesg/initUpdate/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
	                        	elm = dialog.find('#'+ index + 'ID');
	                        	if(index=='content'){
	                        		elm.append(json[index]);
	                        	}else{
		                            if(elm.hasClass('select')){
		                                elm.setValue(json[index]);
		                            }else{
		                                elm.val(json[index]);
		                            }
	                        	}
	                        }
	                        //mesgType = json['mesgType'];
	    				    //packetId = json['packetId'];
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    //if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    //$.post('${pageContext.request.contextPath}/Mesg/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
	                        //if(result.success){
	                            //dialog.modal('hide');
	                            //e.data.grid.data('koala.grid').refresh();
	                            //e.data.grid.message({
	                            //type: 'success',
	                            //content: '保存成功'
	                            //});
	                        //}else{
	                        	//alert(result.errorMessage);
// 	                            dialog.find('.modal-content').message({
// 	                            type: 'error',
// 	                            content: result.actionError
// 	                            });
	                        //}
	                    //});
	                	var xml = '<?xml version="1.0" encoding="UTF-8"?><Document><AcctInf>';
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
						xml += '</AcctInf></Document>';
						//alert(xml);	           
	                if(!Validator.Validate(dialog.find('form')[0],3))return;
	 				var data = [{ name: 'nodeValues', value: xml },
	 				            { name: 'mesgType', value: dialog.find("#mesgTypeID").val()},
	 				            { name: 'packetId', value: dialog.find("#packetIdID").val()},
	 				            //{ name: 'id', value: dialog.find("#idID").val()},
	 				            { name: 'mesgId', value: dialog.find("#mesgIdID").val()},
	 				            { name: 'remark', value: dialog.find("#remarkID").val()},
	 				            { name: 'version', value: dialog.find("#versionID").val()}
	 				           ];
	 			
	    	        $.post('${pageContext.request.contextPath}/Mesg/update.koala?id='+id, data).done(function(result){
	     	        	if(result.success ){
	     	            	dialog.modal('hide');
	     	                e.data.grid.data('koala.grid').refresh();
	     	                e.data.grid.message({
	     	                	type: 'success',
	     	                	content: '保存成功'
	                		});
	     	            }else{
							alert(result.errorMessage);
	   	                    dialog.find('.modal-dialog').append('<div class="errMsg" style="float:left;position:absolute;max-width:500px;min-height:200px;bottom:20px;left:20px;background-color:#4cae4c;color:white;">'+result.errorMessage+ '</div>');
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
    	    },
    	    batch: function(id, grid){
    	        var self = this;
    	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">批量添加</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
    	      
    	        $.get('<%=path%>/Mesg-batch.jsp').done(function(html){
    	               dialog.find('.modal-body').html(html);
    	               self.initPage(dialog.find('form'));
    	                //alert(dialog.find('#batchNumberId').val())
    	                $.get( '${pageContext.request.contextPath}/Mesg/initBatch/' + id + '.koala').done(function(json){
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
    	                	//alert(dialog.find('#batchNumberID').val())
    	            
    	                if(!Validator.Validate(dialog.find('form')[0],3))return;
    	 				var data = [{ name: 'nodeValues', value: dialog.find('#contentID').val()},
    	 				            { name: 'mesgType', value: dialog.find("#mesgTypeID").val()},
    	 				            { name: 'packetId', value: dialog.find("#packetIdID").val()},		        
    	 				            { name: 'mesgId', value: dialog.find("#mesgIdID").val()},
    	 				            { name: 'version', value: dialog.find("#versionID").val()},
    	 				            { name: 'batchNumber', value: dialog.find("#batchNumberID").val()}
    	 				           ];
    	 			
    	    	        $.post('${pageContext.request.contextPath}/Mesg/batch.koala', data).done(function(result){
    	     	        	if(result.success ){
    	     	            	dialog.modal('hide');
    	     	                e.data.grid.data('koala.grid').refresh();
    	     	                e.data.grid.message({
    	     	                	type: 'success',
    	     	                	content: '保存成功'
    	                		});
    	     	            }else{
    							//alert(result.errorMessage);
    	   	                    dialog.find('.modal-dialog').append('<div class="errMsg" style="float:left;position:absolute;max-width:500px;min-height:200px;bottom:20px;left:20px;background-color:#4cae4c;color:white;">'+result.errorMessage+ '</div>');
    	  				    }
    	      	        });
    	            });
    	        });
    	    },
    	}
    	PageLoader.initGridPanel();
    });

    var openDetailsPage = function(id){
            var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/Mesg-view.jsp').done(function(html){
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
    
    var sortNum=1;
    function addMesg(){
    	var html='<tr><td width="50"><div class="checkerbox" data-role="indexCheckbox" data-value="'+ sortNum +'" indexvalue="0" ></div></td>';
    	html=html+'<td width="70">'+ sortNum +'</td>';
    	html=html+'<td width="280"><select id="'+ sortNum +'" style="width:90%;">'+ mesgTypeOption +'</select></td>';
    	html=html+'<td width="auto"><button class="btn btn-success" type="button" onclick="modifyMesg('+ sortNum +');"><span class="glyphicon glyphicon-edit"><span>修改</span></span></button></td>';
    	html=html+'</tr>';
    	$("#content").append(html);
    	sortNum++;
    }
    
    function cloneHtml(obj,countTagId){
    	var brother = $($(obj).parent().next().children("FIELDSET:first").get(0));
    	if(brother.get(0).tagName=="FIELDSET"){
    		var countNum = $("#"+countTagId).val();
        	$("#"+countTagId).val(parseInt(countNum) + 1);
        //	console.debug(obj);
        	var html = $($(obj).parent().next().children("FIELDSET:first").get(0)).clone(true);
        	$(obj).parent().next().append(html);
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
</body>
</html>
