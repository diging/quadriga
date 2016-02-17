<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css"
	rel="stylesheet" />
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
	d3visualize(json,networkid,path,type);
}

</script>
<script>
$("input[type=button]").button().click(function(event) {
	event.preventDefault();
});</script>


<body
	onload="d3visualize(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />




<!-- <div id="dspace_metadata"></div>  -->




<div id="chart"></div>

<div id="inner-details"></div>
<div id="allannot_details">
<table id = annotationsTable></table>
</div>


<div id="log"></div>

<script type="text/javascript">

  function goFullscreen(id) {

    // Get the element that we want to take into fullscreen mode

    var element = document.getElementById(id);



    // These function will not exist in the browsers that don't support fullscreen mode yet, 

    // so we'll have to check to see if they're available before calling them.



    if (element.mozRequestFullScreen) {

      // This is how to go into fullscren mode in Firefox

      // Note the "moz" prefix, which is short for Mozilla.

      element.mozRequestFullScreen();

    } else if (element.webkitRequestFullScreen) {

      // This is how to go into fullscreen mode in Chrome and Safari

      // Both of those browsers are based on the Webkit project, hence the same prefix.

      element.webkitRequestFullScreen();

   }

   // Hooray, now we're in fullscreen mode!

  }

</script>


<input type="button" value="click to go fullscreen" onclick="goFullscreen('chart')">