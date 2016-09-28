var quantityConfig = function(){
	var baseUrl = contextPath + '/QuantityConfig/';
	var batchConfigObject = null;
	var mesgTypeSelect = null;
	var paragraphSelect = null;
	var dialog = null;
	var batchConfigDialog = null;
	var configLeftTable = null;
	var configRightTable = null;
	var dataGrid = null;
	/*
	 新增方法
	 */
	var add = function(recordTypeId, grid){
		dataGrid = grid;
		$.get(contextPath + '/pages/domain/Quantity-config.jsp').done(function(data){
			init(data);
			mesgTypeSelect.setValue(recordTypeId).find('button').off().addClass('disabled');
		});
	};
	/*
	 修改方法
	 */
	var modify = function(id, grid){
		dataGrid = grid;
		$.get(contextPath + '/pages/domain/Quantity-config.jsp').done(function(data){
			init(data, id);
			setData(id);
			dialog.trigger('tableSelectComplete.koala');
		});
	};
	/*
	 删除方法
	 */
	var delDataResource = function(ids, grid){
		dataGrid = grid;
		$.post(baseUrl + 'delete.koala', {ids:ids}).done(function(data){
			 if(data.success){
				dataGrid.message({
					 type: 'success',
					 content: '删除成功'
				 }) ;
				 dataGrid.grid('refresh');
			 }else{
				 dataGrid.message({
					 type: 'error',
					 content: '删除失败'
				 });
			 }
		}).fail(function(data){
			dataGrid.message({
				type: 'error',
				content: '删除失败'
			});
		});
	};
	
	/**
	 * 初始化
	 */
	var init = function(data, id){
		dialog = $(data);
		dialog.on('tableSelectComplete.koala', $.proxy(fillRightTable, this));
		dialog.find('.modal-header').find('.modal-title').html(id ? '修改批量规则':'添加批量规则');
		configLeftTable = dialog.find('#configLeftTable');
		configRightTable = dialog.find('#configRightTable');
		mesgTypeSelect = dialog.find('#mesgTypeSelect');
		paragraphSelect = dialog.find('#paragraphSelect');
		initLayout();
		fillSelectData();
		fillWidgetTypeSelect(dialog.find('.select[data-role="ruleType"]'));
		fillQueryOperationSelect(dialog.find('.select[data-role="ruleType"]'));
		dialog.find('#quantityConfigSave').on('click',function(){
			$(this).attr('disabled', 'disabled');
			save(id);
		}).end().modal({
			keyboard: false
		}).on({
			'hidden.bs.modal': function(){
				$(this).remove();
			},
			'complete': function(){
				dataGrid.message({
					type: 'success',
					content: '保存成功'
				});
				$(this).modal('hide');
				//dataGrid.grid('refresh');
			}
		});
		dialog.find('.grid').find('.grid-table-body').on('scroll', function(){
			$(this).closest('.grid-body').find('.grid-table-head').css('left', -$(this).scrollLeft());
		});
	};
	
	//窗口大小变化调节各组建大小
	window.onresize=function(){
		initLayout();
	}
	
	var initLayout = function(){
		var width = $(window).width() * 0.73;
		dialog.find('.mesg-coloum').each(function(){
			var leftWidth =  width * 0.3;
			var $this = $(this);
			var padding = width>1150 ? 8 : 14*1150/width;
			$this.closest('td').css('width', leftWidth).next('td').css('width', width * 0.73);
			$this.find('.grid-table-body').css('width', leftWidth-padding).find('table').css('width',leftWidth);
			$this.find('.grid-table-head').find('table').css('width',leftWidth);
		});

		dialog.find('.batchRuleSelected').each(function(){
			var rightWidth = width * 0.7;
			var $this = $(this);
			var padding = width>1150 ? 0 : 8*1150/width;
			$this.find('.grid-table-body').css('width', rightWidth-padding).find('table').css('width', rightWidth);
			$this.find('.grid-table-head').find('table').css('width', rightWidth);
		});
	};
	
	/**
	 * 填充选择框
	 */
	var fillSelectData = function(){
		$.ajaxSetup({  
		    async : false //取消异步  
		});  
		$.get(contextPath + '/RecordType/findRecordTypes.koala').done(function(data){
			var contents = new Array();
			$.each(data, function() {
				contents.push({value: this.id, title: this.recordType});
			});
			mesgTypeSelect.select({
				title: '选择记录类型',
				contents: contents
			}).on('change', function(){
				var id = $(this).getValue();
				var url = baseUrl + 'getRecordSegments/'+ id+'.koala';
				$.get(url).done(function(json){
					var recordSegments = json.data;
					clearSelectedItem();
					fillLeftTable(recordSegments);
				});
			});
		});
	};
	
	/*
	 *修改填充数据
	 */
	var setData = function(id){
		$.get(baseUrl + 'get/' + id + '.koala').done(function(data){
			batchConfigObject = data.data;
			mesgTypeSelect.setValue(batchConfigObject.recordTypeDTO.id).find('button').off().addClass('disabled');
		}).fail(function(){
			dialog.find('.modal-content').message({
				type: 'error',
				content: '获取信息失败, 请稍后重试'
			});
		});
	};
	
	//清除右边表格填充内容
	var clearSelectedItem = function(){
		configRightTable.children().remove();
	};

	//填充左边表格列
	var fillLeftTable = function(recordSegments) {
		var xmlNodeRows = new Array();
		for(var i = 0; i < recordSegments.length; i++){
			var recordItems = recordSegments[i].recordItems;
			for (var j = 0; j < recordItems.length; j++) {
				xmlNodeRows.push('<tr><td class="column-name">'+recordItems[j].itemName+'</td><td class="paragraph-name">'+recordSegments[i].segName+'</td><td class="operation">'
						+'<a data-value="'+recordItems[j].itemName
						+'" data-paragraph="'+recordSegments[i].segName
						+'" data-segmark="'+recordSegments[i].segMark
						+'" data-itemid="'+recordItems[j].itemId
						+'" data-itemlength="'+recordItems[j].itemLength
						+'" data-itemtype="'+recordItems[j].itemType
						+'" data-role="add"><span class="glyphicon glyphicon-plus">添加</span></a></td></tr>');
			}
		}
		var xmlNodeRowsHtml = xmlNodeRows.join('');
		configLeftTable.html(xmlNodeRowsHtml)
			.find('a[data-role="add"]')
			.on('click', function(){
				var $this = $(this);
				var cnName = $this.data('value');//中文名
				var paragraph = $this.data('paragraph');//所属段中文名
				var segMark = $this.data('segmark');//所属段段标
				var itemId = $this.data('itemid');//标识符
				var itemLength = $this.data('itemlength');//数据项长度
				var itemType = $this.data('itemtype');//数据项类型
				var row = $('<tr><td class="column-name">'+cnName+'<input data-role="cnName" type="hidden" value="'+cnName+'"/><input data-role="itemId" type="hidden" value="'+itemId+'"/><input data-role="itemLength" type="hidden" value="'+itemLength+'"/><input data-role="itemType" type="hidden" value="'+itemType+'"/></td>' +
						'<td class="column-paragraph">'+paragraph+'<input data-role="fieldName" type="hidden" value="'+paragraph+'"/><input data-role="segMark" type="hidden" value="'+segMark+'"/></td>'+
					'<td class="query-operation"><div class="btn-group select" data-role="ruleType"></div></td>' +
					'<td class="value">起始值：&nbsp<input data-role="startValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="1"/><span class="required" >*</span></td>' +
					'<td class="visibility"><div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="inUse" checked="true"></span></div></td><td class="delete-btn"><a data-role="delete" data-value="'+cnName+'"><span class="glyphicon glyphicon-remove">删除</span></a></td></tr>');
				var ruleType = row.find('[data-role="ruleType"]');
				fillQueryOperationSelect(ruleType);
				ruleType.on('change', function(){
					var valueTd = row.find('.value');
					if($(this).getValue() == '0'){
						valueTd.html('起始值：&nbsp<input data-role="startValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="1"/><span class="required" >*</span>');
					}else if($(this).getValue() == '1'){
						valueTd.html('<div class="btn-group select" data-role="dicType"></div>');
						fillWidgetTypeSelect(row.find('[data-role="dicType"]'));
					}else if($(this).getValue() == '2'){
						valueTd.html('<input data-role="custom" class="form-control" required="true" readonly /><span class="required" >*</span>');
						valueTd.find('[data-role="custom"]').on('click', function(e){
							openCustomRuleConfig(this);
						});
					}else{
						valueTd.html('<div class="btn-group select" data-role="threeStandardColoum"></div>');
						fillThreeStandardSelect(row.find('[data-role="threeStandardColoum"]'));
					}
				});
				$this.closest('tr').hide();
				row.appendTo(configRightTable).find('[data-role="delete"]').on('click', function(){
					removeQueryRightTableRow($(this));
				});
				
				row.find('[data-role="inUse"]').on('click', function(){
					if(this.checked){
						$(this).parent().addClass('checked');
					}else{
						$(this).parent().removeClass('checked');
					}
				});
			});
	};
	
	//填充右边表格
	var fillRightTable = function(){
		var batchRules = batchConfigObject.quantityRules;
		for(var i=0, j=batchRules.length; i<j; i++){
			var batchRule = batchRules[i];
			var cnName = batchRule.cnName;//中文名
			var paragraph = configLeftTable.find('a[data-value="'+cnName+'"]').data('paragraph');//所属段中文名
			var segMark = batchRule.segMark;//所属段段标
			var itemId = batchRule.itemId;//标识符
			var itemLength = batchRule.itemLength;//数据项长度
			var itemType = batchRule.itemType;//数据项类型
			var row = $('<tr><td class="column-name">'+cnName+'<input data-role="cnName" type="hidden" value="'+cnName+'"/><input data-role="itemId" type="hidden" value="'+itemId+'"/><input data-role="itemLength" type="hidden" value="'+itemLength+'"/><input data-role="itemType" type="hidden" value="'+itemType+'"/></td>' +
				'<td class="column-paragraph">'+paragraph+'<input data-role="fieldName" type="hidden" value="'+paragraph+'"/><input data-role="segMark" type="hidden" value="'+segMark+'"/></td>'+
				'<td class="query-operation"><div class="btn-group select" data-role="ruleType"></div></td>' +
				'<td class="value">起始值：&nbsp<input data-role="startValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="1"/><span class="required" >*</span></td>' +
				'<td class="visibility"><div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="inUse" checked="true"></span></div></td><td class="delete-btn"><a data-role="delete" data-value="'+cnName+'"><span class="glyphicon glyphicon-remove">删除</span></a></td></tr>');
			var ruleType =  row.find('[data-role="ruleType"]');
			fillQueryOperationSelect(ruleType);
			var valueTd = row.find('.value');
			ruleType.on('change', function(){
				var row = $(this).closest('tr');
				var valueTd = row.find('.value');
				if($(this).getValue() == '0'){
					valueTd.html('起始值：&nbsp<input data-role="startValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:30%!important;" value="1"/><span class="required" >*</span>');
				}else if($(this).getValue() == '1'){
					valueTd.html('<div class="btn-group select" data-role="dicType"></div>');
					fillWidgetTypeSelect(row.find('[data-role="dicType"]'));
				}else if($(this).getValue() == '2'){
					valueTd.html('<input data-role="custom" class="form-control" required="true" readonly /><span class="required" >*</span>');
					valueTd.find('[data-role="custom"]').on('click', function(e){
						openCustomRuleConfig(this);
					});
				}else{
					valueTd.html('<div class="btn-group select" data-role="threeStandardColoum"></div>');
					fillThreeStandardSelect(row.find('[data-role="threeStandardColoum"]'));
				}
			});
			ruleType.setValue(batchRule.ruleType);
			var jsonObj = JSON.parse(batchRule.ruleProperties);
			if(batchRule.ruleType == '0'){
				valueTd.find('[data-role="startValue"]').val(jsonObj.startValue);
				valueTd.find('[data-role="stepSize"]').val(jsonObj.stepSize);
			}else if(batchRule.ruleType == '1'){
				valueTd.find('[data-role="dicType"]').setValue(jsonObj.dicType);
			}else if(batchRule.ruleType == '2'){
				valueTd.find('[data-role="custom"]').val(jsonObj.custom);
				valueTd.find('[data-role="custom"]').on('click', function(){
					openCustomRuleConfig(this);
				});
			}else{
				valueTd.find('[data-role="threeStandardColoum"]').setValue(jsonObj.threeStandardColoum);
			}
			row.find('[data-role="delete"]').on('click', function(){
				removeQueryRightTableRow($(this));
			});
			row.find('[data-role="inUse"]').on('click', function(){
				if(this.checked){
					$(this).parent().addClass('checked');
				}else{
					$(this).parent().removeClass('checked');
				}
			});
			!batchRule.inUse && row.find('[data-role="inUse"]').prop('checked','').parent().removeClass('checked');;
			configRightTable.append(row);
			hideQueryLeftTableRow(cnName, segMark);
		}
	};
	
	var openCustomRuleConfig = function(self){
		 var self = $(self);
	        batchConfigDialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">自定义规则配置</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get(contextPath+'/pages/domain/BatchConfig-detailConfig.jsp').done(function(html){
	               batchConfigDialog.find('.modal-body').html(html);
	               if(self.val()){
						var obj =  JSON.parse(self.val());
						batchConfigDialog.find('#templeteID').val(obj.templete);
						var propTable = batchConfigDialog.find('#propTable');
						var variables= obj.variables;
						for(key in variables){
							addRow(propTable, variables[key]);
						}
					}
	                batchConfigDialog.modal({
	                    keyboard:false,
	                    backdrop:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                batchConfigDialog.find('[data-role="addRow"]').on('click', function(e){
	                	addRow();
	                })
	                batchConfigDialog.find('#save').on('click',{grid: self}, function(e){
	                	if(!validateVariables()){
	                		batchConfigDialog.find('#save').removeAttr('disabled');
	            			return false;
	            		}
	                    var data = {};
	                    var variables = [];
	                    data.templete = batchConfigDialog.find('#templeteID').val();
	                    var propTable = batchConfigDialog.find('#propTable');
	                    propTable.find('tr').each(function(index,tr){
	            			var $tr = $(tr);
		                    var vType = $tr.find('[data-role="vType"]').getValue();
		        			var properties = {};
		        			properties.vType = vType;
		        			properties.vName = $tr.find('input[data-role="vName"]').val();
		        			if (vType == '0'){
		        				properties.initValue = $tr.find('input[data-role="initValue"]').val();
		        				properties.increment = $tr.find('input[data-role="increment"]').val();
		        				properties.isWidth = $tr.find('input[data-role="isWidth"]').is(':checked');
		        				properties.dataWidth = $tr.find('input[data-role="dataWidth"]').val();
		        				
		        			}else if (vType == '1'){
		        				properties.randomArea = $tr.find('[data-role="randomArea"]').val();
		        			}
		        			variables.push(properties);
		                  })
		                  data.variables = variables;
	                    batchConfigDialog.modal('hide');
	                    self.val(JSON.stringify(data));
	                });
	        });
	
	}
	
	var addRow = function(propTable, variable){
		var row = $('<tr><td class="v-name"><input  data-role="vName" required="true" style="display: inline; " class="form-control" type="text" /></td>'
		+'<td class="v-type"><div class="btn-group select" id="variableTypeSelect" data-role="vType"></div></td>'
		+'<td class="v-properties">初值：<input data-role="initValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:20%!important;display:inline" value="0"/>&nbsp;&nbsp;增量：&nbsp;<input data-role="increment" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字" style="width:10%!important;display:inline" value="1"/>&nbsp;&nbsp;'
		+'<div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="isWidth" checked="checked"></span></div>&nbsp;数字宽度：&nbsp;<input data-role="dataWidth" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字" style="width:10%!important;display:inline" value="1"/></td>'
		+'<td class="delete-btn"><a data-role="delete"><span class="glyphicon glyphicon-remove">删除</span></a></td>')
	
		if(!propTable){
			propTable=$('#propTable')
		}
		var vType = row.find('[data-role="vType"]')
		fillVTypeSelect(vType);
		var valueTd = row.find('.v-properties');
		vType.on('change', function(){
			var row = $(this).closest('tr');
			var valueTd = row.find('.v-properties');
			if($(this).getValue() == '0'){
				valueTd.html('<td class="v-properties">初值：<input data-role="initValue" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="只能输入数字" style="width:20%!important;display:inline" value="0"/>&nbsp;&nbsp;增量：&nbsp;<input data-role="increment" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字" style="width:10%!important;display:inline" value="1"/>&nbsp;&nbsp;'
						+'<div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="isWidth" checked="checked"></span></div>&nbsp;数字宽度：&nbsp;<input data-role="dataWidth" class="form-control" required="true" rgExp="/^[0-9]{1,}$/" data-content="只能输入数字" placeholder="数字" style="width:10%!important;display:inline" value="1"/></td>');
				valueTd.find('[data-role="isWidth"]').on('click', function(){
					if(this.checked){
						$(this).parent().addClass('checked');
					}else{
						$(this).parent().removeClass('checked');
					}
				});
			}else if($(this).getValue() == '1'){
				valueTd.html('枚举值(以,分隔)：<input data-role="randomArea" class="form-control" style="width:75%;display:inline !important" required="true" placeholder="请输入以英文逗号分隔的枚举值"/><span style="display:inline" class="required" >*</span>');
			}
		});
		if(variable){
			vType.setValue(variable.vType);
			row.find('input[data-role="vName"]').val(variable.vName);
			if(variable.vType==0){
			row.find('input[data-role="initValue"]').val(variable.initValue);
			row.find('input[data-role="increment"]').val(variable.increment);
			row.find('input[data-role="dataWidth"]').val(variable.dataWidth);
			!variable.isWidth && row.find('[data-role="isWidth"]').prop('checked','').parent().removeClass('checked');;
			}else{
				row.find('input[data-role="randomArea"]').val(variable.randomArea)
			}
		}
		row.appendTo(propTable).find('[data-role="delete"]').on('click', function(){
			removeRow($(this));
		});
		row.find('[data-role="isWidth"]').on('click', function(){
			if(this.checked){
				$(this).parent().addClass('checked');
			}else{
				$(this).parent().removeClass('checked');
			}
		});
		
	}
	
	//删除行
	var removeRow = function($this){
		$this.closest('tr').remove();
	};
	
	//填充变量类型
	var fillVTypeSelect = function(select){
		select.select({
			contents: [
						{value: '0', title: '自增',selected: true},
						{value: '1', title: '随机'}
					]
		});
	};
	
	 //隐藏查询条件左边的行
	var hideQueryLeftTableRow = function(column, segMark){
		//configLeftTable.find('a[data-value="'+column+'"]')
		configLeftTable.find('a[data-value="'+column+'"][data-segmark="'+segMark+'"]')
			.closest('tr')
			.hide();
	};
	//删除右边的行
	var removeQueryRightTableRow = function($this){
		var column = $this.data('value');
		$this.closest('tr').remove();
		configLeftTable.find('a[data-value="'+column+'"]')
			.closest('tr')
			.show();
	};

	//填充数据字典
	var fillWidgetTypeSelect = function(select){
		select.select({
			contents: [
				{value: 'zjlx', title: '证件类型',selected: true},
				{value: 'khzllx', title: '客户资料类型'}
			]
		});
	};
	
	//填充规则类型
	var fillQueryOperationSelect = function(select){
		select.select({
			contents: [
				{value: '0', title: '自增',selected: true},
				{value: '1', title: '数据字典'},
				{value: '2', title: '自定义'},
				{value: '3', title: '三标信息'}
			]
		});
	};
	
	//填充三标信息
	var fillThreeStandardSelect = function(select){
		select.select({
			contents: [
			           {value: 'name', title: '姓名',selected: true},
			           {value: 'credentialType', title: '证件类型'},
			           {value: 'credentialNumber', title: '证件号码'},
			           {value: 'organizationCode', title: '机构代码'},
			           {value: 'customerCode', title: '客户资料标识号'},
			           {value: 'acctCode', title: '账户标识号'},
			           {value: 'conCode', title: '合同标识号'},
			           {value: 'ccc', title: '抵质押合同标识号'}
			           ]
		});
	};

	/*
	 *   保存数据 id存在则为修改 否则为新增
	 */
	var save = function(id){
		if(!validate()){
			dialog.find('#quantityConfigSave').removeAttr('disabled');
			return false;
		}
		var url = baseUrl + 'add.koala';
		var params = getAllData();
		if(id){
			url =  baseUrl + 'update.koala';
			params.id = id;
			params.version = batchConfigObject.version;
		}
		$.post(url, params).done(function(data){
			if(data.success){
				dialog.trigger('complete');
			}else{
				dialog.find('.modal-content').message({
					type: 'error',
					content: data.errorMessage
				});
			}
			dialog.find('#quantityConfigSave').removeAttr('disabled');
		});
	};
	/*
	* 数据验证  成功返回true  失败返回false
	 */
	var validate = function(){
		if(!mesgTypeSelect.getValue()){
			showErrorMessage(mesgTypeSelect, '请选择记录类型');
			return false;
		}
//		if(!paragraphSelect.getValue()){
//			showErrorMessage(paragraphSelect, '请选择段');
//			return false;
//		}
		var flag = true;
		dialog.find('.batchRule').find('input[required=true],input[rgExp]').each(function(){
			var $this = $(this);
			var value = $this.val();
			if($this.attr('required') && !checkNotNull(value)){
				showErrorMessage(dialog, $this, '请填入该选项内容');
				flag = false;
				return false;
			}
			var rgExp = $this.attr('rgExp');
			if(rgExp && !value.match(eval(rgExp))){
				showErrorMessage(dialog, $this, $this.data('content'));
				flag = false;
				return false;
			}
		});
		return flag;
	};
	/*
	 * 数据验证  成功返回true  失败返回false
	 */
	var validateVariables = function(){
		var flag = true;
		batchConfigDialog.find('textarea[required=true],input[required=true],input[rgExp]').each(function(){
			var $this = $(this);
			var value = $this.val();
			if($this.attr('required') && !checkNotNull(value)){
				showErrorMessage(batchConfigDialog, $this, '请填入该选项内容');
				flag = false;
				return false;
			}
			var rgExp = $this.attr('rgExp');
			if(rgExp && !value.match(eval(rgExp))){
				showErrorMessage(batchConfigDialog, $this, $this.data('content'));
				flag = false;
				return false;
			}
		});
		return flag;
	};
	
	/**
	 * 显示提示信息
	 */
	var showErrorMessage = function(container, $element, content){
		$element.popover({
			content: content,
			trigger: 'manual',
			container: container
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
	
	var getAllData = function(){
		var data = {};
		data['recordTypeDTO.id'] = mesgTypeSelect.getValue();
		configRightTable.find('tr').each(function(index,tr){
			var $tr = $(tr);
			data['quantityRules['+index+'].cnName'] = $tr.find('input[data-role="cnName"]').val();
			data['quantityRules['+index+'].segMark'] = $tr.find('input[data-role="segMark"]').val();
			data['quantityRules['+index+'].itemId'] = $tr.find('input[data-role="itemId"]').val();
			data['quantityRules['+index+'].itemLength'] = $tr.find('input[data-role="itemLength"]').val();
			data['quantityRules['+index+'].itemType'] = $tr.find('input[data-role="itemType"]').val();
			var ruleType = $tr.find('[data-role="ruleType"]').getValue();
			data['quantityRules['+index+'].ruleType'] = ruleType;
			var properties = {};
			if (ruleType == '0'){
				properties.startValue = $tr.find('input[data-role="startValue"]').val();
				properties.stepSize = $tr.find('input[data-role="stepSize"]').val();
			}else if (ruleType == '1'){
				properties.dicType = $tr.find('[data-role="dicType"]').getValue();
			}else if (ruleType == '2'){
				properties.custom = $tr.find('input[data-role="custom"]').val();
			}else{
				properties.threeStandardColoum = $tr.find('[data-role="threeStandardColoum"]').getValue();
			}
			data['quantityRules['+index+'].ruleProperties'] = JSON.stringify(properties);
			var inUse = $tr.find('input[data-role="inUse"]').is(':checked');
			if(inUse){
				data['quantityRules['+index+'].inUse'] = inUse;
			}
		});
		return data;
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
	return {
		add: add,
		modify: modify,
		del: delDataResource
	};
};

