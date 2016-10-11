<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-ZH">
<style>
	.select-role .modal-body {
		height: 430px;
	}
	
	.select-role .grid-table-body {
		height: 250px;
	}
	
	.select-role .modal-dialog {
		width: 1200px;
	}
</style>
<body>
    <div class="modal fade select-role">
             
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">选择报文类型</h4>
                </div>
                <div class="modal-query"></div>
                <div class="modal-body" style="padding-left:45px; padding-right:65px;"><div class="selectRecordTypeGrid"></div></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-success" id="selectRecordTypeGridSave">保存</button>
                </div>
        	</div>
        </div>
    </div>
</body>
</html>
