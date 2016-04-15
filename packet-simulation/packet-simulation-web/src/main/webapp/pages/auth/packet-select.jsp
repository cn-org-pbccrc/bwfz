<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-ZH">
<style>
	.select-role .modal-body {
		height: 460px;
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
                    <h4 class="modal-title">选择报文</h4>
                    <button class="btn btn-success" type="button" style="margin-top:10px" onclick="$('#searchQueryDiv').slideToggle('slow')"><span class="glyphicon glyphicon-flash"></span>高级搜索<span class="caret"></span></button>
					<div id="searchQueryDiv" style="margin-top:5px" hidden="true">
						<table border="0" cellspacing="0" cellpadding="0">
  							<tr>
    							<td>
          							<div class="form-group">
           								<label class="control-label" style="width:130px;float:left;">报文名称:</label>
            							<div style="margin-left:5px;margin-right: 25px;float:left;">
            								<input name="packetName" class="form-control" type="text" style="width:160px;" id="packetNameID"  />
        								</div>          
          								<label class="control-label" style="width:130px;float:left;">文件格式版本号:</label>
            							<div style="margin-left:5px;margin-right: 25px;float:left;">
            								<input name="fileVersion" class="form-control" type="text" style="width:160px;" id="fileVersionID"  />
        								</div>
                      					<label class="control-label" style="width:130px;float:left;">数据提供机构代码:</label>
            							<div style="margin-left:5px;margin-right: 25px;float:left;">
            								<input name="origSender" class="form-control" type="text" style="width:160px;" id="origSenderID"  />
        								</div>
            						</div>
  								</td>
  							</tr>
  							<tr>
  								<td>
  									<div class="form-group">
                 						<label class="control-label" style="width:130px;float:left;">记录类型:</label>
            							<div style="margin-left:5px;margin-right: 25px;float:left;">
            								<input name="recordType" class="form-control" type="text" style="width:160px;" id="recordID"  />
        								</div>
										<label class="control-label" style="width:130px;float:left;">文件生成时间:</label>
           								<div style="margin-left:5px;margin-right: 25px;float:left;">
            								<div class="input-group date form_datetime" style="width:160px;float:left;" >
                								<input type="text" class="form-control" style="width:160px;" name="origSendDate" id="origSendDateID_start" >
                								<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            								</div>
            								<div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            								<div class="input-group date form_datetime" style="width:160px;float:left;" >
                								<input type="text" class="form-control" style="width:160px;" name="origSendDateEnd" id="origSendDateID_end" >
                								<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            								</div>
       									</div>       									
									</div>
  								</td>
  								<td style="vertical-align: bottom;"><button id="search2" type="button" style="position:relative; margin-left:15px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  							</tr>
						</table>
					</div>	   
                </div>
                <div class="modal-query"></div>
                <div class="modal-body" style="padding-left:45px; padding-right:65px;"><div class="selectPacketGrid"></div></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-success" id="selectPacketGridSave">保存</button>
                </div>
        	</div>
        </div>
    </div>
</body>
</html>
