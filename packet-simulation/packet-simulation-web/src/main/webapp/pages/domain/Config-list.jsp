<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var grid;
var form;
var _dialog;

/**
 * 表单验证 通过返回true  失败返回false
 */
var validate = function(dialog){
	var type = $("#dataSourceType").getValue();

// 	if(!dataSourceType.getValue()){
// 		showErrorMessage(dataSourceType, '请选择数据源类型');
// 		return false;
// 	}
// 	if(!checkNotNull(dataSourceId.val())){
// 		showErrorMessage(dataSourceId, '请填写数据源ID');
// 		return false;
// 	}
	if(type == 'http'){
		if(!checkNotNull(dialog.find('#addHttpAddressID').val())){
			showErrorMessage(dialog,dialog.find('#addHttpAddressID'), '请填写http地址');
			return false;
		}
		if(!checkNotNull(dialog.find('#addHttpPortID').val())){
			showErrorMessage(dialog,dialog.find('#addHttpPortID'), '请填写http端口');
			return false;
		}
		
	}else if(type=="mq"){
		if(!checkNotNull(dialog.find('#addMqAddressID').val())){
			showErrorMessage(dialog,dialog.find('#addMqAddressID'), '请填写mq地址');
			return false;
		}
		if(!checkNotNull(dialog.find('#addMqPortID').val())){
			showErrorMessage(dialog,dialog.find('#addMqPortID'), '请填写mq端口');
			return false;
		}
		if(!checkNotNull(dialog.find('#addMqCCISIDID').val())){
			showErrorMessage(dialog,dialog.find('#addMqCCISIDID'), '请填写mq CCISID');
			return false;
		}
		if(!checkNotNull(dialog.find('#addMqChannelID').val())){
			showErrorMessage(dialog,dialog.find('#addMqChannelID'), '请填写mq通道');
			return false;
		}
		if(!checkNotNull(dialog.find('#addMqQueueManagerID').val())){
			showErrorMessage(dialog,dialog.find('#addMqQueueManagerID'), '请填写mq队列管理器');
			return false;
		}
		if(!checkNotNull(dialog.find('#addMqQueueID').val())){
			showErrorMessage(dialog,dialog.find('#addMqQueueID'), '请填写mq队列名');
			return false;
		}
	}
	return true;
};

/**
 * 显示提示信息
 */
var showErrorMessage = function(dialog,$element, content){
	$element.popover({
		content: content,
		trigger: 'manual',
		container: dialog
	}).popover('show').on({
			'blur': function(){
				$element.popover('destroy');
				$element.parent().removeClass('has-error');
			},
			'keydown': function(){
				$element.popover('destroy');
				$element.parent().removeClass('has-error');
			}
	}).focus().parent().addClass('has-error');
};
/**
 * 检查变量是否不为空  true:不空   false:空
 */
var checkNotNull = function(item){
	//不能为空和空格
	if(item==null || item=="" || item.replace(/(^\s*)|(\s*$)/g, "")=="" ){
		return false;
	}else{
		return true;
	}
};

// var fillSelectData  = function(dialog){
// 	//var dialog = $(this);
// 	var httpAddressID = dialog.find('#addHttpAddressID');
// 	var httpPortID = dialog.find('#addHttpPortID');

// 	var mqAddressID = dialog.find('#addMqAddressID');
// 	var mqPortID =  dialog.find('#addMqPortID');
// 	var mqCCISIDID = dialog.find('#addMqCCISIDID');
// 	var mqChannelID = dialog.find('#addMqChannelID');
// 	var mqQueueManagerID = dialog.find('#addMqQueueManagerID');
// 	var mqQueueID = dialog.find('#addMqQueueID'); 
	
// 	dialog.find('#dataSourceType').select({
// 		title: '选择数据源类型',
// 		contents: [
// 			{value: 'http', title: 'http', selected: true},
// 			{value: 'mq', title: 'mq'}
// 		]
// 	}).on('change', function(){
// 		//alert('this value is ' + $(this).getValue());
// 		//alert(httpPortID.text());
// 		//alert('the mq queue is ' + mqQueueID.html());
// 			if($(this).getValue() == 'http'){
// 			httpPortID.closest('.form-group').show();
// 			httpAddressID.closest('.form-group').show();

// 			mqAddressID.closest('.form-group').hide();
// 			mqPortID.closest('.form-group').hide();
// 			mqCCISIDID.closest('.form-group').hide();
// 			mqChannelID.closest('.form-group').hide();
// 			mqQueueManagerID.closest('.form-group').hide();
// 			mqQueueID.closest('.form-group').hide();
// 		}else{
// 			httpPortID.closest('.form-group').hide();
// 			httpAddressID.closest('.form-group').hide();

// 			mqAddressID.closest('.form-group').show();
// 			mqPortID.closest('.form-group').show();
// 			mqCCISIDID.closest('.form-group').show();
// 			mqChannelID.closest('.form-group').show();
// 			mqQueueManagerID.closest('.form-group').show();
// 			mqQueueID.closest('.form-group').show();
// 		}
// 	});
// };

