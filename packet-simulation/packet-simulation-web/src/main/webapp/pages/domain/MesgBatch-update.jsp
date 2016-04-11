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
                    <label class="col-lg-3 control-label">版本号:</label>
	                    <div class="col-lg-9">
                           <input name="fileVersion" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="fileVersionID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">数据提供机构代码:</label>
	                    <div class="col-lg-9">
                           <input name="origSender" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="origSenderID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">文件生成时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID" dataType="Require">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
		<span class="required">*</span>	    </div>
	</div>
		<div class="form-group">
	                    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="xmlID">
					</div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                            </script>
</body>
</html>