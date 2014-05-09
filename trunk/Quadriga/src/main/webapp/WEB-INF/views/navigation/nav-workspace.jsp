
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

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
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteworkspace">Delete</a></li>

				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/archiveworkspace">Archive</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/unarchiveworkspace">Unarchive</a></li>

				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deactivateworkspace">Deactivate
		</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/activateworkspace">Activate</a></li>
			</ul>
		</li>
		<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/down.png"}'>Dictionary
			<ul>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary">Add</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/list.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/dictionaries">List</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary">Delete</a></li>
			</ul>
		</li>
		<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/down.png"}'>Collections
			<ul>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection">Add</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/list.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/conceptcollections">List</a></li>
				<li data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections">Delete</a></li>
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