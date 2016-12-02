<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
</style>
</head>
<body>
<!-- <div class="tab-content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="col-lg-12">
						<div id="ifChoose" class="btn-group select"></div>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<form class="form-inline" id="ifQuery">
					<button id="query" type="button" class="btn btn-primary">查询</button>
				</form>
			</div>
		</div>
		<div id="right-box" style="height: 510px; border-right: 1px solid rgb(221, 221, 221); border-bottom: 1px solid rgb(221, 221, 221); border-radius: 0px; resize: none; overflow-y: scroll; outline: medium none; position: relative;">
            <div class="ro" id="json-target" style="padding: 0px 25px;"></div>
        </div>
	</div>
</div> -->
<div class="form-inline">
	<div class="form-group">
		<span class="control-label">接口:</span>
        <div class="btn-group select" id="ifSelect"></div>
	</div>
    <div class="form-group">
        <span class="control-label">方法:</span>
        <div class="btn-group select" id="mtSelect"> </div>
    </div>
    <div class="form-group">
		<form class="form-inline" id="ifQuery">
			<button id="query" type="button" class="btn btn-primary">查询</button>
		</form>
	</div>
</div>
<div id="right-box" style="height: 510px; border-right: 1px solid rgb(221, 221, 221); border-bottom: 1px solid rgb(221, 221, 221); border-radius: 0px; resize: none; overflow-y: scroll; outline: medium none; position: relative;">
    <div class="ro" id="json-target" style="padding: 0px 25px;"></div>
</div>

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
var ifContents = [];
var selectedInfo;
var info = {};
$.ajax({
	url : '${pageContext.request.contextPath}/QueryRepository/getInterfaceList.koala',
	success : function(data){
		$.each(data.data, function(k, v){
			ifContents.push({title : v.id, value : v.id});
		});
		$('#ifSelect').data('koala.select').options.contents = ifContents;
		$('#ifSelect').data('koala.select').setItems(ifContents);

     }
});
$('#ifSelect').select({
	title: '请选择查询接口',
	contents : ifContents
}).on('change', function(){
	$('#mtSelect').setSelectItems([]);
	var interfaceId = $(this).getValue();
	var url = '${pageContext.request.contextPath}/QueryRepository/getMethodList.koala?interfaceId=' + interfaceId;
	$.get(url).done(function(data){
		if(data.result){
			$('#ifSelect').message({
				type: 'error',
				content: data.errorMessage
			});
			$(this).find('[data-toggle="item"]').text('请选择');
			return;
		}
		var mtContents = [];
		info.data = data.data;
		$.each(data.data, function(k, v){
			mtContents.push({title : v.title, value : v.id});
		});
		$('#mtSelect').setSelectItems(mtContents);
		$('#mtSelect').setValue(mtContents[0].value);
	});
});
$('#mtSelect').select({
	title : '请选择查询方法'
}).on('change', function(){
	var choosenTitle = $(this).getValue();
	$.each(info.data, function(k, v){
		if(info.data[k].id == choosenTitle){
			selectedInfo = info.data[k];
		}	
	});
	$('#ifQuery').children('.form-group').remove();
	if(selectedInfo.queryCondition){
		$.each(selectedInfo.queryCondition, function(k,v){
			$('#ifQuery').prepend(
				'<div class="form-group"><div class="input-group"><div class="input-group-addon">' + v.title + '</div><input id="param" name= ' + v.name +' type="text" class="form-control" placeholder=""/></div></div>'
			);
		});
	}
});
</script>
</body>
</html>