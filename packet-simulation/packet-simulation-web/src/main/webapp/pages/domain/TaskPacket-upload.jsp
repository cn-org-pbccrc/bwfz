<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%--     <%@include file="/commons/statics.jsp"%> --%>
<script type="text/javascript" src="${contextPath}/lib/jquery.uploadify.min.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/lib/uploadify.css">
<script type="text/javascript">
	 $(document).ready(function(){
		//alert(taskId);
		$("#uploadify").uploadify({
			'uploader':'${pageContext.request.contextPath}/TaskPacket/uploadFile.koala?taskId='+taskId,
			'swf':'${pageContext.request.contextPath}/lib/uploadify.swf',
			'cancelImg':'${pageContext.request.contextPath}/lib/uploadify-cancel.png',
			'folder':'uploads',
			'queueID':'fileQueue',
			'queueSizeLimit':1,
			'auto':false,
			'fileObjName':'file',
			'multi':false,
			'simUploadLimit':1,
			'buttonText':'选择文件',
			'buttonCursor' : "hand",
			'checkExisting' : '${pageContext.request.contextPath}/TaskPacket/checkExisting.koala?taskId='+taskId,
			'fileSizeLimit' : '500MB',
// 	        'overrideEvents' : ['onSelectError' ],
// 	        'onSelectError':function(file, errorCode, errorMsg){
// 	            switch(errorCode) {
// 	            	case -100:
// 	                	alert("上传的文件数量已经超出系统限制的"+$('#uploadify').uploadify('settings','queueSizeLimit')+"个文件！");
// 	                    break;
// 	                case -110:
// 	                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#uploadify').uploadify('settings','fileSizeLimit')+"大小！");
// 	                    break;
// 	                case -120:
//                         alert("文件 ["+file.name+"] 大小异常！");
//                         break;
//                     case -130:
//                         alert("文件 ["+file.name+"] 类型不正确！");
//                         break;
// 	            }
// 	        },
			'onUploadSuccess':function(file,data,response){
				if(data=="上传成功!"){
					grid.message({
	                	type: 'success',
	                	content: data
	                });
					grid.data('koala.grid').refresh();
				}else{
					grid.message({
                		type: 'error',
                		content: data
                	});
				}
			},
		    'onUploadStart':function(d){
// 		    	var data = [{ name: 'fileName', value: file.name},
// 		    	            { name: 'taskId', value: taskId}
// 		    	            ];
// 	            $.post('${pageContext.request.contextPath}/TaskPacket/checkExisting.koala', data).done(function(result){
// 	            	if(result.success){
// 	            		alert("哈哈成功啦");
// 	            	}else{
// 	            		alert("羞羞失败啦");
//	            		$('#uploadify').uploadify('stop');
// 	            		var haha = function(){
// 	            			$('#uploadify').uploadify('upload');
// 						};
// 	    	            grid.confirm({
// 		                    content: '确定要覆盖报文吗?',
// 		                    callBack: haha
// 		                });
// 	            	}
// 	            });			
		    },
		    'onUploadError': function(file, errorCode, errorMsg, errorString) { 
// 		    	grid.message({
//                     type: 'error',
//                     content: errorString
//                 });
		    },
		    'onFallback' : function() {//检测FLASH失败调用  
		    	grid.message({
                    type: 'error',
                    content: "您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。"
                });     
		    },
		});
	}); 
</script>
</head>
<body>
<form name="form" method="POST" enctype="multipart/form-data">
	<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify" />
<!-- 	<p> -->
<!-- 		<a href="javascript:$('#uploadify').uploadify('upload')">上传</a> -->
<!-- 		<a href="javascript:$('#uploadify').uploadify('cancel')">取消上传</a> -->

<!-- 	</p> -->
</form>
</body>
</html>