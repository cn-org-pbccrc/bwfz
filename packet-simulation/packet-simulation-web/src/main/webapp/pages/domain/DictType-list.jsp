<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- <link rel="stylesheet" type="text/css" href="/css/easy-ui/easyui.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="/css/easy-ui/icon.css"> -->
<!-- <script type="text/javascript" -->
<!-- 	src="/js/jquery-easyui-1.4.5/jquery.min.js"></script> -->
<!-- <script type="text/javascript" -->
<!-- 	src="/js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script> -->

<%-- <script type="text/javascript" src="<c:url value='/js/packet-simulation/dataDict.js' />"></script> --%>
<script type="text/javascript">

var dictId="";
var grid;
var form;
var _dialog;
$(function (){
	
	grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	    initGridPanel: function(id){
        //console.log('grid function');
		//console.log(grid.grid);
	         var self = this;
	         var width = 180;
// 	         console.log('grid is '+ grid);
// 	         $.each(grid,function(index,data){ 
// 	        	 console.info(index+" "+data); 
// 	        	 })
// 	         console.log('grid.grid is '+grid.grid);
	         
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/DictItem/pageJsonByDictId/"+id+".koala",
	                columns: [
// 	                          { title: '数据字典类型id', name: 'dictId', width: width},
	                          { title: '数据字典项代码', name: 'dictItemCode', width: width},
	                          { title: '数据字典项名称', name: 'dictItemName', width: width},
	                          { title: 'itemSort', name: 'itemSort', width: width},
// 	                          { title: 'delFlag', name: 'delFlag', width: width},
	                          { title: '备注', name: 'remark', width: width},
	                          { title: '操作', width: 120,
	                        	  		render: function (rowdata, name, index){
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
	        $.get('<%=path%>/DictItem-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	         
	            //console.log("i want to set value to dictId");
	            dialog.find('#dictIdID').val(dictId);
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	          //    if(!Validator.Validate(dialog.find('form')[0],3))return;
	              
	              if (!Validation.notNull(dialog, dialog.find('#dictIdID'), dialog.find('#dictIdID').val(), '请输入字典类型')) {
	     			    return false;
	     		    }
	              
	     		   if (!Validation.notNull(dialog, dialog.find('#dictItemCodeID'), dialog.find('#dictItemCodeID').val(), '请输入字典项代码')) {
	     			    return false;
	     		    }
	              $.post('${pageContext.request.contextPath}/DictItem/add.koala', dialog.find('form').serialize()).done(function(result){
//       打印返回的result结果，便于测试
// 	            	  var description = ""; 
// 	            	  for(var i in result){ 
// 	            	   var property=result[i]; 
// 	            	   description+=i+" = "+property+"\n"; 
// 	            	  }
// 	            	  alert(description);
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
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/DictItem-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/DictItem/get/' + id + '.koala').done(function(json){
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
	                  //  if(!Validator.Validate(dialog.find('form')[0],3))return;
	  	              
	  	              if (!Validation.notNull(dialog, dialog.find('#dictIdID'), dialog.find('#dictIdID').val(), '请输入字典类型')) {
	  	     			    return false;
	  	     		    }
	  	            if (!Validation.notNull(dialog, dialog.find('#dictItemCodeID'), dialog.find('#dictItemCodeID').val(), '请输入字典项代码')) {
	     			    return false;
	     		    }
	                    $.post('${pageContext.request.contextPath}/DictItem/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	    	$.post('${pageContext.request.contextPath}/DictItem/delete.koala', data).done(function(result){
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
	PageLoader.initGridPanel(0);
    dict();
 });

 var dict = function(){
	//alert('ddd');
	var baseUrl =  contextPath + '/DictType/';
	var dialog = null;    //对话框
	
	var dictName=null;
	var upDictId =null;
	//addDictType();
	
	// 创建树
	getTreee();
	/*
	 *新增部门
	 */
//	$.ajaxSetup({cache:false});
	/**
	新增数据字典类型
	*/
	var addDictType = function(id, organizationType, $element){
		$.get(contextPath + '/pages/domain/DictType-template.jsp').done(function(data){
			init(data,  id , 'addDictType', organizationType, $element);
			setData(id,'addDictType');
		});
	};
	/*
	 * 更新数据字典类型
	 */
	var updateDictType = function(id, $element){
		$.get( contextPath +'/pages/domain/DictType-template.jsp').done(function(data){
			init(data,  id , 'updateDictType', null, $element);
			setData(id, 'updateDictType');
		});
	};
	/*
	 删除方法
	 */
	var del = function(data,$element){
		url='delete.koala?id='+data.id;
		$.post(baseUrl + url).done(function(data){
			if(data.success){
				$('#departmentDetail').message({
					type: 'success',
					content: '删除成功'
				});
				var departmentTree = $('#departmentTree2');
                if($element.hasClass('tree-item')){
                    departmentTree.tree('removeChildren', $element);
                }else{
                    departmentTree.tree('removeChildren', $element.parent());
                }
                //departmentTree.find('.tree-folder-header:first').click();
			}else{
				$('body').message({
					type: 'error',
					content: data.errorMessage
				});
			}
		}).fail(function(data){
			$('body').message({
				type: 'error',
				content: '撤销失败'
			});
		});
	};
	

	/**
		 * 初始化
		 */
		var init = function(data, id, type, organizationType, $element) {
			dialog = $(data);
			var title = '';
			switch (type) {
			case 'addDictType':
				title = '创建数据字典类型';
				break;
			case 'addCompany':
				title = '创建子公司';
				break;
			case 'updateDictType':
				title = '更新数据字典类型信息';
				break;
			case 'updateDepartment':
				title = '修改机构信息';
				break;
			}
			dialog.find('.modal-header').find('.modal-title').html(title);
			dictName = dialog.find('#dictName');
			upDictId = dialog.find('#upDictId');
			dialog.find('#save').on('click', function() {
				$(this).attr('disabled', 'disabled');
				save(id, type, organizationType);
			}).end().modal({
				keyboard : false
			}).on(
					{
						'hidden.bs.modal' : function() {
							$(this).remove();
						},
						'complete' : function(event, data) {
							var type = data.type;
							$('#departmentDetail').message({
								type : 'success',
								content : '保存成功'
							});
							//	在update之后对树做何操作？
							if (type == 'updateDictType') {
								$('#departmentTree2').off().empty().data(
										'koala.tree', null);
								getTreee();
							} else {

								$('#departmentTree2').off().empty().data(
										'koala.tree', null);
								getTreee();
							}
							$(this).modal('hide');
						}
					});
		}// end of init()
		/*
		 *设置值
		 */
		 // version用于应对乐观锁
		 var version =0;
		var setData = function(id, type){
			if(type=='addDictType'){
				upDictId.val(id);
				return;
			}
			$.get(baseUrl+'/get/'+id+'.koala').done(function(data){
				org = data.data;
				upDictId.val(org.upDictId);
				dictName.val(org.dictName);
				version = org.version;
				//departmentSN.val(org.sn);
				//departmentName.val(org.name);
				//description.val(org.description);
			});
		};
		/*
		 *   保存数据 id存在则为修改 否则为新增
		 */
		var save = function(id, type, organizationType){
			if(!validate()){
				dialog.find('#save').removeAttr('disabled');
				return false;
			}
			var url = '';
			switch(type){
				case 'addDictType':
					url =  baseUrl + 'add.koala';
					break;
				case 'addCompany':
					url =  baseUrl + 'create-company.koala?parentId='+id;
					break;
				case 'updateDictType':
					url =  baseUrl + 'update.koala?version='+version;
					break;
				case 'updateDepartment':
					url =  baseUrl + 'update-department.koala';
					break;
			}
			var data = getAllData(id, type);
			$.post(url, data).done(function(data){
				if(data.success){
					dialog.trigger('complete', {type:type, id: data.id});
				}else{
					dialog.find('.modal-content').message({
						type: 'error',
						content: data.errorMessage
					});	
					refreshToken(dialog.find('input[name="koala.token"]'));
				}
				dialog.find('#save').removeAttr('disabled');
			}).fail(function(data){
				dialog.find('.modal-content').message({
					type: 'error',
					content: '保存失败！'
				});
				dialog.find('#save').removeAttr('disabled');
				refreshToken(dialog.find('input[name="koala.token"]'));
			});
		};
		/*
		 *获取表单数据
		 */
		var getAllData = function(id, type){
			if(type == 'addDictType'){
//	 			var department = {};
//	 			department.sn = departmentSN.val();
//	 			department.name = departmentName.val();
//	 			department.description = description.val();
//	 			department['koala.token'] = dialog.find('input[name="koala.token"]').val();
//	 			return department;
					var dictType={};
					dictType.upDictId=upDictId.val();
					dictType.dictName=dictName.val();
					return dictType;
			}else if(type == 'updateDictType'){
				var dictType={};
				dictType.id=id;
				dictType.upDictId=upDictId.val();
				dictType.dictName=dictName.val();
				return dictType;
		   }else {
				org.sn = departmentSN.val();
				org.name = departmentName.val();
				org.description = description.val();
				org['koala.token'] = dialog.find('input[name="koala.token"]').val();
				return org;
			}
		};
		/**
		 * 表单验证 通过返回true  失败返回false
		 */
		var validate = function(){
			if(!Validation.notNull(dialog, upDictId, upDictId.val(), '请填写上级字典类型Id！')){
				return false;
			}
//	 		if(!Validation.serialNmuber(dialog, departmentSN, departmentSN.val(), '请填写正确的机构编号！')){
//	 			return false;
//	 		}
//	 		if(!Validation.notNull(dialog, departmentName, departmentName.val(), '请填写机构名称！')){
//	 			return false;
//	 		}
			return true;
		};
		/**
		 * 显示提示信息
		 */
		var showErrorMessage = function($element, content){
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
		}
		
		/**
		 * 生成树
		 */
		 function getTreee(){

			console.log('building Tree');
			$('#departmentTree2').loader({
				opacity: 200
			});
	        $.get(baseUrl + 'loadDictType.koala').done(function(json){

	        	data = json.data.childTypesSet;
	     
	        	$('#departmentTree2').empty();
	        	$('#departmentTree2').loader('hide');
	            var zNodes = new Array();
	            $.each(data, function(){
	                var zNode = {};
	               // if(this.organizationType == 'Company'){
	                //    zNode.type = 'parent';
	                //}else{
	                 //   zNode.icon = 'glyphicon glyphicon-list-alt'
	                //}
	                zNode.type = 'parent';
	                //console.log(this);
	                //alert('this id is '+this.id);
	                this.title = this.dictName;
	                zNode.menu = this;
	                if(this.childTypesSet && this.childTypesSet.length > 0){
	                    zNode.children = getChildrenData( this.childTypesSet);
	                }
	                zNodes.push(zNode);
	            });
	            var dataSourceTree = {
	                data: zNodes,
	                delay: 400
	            };
	          // console.log('make tree funtion is');
	           //console.log($('#departmentTree').data('koala.tree').tree);
	            $('#departmentTree2').off().data('koala.tree', null).tree({
	                dataSource: dataSourceTree,
	                loadingHTML: '<div class="static-loader">Loading...</div>',
	                multiSelect: false,
	                cacheItems: true
	            }).on({
	                'contextmenu': function(e){
	                    return false;
	                },
	                'rightClick': function(e, originalEvent){
	                    createRightMenu(originalEvent);
	                   // alert('1111');
	                },
	                'selectParent': function(event, data){
	                	//dictTypeInfo.id=data.data.id;
	                	//alert('id '+dictTypeInfo.id);
	                    showDepartmentDetail(data);
	                },
	                'selectChildren': function(event, data){
	                    showDepartmentDetail(data.id);
	                },
	                'addDictType': function(event, data){
	                    var $element = $(data);
	                   // console.log($element);
	                    var data = $element.data();
	                    addDictType(data.id, $element);
	                },
	                'deleteDictType': function(event, data){
	                    var $element = $(data);
	                    var data = $element.data();
	                    console.log(data);
	                    $(this).confirm({
	                        content: '确定要删除该数据字典类型?',
	                        callBack: function(){
//	                             var type = data.organizationType;
//	                             delete data.children;
//	                             delete data.organizationType;
	                            //department().del(data, type, $element);
	                            del(data, $element);
	                        }
	                    });
	                    
	                },
	                'updateDictType':function(event, data){
	                	   var $element = $(data);
	                       var data = $element.data();
	                       updateDictType(data.id, $element);
	                },
	                'addDepartment': function(event, data){
	                    var $element = $(data);
	                    var data = $element.data();
	                    addDepartment(data.id, data.organizationType, $element);
	                },
	                'update': function(event, data){
	                    var $element = $(data);
	                    var data = $element.data();
	                    if(data.organizationType == 'Company'){
	                        updateCompany(data.id, $element);
	                    }else{
	                        updateDepartment(data.id, $element);
	                    }
	                },
	                'delete': function(event, data){
	                    var $element = $(data);
	                    var data = $element.data();
	                    $(this).confirm({
	                        content: '确定要撤销该机构吗?',
	                        callBack: function(){
	                            var type = data.organizationType;
	                            delete data.children;
	                            delete data.organizationType;
	                            department().del(data, type, $element);
	                        }
	                    });
	                }
	            });
//	         	if(id){
//	         		var $element = $('#departmentTree').find('#'+id).click();
//	         		if($element.hasClass('tree-folder')){
//	         			$element.find('.tree-folder-header:first').click();
//	         		}
//	         		$element.parents().filter('.tree-folder-content').each(function(){
//	         			var $this = $(this);
//	 					$this.show()
//	 						 .prev('.tree-folder-header')
//	 						 .find('.glyphicon-folder-close')
//	 						 .removeClass('glyphicon-folder-close')
//	 						 .addClass('glyphicon-folder-open');
//	         		});
//	         	}else{
//	                 if ($('#departmentTree').find('.tree-folder-header:first').length != 0) {
//	                     $('#departmentTree').find('.tree-folder-header:first').click();
//	                 } else {
//	                     $('#departmentTree').find('.tree-item:first').click();
//	                 }

//	         	}
	        });
		};
	    var createRightMenu = function(ev){
	        var $element = $(ev.currentTarget);
	        var menuData = null;
	        menuData = [
	            {title:'创建数据字典类型', action: 'addDictType'},
	            {title:'修改数据字典类型', action: 'updateDictType'},
	            {title:'删除字典类型', action: 'deleteDictType'},
	        ];
	        $('#departmentTree2').tree('createRightMenu', {event:ev, element: $element, data:menuData});
	    };
	    
	    var getChildrenData = function( items){
	    	var nodes=new Array();
	        $.each(items, function(){
	            var zNode = {};
	            //if(this.organizationType == 'Company'){
	                zNode.type = 'parent';
	            //}else{
	             //   zNode.icon = 'glyphicon glyphicon-list-alt'
	            //}
	            this.title = this.dictName;
	            zNode.menu = this;
	            if(this.childTypesSet && this.childTypesSet.length > 0){
	                zNode.children = getChildrenData( this.childTypesSet);
	            }
	            nodes.push(zNode);
	        });
	        return nodes;
	    };

		var showDepartmentDetail =  function(data){
			//$.get(contextPath + '/DictType/get.koala?id='+id).done(function(data){
				var org = data.data;

				dictId = org.id;
				console.log('current dict id is '+dictId);
				var departmentDetail = $('.right-content');
				departmentDetail.find('[data-role="id"]').text(org.id);
				departmentDetail.find('[data-role="upDictId"]').text(org.upDictId);
				departmentDetail.find('[data-role="version"]').text(org.version);
				if(org.organizationType == 'Company'){
					$('#addCompany').show();
					$('#updateCompany').show();
					$('#updateDepartment').hide();
				}else{
					$('#addCompany').hide();
					$('#updateCompany').hide();
					$('#updateDepartment').show();
				}
				
				// 初始化右侧下面的显示
				dictId = org.id;
				
				grid.data('koala.grid').options.url="${pageContext.request.contextPath}/DictItem/pageJsonByDictId/"+org.id+".koala";
				grid.data('koala.grid').refresh();
				//	alert('init ---- ');
		//	}
//			).fail(function(data){
//				$('body').message({
//					type: 'error',
//					content: '无法获取该部门信息'
//				});
//			});
		}
 

	}
 // 查看页面
 var openDetailsPage = function(id){
     var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看数据字典项</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
     $.get('<%=path%>/DictItem-view.jsp').done(function(html){
            dialog.find('.modal-body').html(html);
            $.get( '${pageContext.request.contextPath}/DictItem/get/' + id + '.koala').done(function(json){
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
	<div style="width: 98%; margin-right: auto; margin-left: auto; padding-top: 15px;">

		<div class="department-detail" id="departmentDetail">
			<ul class="u-tree tree" id="departmentTree2" 	oncontextmenu="return false"></ul>
		</div>

			
			<div class="right-content">
				<table class="table table-bordered table-hover base-info">
					<tr>
						<td><label class="col-lg-5 control-label">数据字典类型id:</label>
							<div class="col-lg-7">
								<span data-role="id"></span>
								<!--                         <input type="hidden" data-role="id"> -->
								<!--                         <input type="hidden" data-role="organizationType"> -->
							</div></td>
					</tr>
					<tr>
						<td><label class="col-lg-5 control-label">上级字典类型id:</label>
							<div class="col-lg-7">
								<span data-role="upDictId"></span>
							</div></td>
					</tr>
					<tr>
						<td><label class="col-lg-5 control-label">version:</label>
							<div class="col-lg-7">
								<span data-role="version"></span>
							</div></td>
					</tr>

				</table>

				<!-- grid -->
				<div id=<%=gridId%>></div>

			</div>
			
			</div>
			</body>
</html>
