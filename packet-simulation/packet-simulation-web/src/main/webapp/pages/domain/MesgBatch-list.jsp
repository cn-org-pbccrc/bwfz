<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
#xmlID li a{
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
  width: 26%;
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
var mesgTypeOption = '';
function initFun(){
	$.get('${pageContext.request.contextPath}/MesgType/findMesgTypes.koala').done(function(data) {
		json = data;
		for(var index in json){
			mesgTypeOption = mesgTypeOption + '<option value="' + json[index].id + '"> ' + json[index].mesgType + '</option>';
		}
	});
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
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
// 	                                        minView: 2,
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
// 	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        //{content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/MesgBatch/pageJson/"+currentUserId+".koala",
	                columns: [
	                     	                         	                         { title: '报文名称', name: 'packetName', width: 250},
	                         	                         	                         	                         { title: '版本号', name: 'fileVersion', width: 120},
	                         	                         	                         	                         { title: '数据提供机构代码', name: 'origSender', width: 170},
	                         	                         	                         	                         { title: '文件生成时间', name: 'origSendDate', width: width,
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
	                         	                         	                         	                         { title: '数据类型', name: 'dataType', width: 120,
	                         	                         	                         	                        	 render: function(item, name, index){
	                     	                         	                         						                 if(item[name] == '0'){
	                     	                         	                         						                     return '正常';
	                     	                         	                         						                 }
	                     	                         	                         						             }	
	                         	                         	                         	                         },
	                         	                         	                         	                         { title: '三标起始值', name: 'start', width: 120},
// 	                         	                         	                         	                         { title: '文件数', name: 'packetNum', width: width},
	                         	                         	                         	                         { title: '记录数', name: 'mesgNum', width: 120},
	                         	                         	                         	                         { title: '创建者', name: 'createdBy', width: 120},
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
	        $.get('<%=path%>/MesgBatch-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            dialog.find("#mesgTypeID").append(mesgTypeOption);
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;
	              if(dialog.find('#mesgTypeID').val()=='请选择'){
	            	  showErrorMessage(dialog, dialog.find('#mesgTypeID'), '请选择记录');
	            	  return false;
	              }
	              dialog.find('.modal-footer').html("<html><body><img src='${pageContext.request.contextPath}/images/loading.gif'  alt='批量中' /></body></html>");
	              var info = dialog.find($(".tab-content")).attr('id');
  				  var xml = '<?xml version="1.0" encoding="UTF-8"?><Document><'+info+'>';
  				  dialog.find($(".true")).each(function(){   					
      				var id = $(this).attr('id');
      				xml += '<' + id + '>';
      				$('#'+id).find($("[name]")).each(function(){
  						if($(this).parent().parent()[0].tagName != 'FIELDSET'){
  							if($(this).attr('save')=='true'){
  								var name = ($(this).attr('name'));
      	    					var value = ($(this).val());
      							xml += '<' + name + '>' + value + '</' + name + '>';
  							}
  						}
  						else{
  							var name = ($(this).attr('name')); 
  							xml += '<' + name + '>';
  							$(this).parent().parent().find($("[subName]")).each(function(){
  								if($(this).attr('save')=='true'){
  									var subName = ($(this).attr('subName'));
      								var value = ($(this).val());	
      								xml += '<' + subName + '>' + value + '</' + subName + '>';
  								} 
  							});
  							xml += '</' + name + '>';
  						}
      				});
      				xml += '</' + id + '>';
    			  });
  				  xml += '</'+info+'></Document>';
  				  var data = [{ name: 'xml', value: xml },
  	      					  { name: 'createdBy', value: currentUserId},
  	     				      { name: 'mesgType', value: dialog.find("#mesgTypeID").val()},
  	     				      { name: 'fileVersion', value: dialog.find("#fileVersionID").val()},
  	     				      { name: 'origSender', value: dialog.find("#origSenderID").val()},
  	     				      { name: 'origSendDate', value: dialog.find("#origSendDateID").val()},
  	     				      { name: 'start', value: dialog.find("#startID").val()},
  	     				      { name: 'mesgNum', value: dialog.find("#mesgNumID").val()},
  	     				      { name: 'packetNum', value: dialog.find("#packetNumID").val()},
  	     				];		
	              $.post('${pageContext.request.contextPath}/MesgBatch/add.koala',data).done(function(result){
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
	                        dialog.modal('hide');
	                     }
	              });
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/MesgBatch-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/MesgBatch/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/MesgBatch/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
// 	                   minView: 2,
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
	    	$.post('${pageContext.request.contextPath}/MesgBatch/delete.koala', data).done(function(result){
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

var openDetailsPage = function(id){
	var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/MesgBatch-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/MesgBatch/get/' + id + '.koala').done(function(json){
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
	if($(obj).children("span").attr("class")=="glyphicon glyphicon-remove"){
		var len = $(obj).parent().attr("href").length;
		var tab = $(obj).parent().attr("href").substring(1,len);
		$("#"+tab).attr("class","tab-pane fade in active false");
	    $(obj).children("span").attr("class","glyphicon glyphicon-ok");
	}else{
		var len = $(obj).parent().attr("href").length;
		var tab = $(obj).parent().attr("href").substring(1,len);
		$("#"+tab).attr("class","tab-pane fade in active true");
	    $(obj).children("span").attr("class","glyphicon glyphicon-remove");
	}
}
function removeField(obj){
	if($(obj).children("span").attr("class")=="glyphicon glyphicon-remove"){
		$(obj).prev().children("input").attr("save","false");
	    $(obj).children("span").attr("class","glyphicon glyphicon-ok");
	}else{
		$(obj).prev().children("input").attr("save","true");
	    $(obj).children("span").attr("class","glyphicon glyphicon-remove");
	}
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
          <label class="control-label" style="width:100px;float:left;">数据提供机构代码:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="origSender" class="form-control" type="text" style="width:180px;" id="origSenderID_"/>
        </div>
                      <label class="control-label" style="width:100px;float:left;">文件生成时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID_start">
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="origSendDateEnd" id="origSendDateID_end">
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
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
