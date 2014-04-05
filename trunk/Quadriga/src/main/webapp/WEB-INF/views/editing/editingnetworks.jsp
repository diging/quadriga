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
	d3init(json,networkid,path,type);
}

</script>
<script>
$("input[type=button]").button().click(function(event) {
	event.preventDefault();
});</script>
<script type="text/javascript">
  $(function() {
	  
	var availableTags = [];
	<c:forEach var="node" items="${nodeList}">
		availableTags.push("${node.nodeName}");                                  
	</c:forEach>
    $( "#tags" ).autocomplete({
      source: availableTags
    });
  });
  </script>

<body
	onload="d3init(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />




<!-- <div id="dspace_metadata"></div>  -->

<div class="ui-widget">
	<label for="tags"></label> <input id="tags">
	<button>Search</button>
</div>

<br/>


<div id="chart"></div>


<div id="allannot_details"></div>


<div id="log"></div>

