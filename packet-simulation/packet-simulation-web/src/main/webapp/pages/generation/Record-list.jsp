<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="/js/batchConfig/batchConfig.js"></script>
<div class="recordDetail" id="recordDetail">
<html lang="zh-CN">
<head>
<%-- <link href="${contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet"/> --%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
</head>
<body>
<div>
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>
<script type="text/javascript">
var selectItems = {};
var tabData = $('.tab-pane.active').data();
// var packetId = $('.mesgDetail').parent().attr('data-value');
var mesgTypeOption = '';
var grid;
var form;
var _dialog;
var currentUserId;
var batchContent;
var mesgType;
var packetId;
var version;

var selectMesg;
var mesgInput;
var recordTypeId;
var recordName;
var recordCode;
$.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function(json) {
	json = json.data;
    currentUserId = json['userAccount']; 	
    initFun();
});   
function initFun(){
// 	$.get( '${pageContext.request.contextPath}/RecordType/findRecordTypes.koala').done(function(data){
//     	json = data;
//         for(var index in json){
//         	mesgTypeOption=mesgTypeOption+'<option value="'+json[index].id+'"> '+json[index].filePath + '</option>';
//         }
//     });
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
    PageLoader = {
    	initGridPanel: function(){
    		var self = this;
    	    var width = 180;
    	    return grid.grid({
    	    	identity:"id",
    	        buttons: [
    	        	{content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
    	            {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
    	            {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
    	            {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-repeat"><span>批量规则</button>', action: 'ruleConfig'},
    	            {content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-repeat"><span>批量添加</button>', action: 'batch'}
    	        ],
    	        url:"${pageContext.request.contextPath}/Record/pageJson/" + currentUserId + ".koala",
    	        columns: [								 	
    	            { title: '报文名称', name: 'recordName', width: 300},
    	        	{ title: '报文类型', name: 'recordTypeStr', width: 400},
    	            { title: '操作', width: 180, render: function (rowdata, name, index)
    	            	{
    	                	var param = '"' + rowdata.id + '"';
    	                    var h = "<a href='javascript:openDetailsPageOfMesg(" + param + ")'>查看</a> ";
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
  	            },
    	        'ruleConfig': function(event, data){
  	            	var indexs = data.data;
  	                var $this = $(this);
  	                	if(indexs.length == 0){
  	                    	$this.message({
  	                        	type: 'warning',
  	                            content: '请选择一条记录进行批量配置'
  	                    })
  	                    return;
  	                }
  	                if(indexs.length > 1){
  	                	$this.message({
  	                    	type: 'warning',
  	                        content: '只能选择一条记录进行批量配置'
  	                    })
  	                    return;
  	                }  
  	                $.get( '${pageContext.request.contextPath}/BatchConfig/isExist/' + indexs[0] + '.koala').done(function(data){
    	            	  var ruleConfigId = data.data;
    	            	  if(ruleConfigId){
    	            		  batchConfig().modify(ruleConfigId.id,grid);
    	            	  }else{
    	            		  batchConfig().add(grid);
    	            	  }
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
    	    $.get('<%=path%>/Record-add.jsp').done(function(html){
    	        dialog.modal({
    	            keyboard:false,
    	            backdrop: 'static'
    	        }).on({
    	            'hidden.bs.modal': function(){
    	                $(this).remove();
    	            }
    	        }).find('.modal-body').html(html);
    	        selectMesg = dialog.find('#select-mesg');
	            mesgInput = selectMesg.find('input');
				selectMesg.find('[data-toggle="dropdown"]').on('click', function(){
					selectMesgTypes();
				});
    	        dialog.find('#save').on('click',{grid: grid}, function(e){
    	            if(!Validator.Validate(dialog.find('form')[0],3))return;
    	            $.post('${pageContext.request.contextPath}/Record/add.koala?createdBy='+currentUserId, dialog.find('form').serialize()).done(function(result){
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
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="	btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');	      
	        $.get('<%=path%>/Mesg-update.jsp').done(function(html){
	            dialog.find('.modal-body').html(html);	        	
	            self.initPage(dialog.find('form'));
	            $.get('${pageContext.request.contextPath}/Mesg/initUpdate/' + id + '.koala').done(function(json){
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
	            });
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            });
	            dialog.find('#save').on('click',{grid: grid}, function(e){
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
	 				var data = [{ name: 'content', value: xml },
	 				    { name: 'mesgType', value: dialog.find("#mesgTypeID").val()},
	 	     			{ name: 'packetId', value: dialog.find("#packetIdID").val()},
	 	     			{ name: 'version', value: dialog.find("#versionID").val()},
 	 					{ name: 'remark', value: dialog.find("#remarkID").val()},
 	 					{ name: 'createBy', value: dialog.find("#createByID").val()},
 	 					{ name: 'mesgFrom', value: dialog.find("#mesgFromID").val()}
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
    		$.get( '${pageContext.request.contextPath}/Mesg/initBatch/' + id + '.koala').done(function(json){
	    		json = json.data;
	     		mesgType = json["mesgType"];
	     		if(mesgType==1||mesgType==2||mesgType==3||mesgType==4||mesgType==5||mesgType==6){
	     			packetId = json["packetId"];
    	    	    var width = 180;
    	    	    $.get(contextPath + '/pages/auth/three-standard-select.jsp').done(function(data) {	    		
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
    	    	 	    	        { title:'姓名', name:'name' , width: 2*width/3},
    	    	 	    	        { title:'证件类型', name:'credentialType', width: width/2,
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
    	    	 	    	        { title:'证件号', name:'credentialNumber', width: width},
    	    	 	    	        { title:'机构代码', name:'organizationCode', width: width},
    	    	 	    	        { title:'客户资料标识号', name:'customerCode', width: 2*width/3},
    	    	 	    	        { title: '账户标识号', name: 'acctCode', width: 3*width/4},
                     	            { title: '合同标识号', name: 'conCode', width: 2*width/3},
                     	            { title: '抵质押合同标识号', name: 'ccc', width: width},
    	    	 	    	        { title:'创建日期', name:'createdDate', width: 2*width/3},
    	    	 	    	        { title:'创建者', name:'createdBy', width: width/2}
    	    	 	    	    ];//<!-- definition columns end -->
    	    	 	    	    //查询出当前表单所需要得数据。
    	    	 	    	    dialog.find('.selectThreeStandardGrid').grid({
    	    	 	    	        identity: 'id',
    	    	 	    	        columns: columns,
    	    	 	    	        url: contextPath + '/ThreeStandard/pageJson/' + currentUserId + '.koala'
    	    	 	    	    });
							}
    	    	 	    });//<!-- 显示对话框数据结束-->
						dialog.find('#selectThreeStandardGridSave').on('click',{grid: grid}, function(e){   	    	 	    		
							var reg = new RegExp(/^[0-9]*$/);
							if (!reg.test(dialog.find('#startID').val()) || !reg.test(dialog.find('#endID').val())) {
    	    	 	    		dialog.find('.modal-content').message({
	    		 	                type: 'error',
	    		 	                content: "批量起始或结束行号应为数字"
	    		 	            });
    	    	 	    		return false;
    	    	 			}
    	    		    	var items = dialog.find('.selectThreeStandardGrid').data('koala.grid').selectedRowsIndex();   
    	    		    	if(items.length == 0 && (dialog.find('#startID').val()==null||dialog.find('#startID').val()=="") && (dialog.find('#endID').val()==null||dialog.find('#endID').val()=="")){
								dialog.find('.selectThreeStandardGrid').message({
    	    		    	    	type: 'warning',
    	    		    	        content: '请选择需要关联的三标信息！'
    	    		    	    });
    	    		    	}
    	    		    	var data = [{ name: 'id', value: id},
    	    		    		{ name: 'ids', value: items.join(',')},
    	    		    	    { name: 'start', value: dialog.find('#startID').val()},
    	    		    	    { name: 'end', value: dialog.find('#endID').val()},
    	    		    	    { name: 'mesgType', value: mesgType},
    	   	     	 			{ name: 'packetId', value:packetId},		        
    	    		     	];
    	    		    	document.getElementById("selectThreeStandardGridSave").disabled = true;
    	    		    	dialog.find('.modal-progress').html("<html><body><img src='${pageContext.request.contextPath}/images/loading.gif'  alt='上海鲜花港 - 郁金香' /></body></html>");
    	    		    	$.post('${pageContext.request.contextPath}/Mesg/batch.koala', data).done(function(result){
    	    		 	        if(result.success ){
    	    		 	        	dialog.modal('hide');
    	    		 	            e.data.grid.data('koala.grid').refresh();
    	    		 	            e.data.grid.message({
    	    		 	            	type: 'success',
    	    		 	                content: '批量成功'
    	    		 	            });
    	    		 	        }else{
    	    		 	        	dialog.find('.modal-progress').empty();
    	    		 	        	document.getElementById("selectThreeStandardGridSave").disabled = false;
    	    		 	            dialog.find('.modal-content').message({
    	    		 	                type: 'error',
    	    		 	                content: result.errorMessage
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
    	    	}else{
					grid.message({
	                	type: 'error',
	                    content: "只有正常报送记录才能进行批量"
	                });
	            }
    	    });
    	},	
    	batchConfig: function(id, grid){
    		batchConfig().add($(this));
    	}
    }
    PageLoader.initGridPanel();
}
var openDetailsPageOfMesg = function(id){
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

var selectMesgTypes = function(){
	$.get( contextPath + '/pages/organisation/select-mesg-template.jsp').done(function(data){
		var mesgTreeDialog = $(data);
		mesgTreeDialog.find('.modal-body').css({height:'325px'});
		mesgTree = mesgTreeDialog.find('.tree');
        loadMesgTree();
		mesgTreeDialog.find('#confirm').on('click',function(){
			mesgInput.val(recordTypeId);
			selectMesg.find('[data-toggle="item"]').text(recordName);
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
    $.get(contextPath  + '/Record/findRecordTypes.koala').done(function(data){
        mesgTree.parent().loader('hide');
        var zNodes = new Array();
		var cNodes = new Array();
		if (!data) {
			return;
		}
		$.each(data, function() {
			var cNode = {};
			this.title = this.recordType;
			cNode.menu = this;
			cNodes.push(cNode);
		});
		var zNode = {};
		var menu = {};
		zNode.type = 'parent';
		menu.title = '企业';
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
            	recordTypeId = data.id;
                recordName = data.recordType;
                recordCode = data.code;
            }
        });
    });
};
</script>
</body>
</html>
</div>
