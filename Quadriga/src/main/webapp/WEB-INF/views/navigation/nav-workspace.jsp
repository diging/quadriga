
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
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
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/"+ "${project.projectId}"+ "/dictionaries.json",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	  $( "#projectDictionaries" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/dictionaries/"+value['id']+"'>"+value['name']+"</a><br>");
              });
          }
	  });
	  
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/"+ "${project.projectId}"+ "/conceptcollections.json",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	 $( "#projectConceptCollections" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/conceptcollections/"+value['id']+"'>"+value['name']+"</a><br>");
              });
          }
	  });
  });
</script>
<!--  Issue fixed by QUAD-55 Bharat Srikantan and Ajay Modi -->
<div>
	<ul>
		<li>
			<a href="${pageContext.servletContext.contextPath}/auth/workbench"><span class="glyphicon glyphicon-circle-arrow-left"></span> All Projects </a>
		</li>
	</ul>
</div>
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
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary"><i class="fa fa-plus-circle"></i> Add</a>
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary"><i class="fa fa-times-circle"></i> Delete</a>
</div>
<h5 class="major" style="margin-top: 0.5em;margin-bottom: 0em;">
	<span>CONCEPT COLLECTIONS</span>
</h5>
<div id="projectConceptCollections">		
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection"><i class="fa fa-plus-circle"></i> Add</a>
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections"><i class="fa fa-times-circle"></i> Delete</a>
</div>
<hr>
<a href=""><i class="fa fa-cog"></i> Public Page Settings</a>
