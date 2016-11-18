<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
</style>
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
    	<label class="col-lg-3 control-label">两标:</label>
		<div class="col-lg-9">
    		<input name="compression" style="display:inline; width:27%;" class="form-control"  type="text"  id="compressionID" />
			<button id="query" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>查询</button>
		</div>
	</div>
	<div id="right-box" style="height: 510px; border-right: 1px solid rgb(221, 221, 221); border-bottom: 1px solid rgb(221, 221, 221); border-radius: 0px; resize: none; overflow-y: scroll; outline: medium none; position: relative;">
        <div class="ro" id="json-target" style="padding: 0px 25px;"></div>
    </div>
</form>
<link rel="stylesheet" href="${contextPath}/query/index.css"/>
<script src="${contextPath}/query/hm.js"></script>
<script src="${contextPath}/query/hm_001.js"></script>
<%-- <script src="${contextPath}/query/jquery.min.js"></script> --%>
<script src="${contextPath}/query/jquery.message.js"></script><div style="clear: both;"></div><div id="jquery-beauty-msg"></div>
<script src="${contextPath}/query/jquery.json.js"></script>
<script src="${contextPath}/query/jquery.xml2json.js"></script>
<script src="${contextPath}/query/jquery.json2xml.js"></script>
<script src="${contextPath}/query/json2.js"></script>
<script src="${contextPath}/query/jsonlint.js"></script>
<%-- <script src="${contextPath}/query/bootstrap.min.js"></script> --%>
<script src="${contextPath}/query/jquery.numberedtextarea.js"></script>
<script type="text/javascript">
$('#query').click(function(){
	$.post('${pageContext.request.contextPath}/QueryRepository/query.koala').done(function(json){
		var content = json.data;
		//var content = $.trim('{"结果":{"结果列表":[{"条码":"UC-ER6053","INVECORPBORRCODE":"10000165-X","LOANCARDCODE":1101000001753875,"entName":"中国邮电器材集团公司","TipMSG":"贷款业务五级分类新增不良","FinanceName":"平安银行股份有限公司北京分行","GETTIME":"2014-10-13 00:00:00"},{"条码":"UC-ER6054","INVECORPBORRCODE":"10000165-X","LOANCARDCODE":1101000001753875,"entName":"中国邮电器材集团公司","TipMSG":"贷款业务五级分类新增不良","FinanceName":"平安银行股份有限公司北京分行","GETTIME":"2014-10-13 00:00:00"},{"条码":"UC-ER6055","INVECORPBORRCODE":"10000165-X","LOANCARDCODE":1101000001753875,"entName":"中国邮电器材集团公司","TipMSG":"贷款业务五级分类新增不良","FinanceName":"平安银行股份有限公司北京分行","GETTIME":"2014-10-13 00:00:00"}]},"interCode":"10000165-X","entName":"中国邮电器材集团公司","Msg":"查询成功","feeCount":1,"Code":"000000"}');
	    $('#json-target').html(content);
	    var result = '';
	    var current_json = '';
	    var current_json_str = '';
	    if (content!='') {
	        try{
	            current_json = jsonlint.parse(content);
	            current_json_str = JSON.stringify(current_json);
	            result = new JSONFormat(content,4).toString();
	        }catch(e){
	            result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
	            current_json_str = result;
	        }
	        $('#json-target').html(result);
	    }else{
	        $('#json-target').html('');
	    }
    });
});
</script>
</body>
</html>