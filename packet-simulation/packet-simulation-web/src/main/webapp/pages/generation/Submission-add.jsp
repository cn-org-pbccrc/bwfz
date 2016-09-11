<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">报文名称:</label>
	                    <div class="col-lg-9">
                           <input name="Name" style="display:inline; width:94%;" class="form-control"  type="text"  id="NameID" />
			    </div>
	</div>
	<div class="form-group">
                            <label class="col-lg-3 control-label">报文类型:</label>
                            <div class="col-lg-9">
                                <div class="btn-group select" id="select-mesg">
                                    <button data-toggle="item" class="btn btn-default" type="button">
                                        选择类型
                                    </button>
                                    <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
                                        <span class="caret"></span>
                                    </button>
                                    <input type="hidden" data-toggle="value" name="recordTypeId" id="recordTypeIdID">
                                </div>
                            </div>
                        </div>   
	</form>
<script type="text/javascript">
    var selectItems = {};
            </script>
</body>
</html>