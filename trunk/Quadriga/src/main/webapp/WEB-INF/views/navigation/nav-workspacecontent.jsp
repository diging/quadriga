<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />

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
	<li><a href="#">DSpace</a>
		<ul>
			<li><a
				href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/communities">Add
					Text</a></li>
		</ul></li>
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
</ul>