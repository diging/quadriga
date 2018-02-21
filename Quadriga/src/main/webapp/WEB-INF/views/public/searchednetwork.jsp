<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/dist/cytoscape.js"></script>


<div class="row">
	<div class="alert alert-info" id="load">Searching for network
		containing the term ${searchNodeLabel}...</div>
	<div class="alert alert-info" id="network-empty" style="display: none;">Could
		not find network containing the term ${searchNodeLabel}</div>
	<div class="alert alert-info" id="network-error" style="display: none;">Server
		could not generate a response...Try again later.</div>
	<div id="network-available" style="display: none;">
		<a onclick="goFullscreen('networkBox')" style="float: left"
			title="Switch to fullscreen"> <i class="fa fa-arrows-alt"></i>
		</a>

		<div class="row">
			<div id="networkBox" class="col-sm-12"
				style="min-height: 500px; height: 100%; text-align: left;"></div>
		</div>

		<div id="inner-details" class="row"></div>
		<div id="allannot_details" class="row">
			<div class="row">
				<table id="annotationsTable"></table>
			</div>
		</div>
	</div>
</div>


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
	var element=document.getElementById('networkBox');
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
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/publicNetwork.js"></script>
<script type="text/javascript">
//# sourceURL=test.js

var container = document.getElementById('networkBox');
var cy = {};
function renderGraph(jsonstring){
	cy = cytoscape({
	    container: container, // container to render in

	    elements: jsonstring,
	    layout: {
	        name: 'cose',
	        idealEdgeLength: 5
	      },
	    style: [ // the stylesheet for the graph
	             {
	               selector: 'node',
	               style: {
	                 'background-color': 'mapData(group, 0, 1, #E1CE7A, #FDD692)',
	                 'border-color' : '#B98F88',
	                 'border-width' : 1,
	                 'font-family': 'Open Sans',
	                 'font-size': '12px',
	                 'font-weight' : 'bold',
	                 'color': 'mapData(group, 0, 1, #666, #333)',
	                 'label': 'data(conceptName)',
	                 'width':'mapData(group, 0, 1, 40, 55)',
	                 "height":"mapData(group, 0, 1, 40, 55)",
	                 'text-valign' : 'center',
	               }
	             },

	             {
	               selector: 'edge',
	               style: {
	                 'width': 1,
	                 'line-color': '#754F44',
	                 'target-arrow-shape': 'none'
	               }
	             }
	           ]
	});
	defineListeners(cy, '${pageContext.servletContext.contextPath}', '${unixName}');

	$( document ).ready(function() {
		$('#exportJson').on('click', function() {
			var json = cy.json();
			window.open('data:application/json,' +
	        encodeURIComponent(JSON.stringify(json), '_blank'));
		});
	});

	$( document ).ready(function() {
	    $('#exportPng').on('click', function() {
	        var png = cy.png({'scale' : 5});
	        window.open(png, '_blank');
	    });
	});
}

function constructGraphData(graphData){
	var graphObj = [];
	graphData.nodes.forEach(function (node){
		graphObj.push(node);
	});
	graphData.links.forEach(function (link){
		graphObj.push(link);
	});
	return graphObj;
}


function success(){
	$('#load').css('display','none');
	$('#network-available').css('display','block');
}

function empty(){
	$('#load').css('display','none');
	$('#network-empty').css('display','block');
}

function fail(){
	$('#load').css('display','none');
	$('#network-error').css('display','block');
}
var searchComplete = false;
function searchNetwork(){
	$xhr = $.ajax({
		dataType: 'json',
		url: 'search/result',
		data: {
			tokenId: ${token}
		}
	}).done(function (data, status){
		if(data.status == 1){
			clearTimeout(timeout);
			json = constructGraphData(data)
			if(json.length == 0){
				empty();
			}
			else{
				success();
			}
			renderGraph(json);
		}
		
	}).fail(function(xhr, status, error){
		clearTimeout(timeout);
		fail();
	});
	var timeout = setTimeout(searchNetwork, 10000);
}

window.onload = searchNetwork;
</script>
