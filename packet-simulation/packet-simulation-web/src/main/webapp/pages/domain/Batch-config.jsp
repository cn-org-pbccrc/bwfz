<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade">
    <div class="modal-dialog" style="width:80%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">批量规则配置</h4>
            </div>
            <div class="modal-body">
                <div class="batchRule">
                    <div class="form-inline">
                        <div class="form-group">
                            <span class="control-label">记录类型:</span>
                            <div class="btn-group select" id="mesgTypeSelect"></div>
                        </div>
<!--                         <div class="form-group"> -->
<!--                             <span class="control-label">段:</span> -->
<!--                             <div class="btn-group select" id="paragraphSelect"> </div> -->
<!--                         </div> -->
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading"><span class="glyphicon glyphicon-th"></span>&nbsp;<label>添加批量规则</label></div>
                        <table class="table">
                            <tr>
                                <td>
                                    <div class="panel panel-default table-responsive">
                                        <div class="panel-heading">可选列</div>
                                        <table class="table table-responsive table-bordered grid query-condition">
                                            <tr>
                                                <td>
                                                    <div class="grid-body">
                                                        <div class="grid-table-head">
															<table class="table table-bordered">
																<th class="column-name">字段名</th>
																<th class="paragraph-name">所属段</th>
																<th class="operation">操作</th>
															</table>
														</div>
                                                        <div class="grid-table-body" style="overflow-x:hidden">
                                                            <table class="table table-bordered table-hover table-striped" id="staticQueryLeftTable">

                                                            </table>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td>
                                    <div class="panel panel-default table-responsive">
                                        <div class="panel-heading">已选列</div>
                                        <table class="table table-responsive table-bordered grid batchRuleSelected">
                                            <tr>
                                                <td>
                                                    <div class="grid-body">
                                                        <div class="grid-table-head">
                                                            <table class="table table-bordered" >
                                                                <tr>
																	<th class="column-name">字段名</th>
																	<th class="column-paragraph">所属段</th>
																	<th class="query-operation">规则类型</th>
																	<th class="value">规则属性</th>
																	<th class="visibility">是否启用</th>
																	<th class="delete-btn">操作</th>
																</tr>
                                                            </table>
                                                        </div>
                                                        <div class="grid-table-body" style="overflow-x:hidden">
                                                            <table class="table table-bordered table-hover table-striped staticQueryRightTable" id="staticQueryRightTable">

                                                            </table>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" id="generalQuerySave">保存</button>
            </div>
        </div>
    </div>
</div>
