<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="projectIdID" name="projectId" /> 
	<input type="hidden" id="taskCreatedTimeID" name="taskCreatedTime" />
	<input type="hidden" id="taskCreatorID" name="taskCreator" />
	<input type="hidden" id="disabledID" name="disabled" />
	<input type="hidden" id="directorID_" name="director" />
	           <div class="form-group">
                    <label class="col-lg-3 control-label">任务名称:</label>
	                    <div class="col-lg-9">
                           <input name="name" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="nameID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">任务开始时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="startDate" id="startDateID" dataType="Require">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">任务结束时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="endDate" id="endDateID" dataType="Require">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">任务描述:</label>
	                    <div class="col-lg-9">
                           <input name="description" style="display:inline; width:94%;" class="form-control"  type="text"  id="descriptionID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">任务状态:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="statusID"></div>
	                       <input type="hidden" id="statusID_"  name="status" dataType="Require"/>
		<span class="required">*</span>	    </div>
	</div>
	</form>
<script type="text/javascript">
    /* var selectItems = {};
                                            var contents = [{title:'请选择', value: ''}];
        selectItems['directorID'] = contents;
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