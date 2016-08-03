<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css"
	rel="stylesheet" />
<link type="text/css"	href="${pageContext.servletContext.contextPath}/resources/css/projectstatistics.css" rel="stylesheet" />
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="https://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/d3/d3projectstatistics.js"></script>
<script type="text/javascript">
var labelCount = '${labelCount}'; 
var submittedNetworksData = '${submittedNetworksData}';
var approvedNetworksData = '${approvedNetworksData}';
var rejectedNetworksData = '${rejectedNetworksData}';
var workspaceData = '${workspaceData}';
document.body.onload = function()
{
	if(labelCount) {
		d3ProjectStatistics(labelCount);
	}
	if(submittedNetworksData) {
		d3ProjectActivity(submittedNetworksData,"submit");
	}
	if(approvedNetworksData) {
		d3ProjectActivity(approvedNetworksData,"approved");
	}
	if(rejectedNetworksData){
		d3ProjectActivity(rejectedNetworksData,"rejected");
	}
	if(workspaceData) {
		d3ProjectActivity(workspaceData,"workspace");
	}
}
</script> 
<div class="row">
<div class="col-md-12">
<h2>Project Statistics</h2>

<p>This page gives a brief overview of what has been happening in this project! </p>
</div>
</div>

<div class="row">
<div class="col-md-6">
<h3>Concept Statistics</h3>
<p>The diagram on the right shows what the most used concepts are in this project and how often they appear in the submitted Quadruples.
</div>
<div class="col-md-6">
<div id='stats'></div>
</div>
</div>

<div class="row">
<div class="col-md-6">
    <div id='submit'></div>
</div>
<div class="col-md-6">
   <h3>Submitted Networks </h3>
   <p>The diagram on the left shows how many networks have been submitted to this project in the past.
</div>
</div>
