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
	<li><a href="#">Dictionary</a>
		<ul>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/adddictionary">Add Dictionary</a></li>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/dictionaries">List Dictionary</a></li>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/deletedictionary">Delete Dictionary</a></li>
		</ul></li>
	<li><a href="#">Collections</a>
		<ul>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/addconceptcollection">Add Concept Collection</a></li>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/conceptcollections">List Concept Collection</a></li>
			<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/deleteconceptcollections">Delete Concept Collection</a></li>
		</ul></li>
		<li><a href="#">Collaborators</a>
		<ul> 
		<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/addcollaborators">Add Collaborator</a></li>
		<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/deletecollaborators">Delete Collaborator</a></li>
		<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/updatecollaborators">Update Collaborator</a></li>
		</ul>
		</li>
		<li><a href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/transferworkspaceowner">
			Change Owner</a></li>
</ul>

