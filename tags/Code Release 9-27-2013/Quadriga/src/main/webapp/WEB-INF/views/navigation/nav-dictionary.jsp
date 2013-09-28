<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
			<span>Tools</span>
		</h2>
	</li>
	<li><a href="#">Collaborators</a>
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/showAddCollaborators">
				Add Collaborators</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/showDeleteCollaborators">
				Delete Collaborators</a></li>
			<li><a href="/quadriga/auth/dictionaries/${dictionaryid}/updatecollaborators">
				Update Collaborators</a></li>
		</ul>
	</li>
		<li><a href="${pageContext.servletContext.contextPath}/auth/dictionaries/updatedictionary/${dictionaryid}">
			Update</a>
	</li>
		<li><a href="${pageContext.servletContext.contextPath}/auth/dictionaries/changedictionaryowner/${dictionaryid}">
			Change Owner</a>
	</li>
</ul>