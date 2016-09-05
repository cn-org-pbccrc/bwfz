<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
		<div class="form-group">
			<label class="col-lg-3 control-label">任务名称:</label>
			<div class="col-lg-9">
				<input name="taskName" style="display: inline; width: 94%;"
					class="form-control" type="text" dataType="Require" id="taskNameID" />
				<input name="selectedRecordType" class="form-control" type="hidden" id="selectedRecordTypeID" />
				<input name="oriMesgType" class="form-control" type="hidden" id="oriMesgTypeID" />
				<span class="required">*</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">发送渠道:</label>
			<div class="col-lg-9">
				<input name="sendChannel" style="display: inline; width: 94%;"
					class="form-control" type="text" dataType="Require"
					id="sendChannelID" /> <span class="required">*</span>
			</div>
		</div>
		<div class="form-group">
                    <label class="col-lg-3 control-label">业务类型:</label>
	                    <div class="col-lg-9">
                           <input name="selectedBizType" style="display:inline; width:94%;" class="form-control"  type="text"  id="selectedBizTypeID" />
			    </div>
	</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">是否加密:</label>
			<div class="col-lg-9">
				<div class="radio">
					<span class="checked"><input value=1 type="radio" checked="checked"
						name="encryption" style="opacity: 0;"></span>
				</div>
				<span style="position: relative; top: 5px;">是</span>
				&nbsp;&nbsp;&nbsp;
				<div class="radio">
					<span><input type="radio" 
						value=0 name="encryption" style="opacity: 0;"></span>
				</div>
				<span style="position: relative; top: 5px;">否</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">是否加压:</label>
			<div class="col-lg-9">
				<div class="radio">
					<span class="checked"><input value=1 type="radio" checked="checked"
						name="compression" style="opacity: 0;"></span>
				</div>
				<span style="position: relative; top: 5px;">是</span>
				&nbsp;&nbsp;&nbsp;
				<div class="radio">
					<span><input type="radio" 
						value=0 name="compression" style="opacity: 0;"></span>
				</div>
				<span style="position: relative; top: 5px;">否</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">文件内容:</label>
			<div class="col-lg-9">
				<textarea name="mesgContent" style="display: inline;width: 94%;height:300px;overflow-x:scroll;"
					class="form-control" id="mesgContentID">
						   </textarea>
			</div>
		</div>

	</form>



<script type="text/javascript">
	var isEncryption = $('[name="encryption"]');
	var isCompression = $('[name="compression"]');
	isEncryption.on('click', function() {
		isEncryption.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
	isCompression.on('click', function() {
		isCompression.parent().removeClass('checked');
		$(this).parent().addClass('checked');
	});
	$('body').unbind("keydown");
	// $(function(){
	// 	var datetimepicker = $('#datetimepicker2').datetimepicker({
	// 		 language: 'zh-CN',
	// 		 pickDate: true,
	// 		 pickTime: true
	// 	});
	// 	//alert(datetimepicker.getDate());
	// });
	// $('.input_cxcalendar').each(function(){
	// 			var a = new Calendar({
	// 				targetCls: $(this),
	// 				type: 'yyyy-mm-dd HH:MM:SS',
	// 				wday:2
	// 			},function(val){
	// 				console.log(val);
	// 			});
	// 		});
	//     var selectItems = {};
	//     var contents = [{title:'请选择', value: ''}];
	//     contents.push({title:'正常' , value:'0'});
	//     contents.push({title:'删除且不需重报' , value:'1'});
	//     contents.push({title:'删除且需重报' , value:'2'});
	//     selectItems['dataTypeID'] = contents;
</script>
</body>
</html>