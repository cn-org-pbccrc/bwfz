<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<!-- 	<input type="hidden" id="idID" name="id" /> -->
	<input type="hidden" id="versionID" name="version" />
	<input type="hidden" id="dataTypeID" name="dataType" />
	<input type="hidden" id="recordTypeID" name="recordType" />
	 				<div class="form-group">
                    <label class="col-lg-3 control-label">报文名称:</label>
	                    <div class="col-lg-9">
                           <input name="packetName" style="display:inline; width:94%;" class="form-control"  type="text"  id="packetNameID" />
			    </div>
	</div>
<div class="form-group">
                    <label class="col-lg-3 control-label">文件格式版本号:</label>
	                    <div class="col-lg-9">
                           <input name="fileVersion" style="display:inline; width:94%;" class="form-control"  type="text"  id="fileVersionID" />
			    </div>
	</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">数据提供机构代码:</label>
	                    <div class="col-lg-9">
                           <input name="origSender" style="display:inline; width:94%;" class="form-control"  type="text"  id="origSenderID" />
			    </div>
	</div>
<div class="form-group">
                    <label class="col-lg-3 control-label">文件生成时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
                     </div>
                     </div>
<!-- 	         <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">记录类型:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="recordType" style="display:inline; width:94%;" class="form-control"  type="text"  id="recordTypeID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
	</form>
<script type="text/javascript">
</script>
</body>
</html>