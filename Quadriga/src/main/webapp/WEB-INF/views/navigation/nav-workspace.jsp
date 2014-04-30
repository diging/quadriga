
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
  $(function () {
   $('#projectmenu').jstree().on("select_node.jstree", function (e, data) {
       document.location = data.instance.get_node(data.node, true).children('a').attr('href');
   });
  });
  </script>

<h2 class="major">
			<span>Menu</span>
</h2>
<div id="projectmenu">
	<ul>
		<li>Workspace
			<ul>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/new.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addworkspace">Add</a></li>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/delete.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteworkspace">Delete</a></li>

				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/archiveworkspace">Archive</a></li>
				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/unarchiveworkspace">Unarchive</a></li>

				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deactivateworkspace">Deactivate
		</a></li>
				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/activateworkspace">Activate</a></li>
			</ul>
		</li>
		<li>Dictionary
			<ul>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/new.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary">Add</a></li>
				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/dictionaries">List</a></li>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/delete.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary">Delete</a></li>
			</ul>
		</li>
		<li>Collections
			<ul>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/new.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection">Add</a></li>
				<li><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/conceptcollections">List</a></li>
				<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/delete.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections">Delete</a></li>
			</ul>
		</li>
		<li>Collaborators
		<ul>
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/new.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators">
			Add</a></li>
			
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/delete.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletecollaborators">
			Delete</a></li>
			<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/edit.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators">
			Update</a></li>
		</ul>
		</li>
		<li data-jstree='{"icon":"/quadriga/resources/txt-layout/images/edit.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${project.projectId}">
			Change Owner</a></li>

	</ul>
</div>