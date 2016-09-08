<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
.recordItemConfig  .v-itemId {
	width: 7%;
}
.recordItemConfig  .v-itemName {
	width: 17%;
}
.recordItemConfig  .v-itemType {
	width: 10%;
}
.recordItemConfig .v-itemLength {
	width: 8%;
}
.recordItemConfig  .v-itemLocation {
	width: 10%;
}
.recordItemConfig  .v-itemDesc {
	width: 19%;
}
.recordItemConfig .v-state {
	width: 13%;
}
.recordItemConfig .v-itemValue {
	width: 16%;
}
</style>
<body>
	<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
		<div class="form-group row">
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">段名称:</label>
				<div class="col-lg-9">
					<input name="segName" 
						class="form-control" readonly="true" type="text" dataType="Require" id="segNameID" />
					<span class="required">*</span>
				</div>
			</div>
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">段标:</label>
				<div class="col-lg-9">
					<input name="segMark" 
						class="form-control" readonly="true" type="text" dataType="Require" id="segMarkID" />
					<span class="required">*</span>
				</div>
			</div>
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">描述:</label>
				<div class="col-lg-9">
					<input name="segDescID" 
						class="form-control" readonly="true" type="text" id="segDescID" />
				</div>
			</div>
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">长度:</label>
				<div class="col-lg-9">
					<input name="segLength" readonly="true"
						class="form-control" type="text" id="segLengthID" />
				</div>
			</div>
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">状态:</label>
				<div class="col-lg-9">
					<div class="btn-group select" id="stateID"></div>
					<input type="hidden" id="stateID_" name="state" dataType="Require" />
					<span class="required">*</span>
				</div>
			</div>
			<div class="col-lg-6 form-group">
				<label class="col-lg-3 control-label">出现次数:</label>
				<div class="col-lg-9">
					<div class="btn-group select" id="appearTimesID"></div>
					<input type="hidden" id="appearTimesID_" name="appearTimes"
						dataType="Require" /> <span class="required">*</span>
				</div>
			</div>

		</div>
		<div class="panel panel-default table-responsive">
				<div class="panel-heading">数据项</div>
				<table	class="table table-responsive table-bordered grid recordItemConfig">
					<tr>
						<td>
							<div class="grid-body">
								<div class="grid-table-head"  style="width:950px">
									<table class="table table-bordered" >
										<tr>
											<th class="v-itemId">标识符</th>
											<th class="v-itemName">数据项名称</th>
											<th class="v-itemType">类型</th>
											<th class="v-itemLength">长度</th>
											<th class="v-itemLocation">位置</th>
											<th class="v-itemDesc">描述及代码表</th>
											<th class="v-state">状态</th>
											<th class="v-itemValue">值</th>
										</tr>
									</table>
								</div>
								<div class="grid-table-body" style="overflow-x: hidden;width:950px">
									<table
										class="table table-bordered table-hover table-striped staticQueryRightTable" style="width:950px"
										id="itemTable">
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
		</div>
<!-- 		<div id="context-menu"> -->
<!-- 			<ul class="dropdown-menu" role="menu"> -->
<!-- 				<li><a tabindex="-1" href="#">插入数据项</a></li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
	</form>
	<script type="text/javascript">
		
	</script>
</body>
</html>