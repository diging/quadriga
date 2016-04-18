<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
var labelCount = '${labelCount}'; 
var submittedNetworksData = '${submittedNetworksData}';
var approvedNetworksData = '${approvedNetworksData}';
var rejectedNetworksData = '${rejectedNetworksData}';
var workspaceData = '${workspaceData}';
document.body.onload = function()
{
	d3ProjectStatistics(labelCount);
	d3ProjectActivity(submittedNetworksData,"submit");
	d3ProjectActivity(approvedNetworksData,"approved");
	d3ProjectActivity(rejectedNetworksData,"rejected");
	d3ProjectActivity(workspaceData,"workspace");
}
</script> 
<h3>Project Statistics</h3>
<div id='stats'></div>
<h3>Submitted Networks </h3>
<div id='submit'></div>
<h3>Approved Networks</h3>
<div id='approved'></div>
<h3>Rejected Networks</h3>
<div id='rejected'></div>
<h3>Workspace</h3>
<div id='workspace'></div>

