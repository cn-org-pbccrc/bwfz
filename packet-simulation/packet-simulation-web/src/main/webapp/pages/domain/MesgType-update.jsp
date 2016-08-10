<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" />
	<input type="hidden" id="createdByID" name="createdBy" />
	<input type="hidden" id="sortID" name="sort" />
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">报文类型:</label>
	                    <div class="col-lg-9">
                           <input name="mesgType" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgTypeID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">类型代码:</label>
	                    <div class="col-lg-9">
                           <input name="code" style="display:inline; width:94%;" class="form-control"  type="text"  id="codeID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">业务类型:</label>
	                    <div class="col-lg-9">
                           <input name="bizType" style="display:inline; width:94%;" class="form-control"  type="text"  id="bizTypeID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">统计标签:</label>
	                    <div class="col-lg-9">
                           <input name="countTag" style="display:inline; width:94%;" class="form-control"  type="text"  id="countTagID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">转换模板:</label>
	                    <div class="col-lg-9">
                           <input name="transform" style="display:inline; width:94%;" class="form-control"  type="text"  id="transformID" />
			    </div>
	</div>
				           <div class="form-group">
                    <label class="col-lg-3 control-label">基础模板:</label>
	                    <div class="col-lg-9">
                           <textarea name="xml" style="display:inline; width:94%;" class="form-control" id="xmlID">
						   </textarea>
				</div>
		</div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>