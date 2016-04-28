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
		  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/workspace/"+ "${workspacedetails.workspaceId}"+ "/dictionaries.json",
			  type : "GET",
	          success: function(data){
	              $.each(data, function( index, value ) {
	            	  $( "#workspaceDictionaries" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/dictionaries/"+value['id']+"'><i class='fa fa-book'></i> "+value['name']+"</a><br>");
	              });
	          }
		  });
		  
		  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/workspace/"+ "${workspacedetails.workspaceId}"+ "/conceptcollections.json",
			  type : "GET",
	          success: function(data){
	              $.each(data, function( index, value ) {
	            	 $( "#workspaceConceptCollections" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/conceptcollections/"+value['id']+"'><i class='fa fa-list-alt'></i> "+value['name']+"</a><br>");
	              });
	          }
		  });
	});
</script>
<div>
	<ul>
		<li><a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}"><span
				class="glyphicon glyphicon-circle-arrow-left"></span> All Workspaces
		</a></li>
	</ul>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>Dictionaries</span>
</h5>
<div id="workspaceDictionaries">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/adddictionary"><i class="fa fa-plus-circle"></i> Add</a>
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletedictionary"><i class="fa fa-times-circle"></i> Delete</a>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>Concept Collections</span>
</h5>
<div id="workspaceConceptCollections">		
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addconceptcollection"><i class="fa fa-plus-circle"></i> Add</a>
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deleteconceptcollections"><i class="fa fa-times-circle"></i> Delete</a>
</div>

