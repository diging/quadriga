<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css"
	rel="stylesheet" />
<link type="text/css"	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/projectstatistics.css" rel="stylesheet" />
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/d3projectstatistics.js"></script>
<script type="text/javascript">
var data = '${jsonstring}'; 
document.body.onload = function()
{
	d3projectstatistics(data);
}
</script>
</head>
<body>
	<h2>Project Statistics</h2>
	<div id='stats'></div>
</body>
</html>

