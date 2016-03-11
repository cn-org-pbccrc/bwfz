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
	<div class="form-group">
    	<label class="col-lg-3 control-label">随机生成三标数目:</label>
	    <div class="col-lg-9">
        	<input name="threeStandardNumber" style="display:inline; width:94%;" class="form-control"  type="text" id="threeStandardNumberID"/>
		</div>
	</div>
	<div class="form-group">
    	<label class="col-lg-3 control-label">并发线程数:</label>
	    <div class="col-lg-9">
        	<input name="threadNumber" style="display:inline; width:94%;" class="form-control"  type="text" id="threadNumberID"/>
		</div>
	</div>
</form>
</body>
</html>