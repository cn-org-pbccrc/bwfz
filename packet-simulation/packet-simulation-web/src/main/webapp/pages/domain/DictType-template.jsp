<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="k" uri="http://www.openkoala.com/token"%>

<div class="modal fade" id="departmentAdd">
    <style>
        .modal-dialog  input {
            width: 300px;
        }
        .modal-dialog .modal-body {
            padding: 20px 10px 10px!important;
        }
        .modal-dialog .modal-body .help-block {
            display: none;
            font-weight: normal;
            margin-bottom: 0;
        }
        .modal-dialog .modal-footer {
            margin-top: 0;
        }
        .modal-dialog .col-lg-9 > input, .col-lg-9 > span {
            display: inline;
        }
        .modal-dialog .col-lg-9 .form-control {
            width: 85%;
        }
        .modal-dialog .col-lg-9 > span {
            position: relative;
            top: 3px;
            margin-left: 5px;
            color: red;
        }
        .modal-dialog  .checker {
            padding-top: 5px;
        }
    </style>
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">添加下级部门</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                	<k:token/>
                	
                	<div class="form-group">
                        <label class="col-lg-3 control-label">上级字典类型Id:</label>
                        <div class="col-lg-9">
                            <input type="text" class="form-control" id="upDictId" maxlength="30"><span class="required">*</span>
                        </div>
                        </div>
                	
                    <div class="form-group">
                        <label class="col-lg-3 control-label">数据字典类型名称:</label>
                        <div class="col-lg-9">
                            <input type="text" class="form-control" id="dictName" maxlength="30">
                        </div>
                    </div>

					<div class="form-group">
						<label class="col-lg-3 control-label">字典分类id:</label>
						<div class="col-lg-9">
							<input name="dictClassify" 
								class="form-control" type="text" id="dictClassifyID" maxlength="30" />
						</div>
					</div>

<!-- 					<div class="form-group"> -->
<!-- 						<label class="col-lg-3 control-label">修改用户id:</label> -->
<!-- 						<div class="col-lg-9"> -->
<!-- 							<input name="mendUserId" style="display: inline; width: 94%;" -->
<!-- 								class="form-control" type="text" id="mendUserIdID" /> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="form-group">
						<label class="col-lg-3 control-label">备注:</label>
						<div class="col-lg-9">
							<input name="remark" 
								class="form-control" type="text" id="remarkID"  maxlength="30"/>
						</div>
					</div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" id="save">保存</button>
            </div>
        </div>
    </div>
</div>