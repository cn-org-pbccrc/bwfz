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
                           <input name="mesgType" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgTypeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">模版名称:</label>
	                    <div class="col-lg-9">
                           <input name="filePath" style="display:inline; width:94%;" class="form-control"  type="text"  id="filePathID" />
			    </div>
	</div>
					<div class="form-group">
                    <label class="col-lg-3 control-label">类型代码:</label>
	                    <div class="col-lg-9">
                           <input name="code" style="display:inline; width:94%;" class="form-control"  type="text"  id="codeID" />
			    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">统计标签:</label>
	                    <div class="col-lg-9">
                           <input name="countTag" style="display:inline; width:94%;" class="form-control"  type="text"  id="countTagID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">显示顺序:</label>
	                    <div class="col-lg-9">
                           <input name="sort" style="display:inline; width:94%;" class="form-control"  type="text"  id="sortID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                            </script>
</body>
</html>