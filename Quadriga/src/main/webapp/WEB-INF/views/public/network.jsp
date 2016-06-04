<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/d3.css"
	rel="stylesheet" /> 
	
<script src="${pageContext.servletContext.contextPath}/resources/js/d3/common_functions.js"></script> 
<script
	src="${pageContext.servletContext.contextPath}/resources/js/d3networkspublic.js"></script>
	
	<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script src="http://d3js.org/d3.v3.js" charset="utf-8"></script>


</head>
<script type="text/javascript">
function changeLayout(json,networkid,path,type)
{
	d3visualizepublic(json,networkid,path,type, '${unixName}');
}

</script>

<body
	onload="d3visualizepublic(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force', '${unixName}');" />



<button type="submit" onclick="goFullscreen('chart')" style="float: left">
    <i class="fa fa-arrows-alt"></i> 
    
</button>
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
<script type="text/javascript">
//# sourceURL=dynamicScript.js 

  function goFullscreen(id) {
	var element = document.getElementById(id);
    if (element.mozRequestFullScreen) {
    	// This is how to go into fullscren mode in Firefox
        // Note the "moz" prefix, which is short for Mozilla.
        element.mozRequestFullScreen();
    } else if (element.webkitRequestFullScreen) {
      // This is how to go into fullscreen mode in Chrome and Safari
      // Both of those browsers are based on the Webkit project, hence the same prefix.
      element.webkitRequestFullScreen();
    }
  }

</script>

<script type="text/javascript">
//# sourceURL=dynamicScript2.js 
function clear()
{
	var element=document.getElementById('chart');
	element.style.removeProperty('position');//=null;
	element.style.top=null;
	element.style.bottom=null;
	element.style.right=null;
	element.style.left=null;
}

</script>

<script>
if (document.addEventListener)
{
    document.addEventListener('webkitfullscreenchange', exitHandler, false);
    document.addEventListener('mozfullscreenchange', exitHandler, false);
    document.addEventListener('fullscreenchange', exitHandler, false);
    document.addEventListener('MSFullscreenChange', exitHandler, false);
}

function exitHandler()
{
    if (document.webkitIsFullScreen || document.mozFullScreen || document.msFullscreenElement !== null)
    {
    	if(window.innerWidth == screen.width && window.innerHeight == screen.height) {
    	}
    	else
    	{
    		clear();
    	}
    }
}
</script>
