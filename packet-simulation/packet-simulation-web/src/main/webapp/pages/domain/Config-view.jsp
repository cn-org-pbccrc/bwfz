<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form-horizontal">
	<div class="form-group">
		<label class="col-lg-3 control-label">配置类型:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="typeID"></p>
		</div>
	</div>

	<div class="form-group">
		<label class="col-lg-3 control-label">配置名称:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="nameID"></p>
		</div>
	</div>



	<div class="form-group">
		<label class="col-lg-3 control-label">http端口:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addHttpPortID"></p>
		</div>
	</div>

	<div class="form-group">
		<label class="col-lg-3 control-label">http地址:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addHttpAddressID"></p>
		</div>
	</div>


	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQ地址:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addMqAddressID"></p>
		</div>
	</div>

	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQ端口:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addMqPortID"></p>
		</div>
	</div>
	
	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQCCISD:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addMqCCISIDID"></p>
		</div>
	</div>
	
	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQ通道:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id=addMqChannelID></p>
		</div>
	</div>
	
	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQ队列管理器:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addMqQueueManagerID"></p>
		</div>
	</div>

	<div class="form-group" style="display: none;">
		<label class="col-lg-3 control-label">MQ队列:</label>
		<div class="col-lg-9">
			<p class="form-control-static" id="addMqQueueID"></p>
		</div>
	</div>
</form>
