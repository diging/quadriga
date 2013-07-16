
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
}
</style>

<ul id="menu">
	<li>
		<h2 class="major">
			<span>Tools</span>
		</h2>
	</li>
	<li><a href="#">Workspace</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/addworkspace">Add
					Workspace</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/deleteworkspace">Delete
					Workspace</a></li>
		</ul></li>
		<li><a href="#">Archive Workspace</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/archiveworkspace">Archive
					Workspace</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/unarchiveworkspace">Activate
					Archived Workspace</a></li>
		</ul></li>
			<li><a href="#">Deactivate Workspace</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/deactivateworkspace">Deactivate
					Workspace</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/activateworkspace">Activate
					Workspace</a></li>
		</ul></li>
	<li><a href="#">Dictionary</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/adddictionary">Add
					Dictionary</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/dictionaries">List
					Dictionary</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/deletedictionary">Delete
					Dictionary</a></li>
		</ul></li>
	<li><a href="#">Collections</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/addconceptcollection">Add
					Collection Concept</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/conceptcollections">List
					Collection Concept</a></li>
			<li><a
				href="/quadriga/auth/workbench/${project.internalid}/deleteconceptcollections">Delete
					Collection Concept</a></li>
		</ul></li>
		
		<li><a href="#">Collaborators</a>
		<ul>
			<li><a href="/quadriga/auth/workbench/${project.internalid}/showAddCollaborators">
			Add Collaborators</a></li>
			
			<li><a href="/quadriga/auth/workbench/${project.internalid}/showDeleteCollaborators">
			Delete Collaborators</a></li>
		</ul></li>
</ul>
