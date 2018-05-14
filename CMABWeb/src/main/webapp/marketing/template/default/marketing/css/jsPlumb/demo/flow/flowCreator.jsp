<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程</title>
		<link rel="stylesheet" href="css/jsplumb.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/Common.js"></script>
		<!-- 引入jsPlumb需要用到的css和js -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/jquery/jquery-1.9.1.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/jquery/jsPlumb/css/jquery-ui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/jquery/jsPlumb/jquery-ui-1.9.2.custom.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/jquery/jsPlumb/jquery.jsPlumb-1.7.5-min.js"></script>
		<script type="text/javascript" src="script/flowCreator.js"></script>
	</head>

	<body class="zte-layout">
		<div data-options="region:'west',title:'流程元素',split:true" style="width: 300px; overflow-x: hidden;">
			<div id="searchForm" name="searchForm" style="padding: 5px;">
				<div name="start" class="dragitem tache rela start">start</div>
				<div name="next" class="dragitem tache rela next">next</div>
				<div name="stop" class="dragitem tache rela stop">stop</div>
			</div>
		</div>
		
		<div data-options="region:'center',title:'图形配置',split:true" style="padding: 5px; background: #eee;">
			<div style="padding:5px;border:1px solid #ddd;margin-bottom: 5px;">
				<a href="javascript:void(0)" class="zte-linkbutton" 
						data-options="plain:true,iconCls:'icon-cancel'" onclick="deleteObject()">删除</a>
			</div>
			
			<div class="container targetarea" id="holder">
		        
		    </div>
		</div>
	</body>
</html>