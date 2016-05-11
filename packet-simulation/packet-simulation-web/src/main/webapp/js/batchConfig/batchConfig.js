var batchConfig = function(){
	var baseUrl = contextPath + '/BatchConfig/';
	var batchConfigObject = null;
	var mesgTypeSelect = null;
	var paragraphSelect = null;
	var dialog = null;
	var staticQueryLeftTable = null;
	var staticQueryRightTable = null;
	var dynamicQueryLeftTable = null;
	var dynamicQueryRightTable = null;
	var showColumnLeftTable = null;
	var showColumnRightTable = null;
	var dataGrid = null;
	/*
	 新增方法
	 */
	var add = function(grid){
		dataGrid = grid;
		$.get(contextPath + '/pages/domain/Batch-config.jsp').done(function(data){
			init(data);
		});
	};
	/*
	 修改方法
	 */
	var modify = function(id, grid){
		dataGrid = grid;
		$.get(contextPath + '/pages/domain/Batch-config.jsp').done(function(data){
			init(data, id);
			setData(id);
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
		dialog.find('.modal-header').find('.modal-title').html(id ? '修改批量规则':'添加批量规则');
		staticQueryLeftTable = dialog.find('#staticQueryLeftTable');
		staticQueryRightTable = dialog.find('#staticQueryRightTable');
		mesgTypeSelect = dialog.find('#mesgTypeSelect');
		paragraphSelect = dialog.find('#paragraphSelect');
		initLayout();
		fillSelectData(id);
		fillWidgetTypeSelect(dialog.find('.select[data-role="ruleType"]'));
		fillQueryOperationSelect(dialog.find('.select[data-role="ruleType"]'));
		dialog.find('#generalQuerySave').on('click',function(){
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
	var initLayout = function(){
		var width = $(window).width() * 0.73;
		dialog.find('.query-condition').each(function(){
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
	var fillSelectData = function(id){
		$.get(contextPath + '/MesgType/findMesgTypes.koala').done(function(data){
			var contents = new Array();
			$.each(data, function() {
				contents.push({value: this.id, title: this.mesgType});
			});
			mesgTypeSelect.select({
				title: '选择记录类型',
				contents: contents
			}).on('change', function(){
				var id = $(this).getValue();
				var url = baseUrl + 'getNodes/'+ id+'.koala';
				$.get(url).done(function(json){
					var xmlNode = json.data;
					var paragraphs = json.data.nodes[0].nodes;
					clearSelectedItem();
					fillLeftTable(paragraphs, paragraphs);
					dialog.trigger('tableSelectComplete.koala');
				});
//				paragraphSelect.setSelectItems([]);
//				var dataSourceId = $(this).getValue();
//				var url = baseUrl + 'findAllTable.koala?id='+ dataSourceId;
//				$.get(url).done(function(data){
//					if(data.result){
//						dialog.message({
//							type: 'error',
//							content: data.errorMessage
//						});
//						$(this).find('[data-toggle="item"]').text('请选择');
//						return;
//					}
//					var tableList = data;
//					var contents = new Array();
//					for(var i=0, j=tableList.length; i<j; i++){
//						contents.push({value: tableList[i], title: tableList[i]});
//					}
//					paragraphSelect.setSelectItems(contents);
//					if(id){
//						dialog.trigger('mesgTypeSelectComplete.koala');
//					}else{
//						paragraphSelect.setValue(tableList[0]);							
//					}
//				});
//				
//					paragraphSelect.setSelectItems([]);
//					var mesgTypeId = $(this).getValue();
//					var url = baseUrl + 'getNodes/'+ mesgTypeId+'.koala';
//					$.get(url).done(function(json){
//						var xmlNode=json.data;
//						var paragraph=json.data.nodes[0].nodes;
//						var contents = new Array();
//						for (var i=0;i< paragraph.length;i++){
////							alert(paragraph[i].cnName);
//							contents.push({value: i, title: paragraph[i].cnName});
//						}
//						paragraphSelect.setSelectItems(contents);
//					});
			});
		});
		paragraphSelect.select({
			title: '选择段'
		}).on('change', function(){
				var id = mesgTypeSelect.getValue();
				var paragraphIndex = $(this).getValue();
				var url = baseUrl + 'getNodes/'+ id+'.koala';
				$.get(url).done(function(json){
					var xmlNode=json.data;
					var paragraph=json.data.nodes[0].nodes;
					clearSelectedItem();
					fillLeftTable(paragraph[paragraphIndex], paragraph[paragraphIndex]);
					dialog.trigger('tableSelectComplete.koala');
				});
		});
	};
	/*
	 *修改填充数据
	 */
	var setData = function(id){
		$.get(baseUrl + 'get/'+id+'.koala').done(function(data){
				batchConfigObject = data.data;
				mesgTypeSelect.setValue(batchConfigObject.mesgTypeDTO.id).find('button').off().addClass('disabled');
				dialog.on('mesgTypeSelectComplete.koala',  function(){
					paragraphSelect.setValue(batchConfigObject.tableName).find('button').off().addClass('disabled');
					var xmlNodeColumns = data.xmlNodeColumns;
				});
				dialog.on('tableSelectComplete.koala', $.proxy(fillRightTable, this));
//				dialog.find('#generalQuery_queryName').val(batchConfigObject.queryName);
//				dialog.find('#generalQuery_description').val(batchConfigObject.description);
			}).fail(function(){
				dialog.find('.modal-content').message({
					type: 'error',
					content: '获取信息失败, 请稍后重试'
				});
			});
	};
	//清除右边表格填充内容
	var clearSelectedItem = function(){
		staticQueryRightTable.children().remove();
	};

	//填充左边表格列
	var fillLeftTable = function(xmlNodeColumns, showColumns) {
		var xmlNodeRows = new Array();
//		alert(xmlNodeColumns.nodes.length)
		for(var i=0;i< xmlNodeColumns.length;i++){
			for (var int = 0; int < xmlNodeColumns[i].nodes.length; int++) {
				xmlNodeRows.push('<tr><td class="column-name">'+xmlNodeColumns[i].nodes[int].cnName+'</td><td class="paragraph-name">'+xmlNodeColumns[i].cnName+'</td><td class="operation">'
						+'<a data-value="'+xmlNodeColumns[i].nodes[int].cnName
						+'" data-ename="'+xmlNodeColumns[i].nodes[int].tagName
						+'" data-paragraph="'+xmlNodeColumns[i].cnName
						+'" data-path="'+xmlNodeColumns[i].nodes[int].path
						+'" data-type="'+xmlNodeColumns[i].nodes[int].nodeType
						+'" data-role="add"><span class="glyphicon glyphicon-plus">添加</span></a></td></tr>');
			}
		}
		var xmlNodeRowsHtml = xmlNodeRows.join('');
		staticQueryLeftTable.html(xmlNodeRowsHtml)
			.find('a[data-role="add"]')
			.on('click', function(){
				var $this = $(this);
				var cnName = $this.data('value');//中文名
				var enName = $this.data('ename');//英文名
				var paragraph = $this.data('paragraph');//所属段中文名
				var xpath = $this.data('path');
				var fieldType = $this.data('type');
				var row = $(' <tr><td class="column-name">'+cnName+'<input data-role="cnName" type="hidden" value="'+cnName+'"/><input data-role="enName" type="hidden" value="'+enName+'"/><input data-role="xpath" type="hidden" value="'+xpath+'"/><input data-role="fieldType" type="hidden" value="'+fieldType+'"/></td>' +
						'<td class="column-paragraph">'+paragraph+'<input data-role="fieldName" type="hidden" value="'+paragraph+'"/></td>'+
					'<td class="query-operation"><div class="btn-group select" data-role="ruleType"></div></td>' +
					'<td class="value">起始值：&nbsp<input data-role="startValue" class="form-control" required="true" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" style="width:30%!important;" value="1"/><span class="required" >*</span></td>' +
					'<td class="visibility"><div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="inUse" checked="true"></span></div></td><td class="delete-btn"><a data-role="delete" data-value="'+cnName+'"><span class="glyphicon glyphicon-remove">删除</span></a></td></tr>');
				var ruleType =  row.find('[data-role="ruleType"]');
				fillQueryOperationSelect(ruleType);
				ruleType.on('change', function(){
					var valueTd = row.find('.value');
					if($(this).getValue() == '0'){
						valueTd.html('起始值：&nbsp<input data-role="startValue" class="form-control" required="true" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" style="width:30%!important;" value="1"/><span class="required" >*</span>');
					}else if($(this).getValue() == '1'){
						valueTd.html('<div class="btn-group select" data-role="dicType"></div>');
						fillWidgetTypeSelect(row.find('[data-role="dicType"]'));
					}else{
						valueTd.html('<input data-role="custom" class="form-control" required="true"/><span class="required" >*</span>');
					}
				});
				$this.closest('tr').hide();
				row.appendTo(staticQueryRightTable).find('[data-role="delete"]').on('click', function(){
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
		var batchRules = batchConfigObject.batchRules;
		for(var i=0, j=batchRules.length; i<j; i++){
			var  batchRule = batchRules[i];
			var cnName = batchRule.cnName;//中文名
			var enName = batchRule.enName;//英文名
			var paragraph = staticQueryLeftTable.find('a[data-value="'+cnName+'"]').data('paragraph');//所属段中文名
			var xpath = batchRule.xpath;
			var fieldType = batchRule.ruleType;
			var row = $(' <tr><td class="column-name">'+cnName+'<input data-role="cnName" type="hidden" value="'+cnName+'"/><input data-role="enName" type="hidden" value="'+enName+'"/><input data-role="xpath" type="hidden" value="'+xpath+'"/><input data-role="fieldType" type="hidden" value="'+fieldType+'"/></td>' +
					'<td class="column-paragraph">'+paragraph+'<input data-role="fieldName" type="hidden" value="'+paragraph+'"/></td>'+
				'<td class="query-operation"><div class="btn-group select" data-role="ruleType"></div></td>' +
				'<td class="value">起始值：&nbsp<input data-role="startValue" class="form-control" required="true" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" style="width:30%!important;" value="1"/><span class="required" >*</span></td>' +
				'<td class="visibility"><div class="checker"><span class="checked"><input type="checkbox" style="opacity: 0;" data-role="inUse" checked="checked"></span></div></td><td class="delete-btn"><a data-role="delete" data-value="'+cnName+'"><span class="glyphicon glyphicon-remove">删除</span></a></td></tr>');
			var ruleType =  row.find('[data-role="ruleType"]');
			fillQueryOperationSelect(ruleType);
			var valueTd = row.find('.value');
			ruleType.on('change', function(){
				var row = $(this).closest('tr');
				var valueTd = row.find('.value');
				if($(this).getValue() == '0'){
					valueTd.html('起始值：&nbsp<input data-role="startValue" class="form-control" required="true" style="width:30%!important;" value="0"/>&nbsp;步长：&nbsp;<input data-role="stepSize" class="form-control" required="true" style="width:30%!important;" value="1"/><span class="required" >*</span>');
				}else if($(this).getValue() == '1'){
					valueTd.html('<div class="btn-group select" data-role="dicType"></div>');
					fillWidgetTypeSelect(row.find('[data-role="dicType"]'));
				}else{
					valueTd.html('<input data-role="custom" class="form-control" required="true"/><span class="required" >*</span>');
				}
			});
			ruleType.setValue(batchRule.ruleType);
			var jsonObj = JSON.parse(batchRule.ruleProperties);
			if(batchRule.ruleType == '0'){
				valueTd.find('[data-role="startValue"]').val(jsonObj.startValue);
				valueTd.find('[data-role="stepSize"]').val(jsonObj.stepSize);
			}else if(batchRule.ruleType == '1'){
				valueTd.find('[data-role="dicType"]').setValue(jsonObj.dicType);
			}else{
				valueTd.find('[data-role="custom"]').val(jsonObj.custom);
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
			staticQueryRightTable.append(row);
			hideQueryLeftTableRow(cnName);
		}
	};
	 //隐藏查询条件左边的行
	var hideQueryLeftTableRow = function(column){
		staticQueryLeftTable.find('a[data-value="'+column+'"]')
			.closest('tr')
			.hide();
	};
	//删除右边的行
	var removeQueryRightTableRow = function($this){
		var column = $this.data('value');
		$this.closest('tr').remove();
		staticQueryLeftTable.find('a[data-value="'+column+'"]')
			.closest('tr')
			.show();
	};

	//填充条件
	var fillWidgetTypeSelect = function(select){
		select.select({
			contents: [
				{value: 'zjlx', title: '证件类型',selected: true},
				{value: 'khzllx', title: '客户资料类型'}
			]
		});
	};
	//填充条件
	var fillQueryOperationSelect = function(select){
		select.select({
			contents: [
				{value: '0', title: '自增',selected: true},
				{value: '1', title: '数据字典'},
				{value: '2', title: '自定义'},
			]
		});
	};

	/*
	 *   保存数据 id存在则为修改 否则为新增
	 */
	var save = function(id){
		if(!validate()){
			dialog.find('#generalQuerySave').removeAttr('disabled');
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
			dialog.find('#generalQuerySave').removeAttr('disabled');
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
		dialog.find('.generalQuery').find('input[required=true],input[rgExp]').each(function(){
			var $this = $(this);
			var value = $this.val();
			if($this.attr('required') && !checkNotNull(value)){
				showErrorMessage($this, '请填入该选项内容');
				flag = false;
				return false;
			}
			var rgExp = $this.attr('rgExp');
			if(rgExp && !value.match(eval(rgExp))){
				showErrorMessage($this, $this.data('content'));
				flag = false;
				return false;
			}
		});
		return flag;
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
	var getAllData = function(){
		var data = {};
		data['mesgTypeDTO.id'] = mesgTypeSelect.getValue();
		staticQueryRightTable.find('tr').each(function(index,tr){
			var $tr = $(tr);
			data['batchRules['+index+'].enName'] = $tr.find('input[data-role="enName"]').val();
			data['batchRules['+index+'].cnName'] = $tr.find('input[data-role="cnName"]').val();
			data['batchRules['+index+'].xpath'] = $tr.find('input[data-role="xpath"]').val();
			var ruleType = $tr.find('[data-role="ruleType"]').getValue();
			data['batchRules['+index+'].ruleType'] = ruleType;
			var properties = {};
			if (ruleType == '0'){
				properties.startValue = $tr.find('input[data-role="startValue"]').val();
				properties.stepSize = $tr.find('input[data-role="stepSize"]').val();
			}else if (ruleType == '1'){
				properties.dicType = $tr.find('[data-role="dicType"]').getValue();
			}else{
				properties.custom = $tr.find('input[data-role="custom"]').val();
			}
			data['batchRules['+index+'].ruleProperties'] = JSON.stringify(properties);
			var inUse = $tr.find('input[data-role="inUse"]').is(':checked');
			if(inUse){
				data['batchRules['+index+'].inUse'] = inUse;
			}
		});
//		dynamicQueryRightTable.find('tr').each(function(index,tr){
//			var $tr = $(tr);
//			data['dynamicQueryConditions['+index+'].fieldName'] = $tr.find('input[data-role="fieldName"]').val();
//			data['dynamicQueryConditions['+index+'].fieldType'] = $tr.find('input[data-role="fieldType"]').val();
//			data['dynamicQueryConditions['+index+'].label'] = $tr.find('input[data-role="label"]').val();
//			data['dynamicQueryConditions['+index+'].queryOperation'] = $tr.find('[data-role="queryOperation"]').getValue();
//			data['dynamicQueryConditions['+index+'].widgetTypeDTO'] = $tr.find('[data-role="widgetType"]').getValue();
//		});
//		showColumnRightTable.find('tr').each(function(index,tr){
//			var $tr = $(tr);
//			data['fieldDetails['+index+'].fieldName'] = $tr.find('input[data-role="fieldName"]').val();
//			data['fieldDetails['+index+'].label'] = $tr.find('input[data-role="label"]').val();
//		});
		return data;
	};
	var preview = function(id, dataSourceId){
		$.get(contextPath + "/dataSource/checkDataSourceById.koala?id=" + dataSourceId).done(function(data){
			if (data.success) {
				var previewWindow = window.open(contextPath + '/previewTemplate/'+id+'.koala', '预览');
				previewWindow.resizeTo(previewWindow.screen.width, previewWindow.screen.height);
			} else {
				$('#generalQueryGrid').message({
					type: 'error',
					content: data.errorMessage
				});
			}
		});
		
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
		del: delDataResource,
		preview: preview
	};
};