// 将前台的数据按照不同的类型封装json，传到后台，主要是对param参数进行封装 
var mySerialize=function(dialog){
	// 将前台数据封装成json对象向后台传输
	var name = dialog.find('#nameID').val();
	var type = $("#dataSourceType").getValue();
	var httpAddressID = dialog.find('#addHttpAddressID');
	var httpPortID = dialog.find('#addHttpPortID');

	var mqAddressID = dialog.find('#addMqAddressID');
	var mqPortID =  dialog.find('#addMqPortID');
	var mqCCISIDID = dialog.find('#addMqCCISIDID');
	var mqChannelID = dialog.find('#addMqChannelID');
	var mqQueueManagerID = dialog.find('#addMqQueueManagerID');
	var mqQueueID = dialog.find('#addMqQueueID'); 
	
	var json;
	if (type == 'http'){
		json = '{"httpAddress":"'+httpAddressID.val()+'", "httpPort":"'+httpPortID.val()+'"}';
	}else{
		json = '{"mqAddress":"' +  mqAddressID.val()+
		'","mqPort":"'+mqPortID.val()+
		'","mqCCISID":"'+ mqCCISIDID.val()+
		'","mqChannel":"'+mqChannelID.val()+
		'","mqQueueManager":"'+mqQueueManagerID.val()+ 
		'","mqQueue":"'+mqQueueID.val()+
		'"}';
		
	}
	var serial = 'name='+name+'&type='+type+'&param='+json;
	//alert(serial);
	return serial;
};

