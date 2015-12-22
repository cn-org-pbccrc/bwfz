<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--     <script src="${contextPath}/lib/koala-datetimepicker/js/Koala_DateTimePicker.js" type="text/javascript" ></script> --%>
<%-- 	<link href="${contextPath}/lib/koala-datetimepicker/css/font-awesome.css" rel="stylesheet">  --%>
<%-- 	<link href="${contextPath}/lib/koala-datetimepicker/css/koala_datetimepicker.css" rel="stylesheet"> --%>
</head>
<body>
<form class="form-horizontal">
<!-- 	<input type="hidden" id="idID" name="id" />  -->
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="packIdID" name="packId" /> 
<!-- 	<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">报文名称:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="packetName" style="display:inline; width:94%;" class="form-control"  type="text"  id="packetNameID" /> -->
<!--                            <span class="required">*</span> -->
<!-- 			    </div> -->
<!-- 	</div> -->
<div class="form-group">
                    <label class="col-lg-3 control-label">文件格式版本号:</label>
	                    <div class="col-lg-9">
                           <input name="fileVersion" style="display:inline; width:94%;" class="form-control"  type="text"  id="fileVersionID" />
<!-- 			    <span class="required">*</span> -->
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
<div class="form-group">
                    <label class="col-lg-3 control-label">文件生成时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
                     </div>
                     </div>
<!-- 	           <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">项目开始时间:</label> -->
<!-- 	                 <div class="col-lg-9"> -->
<!--                     <div class="input-group date form_datetime" style="width:160px;float:left;" > -->
<!--                         <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID" > -->
<!--                         <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> -->
<!--                      </div> -->
<!-- 			    </div> -->
<!-- 	</div> -->
	         <div class="form-group">
                    <label class="col-lg-3 control-label">记录类型:</label>
	                    <div class="col-lg-9">
                           <input name="recordType" style="display:inline; width:94%;" class="form-control"  type="text"  id="recordTypeID" />
<!--                            <span class="required">*</span> -->
			    </div>
	</div>
	 <div class="form-group">
                    <label class="col-lg-3 control-label">数据类型:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="dataTypeID"></div>
                           
	                       <input type="hidden" id="dataTypeID_"  name="dataType"/>
<!-- 	                       <span class="required">*</span> -->
	                       </div>
		    </div>
		    
<!-- 				<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">数据类型:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="dataType" style="display:inline; width:94%;" class="form-control"  type="text"  id="dataTypeID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
<!-- 				<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">信息记录数:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="mesgNum" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgNumID" /> -->
<!-- 			    </div> -->
<!-- 	</div> -->
<!-- 				<div class="form-group"> -->
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
// });
var selectItems = {};
var contents = [{title:'请选择', value: ''}];
contents.push({title:'正常' , value:'0'});
contents.push({title:'删除且不需重报' , value:'1'});
contents.push({title:'删除且需重报' , value:'2'});
selectItems['dataTypeID'] = contents;
</script>
</body>
</html>