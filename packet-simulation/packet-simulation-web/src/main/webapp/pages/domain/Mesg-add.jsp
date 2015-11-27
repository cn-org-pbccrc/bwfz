<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <style>
#content li a{
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
  width: 22%;
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
                    <label class="col-lg-3 control-label">备注:</label>
	                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:80%;" class="form-control"  type="text"  id="remarkID" />
			    </div>
	</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">报文类型:</label>
	                    <div class="col-lg-9">
	                       <input type="hidden" name="packetId" id="packetIdID" />
                           <select onchange="addContent(this.value);" id="mesgTypeID" name="mesgType" style="display:inline; width:80%;" class="form-control"><option selected>请选择</option></select>
			    </div>
			    </div>
			    
<!-- 			    <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">优先级:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="mesgPriority" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgPriorityID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
<!-- 			    <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">传输方向:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="mesgDirection" style="display:inline; width:94%;" class="form-control"  type="text"  id="mesgDirectionID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
<!-- 			    <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">保留域:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!-- 	                    <input name="reserve" style="display:inline; width:94%;" class="form-control"  type="text"  id="reserveID" /> -->
<!-- 			    </div> -->
<!-- 			    </div> -->
			    
			    
			    <div class="form-group">
<!-- 			    <label class="col-lg-3 control-label">报文内容:</label> -->
	                    <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content">
					</div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    function addContent(id){
    	//alert(id)
    	$("#content").empty();
    	$.get( '${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/' + id + '.koala').done(function(data){
            $("#content").append(data.data);
     	});
    }
    
            </script>
</body>
</html>