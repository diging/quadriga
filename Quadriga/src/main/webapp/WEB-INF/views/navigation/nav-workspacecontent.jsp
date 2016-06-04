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
<div style="margin-bottom: 20px;">
    <a href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}">
        <i class="fa fa-arrow-circle-left" aria-hidden="true"></i> All Workspaces
	</a>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Dictionaries</h3>
  </div>
  <div class="panel-body">
      <div id="workspaceDictionaries" style="margin-bottom: 10px;"></div>
       <c:if test="${owner=='1' || wsadmin=='1'}">
      <div>
        <a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/adddictionary"><i class="fa fa-plus-circle"></i> Add</a> &nbsp; &nbsp;
	    <a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletedictionary"><i class="fa fa-times-circle"></i> Delete</a>
	  </div>
	  </c:if>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Concept Collections</h3>
  </div>
  <div class="panel-body">
    <div id="workspaceConceptCollections" style="margin-bottom: 10px;"></div>
    <c:if test="${owner=='1' || wsadmin=='1'}">
    <div>
    	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addconceptcollection"><i class="fa fa-plus-circle"></i> Add</a> &nbsp; &nbsp;
    	<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deleteconceptcollections"><i class="fa fa-times-circle"></i> Delete</a>
    </div>
    </c:if>
  </div>
</div>

<div class="list-group">
  <a data-toggle="modal" data-target="<c:choose><c:when test="${isDeactivated == true }">#</c:when><c:otherwise>#deactivate-ws</c:otherwise></c:choose>" class="list-group-item<c:if test="${isDeactivated == true }"> disabled</c:if>" <c:if test="${isDeactivated == true }">title="Workspace is already deactivated."</c:if>>
    <i class="fa fa-toggle-off"></i> Deactivate Workspace
  </a>
  <a data-toggle="modal" data-target="<c:choose><c:when test="${isDeactivated == false }">#</c:when><c:otherwise>#activate-ws</c:otherwise></c:choose>" class="list-group-item<c:if test="${isDeactivated == false }"> disabled</c:if>" <c:if test="${isDeactivated == false }">title="Workspace is currently active."</c:if>>
    <i class="fa fa-toggle-on"></i> Activate Workspace
  </a>
  <a data-toggle="modal" data-target="<c:choose><c:when test="${isDeactivated == false }">#</c:when><c:otherwise>#delete-ws</c:otherwise></c:choose>" class="list-group-item<c:if test="${isDeactivated == false }"> disabled</c:if>" <c:if test="${isDeactivated == false }">title="Only deactivated workspaces can be deleted."</c:if>>
    <i class="fa fa-ban"></i> Delete Workspace
  </a>
</div>



