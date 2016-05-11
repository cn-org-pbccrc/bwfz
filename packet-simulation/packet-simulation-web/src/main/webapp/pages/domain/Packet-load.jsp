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
	 	uploadUrl:'${pageContext.request.contextPath}/Packet/load.koala?createdBy='+currentUserId,		 
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
//     	previewSettings:     {
//    	    	image: {width: "auto", height: "160px"},
//    	    	html: {width: "213px", height: "160px"},
//    	    	text: {width: "auto", height: "auto"},
//    	    	video: {width: "213px", height: "160px"},
//    	    	audio: {width: "213px", height: "80px"},
//    	    	flash: {width: "213px", height: "160px"},
//    	    	object: {width: "213px", height: "160px"},
//    	    	other: {width: "160px", height: "160px"}
//     	},
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