// 从后台提取数据后，按照不同的类型显示不同的input项目
var showSerialize = function(dialog,json){
	var name = dialog.find('#nameID');
	var type = $("#dataSourceType");
	var httpAddressID = dialog.find('#addHttpAddressID');
	var httpPortID = dialog.find('#addHttpPortID');

	var mqAddressID = dialog.find('#addMqAddressID');
	var mqPortID =  dialog.find('#addMqPortID');
	var mqCCISIDID = dialog.find('#addMqCCISIDID');
	var mqChannelID = dialog.find('#addMqChannelID');
	var mqQueueManagerID = dialog.find('#addMqQueueManagerID');
	var mqQueueID = dialog.find('#addMqQueueID'); 
	
	dialog.find('#dataSourceType').select({
		title: '选择数据源类型',
		contents: [
			{value: 'http', title: 'http' , selected: true},
			{value: 'mq', title: 'mq'}
		]
	}).on('change', function(){
		//alert('this value is ' + $(this).getValue());
		//alert(httpPortID.text());
		//alert('the mq queue is ' + mqQueueID.html());
			if($(this).getValue() == 'http'){
			httpPortID.closest('.form-group').show();
			httpAddressID.closest('.form-group').show();

			mqAddressID.closest('.form-group').hide();
			mqPortID.closest('.form-group').hide();
			mqCCISIDID.closest('.form-group').hide();
			mqChannelID.closest('.form-group').hide();
			mqQueueManagerID.closest('.form-group').hide();
			mqQueueID.closest('.form-group').hide();
		}else{
			httpPortID.closest('.form-group').hide();
			httpAddressID.closest('.form-group').hide();

			mqAddressID.closest('.form-group').show();
			mqPortID.closest('.form-group').show();
			mqCCISIDID.closest('.form-group').show();
			mqChannelID.closest('.form-group').show();
			mqQueueManagerID.closest('.form-group').show();
			mqQueueID.closest('.form-group').show();
		}
	});
	// modify时不可以修改type
	dialog.find('#dataSourceType button').attr("disabled",true); 
	
 	// 从返回的json中提取名称，类型，并将param参数转成json对象，再次进行解析，根据类型对不同的param进行操作
	paramJson =	jQuery.parseJSON(json.param);

	var typeItem = dialog.find('#dataSourceType');
	//typeItem.trigger('change');
	// 根据不同的类型，显示不同的input项目
	var typeVal = json.type;
	//alert('type is '+typeVal);
	if (typeVal=='http'){
		// 先设置类型为http,如果是输入类型，如input，则用val（）函数，如果是label，用html（）函数
		 if(typeItem.hasClass('select')){
			 
			typeItem.setValue('http');//type
			name.val(json.name);;//name
			httpAddressID.val(paramJson['httpAddressID']);
			httpPortID.val(paramJson['httpPortID']);
		  }else{
			  //('#typeID').html(json.name);//name
			  dialog.find('#typeID').html('http');//type
			  name.html(json.name);;
			  httpAddressID.html(paramJson['httpAddressID']);
			  httpPortID.html(paramJson['httpPortID']);
		  }
		 // dialog.find('#typeID').text('sdfsfsfhhhhhslllll');

		
		    httpPortID.closest('.form-group').show();
			httpAddressID.closest('.form-group').show();

			mqAddressID.closest('.form-group').hide();
			mqPortID.closest('.form-group').hide();
			mqCCISIDID.closest('.form-group').hide();
			mqChannelID.closest('.form-group').hide();
			mqQueueManagerID.closest('.form-group').hide();
			mqQueueID.closest('.form-group').hide();
		// alert('3');
		//dialog.find('#dataSourceType').setValue('http');
		// 将其他后台传来的参数复制到对应的位置中
		//httpAddressID.val(paramJson['httpAddressID']);
		
// 		// 如果是input，就用val，如果是label，就用html()
// 		if(httpAddressID[0].tagName=='INPUT'){
// 			httpAddressID.val(paramJson['httpAddressID']);
// 		}else {
// 			httpAddressID.html(paramJson['httpAddressID']);
// 		}

// 		if(httpPortID[0].tagName=='INPUT'){
// 			httpPortID.val(paramJson['httpPortID']);

// 		}else{
// 			httpPortID.html(paramJson['httpPortID']);

// 		}
		
//alert('5');
	}else if(typeVal == 'mq'){
		// 先设置类型为mq
		//dialog.find('#dataSourceType').setValue('mq');
		 if(typeItem.hasClass('select')){
				typeItem.setValue('mq');
				name.val(json.name);;

				mqAddressID.val(paramJson['mqAddress']);
		 		mqPortID.val(paramJson['mqPort']);
		 		mqCCISIDID.val(paramJson['mqCCISID']);
		 		mqChannelID.val(paramJson['mqChannel']); 		
		 		mqQueueManagerID.val(paramJson['mqQueueManager']);
		 		mqQueueID.val(paramJson['mqQueue']);
			  }else{
				  dialog.find('#typeID').html('mq');//type
					name.html(json.name);;

					mqAddressID.html(paramJson['mqAddress']);
			 		mqPortID.html(paramJson['mqPort']);
			 		mqCCISIDID.html(paramJson['mqCCISID']);
			 		mqChannelID.html(paramJson['mqChannel']); 		
			 		mqQueueManagerID.html(paramJson['mqQueueManager']);
			 		mqQueueID.html(paramJson['mqQueue']);
		}
		 
		 httpPortID.closest('.form-group').hide();
			httpAddressID.closest('.form-group').hide();

			mqAddressID.closest('.form-group').show();
			mqPortID.closest('.form-group').show();
			mqCCISIDID.closest('.form-group').show();
			mqChannelID.closest('.form-group').show();
			mqQueueManagerID.closest('.form-group').show();
			mqQueueID.closest('.form-group').show();
		 
// 		mqAddressID.val(paramJson['mqAddressID']);
//  		mqPortID.val(paramJson['mqPortID']);
//  		mqCCISIDID.val(paramJson['mqCCISIDID']);
//  		mqChannelID.val(paramJson['mqChannelID']); 		
//  		mqQueueManagerID.val(paramJson['mqQueueManagerID']);
//  		mqQueueID.val(paramJson['mqQueueID']);
	}

};
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
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/Config/pageJson.koala",
	                columns: [
	                     	                         	                         { title: '名称', name: 'name', width: width},
	                         	                         	                         	                         { title: '类型', name: 'type', width: width},
	                         	                         	                         	                         { title: '参数', name: 'param', width: width},
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
	        $.get('<%=path%>/Config-add.jsp').done(function(html){
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
	        	
	            //  if(!Validator.Validate(dialog.find('form')[0],3))return;
	            if(!validate(dialog)) return; 
	            // alert('11');
	              $.post('${pageContext.request.contextPath}/Config/add.koala', mySerialize(dialog)).done(function(result){
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
	        $.get('<%=path%>/Config-update.jsp', function(html){
	               dialog.find('.modal-body').html(html);
	              // self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/Config/get/' + id + '.koala', function(json){
	            	   
	                       json = json.data;
	                       //alert('data is '+JSON.stringify(json));
							var elm;
 	                        for(var index in json){
 	                            elm = dialog.find('#'+ index + 'ID');
 	                            if (index == 'param'){
  	                            	showSerialize(dialog,json);
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
	                    //if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    if(!validate(dialog)) return; 
	                    $.post('${pageContext.request.contextPath}/Config/update.koala?id='+id, mySerialize(dialog)).done(function(result){
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
	              // alert('1');
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
	    	$.post('${pageContext.request.contextPath}/Config/delete.koala', data).done(function(result){
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

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/Config-view.jsp', function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/Config/get/' + id + '.koala', function(json){
                       json = json.data;
                       //alert('#dialog is '+ dialog.html() +' json is '+JSON.stringify(json));
                       showSerialize(dialog,json);
                        var elm;
//                         for(var index in json){
//                         if(json[index]+"" == "false"){
//                         		dialog.find('#'+ index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
//                         	}else if(json[index]+"" == "true"){
//                         		dialog.find('#'+ index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
//                         	}else{
//                           		 dialog.find('#'+ index + 'ID').html(json[index]+"");
//                         	}
//                         }
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
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="name" class="form-control" type="text" style="width:180px;" id="nameID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">类型:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="type" class="form-control" type="text" style="width:180px;" id="typeID"  />
        </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">参数:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="param" class="form-control" type="text" style="width:180px;" id="paramID"  />
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
