<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/base.css"
	rel="stylesheet" />
	
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/ForceDirected.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/d3.css"
	rel="stylesheet" />
<script
	src="${pageContext.servletContext.contextPath}/resources/js/d3networkvisualize.js"></script>
<script src="https://d3js.org/d3.v3.js" charset="utf-8"></script>

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

<button class="btn btn-default" type="submit" onclick="goFullscreen('chart')" style="float: left">
    <i class="fa fa-arrows-alt"></i> 
    
</button>


<!-- <div id="dspace_metadata"></div>  -->




<div id="chart"></div>

<div id="inner-details"></div>
<div id="allannot_details">
<table class="table table-striped table-bordered table-white" id ="annotationsTable"></table>
</div>


<div id="log"></div>

<script type="text/javascript">

  function goFullscreen(id) {
	  var element = document.getElementById(id);
    if (element.mozRequestFullScreen) {
      element.mozRequestFullScreen();
    } else if (element.webkitRequestFullScreen) {
      element.webkitRequestFullScreen();
   }
  }

</script>

<script type="text/javascript">
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