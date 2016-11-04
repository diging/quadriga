<%@ page language="java" contentType="text/html;"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css" rel="stylesheet" />

</head>
<script type="text/javascript">
function changeLayout(json,networkid,path,type)
{
	d3init(json,networkid,path,type,'${_csrf.token}','${_csrf.headerName}');
}
</script>
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

<body onload="d3init(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force', <c:out value='"${_csrf.token}"'></c:out>,<c:out value='"${_csrf.headerName}"'></c:out>);" />  

  <div class="ui-widget">
  <label for="tags"></label> 
  <input id="tags">  <button>Search</button>
</div> 
    
  

<div id="container">

<div id="top-container">
<div id="dspace_metadata">
</div>
</div>

<div id="center-container">
<div id="chart"></div>
</div>


<div id="right-container">
<div id="annot_details"></div>
<div id="inner-details"></div>
<div id="test"></div>

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

