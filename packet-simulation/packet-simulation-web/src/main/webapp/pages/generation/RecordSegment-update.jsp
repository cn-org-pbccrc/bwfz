<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">段名称:</label>
	                    <div class="col-lg-9">
                           <input name="segName" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="segNameID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">段标:</label>
	                    <div class="col-lg-9">
                           <input name="segMark" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="segMarkID" />
		<span class="required">*</span>	    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">描述:</label>
	                    <div class="col-lg-9">
                           <input name="describe" style="display:inline; width:94%;" class="form-control"  type="text"  id="describeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">长度:</label>
	                    <div class="col-lg-9">
                           <input name="segLength" style="display:inline; width:94%;" class="form-control"  type="text"  id="segLengthID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">出现次数:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="appearTimesID"></div>
	                       <input type="hidden" id="appearTimesID_"  name="appearTimes" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">状态:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="stateID"></div>
	                       <input type="hidden" id="stateID_"  name="state" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                            var contents = [{title:'请选择', value: ''}];
        contents.push({title:'1：n' , value:'3'});
        contents.push({title:'0：n' , value:'2'});
        contents.push({title:'0：1' , value:'1'});
        contents.push({title:'1：1' , value:'0'});
        selectItems['appearTimesID'] = contents;
                var contents = [{title:'请选择', value: ''}];
        contents.push({title:'条件选择' , value:'C'});
        contents.push({title:'必选' , value:'M'});
        contents.push({title:'可选' , value:'O'});
        selectItems['stateID'] = contents;
        </script>
</body>
</html>