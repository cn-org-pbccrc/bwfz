<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">报文类型:</label>
	                    <div class="col-lg-9">
                           <input name="recordType" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="recordTypeID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">类型代码:</label>
	                    <div class="col-lg-9">
                           <input name="code" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="codeID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">记录模板:</label>
	                    <div class="col-lg-9">
                           <input name="recordTemp" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="recordTempID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">转换关系:</label>
	                    <div class="col-lg-9">
                           <input name="transform" style="display:inline; width:94%;" class="form-control"  type="text"  id="transformID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">文件类型:</label>
	                    <div class="col-lg-9">
                           <input name="fileType" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="fileTypeID" />
		<span class="required">*</span>	    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                    </script>
</body>
</html>