<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="nameID" name="name" /> 
	<input type="hidden" id="serialNumberID" name="serialNumber" /> 
	<input type="hidden" id="frontPositionID" name="frontPosition" /> 
	<input type="hidden" id="fileNumberID" name="fileNumber" /> 
	<input type="hidden" id="lotIdID" name="lotId" /> 
	<input type="hidden" id="submissionFromID" name="submissionFrom" /> 
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">加压:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="compressionID"></div>
	                       <input type="hidden" id="compressionID_"  name="compression" dataType="Require"/>
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">加密:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="encryptionID"></div>
	                       <input type="hidden" id="encryptionID_"  name="encryption" dataType="Require"/>
		<span class="required">*</span>	    </div>
	</div>
	</form>
<script type="text/javascript">
</script>
</body>
</html>