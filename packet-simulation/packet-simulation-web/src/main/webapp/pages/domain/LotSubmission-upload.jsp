<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$("#input-id").fileinput({
	language: 'zh', //设置语言
 	previewFileType:'any',
 	uploadUrl:'${pageContext.request.contextPath}/LotSubmission/uploadFile.koala?lotId='+lotId,
 	allowedPreviewTypes: ['text'],
	allowedFileTypes: ['text'],
	allowedFileExtensions:  ['txt', 'csv'],
 	showUpload: true, //是否显示上传按钮
	showCaption: true,//是否显示标题
	layoutTemplates : {
		zoom: ''    	      
	},
	previewTemplates:{
		text:'<div class="file-preview-frame{frameClass}" id="{previewId}" data-fileindex="{fileindex}">\n' +
  	         '<pre class="file-preview-text" title="{caption}" style="width:{width};height:{height};overflow-x:auto;">{data}</pre>\n' +
  	         '{zoom}\n' +
  	         '{footer}\n' +
  	         '</div>'
	},
	maxFileSize: 10240
});
</script>
</head>
<body>
<form name="form" method="POST" enctype="multipart/form-data">
	<div id="fileQueue"></div>
	<input id="input-id" type="file" class="file" data-preview-file-type="text" />
</form>
</body>
</html>