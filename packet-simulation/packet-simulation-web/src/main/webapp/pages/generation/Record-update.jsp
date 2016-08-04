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
                    <label class="col-lg-3 control-label">content:</label>
	                    <div class="col-lg-9">
                           <input name="content" style="display:inline; width:94%;" class="form-control"  type="text"  id="contentID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">用例名称:</label>
	                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:94%;" class="form-control"  type="text"  id="remarkID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">创建人:</label>
	                    <div class="col-lg-9">
                           <input name="createBy" style="display:inline; width:94%;" class="form-control"  type="text"  id="createByID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">mesgFrom:</label>
	                    <div class="col-lg-9">
                           <input name="mesgFrom" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgFromID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                    </script>
</body>
</html>