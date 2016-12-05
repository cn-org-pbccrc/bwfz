<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
.generalQuery .form-inline {
    padding-bottom: 5px;
}
.generalQuery .form-inline .form-group {
    margin-left: 25px;
}
.generalQuery .form-inline .form-group .control-label {
    margin-right: 5px;
}
.generalQuery  .required {
    position: relative;
    top: 4px;
    margin-left: 3px;
    color: red;
}
.generalQuery .form-inline .form-group .btn-group .queryName {
    display: inline;
    width: 200px;
}
.generalQuery .table {
    border-width: 0;
    text-align: center;
}
.generalQuery .table th {
    text-align: center;
}
.generalQuery  a {
    cursor: pointer!important;
}
.generalQuery  .panel {
    margin-top: 5px;
    margin-bottom: 0;
}
.recordItemConfig .grid-table-body {
    overflow-y: auto;
    overflow-x: hidden;
}
.recordItemConfig  .v-itemId {
	width: 5%;
}
.recordItemConfig  .v-itemName {
	width: 14%;
}
.recordItemConfig  .v-itemType {
	width: 5%;
}
.recordItemConfig .v-itemLength {
	width: 5%;
}
.recordItemConfig  .v-itemLocation {
	width: 6%;
}
.recordItemConfig  .v-itemDesc {
	width: 28%;
}
.recordItemConfig .v-state {
	width: 7%;
}
.recordItemConfig .v-itemValue {
	width: 17%;
}
.recordItemConfig .v-itemPrompt {
	width: 13%;
}
</style>
<body>
<div class="generalQuery">
    <div class="form-inline">
	    <div class="form-group">
	        <span class="control-label">段名称:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="segNameID"></p>
	        </div>
	    </div>
	    <div class="form-group">
	    	<span class="control-label">段标:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="segMarkID"></p>
	        </div>
	    </div>
	    <div class="form-group">
	    	<span class="control-label">长度:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="segLengthID"></p>
	        </div>
	    </div>
	    <div class="form-group">
	    	<span class="control-label">状态:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="stateID"></p>
	        </div>
	    </div>
	    <div class="form-group">
	    	<span class="control-label">出现次数:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="appearTimesID"></p>
	        </div>
	    </div>
	    <div class="form-group">
	    	<span class="control-label">描述:</span>
	        <div class="btn-group">
	        	<p class="form-control-static" id="segDescID"></p>
	        </div>
	    </div>
	</div>
	
	<div class="panel panel-default table-responsive">
		<div class="panel-heading">数据项</div>
		<table class="table table-responsive table-bordered grid recordItemConfig">
			<tr>
				<td>
					<div class="grid-body">
						<div class="grid-table-head">
							<table class="table table-bordered">
								<tr>
									<th class="v-itemId">标识符</th>
									<th class="v-itemName">数据项名称</th>
									<th class="v-itemType">类型</th>
									<th class="v-itemLength">长度</th>
									<th class="v-itemLocation">位置</th>
									<th class="v-itemDesc">描述及代码表</th>
									<th class="v-state">状态</th>
									<th class="v-itemValue">值</th>
									<th class="v-itemPrompt">提示</th>
								</tr>
							</table>
						</div>
						<div class="grid-table-body">
							<table class="table table-bordered table-hover table-striped staticQueryRightTable" id="itemTable"></table>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
</script>
</body>
</html>