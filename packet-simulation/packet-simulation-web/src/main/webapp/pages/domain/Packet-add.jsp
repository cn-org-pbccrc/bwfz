<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- 	<script src="${contextPath}/lib/koala-datetimepicker/js/Koala_DateTimePicker.js" type="text/javascript" ></script> --%>
<%-- 	<link href="${contextPath}/lib/koala-datetimepicker/css/font-awesome.css" rel="stylesheet">  --%>
<%-- 	<link href="${contextPath}/lib/koala-datetimepicker/css/koala_datetimepicker.css" rel="stylesheet">  --%>
<%-- <link rel="stylesheet" href="${contextPath}/datetime/css/cxcalendar.css"> --%>
<!-- <style>
h1,h2,h3{font:bold 36px/1 "\5fae\8f6f\96c5\9ed1";}
h2{font-size:20px;}
h3{font-size:16px;}
fieldset{margin:1em 0;}
fieldset legend{font:bold 14px/2 "\5fae\8f6f\96c5\9ed1";}
a{color:#06f;text-decoration:none;}
a:hover{color:#00f;}

.wrap{width:600px;margin:0 auto;padding:20px 40px;border:2px solid #999;border-radius:8px;background:#fff;box-shadow:0 0 10px rgba(0,0,0,0.5);}
</style> -->
<%-- <script src="${contextPath}/datetime/jquery-1.9.1.js"></script> --%>
<%-- <script src="${contextPath}/datetime/calendar.js"></script> --%>
</head>
<body>
<form class="form-horizontal">
<!-- 				<div class="form-group">
                    <label class="col-lg-3 control-label">报文名称:</label>
	                    <div class="col-lg-9">
                           <input name="packetName" style="display:inline; width:94%;" class="form-control"  type="text"  id="packetNameID" />
                           <span class="required">*</span>
			    </div>
	</div> -->
	         <div class="form-group">
                    <label class="col-lg-3 control-label">文件格式版本号:</label>
	                    <div class="col-lg-9">
                           <input name="fileVersion" style="display:inline; width:94%;" class="form-control"  type="text"  id="fileVersionID" />
<!--                            <span class="required">*</span> -->
			    </div>
	</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">数据提供机构代码:</label>
	                    <div class="col-lg-9">
                           <input name="origSender" style="display:inline; width:94%;" class="form-control"  type="text"  id="origSenderID" />
<!-- 			    <span class="required">*</span> -->
			    </div>
	</div>
<!-- 	     <div class="form-group"> -->
<!-- 			<label class="col-lg-3 control-label">文件生成时间:</label> -->
<!-- 			<div class="col-lg-9"> -->
<!-- 				<div id="datetimepicker2" class="input-group"> -->
<!-- 					<input type="text" class="form-control" name="origSendDate" id="origSendDateID"></input>  -->
<!-- 					<span class="input-group-btn add-on input-append">  -->
<!-- 							<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i> -->
<!-- 					</span> -->
<!-- 					<span class="required">*</span> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- <p> -->
<!-- 		开始时间：<input name="origSendDate" id="origSendDateID" type="text" class="input_cxcalendar" style="width:200px;"> -->
<!-- </p> -->
<div class="form-group">
                    <label class="col-lg-3 control-label">文件生成时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
                     </div>
                     </div>
		         <div class="form-group">
                    <label class="col-lg-3 control-label">记录类型:</label>
	                    <div class="col-lg-9">
                           <input name="recordType" style="display:inline; width:94%;" class="form-control"  type="text"  id="recordTypeID" />
<!--                            <span class="required">*</span> -->
			    </div>
	</div>
<!-- 	<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">数据类型:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="dataType" style="display:inline; width:94%;" class="form-control"  type="text"  id="dataTypeID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
	
			           <div class="form-group">
                    <label class="col-lg-3 control-label">数据类型:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="dataTypeID"></div>                     
	                       <input type="hidden" id="dataTypeID_"  name="dataType"/>
<!-- 	                       <span class="required">*</span> -->
		    </div>
	</div>
	
	
<!-- 	<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">信息记录数:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="mesgNum" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgNumID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
<!-- 	<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">预留字段:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="reserve" style="display:inline; width:94%;" class="form-control"  type="text"  id="reserveID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
	</form>



<script type="text/javascript">
// $(function(){
// 	var datetimepicker = $('#datetimepicker2').datetimepicker({
// 		 language: 'zh-CN',
// 		 pickDate: true,
// 		 pickTime: true
// 	});
// 	//alert(datetimepicker.getDate());
// });
// $('.input_cxcalendar').each(function(){
// 			var a = new Calendar({
// 				targetCls: $(this),
// 				type: 'yyyy-mm-dd HH:MM:SS',
// 				wday:2
// 			},function(val){
// 				console.log(val);
// 			});
// 		});
    var selectItems = {};
    var contents = [{title:'请选择', value: ''}];
    contents.push({title:'正常' , value:'0'});
    contents.push({title:'删除且不需重报' , value:'1'});
    contents.push({title:'删除且需重报' , value:'2'});
    selectItems['dataTypeID'] = contents;
</script>
</body>
</html>