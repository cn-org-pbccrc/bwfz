<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="/pages/common/header.jsp"%>
   <%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

<label class="col-lg-3 control-label">请选择需要解析的报文文件:</label>
<div class="col-lg-9" id="content">
<input type="file" id="fileID"/>
</div>
<input type="button" value="解析" onclick="getPath()"/>

<script type="text/javascript">
function getPath(){
	var thiz 	= $(this);
    var  mark 	= thiz.attr('mark');
	var path01=$("#fileID").val();
	alert(path01);
	mark=openTab('/pages/Analyze/Analyze-list.jsp','报文列表',mark,"analyze",{"path01":path01});
    if(mark){
        thiz.attr("mark",mark);
    }
}


</script>
	
</body>
</html>

