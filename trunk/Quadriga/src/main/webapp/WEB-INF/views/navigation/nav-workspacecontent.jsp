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
	<li><a href="#">Dictionary</a>
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/adddictionary">Add Dictionary</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/dictionaries">List Dictionary</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletedictionary">Delete Dictionary</a></li>
		</ul></li>
	<li><a href="#">Collections</a>
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addconceptcollection">Add Concept Collection</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/conceptcollections">List Concept Collection</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deleteconceptcollections">Delete Concept Collection</a></li>
		</ul></li>
		<li><a href="#">Collaborators</a>
		<ul> 
		<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addcollaborators">Add Collaborator</a></li>
		<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletecollaborators">Delete Collaborator</a></li>
		<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/updatecollaborators">Update Collaborator</a></li>
		</ul>
		</li>
		<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/transferworkspaceowner">
			Change Owner</a></li>
</ul>

