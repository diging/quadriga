
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	$(function() {
		$("#menu").menu();
	});
</script>

<style>
.ui-menu {
	width: 150px;
	font-size:14px;
	position:relative;
    z-index:9999;
}
</style>

<ul id="menu">
	<li>
		<h2 class="major">
			<span>Toolbox</span>
		</h2>
	</li>
	<li><a href="#">Workspace</a>
		<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addworkspace">Add
					Workspace</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteworkspace">Delete
					Workspace</a></li>
		</ul></li>
		<li><a href="#">Archive Workspace</a>
		<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/archiveworkspace">Archive
					Workspace</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/unarchiveworkspace">Activate
					Archived Workspace</a></li>
		</ul></li>
			<li><a href="#">Deactivate Workspace</a>
		<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deactivateworkspace">Deactivate
					Workspace</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/activateworkspace">Activate
					Workspace</a></li>
		</ul></li>
	<li><a href="#">Dictionary</a>
		<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary">Add
					Dictionary</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/dictionaries">List
					Dictionary</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary">Delete
					Dictionary</a></li>
		</ul></li>
	<li><a href="#">Collections</a>
		<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection">Add
					Collection Concept</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/conceptcollections">List
					Collection Concept</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections">Delete
					Collection Concept</a></li>
		</ul></li>
		
		<li><a href="#">Collaborators</a>
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators">
			Add Collaborators</a></li>
			
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletecollaborators">
			Delete Collaborators</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators">
			Update Collaborators</a></li>
		</ul></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${project.projectId}">
			Change Owner</a></li>
</ul>
