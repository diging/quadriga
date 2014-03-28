<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- CSS Files -->
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css" rel="stylesheet" />
</head>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>
<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing'"
	value='Okay'>
	
<br/>
<br/>
<body onload="d3visualize(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />


<div id="container">

<div id="center-container">
<div id="chart"></div>
</div>


<div id="right-container">
<div id="annot_details"></div>
<div id="inner-details"></div>

</div>

<div id="left-container">
<div id="concept_desc">
</div>
</div>

<div id="bottom-container">
<div id="allannot_details">
</div>

</div>

</div>

<div id="log"></div>

</html>
