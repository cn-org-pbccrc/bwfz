<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<div id="main">
	<div id="rootwizard">
		<div class="navbar">
	    	<div class="navbar-inner">
				<ul>
	  				<li><a href="#tab1" data-toggle="tab">选择模板</a></li>
					<li><a href="#tab2" data-toggle="tab">编辑记录</a></li>
				</ul>
	  		</div>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab1">
				<div class="selectProjectGrid"></div>
	    	</div>
	    	<div class="tab-pane" id="tab2">
				<div class="selectMissionGrid"></div>
			</div>
			<ul class="pager wizard">
				<li class="previous"><a href="javascript:;">上一步</a></li>
		  		<li class="next" id="next"><a href="javascript:;">下一步</a></li>
				<li class="finish" id="sub"><a href="javascript:;">完成</a></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
	var missionGrid;
	$(document).ready(function() {
		$('#rootwizard').bootstrapWizard({
			'tabClass' : 'nav nav-tabs', 
			onTabShow : function(tab, navigation, index) {
				var $total = navigation.find('li').length;
				var $current = index + 1;
				var $percent = ($current / $total) * 100;
				$('#rootwizard').find('.bar').css({
					width : $percent + '%'
				});
			},
			'onNext' : function(tab, navigation, index) {
				var items = $('#main').find('.selectProjectGrid').data('koala.grid').selectedRows();
				if (items.length == 0) {
					$('#main').find('.selectProjectGrid').message({
						type : 'warning',
						content : '请选择项目！'
					});
					return false;
				}
				if (items.length > 1) {
					$('#main').find('.selectProjectGrid').message({
						type : 'warning',
						content : '只能选择一个项目！'
					});
					return false;
				}
				var columns = [{ title: '任务名称', name: 'name', width: 100},
   	                           { title: '负责人', name: 'directorName', width: 70},
     	                       { title: '开始时间', name: 'startDate', width: 95},
     	                       { title: '结束时间', name: 'endDate', width: 95},
     	                       { title: '状态', name: 'status', width: 68},
     	                       { title: '描述', name: 'description', width: 100},
     	                       { title: '建立人', name: 'taskCreator', width: 70},
     	                       { title: '建立时间', name: 'taskCreatedTime', width: 95},];
				var projectId = items[0].id;
				if(!missionGrid || !($('#main').find('.selectMissionGrid').data('koala.grid'))){
					missionGrid = $('#main').find('.selectMissionGrid').grid({
	                    identity: 'id',
	                    columns: columns,
	                    url: contextPath + '/Mission/pagingQueryMissionsByCurrentUser/' + projectId +'.koala'
	                });				
				}else{
					missionGrid.getGrid().options.url= contextPath + '/Mission/pagingQueryMissionsByCurrentUser/' + projectId +'.koala';
					missionGrid.getGrid().refresh();
				}
				$('#main').find('.grid-body').attr('style', 'width: 1000px;');
				$('#main').find('.grid-table-body').attr('style', 'width: 1000px;');
                if(window.ActiveXObject){
                    if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
                        dialog.trigger('shown.bs.modal');
                    }
                }
			},
			'onTabClick' : function() {
				return false;
			}
		});
	});
</script>
</body>
</html>