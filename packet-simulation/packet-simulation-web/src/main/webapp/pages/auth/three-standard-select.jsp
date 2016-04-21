<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-ZH">
<body>
    <div class="modal fade select-role">
         <style> 
            .select-role .modal-body {
                height: 520px;
            }
            .select-role .grid-table-body {
                height: 250px;
            }
            .select-role .modal-dialog {
                width: 1200px;
            }
        </style>      
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">选择三标</h4>
                    <button class="btn btn-success" type="button" style="margin-top:10px" onclick="$('#quickBatchQueryDiv').slideToggle('slow')"><span class="glyphicon glyphicon-flash"></span>快速批量<span class="caret"></span></button>
					<div id="quickBatchQueryDiv" style="margin-top:5px" hidden="true">
					<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
						<div class="form-group">
							<label class="control-label" style="width: 100px; float: left; padding-top:7px">批量起始行号:</label>
							<div style="margin-left: 5px;margin-right: 15px; float: left;">
								<input name="start" class="form-control" type="text"
									style="width: 160px;" id="startID" />
							</div>
							<label class="control-label" style="width: 100px; float: left; padding-top:7px">批量结束行号:</label>
							<div style="margin-left: 5px; float: left;">
								<input name="end" class="form-control" type="text"
									style="width: 160px;" id="endID" />
							</div>
						</div>
						</td>
						</tr>
						</table>
					</div>

				</div>
                



                <div class="modal-query"></div>
<!--                         	<div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">批量起始行号</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="start" style="display:inline; width:20%;" class="form-control"  type="text"  id="startID" /> -->
<!-- 			    </div> -->
<!-- 	</div>  -->
                <div class="modal-body" style="padding-left:45px; padding-right:65px;"><div class="selectThreeStandardGrid"></div></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-success" id="selectThreeStandardGridSave">保存</button>
                </div>
                <div class="modal-progress" align="right">
                </div>
            </div>
        </div>
    </div>
</body>
</html>