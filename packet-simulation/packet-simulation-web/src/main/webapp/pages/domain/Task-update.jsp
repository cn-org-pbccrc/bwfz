<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" />
	<input type="hidden" id="taskCreatorID" name="taskCreator" />
	<input type="hidden" id="packetNumID" name="packetNum" /> 
	<input type="hidden" id="taskStatusID" name="taskStatus" />
	<input type="hidden" id="taskFromID" name="taskFrom" />
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">任务名称:</label>
	                    <div class="col-lg-9">
                           <input name="taskName" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="taskNameID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">发送渠道:</label>
	                    <div class="col-lg-9">
                           <input name="sendChannel" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="sendChannelID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">定时发送:</label>
	                    <div class="col-lg-9">
                           <input name="setTime" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="setTimeID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">创建时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="taskCreatedTime" id="taskCreatedTimeID" dataType="Require">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
		<span class="required">*</span>	    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                    </script>
</body>
</html>