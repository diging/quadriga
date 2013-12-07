<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<script>
	$(function() {
		$("#menu").menu();
	});
</script>

<%--<section>

	<h2 class="major">
		<span>Tools</span>
	</h2>
	<ul class="noStyle">
		<li>
			<article class="is-post-summary">
				<h3>
					» <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">Add Project</a><br /><br />
					» <a href="${pageContext.servletContext.contextPath}/auth/workbench/deleteproject">Delete Project</a><br /><br />
					» <a href="${pageContext.servletContext.contextPath}/auth/workbench/keys">Manage Dspace Login</a><br /><br />
				</h3>
			</article>
		</li>
		
	</ul>
</section> --%>

 <style>
.ui-menu {
	width: 150px;
	font-size:14px;
	position:relative;
    z-index:9999;
    margin:1;
}
</style>

<ul id="menu">
	<li>
		<h2 class="major">
			<span>Tools</span>
		</h2>
	</li>
	<li><a href="#">List of Approved and rejected Network</a>

	</li>
	<li><a href="#">Networks assigned to other editors</a>

	</li>
</ul>  