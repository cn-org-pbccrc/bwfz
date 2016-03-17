<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${contextPath}/lib/Koala_Wizard/css/font-awesome.css" rel="stylesheet">
<link href="${contextPath}/lib/Koala_Wizard/css/koala_wizard.css" rel="stylesheet">
<script src="${contextPath}/lib/Koala_Wizard/js/Koala_Tab.js" type="text/javascript" ></script>
<script src="${contextPath}/lib/Koala_Wizard/js/Koala_Wizard.js" type="text/javascript" ></script>
<style> 
	.wizard {
		height: 750px;
        width: 1000px;
    }
    .wizard .items .page {
        width: 1000px;       
    }
#content li a{
  font-size:13px;
  font: "Microsoft Yahei", "宋体", verdana;
  padding:4px;
}
#main p {
  clear: left;
  margin: 0;
  padding: 0;
  padding-top: 5px;
}
#main p .rgt {
  float: left;
  width: 30%;
  text-align:right;
  font: "Microsoft Yahei", "宋体", verdana;
}
#main p .lft {
  font-weight:100;
  width: 30%;
  text-align:left;
  color:#acacac;
  margin-left:10px;
  float:left;
}
fieldset {
  border: 1px bolid #61B5CF;
  margin-top: 16px;
  padding: 10px;
  font: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
legend {
  font: bold 1em Arial, Helvetica, sans-serif;
  color: #999999;
  background-color: #FFFFFF;
}
</style>
<body>
	<div id="main">
<!-- 		<form class="form-horizontal"> -->
<%-- 		<form action="${pageContext.request.contextPath}/Mesg/add.koala" method="post"> --%>
			<div class="wizard">
				<ul class="nav nav-tabs">
				  <li id="first-step" class="active"><a href="#">第一步</a></li>
				  <li id="second-step"><a href="#" >第二步</a></li>
<!-- 				  <li id="last-step"><a href="#" >第三步</a></li> -->
				</ul>
		
				<div class="items">
					<div class="page">
<!-- 		                <h4>创建一个账户<br/><em>请填写您的注册账户信息，用于登录。</em></h4> -->
<!-- 						<p> -->
<!-- 							 <label>用户名：</label> -->
<!-- 							 <input type="text" class="form-control" name="userName" placeholder="输入6位以上字符"> -->
<!-- 						</p> -->
<!-- 						<p> -->
<!-- 							 <label>密码：</label> -->
<!-- 							 <input type="password" class="form-control" name="password" placeholder="输入6到10位字符"> -->
<!-- 						</p> -->
<!-- 						<p> -->
<!-- 							  <label>确认密码：</label> -->
<!-- 							  <input type="password" class="form-control" name="confirmPassword" placeholder="输入6到10位字符"> -->
<!-- 						</p> -->
						
											
                    <button class="btn btn-success" type="button" style="margin-top:10px" onclick="$('#searchQueryDiv').slideToggle('slow')"><span class="glyphicon glyphicon-flash"></span>高级搜索<span class="caret"></span></button>
					<div id="searchQueryDiv" style="margin-top:5px" hidden="true">
						<table border="0" cellspacing="0" cellpadding="0"> 							
  							<tr>
  								<td>
  									<label class="control-label" style="width:130px;float:left;">报文类型:</label>
            						<div style="margin-left:5px;margin-right: 25px;float:left;">
            							<input name="mesgType" class="form-control" type="text" style="width:160px;" id="mesgTypeID"  />
        							</div>
  									<label class="control-label" style="width:130px;float:left;">类型代码:</label>
            						<div style="margin-left:5px;margin-right: 25px;float:left;">
            							<input name="code" class="form-control" type="text" style="width:160px;" id="codeID"  />
        							</div>                        			
        							
<!--         							<label class="control-label" style="width:130px;float:left;">类型代码:</label> -->
<!--             						<div style="margin-left:5px;margin-right: 25px;float:left;"> -->
<!--             							<input name="code" class="form-control" type="text" style="width:160px;" id="codeID"  /> -->
<!--         							</div> -->
            					</td>
       							<td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:15px;" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  							</tr>
						</table>
					</div>
                    <div class="selectPacketGrid"></div>
    
		               <div class="btn_nav">
		                 	<button data-toggle="next" id="next" class="btn right btn-default">下一步&raquo;</button>
		               </div>
		            </div>
					<div class="page" style="width:1000px;height:700px;overflow-y:scroll;">
<!-- 		                <h4>填写联系信息<br/><em>请告诉我们您的联系方式。</em></h4> -->
<!-- 						<p> -->
<!-- 							 <label>E-mail：</label> -->
<!-- 							 <input type="text" class="form-control" name="email" placeholder="输入6位以上字符"> -->
<!-- 						</p> -->
<!-- 						<p> -->
<!-- 							 <label>QQ：</label> -->
<!-- 							 <input type="text" class="form-control" name="qq" placeholder="输入6到10位字符"> -->
<!-- 						</p> -->
<!-- 						<p> -->
<!-- 							  <label>手机号码：</label> -->
<!-- 							  <input type="text" class="form-control" name="phone" placeholder="输入6到10位字符"> -->
<!-- 						</p> -->
						<div class="form-group">
							<p>
 								<label class="rgt">备注 :</label>
 								<label class="lft">
 									<input name="remark" class="form-control"  type="text"  id="remarkID" />
 								</label> 								
 					    	</p>
 					    	<button class="right btn btn-danger" id="delete">删除字段</button>
 					    </div>
					    <div class="form-group">
	                        <div class="col-xs-10 g-mainc" style="width: 100%; margin: 0 auto;" id="content">
					        </div>
					    </div>

		                <div class="btn_nav">
		                    <button data-toggle="prev" class="left btn btn-default" >&laquo;上一步</button>
<!-- 		               	   <button data-toggle="next" class="right btn btn-default" >下一步&raquo;</button> -->
					        <button data-toggle="complete" class="right btn btn-success" id="sub">保存</button>
		                </div>					        
		            </div>
<!-- 					<div class="page"> -->
<!-- 		                 <h4>完成注册<br/><em>点击确定完成注册。</em></h4> -->
<!-- 				         <h4>Helloweba欢迎您！</h4> -->
<!-- 				         <p>请点击“确定”按钮完成注册。</p> -->
<!-- 				          <br/> -->
<!-- 				          <br/>  -->
<!-- 				           <br/> -->
<!-- 				          <br/>  -->
<!-- 				           <br/> -->
<!-- 				          <br/>    -->
<!-- 		               <div class="btn_nav"> -->
<!-- 		               	  <button data-toggle="prev" class="left btn btn-default" >&laquo;上一步</button> -->
<!-- 		               	  <button data-toggle="complete" class="right btn btn-success" id="sub">确定</button> -->
<!-- 		               	  <button data-toggle="first" class="right btn btn-success" id="gofirst" pageId="second-step">第一页</button> -->
<!-- 		               </div> -->
<!-- 		            </div> -->
				</div>
			</div>
<!-- 		</form> -->
	</div>
<script type="text/javascript">
	$(".wizard").wizard();
    $("#next").click(function(){
        var items = $('#main').find('.selectPacketGrid').data('koala.grid').selectedRows();
        if(items.length == 0){
        	$('#main').find('.selectPacketGrid').message({
                type: 'warning',
                content: '请选择报文模板！'
            });
        	return false;
        }
        if(items.length>1) {
        	$('#main').find('.selectPacketGrid').message({
                type: 'warning',
                content: '只能选择一个报文模板！'
            });
        	return false;
        }
        $("#content").empty();
    	$.get( '${pageContext.request.contextPath}/MesgType/getEditHtmlByMesgType/' + items[0].id + '.koala').done(function(data){
            $("#content").append(data.data);
     	});
    	$("#delete").click(function(){
    		$("button[class='btn btn-failure']").toggle();
    	});
	});
</script>
</body>
</html>