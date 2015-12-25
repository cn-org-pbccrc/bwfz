<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="organizationCodeID" name="organizationCode" /> 
	<input type="hidden" id="customerCodeID" name="customerCode" /> 
	<input type="hidden" id="createdDateID" name="createdDate" /> 
	<input type="hidden" id="versionID" name="version" /> 
	<input type="hidden" id="acctCodeID" name="acctCode" /> 
	<input type="hidden" id="conCodeID" name="conCode" /> 
	<input type="hidden" id="cccID" name="ccc" /> 
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">姓名:</label>
	                    <div class="col-lg-9">
                           <input name="name" style="display:inline; width:94%;" class="form-control"  type="text"  id="nameID" />
			    </div>
	</div>
			           <div class="form-group">
                    <label class="col-lg-3 control-label">证件类型:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="credentialTypeID"></div>
	                       <input type="hidden" id="credentialTypeID_"  name="credentialType" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">证件号:</label>
	                    <div class="col-lg-9">
                           <input name="credentialNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="credentialNumberID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                            var contents = [{title:'请选择', value: ''}];
        contents.push({title:'护照' , value:'2'});
        contents.push({title:'军官证' , value:'1'});
        contents.push({title:'身份证' , value:'0'});
        selectItems['credentialTypeID'] = contents;
        </script>
</body>
</html>