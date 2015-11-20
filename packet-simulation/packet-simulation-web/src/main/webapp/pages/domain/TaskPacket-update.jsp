<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	
<!-- 	           <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">报文名称:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <div class="btn-group select" id="selectedPacketNameID"></div> -->
<!-- 	                       <input type="hidden" id="selectedPacketNameID_"  name="selectedPacketName" /> -->
<!-- 		    </div> -->
<!-- 	</div> -->
<!-- 	<div class="form-group"> -->
<!--                         <label class="col-lg-3 control-label">报文名称:</label> -->
<!--                         <div class="col-lg-9"> -->
<!--                             <div class="btn-group select" id="selectedPacketNameID"> -->
<!--                                 <button data-toggle="item" class="btn btn-default" type="button"> -->
<!--                                     选择报文 -->
<!--                                 </button> -->
<!--                                 <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button"> -->
<!--                                     <span class="caret"></span> -->
<!--                                 </button> -->
<!--                                 <input type="hidden" id="selectedPacketNameID_"  name="selectedPacketName" data-toggle="value"> -->
<!--                             </div><span class="required">*</span> -->
<!--                         </div> -->
<!--                     </div> -->
<!--              <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">加压:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="compression" style="display:inline; width:27%;" class="form-control"  type="text"  id="compressionID" /> -->
<!-- 		                </div> -->
<!-- 	         </div> -->
<!-- 	          <div class="form-group"> -->
<!--                     <label class="col-lg-3 control-label">加密:</label> -->
<!-- 	                    <div class="col-lg-9"> -->
<!--                            <input name="encryption" style="display:inline; width:27%;" class="form-control"  type="text"  id="encryptionID" /> -->
<!-- 		                </div> -->
<!-- 	         </div> -->
		           <div class="form-group">
                    <label class="col-lg-3 control-label">加压:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="compressionID"></div>
	                       <input type="hidden" id="compressionID_"  name="compression" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">加密:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="encryptionID"></div>
	                       <input type="hidden" id="encryptionID_"  name="encryption" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
            var contents = [{title:'请选择', value: ''}];
        selectItems['selectedPacketNameID'] = contents;
        var contents = [{title:'请选择', value: ''}];
        contents.push({title:'是' , value:'是'});
        contents.push({title:'否' , value:'否'});       
        selectItems['compressionID'] = contents;
        var contents = [{title:'请选择', value: ''}];
        contents.push({title:'是' , value:'是'});
        contents.push({title:'否' , value:'否'});        
        selectItems['encryptionID'] = contents;
        </script>
</body>
</html>