<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <style>
        ul,li{list-style:none;margin:0;padding:0;}
        .box{ width: 800px; margin: auto;}
        .nav_link{ margin-top: 15px; overflow: hidden;}
        .nav_link span{float: left; line-height: 30px; font-size: 20px; font-weight:bold;}
        .nav_link li{ float: left; margin-right: 25px; margin-left:15px; border-radius:15px; width: 130px; height: 30px; background-color: #555; line-height: 30px; text-align: center;}
        .nav_link li a{ color: #fff; text-decoration: none;}
        .nav_link li a:hover{ color:#84ff00;}
        .main_ten{overflow: hidden; zoom:1; margin-top: 50px;}
        .main_ten li{ float: left; border-right:1px solid #ccc; width: 230px; height: 260px; padding: 20px 15px; text-align: center;}
        .main_ten li.last{border: none;}
        .main_ten li p{ margin-top: 10px; line-height: 25px;}
    </style>
</head>
<body>
<div class="box">
    <div class="nav_link">
       <%-- <ul>
            <span>快速导航：</span>
            <li><a href="http://openkoala.org/" target="_blank">Koala官网</a></li>
            <li><a href="http://dev.openkoala.org/?/article/4" target="_blank">开发文档</a></li>
            <li><a href="http://dev.openkoala.org/" target="_blank">开发者社区</a></li>
            <li><a href="http://dev.openkoala.org/?/explore/category-4" target="_blank">提交BUG</a></li>
        </ul> --%>
    </div>
    <div class="main_ten">
        <h4>核心功能：</h4>
        <ul>
            <li><img src="images/lyjm_icon01.png"/><p>报文组装：报文模板管理、任务管理、修改报文、生成报文包、加密加压生成报文文件。</p></li>
            <li><img src="images/openci_icon01.png"/><p>报文解析：基于XML解析、展示报文内容。</p></li>
            <li class="last"><img src="images/koalaui_icon01.png"/><p>报文发送：支持不同网络协议的发送方式</p> </li>
        </ul>
    </div>
</div>
</body>
</html>