<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">项目名称:</label>
	                    <div class="col-lg-9">
                           <input name="projectName" style="display:inline; width:94%;" class="form-control"  type="text"  dataType="Regex"  validateExpr="/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$/"  id="projectNameID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">项目编码:</label>
	                    <div class="col-lg-9">
                           <input name="projectCode" style="display:inline; width:94%;" class="form-control"  type="text"  dataType="Regex"  validateExpr="/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$/"  id="projectCodeID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">项目开始时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="projectstartDate" id="projectstartDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">项目结束时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="projectendDate" id="projectendDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">备注:</label>
	                    <div class="col-lg-9">
                           <input name="projectRemark" style="display:inline; width:94%;" class="form-control"  type="text"  dataType="Regex"  validateExpr="/^[\u4e00-\u9fa5_a-zA-Z0-9]{0,200}$/" require="false" id="projectRemarkID" />
			    </div>
	</div>
		           <!--<div class="form-group">
                    <label class="col-lg-3 control-label">项目经理:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="projectManagerID"></div>
	                       <input type="hidden" id="projectManagerID_"  name="projectManager" dataType="Require"/>
		<span class="required">*</span>	    </div>
	</div>-->
	
	<div class="form-group">
                        <label class="col-lg-3 control-label">项目经理:</label>
                        <div class="col-lg-9">
                            <div class="btn-group select" id="projectManagerID">
                                <button data-toggle="item" class="btn btn-default" type="button">
                                    选择用户
                                </button>
                                <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
                                    <span class="caret"></span>
                                </button>
                                <input type="hidden" id="projectManagerID_"  name="projectManager" data-toggle="value">
                            </div><span class="required">*</span>
                        </div>
                    </div>
	
	</form>
<script type="text/javascript">
    var selectItems = {};
                                                    var contents = [{title:'请选择', value: ''}];
        contents.push({title:'张明' , value:'张明'});
        contents.push({title:'徐麟' , value:'徐麟'});
        contents.push({title:'孙同乐' , value:'孙同乐'});
        contents.push({title:'李宁' , value:'李宁'});
        contents.push({title:'赵子恒' , value:'赵子恒'});
        contents.push({title:'胥主任' , value:'胥主任'});
        selectItems['projectManagerID'] = contents;
        </script>
</body>
</html>