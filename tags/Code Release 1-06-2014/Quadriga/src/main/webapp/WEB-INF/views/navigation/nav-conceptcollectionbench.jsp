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
	<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/addCollectionsForm">
			Add ConceptCollection</a>
	</li>
</ul>