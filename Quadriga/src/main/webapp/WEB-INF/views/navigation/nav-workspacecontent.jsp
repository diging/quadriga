<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#workspacemenu').jstree().on(
				"select_node.jstree",
				function(e, data) {
					var i, j = [];
					for (i = 0, j = data.selected.length; i < j; i++) {
						$('#workspacemenu').jstree().toggle_node(
								data.instance.get_node(data.selected[i]));
					}
					document.location = data.instance.get_node(data.node, true)
							.children('a').attr('href');
				});
	});
	
	$(document).ready(function(){
		  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/workspace/rest"+ "${workspacedetails.workspaceId}"+ "/dictionaries.json",
			  type : "GET",
	          success: function(data){
	              $.each(data, function( index, value ) {
	            	  $( "#workspaceDictionaries" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/dictionaries/"+value['id']+"'>"+value['name']+"</a><br>");
	              });
	          }
		  });
		  
		  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/workspace/rest"+ "${workspacedetails.workspaceId}"+ "/conceptcollections.json",
			  type : "GET",
	          success: function(data){
	              $.each(data, function( index, value ) {
	            	 $( "#workspaceConceptCollections" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/conceptcollections/"+value['id']+"'>"+value['name']+"</a><br>");
	              });
	          }
		  });
	});
</script>

<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>DICTIONARIES</span>
</h5>
<div id="workspaceDictionaries">
	<img src="/quadriga/resources/txt-layout/css/images/plus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/adddictionary">Add</a>
	<img src="/quadriga/resources/txt-layout/css/images/minus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletedictionary">Delete</a>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>CONCEPT COLLECTIONS</span>
</h5>
<div id="workspaceConceptCollections">		
	<img src="/quadriga/resources/txt-layout/css/images/plus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addconceptcollection">Add</a>
	<img src="/quadriga/resources/txt-layout/css/images/minus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deleteconceptcollections">Delete</a>
</div>
