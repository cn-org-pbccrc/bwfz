<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="packetIdID" name="packetId" /> 
	<input type="hidden" id="mesgTypeID" name="mesgType" />
	<input type="hidden" id="mesgIdID" name="mesgId" />
	<input type="hidden" id="contentID" name="content" />
		
	           <div class="form-group">
                    <label class="col-lg-3 control-label">批量添加数目:</label>
	                    <div class="col-lg-9">
                           <input name="batchNumber" style="display:inline; width:94%;" class="form-control"  type="text" id="batchNumberID" dataType="Regex"  validateExpr="/^[0-9]*$/"/>
		                </div>
	           </div>
</form>
</body>
</html>