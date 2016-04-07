<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css"
	rel="stylesheet" /> 
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/d3networkvisualize.js"></script>
<script src="http://d3js.org/d3.v3.js" charset="utf-8"></script>


</head>
<script type="text/javascript">
function changeLayout(json,networkid,path,type)
{
	d3visualizepublic(json,networkid,path,type);
}

</script>
<!-- <script>
$("input[type=button]").button().click(function(event) {
	event.preventDefault();
});</script>

 -->
<body
	onload="d3visualizepublic(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />




<!-- <div id="dspace_metadata"></div>  -->

<c:if test="${isNetworkEmpty}">
<div class="row">
	<div class="alert alert-info">Could not find any nodes for the search term in the project</div>
</div>
</c:if>

<c:if test="${!isNetworkEmpty}">

<div id="chart" class="row">
</div>

<div id="inner-details" class="row"></div>
<div id="allannot_details" class="row">
	<div class="row"><table id = "annotationsTable"></table></div>
</div>
</c:if>


<div id="log" class="row"></div>

