
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
  $(function () {
   $('#projectmenu').jstree().on("select_node.jstree", function (e, data) {
	   var i, j = [];
	   for(i = 0, j = data.selected.length; i < j; i++) {
		   $('#projectmenu').jstree().toggle_node(data.instance.get_node(data.selected[i]));
		}
       document.location = data.instance.get_node(data.node, true).children('a').attr('href');
   });
  });
  
  $(document).ready(function(){
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/workbench/"+ "${project.projectId}"+ "/dictionariesJson",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	  $( "#projectDictionaries" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/dictionaries/"+value['id']+"'>"+value['name']+"</a><br>");
              });
          }
	  });
	  
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/workbench/"+ "${project.projectId}"+ "/conceptcollectionsJson",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	 $( "#projectConceptCollections" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/conceptcollections/"+value['id']+"'>"+value['name']+"</a><br>");
              });
          }
	  });
  });
</script>

<h2 class="major">
	<span>Menu</span>
</h2>
<div id="projectmenu">
	<ul>
		<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/down.png"}'>Workspace
			<ul>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addworkspace">Add</a></li>
<%-- 				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"}'><a --%>
<%-- 					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteworkspace">Delete</a></li> --%>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deactivateworkspace">Deactivate</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/archiveworkspace">Archive</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/unarchiveworkspace">Unarchive</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/activateworkspace">Activate</a></li>
			</ul>
		</li>
		<!-- 
		<li data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/down.png"}'>Collaborators
		<ul>
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/plus.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators">
			Add</a></li>
			
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/minus.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletecollaborators">
			Delete</a></li>
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/pen.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators">
			Update</a></li>
		</ul>
		</li>
		 -->
	</ul>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>DICTIONARIES</span>
</h5>
<div id="projectDictionaries">
	<img src="/quadriga/resources/txt-layout/css/images/plus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary"${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary>Add</a>
	<img src="/quadriga/resources/txt-layout/css/images/minus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary">Delete</a>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>CONCEPT COLLECTIONS</span>
</h5>
<div id="projectConceptCollections">		
	<img src="/quadriga/resources/txt-layout/css/images/plus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection">Add</a>
	<img src="/quadriga/resources/txt-layout/css/images/minus.png" style="vertical-align: middle; padding-bottom: 2px;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections">Delete</a>
</div>
