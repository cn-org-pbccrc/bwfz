<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	
	          <div class="form-group">
                   <label class="col-lg-3 control-label">数据源类型</label>
                   <div class="col-lg-9">
				<div id="dataSourceType" class="btn-group select">
					<button data-toggle="item" class="btn btn-default" type="button">系统数据源</button>
					<button data-toggle="button"
						class="btn btn-default dropdown-toggle" type="button">
						<span class="caret"></span>
					</button>
					<input type="hidden" data-toggle="value" value="SYSTEM_DATA_SOURCE">
					<ul role="menu" class="dropdown-menu">
						<li undefined="" data-value="SYSTEM_DATA_SOURCE"><a href="#">系统数据源</a></li>
						<li class="selected" data-value="CUSTOM_DATA_SOURCE"><a
							href="#">自定义数据源</a></li>
					</ul>
				</div>
			</div>
               </div>

                 <div class="form-group">
                    <label class="col-lg-3 control-label">配置名称:</label>
	                    <div class="col-lg-9">
                           <input id="nameID" name="httpPort" style="display:inline; width:94%;" class="form-control"  type="text"  />
			    </div>
			</div>
			
	           <div class="form-group">
                    <label class="col-lg-3 control-label">http端口:</label>
	                    <div class="col-lg-9">
                           <input id="addHttpPortID" name="httpPort" style="display:inline; width:94%;" class="form-control"  type="text"  />
                                                       <span class="required">*</span>
                           
			    </div>
			</div>
			
			<div class="form-group">
                    <label class="col-lg-3 control-label">http地址:</label>
	                    <div class="col-lg-9">
                           <input id="addHttpAddressID" name="httpPort" style="display:inline; width:94%;" class="form-control"  type="text"  />
			               <span class="required">*</span>
			    </div>
			</div>

			
			 <div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQ地址:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqAddressID" />
                          <span class="required">*</span>
		    		</div>
			</div>
			 <div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQ端口:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqPortID" />
                          <span class="required">*</span>
		    		</div>
			</div>
			 <div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQCCISD:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqCCISIDID" />
                          <span class="required">*</span>
		    		</div>
			</div>
			 <div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQ通道:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqChannelID" />
                          <span class="required">*</span>
		    		</div>
			</div>
			 <div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQ队列管理器:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqQueueManagerID" />
                          <span class="required">*</span>
		    		</div>
			</div>
			<div class="form-group"  style="display: none;">
                   <label class="col-lg-3 control-label">MQ队列:</label>
                    <div class="col-lg-9">
                          <input name="httpAddress" style="display:inline; width:94%;" class="form-control"  type="text"  id="addMqQueueID" />
                          <span class="required">*</span>
		    		</div>
			</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    var fillSelectData  = function(){
    	//var dialog = $(this);
		var httpAddressID = $('#addHttpAddressID');
		var httpPortID = $('#addHttpPortID');

		var mqAddressID = $('#addMqAddressID');
		var mqPortID =  $('#addMqPortID');
		var mqCCISIDID = $('#addMqCCISIDID');
		var mqChannelID = $('#addMqChannelID');
		var mqQueueManagerID = $('#addMqQueueManagerID');
		var mqQueueID = $('#addMqQueueID'); 
		
		$('#dataSourceType').select({
			title: '选择数据源类型',
			contents: [
				{value: 'http', title: 'http'},
				{value: 'mq', title: 'mq', selected: true}
			]
		}).on('change', function(){
			//alert('the valuess is ' + $('#dataSourceType').getValue());
			//alert('this value is ' + $(this).getValue());
			//alert(httpPortID.text());
			//alert('the mq queue is ' + mqQueueID.html());
				if($(this).getValue() == 'http'){
				httpPortID.closest('.form-group').show();
				httpAddressID.closest('.form-group').show();

				mqAddressID.closest('.form-group').hide();
				mqPortID.closest('.form-group').hide();
				mqCCISIDID.closest('.form-group').hide();
				mqChannelID.closest('.form-group').hide();
				mqQueueManagerID.closest('.form-group').hide();
				mqQueueID.closest('.form-group').hide();
			}else{
				httpPortID.closest('.form-group').hide();
				httpAddressID.closest('.form-group').hide();

				mqAddressID.closest('.form-group').show();
				mqPortID.closest('.form-group').show();
				mqCCISIDID.closest('.form-group').show();
				mqChannelID.closest('.form-group').show();
				mqQueueManagerID.closest('.form-group').show();
				mqQueueID.closest('.form-group').show();
			}
		});
	};
	$(function() {
		//alert($(this));
		fillSelectData();
		//alert('the valuess is ' + $('#dataSourceType').getValue());
		//$('#dataSourceType').setValue('mq').trigger('change');
		//alert('change');
		$('#dataSourceType').setValue('http').trigger('change');
		//alert('the valuess is ' + $('#dataSourceType').getValue());
		
		
	});
                            </script>
</body>
</html>