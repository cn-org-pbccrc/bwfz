<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form class="form-horizontal">

		<div class="form-group">
			<label class="col-lg-3 control-label">任务名称:</label>
			<div class="col-lg-9">
				<input name="name" style="display: inline; width: 94%;"
					class="form-control" type="text" dataType="Require" id="nameID" />
				<span class="required">*</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">任务开始时间:</label>
			<div class="col-lg-9">
				<div class="input-group date form_datetime"
					style="width: 160px; float: left;">
					<input type="text" class="form-control" style="width: 160px;"
						name="startDate" id="startDateID" dataType="Require"> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span></span>
				</div>
				<span class="required">*</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">任务结束时间:</label>
			<div class="col-lg-9">
				<div class="input-group date form_datetime"
					style="width: 160px; float: left;">
					<input type="text" class="form-control" style="width: 160px;"
						name="endDate" id="endDateID" dataType="Require"> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span></span>
				</div>
				<span class="required">*</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">任务描述:</label>
			<div class="col-lg-9">
				<input name="description" style="display: inline; width: 94%;"
					class="form-control" type="text" id="descriptionID" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-lg-3 control-label">任务负责人:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="directorID">
					<button data-toggle="item" class="btn btn-default" type="button">
						选择用户</button>
					<button data-toggle="dropdown"
						class="btn btn-default dropdown-toggle" type="button">
						<span class="caret"></span>
					</button>
					<input type="hidden" id="directorID_" name="director"
						data-toggle="value">
				</div>
				<span class="required">*</span>
			</div>
		</div>

		<div class="form-group">
			<label class="col-lg-3 control-label">任务状态:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="statusID">
				</div>
					<input type="hidden" id="statusID_" name="status"
						data-toggle="value">
				<span class="required">*</span>
			</div>
		</div>
	</form>
	<script type="text/javascript">
/*      var selectItems = {};
                var contents = [{title:'请选择', value: ''}];
                contents.push({title:'未开始' , value:'0'});
                 contents.push({title:'进行中20%' , value:'1'});
                 contents.push({title:'进行中40%' , value:'2'});
                 contents.push({title:'进行中60%' , value:'3'});
                 contents.push({title:'进行中80%' , value:'4'});
                 contents.push({title:'完成100%' , value:'5'});
                 contents.push({title:'中断' , value:'6'});
                 contents.push({title:'推迟' , value:'7'});
                 contents.push({title:'其它' , value:'8'});
        selectItems['statusID'] = contents; */  
        </script>
</body>
</html>