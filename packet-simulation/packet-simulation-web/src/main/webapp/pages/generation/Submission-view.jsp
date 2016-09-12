<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form  class="form-horizontal">
	           <div class="form-group row">
					<div class="col-lg-6 form-group">
						<label class="col-lg-3 control-label">报文名称:</label>
						<div class="col-lg-9">
							<input name="name" class="form-control" type="text" dataType="Require" readonly="true" id="nameID" />
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
										<table class="table table-bordered table-hover table-striped staticQueryRightTable" style="width:950px" id="itemTable">
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
</form>