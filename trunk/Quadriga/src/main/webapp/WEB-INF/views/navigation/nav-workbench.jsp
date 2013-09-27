
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

<section>

<ul id="menu">
	<li>
		<h2 class="major">
			<span>Tools</span>
		</h2>
	</li>
	<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">Add Project</a></li>
	<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/deleteproject">Delete Project</a></li>
	<li><a href="${pageContext.servletContext.contextPath}/auth/workbench/keys">Manage DSpace Login</a></li>
</ul>
</section>