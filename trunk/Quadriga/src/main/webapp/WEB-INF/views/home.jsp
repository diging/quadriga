<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Content -->

<article class="is-page-content">



	<!-- Highlight -->
	<section class="is-highlight">
		<span class="image image-centered"><img
			src="${pageContext.servletContext.contextPath}/resources/txt-layout/images/panel-new2.png" alt="" />
		</span>

		<ul class="special">
			<sec:authorize
				access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
				<li><a href="workbench" class="battery">Workbench</a></li>
			</sec:authorize>

			<sec:authorize
				access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
				<li><a href="conceptcollections" class="tablet">Concept
						Collections</a></li>
			</sec:authorize>
			<sec:authorize
				access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
				<li><a href="dictionaries" class="chart">Dictionaries</a></li>
			</sec:authorize>
			<sec:authorize
				access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
				<li><a href="networks" class="flask">Networks</a></li>
			</sec:authorize>
		</ul>
		<header>
			<h2>${username}, welcome to Quadriga</h2>
			<span class="byline">manage your network projects here</span>
		</header>

	</section>
	<!-- /Highlight -->
</article>

<!-- /Content -->

