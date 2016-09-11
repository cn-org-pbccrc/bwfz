<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="createdByID" name="createdBy" /> 
	<input type="hidden" id="contentID" name="content" /> 
	<input type="hidden" id="submissionIdID" name="submissionId" /> 
	<input type="hidden" id="recordTypeIdID" name="recordTypeId" >
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">记录名称:</label>
	                    <div class="col-lg-9">
                           <input name="recordName" style="display:inline; width:94%;" class="form-control"  type="text"  id="recordNameID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                    </script>
</body>
</html>