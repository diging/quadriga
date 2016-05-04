<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<c:choose>
    <c:when test="${not empty labelCount}">
		<h3>Project Statistics</h3>
		<div id='stats'></div>
    </c:when>
    <c:otherwise>
		<h3>No project statistics data to show</h3>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty submittedNetworksData}">
    	<h3>Submitted Networks </h3>
        <div id='submit'></div>
    </c:when>
    <c:otherwise>
		<h3>No submitted networks data to show</h3>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty approvedNetworksData}">
    	<h3>Approved Networks </h3>
        <div id='approved'></div>
    </c:when>
    <c:otherwise>
		<h3>No approved networks data to show</h3>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty rejectedNetworksData}">
    	<h3>Rejected Networks</h3>
		<div id='rejected'></div>
    </c:when>
    <c:otherwise>
		<h3>No rejected networks data to show</h3>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty workspaceData}">
		<h3>Workspace</h3>
		<div id='workspace'></div>
    </c:when>
    <c:otherwise>
		<h3>No workspace data to show</h3>
    </c:otherwise>
</c:choose>