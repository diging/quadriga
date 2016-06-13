<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/d3.css"
	rel="stylesheet" />

<!-- <script src="${pageContext.servletContext.contextPath}/resources/js/d3/common_functions.js"></script> 
<script
	src="${pageContext.servletContext.contextPath}/resources/js/d3networkspublic.js"></script> -->

<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<!-- <script src="http://d3js.org/d3.v3.js" charset="utf-8"></script> -->
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/cytoscape.js"></script>

<!--  onload="d3visualizepublic(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force', '${unixName}');" />-->



<button type="submit" onclick="goFullscreen('chart')"
	style="float: left">
	<i class="fa fa-arrows-alt"></i>

</button>
<!-- <div id="dspace_metadata"></div>  -->

<c:if test="${isNetworkEmpty}">
	<div class="row">
		<div class="alert alert-info">Could not find any nodes for the
			search term in the project</div>
	</div>
</c:if>

<c:if test="${!isNetworkEmpty}">

	<div class="row">
		<div id="networkBox" class="col-sm-12"
			style="min-height: 500px; text-align: left;"></div>
	</div>

	<div id="inner-details" class="row"></div>
	<div id="allannot_details" class="row">
		<div class="row">
			<table id="annotationsTable"></table>
		</div>
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
	var element=document.getElementById('cy');
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

<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-cose-bilkent/1.0.2/cytoscape-cose-bilkent.js"></script>
<script type="text/javascript">
//# sourceURL=test.js
/*function changeLayout(json,networkid,path,type) {
    d3visualizepublic(json,networkid,path,type, '${unixName}');
}*/

var container = document.getElementById('networkBox');

var cy = cytoscape({
    container: container, // container to render in

    elements: ${jsonstring},
    layout: {
        name: 'cose',
        idealEdgeLength: 5
      },
    style: [ // the stylesheet for the graph
             {
               selector: 'node',
               style: {
                 'background-color': 'mapData(group, 0, 1, #E1CE7A, #FDD692)',
                 'font-family': 'Open Sans',
                 'font-size': '12px',
                 'font-weight' : 'bold',
                 'color': 'mapData(group, 0, 1, #666, #333)',
                 'label': 'data(conceptName)',
                 'width':'mapData(group, 0, 1, 15, 30)',
                 "height":"mapData(group, 0, 1, 15, 30)",
                 
               }
             },

             {
               selector: 'edge',
               style: {
                 'width': 1,
                 'line-color': '#754F44',
                 'target-arrow-shape': 'triangle'
               }
             }
           ]
});

var elements = cy.elements();
cy.on('mouseover', 'node', function(e) {
	var ele = e.cyTarget;
	var statementIds = ele.data('statementIds');
	fadeOut(statementIds);
})

cy.on('mouseout', 'node', function(e) {
    fadeIn();
});

function fadeIn() {
	cy.elements().animate({
      style: { 'opacity': '1' }
    }, {
      duration: 500
    });
    
    
}

function fadeOut(statementIds) {
    var faded = cy.filter(function(i, element){
        for (var i = 0; i<statementIds.length; i++) {
	      if(element.data("statementIds").includes(statementIds[i])){
	         return false;
	      }
        }
        return true;
	 });
    
    faded.animate({
        style: { 'opacity': '0.2' }
      }, {
        duration: 500
      });
}
   
   /*var stId = d.statementid;
   var associated_nodes = vis.selectAll('circle').filter(function(node) {
       for (var i = 0; i < stId.length; i++) {
           if (($.inArray(stId[i], node.statementid)) > -1)
               return true;
       }
       return false;
   });
   associated_nodes.each(function(a) {
       d3.select(this).style("opacity", 1);
       
       var aIndex = a.index;
       
       var associated_links = vis.selectAll("line").filter(function(d) {
           return d.source.index == aIndex;
       });
       associated_links.each(function(d) {
           //unfade links and nodes connected to the current node
           d3.select(this).style("opacity", 1);
       });
   });*/



</script>

<style>
.faded {
	opacity: 0.5;
}
</style>
