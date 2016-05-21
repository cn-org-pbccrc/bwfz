<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
.batchRuleSelected  .v-name {
	width: 10%;
}
.batchRuleSelected  .v-type {
	width: 12%;
}

.batchRuleSelected .v-properties {
	width: 68%;
}


.batchRuleSelected .delete-btn {
	width: 10%;
}
</style>
<body>
	<form class="form-horizontal">
		<div class="form-group">
			<label class="col-lg-3 control-label" style="width:90px">模板:</label>
			<div class="col-lg-10">
				<textarea name="templete" style="display: inline; width: 100%; height: 50px; overflow: hidden;"	class="form-control" required="true" placeholder="请输入字段值模板" id="templeteID"></textarea>
			</div>
		</div>
			<div class="panel panel-default table-responsive">
				<div class="panel-heading">已选列<button data-role="addRow"  class="btn btn-primary" style="padding:2px 4px;float:right;" type="button"><span class="glyphicon glyphicon-plus"></span></button></div>
				<table	class="table table-responsive table-bordered grid batchRuleSelected">
					<tr>
						<td>
							<div class="grid-body">
								<div class="grid-table-head"  style="width:100%">
									<table class="table table-bordered" >
										<tr>
											<th class="v-name">变量</th>
											<th class="v-type">变量类型</th>
											<th class="v-properties">变量属性</th>
											<th class="delete-btn">操作</th>
										</tr>
									</table>
								</div>
								<div class="grid-table-body" style="overflow-x: hidden">
									<table
										class="table table-bordered table-hover table-striped staticQueryRightTable"
										id="propTable">
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
		</div>
	</form>
	<script type="text/javascript">
		
	</script>
</body>
</html>