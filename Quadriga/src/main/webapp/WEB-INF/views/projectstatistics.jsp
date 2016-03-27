<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	d3ProjectStatistics(data);
}
</script>

<h2 style = "text-align: right;">Project Statistics</h2>
<div id='stats'></div>

