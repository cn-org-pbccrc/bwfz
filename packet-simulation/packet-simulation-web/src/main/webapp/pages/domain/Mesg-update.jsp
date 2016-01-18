<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="packetIdID" name="packetId" />
	<input type="hidden" id="mesgTypeID" name="mesgType" />
	<input type="hidden" id="remarkID" name="remark" />
		<div class="form-group">
                    <label class="col-lg-3 control-label">备注:</label>
	                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:80%;" class="form-control"  type="text"  id="remarkID" />
			    </div>
	</div>
<!-- 				<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">优先级:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="mesgPriority" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgPriorityID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
<!-- 			    <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">传输方向:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="mesgDirection" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgDirectionID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
<!-- 			    <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">保留域:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="reserve" style="display:inline; width:94%;" class="form-control"  type="text"  id="reserveID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
			    
	           <div class="form-group">
<!-- 			    <label class="col-lg-3 control-label">报文内容:</label> -->
	                    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="contentID">
					</div>
					</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
            </script>
</body>
</html>