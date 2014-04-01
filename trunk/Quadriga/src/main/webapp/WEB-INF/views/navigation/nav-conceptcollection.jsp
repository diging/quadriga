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
			<span>Toolbox</span>
		</h2>
	</li>
	<li><a href="#">Collaborators</a>
		<ul>
			<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addcollaborators">
				Add Collaborators</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/deletecollaborators">
				Delete Collaborators</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/updatecollaborators">
				Update Collaborators</a></li>
		</ul>
	</li>
	<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/transferconceptcollectionowner/${collectionid}">
			Change Owner</a>
	</li>
</ul>