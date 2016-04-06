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
	
	<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-1.9.1.min.js"></script>
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


<a href="#" onclick="goFullscreen('chart')"> <img src="${pageContext.servletContext.contextPath}/resources/fullscreen_icon.png" width="30" height="30" style="float: left"/> </a>



<!-- <div id="dspace_metadata"></div>  -->




<div id="chart" class="row"></div>

<div id="inner-details" class="row"></div>
<div id="allannot_details" class="row">
<div class="row"><table id = "annotationsTable"></table></div>
</div>


<div id="log" class="row"></div>
<script type="text/javascript">

  function goFullscreen(id) {
	  document.getElementById('chart').style='position: fixed; top: 0; bottom :0; left: 0; right :0';
    // Get the element that we want to take into fullscreen mode
    var a = document.getElementById('chart');
    document.getElementsByTagName('svg')[0].id = 'svg_id';
    var svgDoc= document.getElementById('svg_id');
       
	svgDoc.setAttribute("height", "100%");
	svgDoc.setAttribute("width", "100%");
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

<script type="text/javascript">
function clear()
{
	var element=document.getElementById('chart');
	element.style.removeProperty('position');//=null;
	element.style.top=null;
	element.style.bottom=null;
	element.style.right=null;
	element.style.left=null;
	var width = $('#chart').parent().width();
	console.log(width);
	height = "500";
	 var svgDoc= document.getElementById('svg_id');
	svgDoc.setAttribute("height", height);
	svgDoc.setAttribute("width", width);
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