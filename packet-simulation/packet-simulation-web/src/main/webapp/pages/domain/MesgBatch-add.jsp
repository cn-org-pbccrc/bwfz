<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
#xmlID li a{
	font-size:13px;
	 font: "Microsoft Yahei", "宋体", verdana;
	padding:4px;
}
form p {
  clear: left;
  margin: 0;
  padding: 0;
  padding-top: 5px;
}
form p .rgt {
  float: left;
  width: 26%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
form p .lft {
  font-weight:100;
  width: 30%;
  text-align:left;
  color:#acacac;
  margin-left:10px;
}
fieldset {
  border: 1px bolid #61B5CF;
  margin-top: 16px;
  padding: 10px;
  font: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
legend {
  font: bold 1em Arial, Helvetica, sans-serif;
  color: #999999;
  background-color: #FFFFFF;
}
</style>
</head>
<body>
<form class="form-horizontal">
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">版本号:</label>
	                    <div class="col-lg-9">
                           <input name="fileVersion" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="fileVersionID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">数据提供机构代码:</label>
	                    <div class="col-lg-9">
                           <input name="origSender" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="origSenderID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">文件生成时间:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID" dataType="Require">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">三标起始值:</label>
	                    <div class="col-lg-9">
                           <input name="start" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="startID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">文件数:</label>
	                    <div class="col-lg-9">
                           <input name="packetNum" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="packetNumID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">单个文件记录数:</label>
	                    <div class="col-lg-9">
                           <input name="mesgNum" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="mesgNumID" />
		<span class="required">*</span>	    </div>
	</div>
	<div class="form-group">
                    <label class="col-lg-3 control-label">选择记录:</label>
	                    <div class="col-lg-9">
	                    <select onchange="addContent(this.value);" id="mesgTypeID" name="mesgType" style="display:inline;width:75%;" class="form-control"><option selected>请选择</option></select>  
			    		<button type="button" class="btn btn-danger" id="delete" style="float:right;display:none;">删除字段</button>
			    </div>
	</div>
	<div class="form-group">
	                    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="xmlID">
					</div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    function addContent(id){
    	$("#xmlID").empty();
    	$("#delete").attr('style','float:right;display:block;');
    	$.get( '${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/' + id + '.koala').done(function(data){   
            $("#xmlID").append(data.data);
            var now = new Date($("#origSendDateID").val());
            var year = now.getFullYear();
            var month =(now.getMonth() + 1).toString();
            var day = (now.getDate()).toString();
            if (month.length == 1) {
                month = "0" + month;
            }
            if (day.length == 1) {
                day = "0" + day;
            }
            var dateTime = year + month +  day;
            $('#xmlID').find('[name="RptDate"]').attr('value', dateTime);
            $('#xmlID').find('[name="InfoUpDate"]').attr('value', dateTime);
            $('#xmlID').find('[name="FinanCode"]').attr('value', $("#origSenderID").val());
     	});
    } 
    $("#delete").click(function() {
		$("button[class='btn btn-failure']").toggle();
	});
                            </script>
</body>
</html>