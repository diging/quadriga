<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Content -->

<article class="is-page-content">



	<!-- Highlight -->
	<section class="is-highlight">
		<ul class="special">
			<li><a href="workbench" class="battery">Workbench</a></li>
			<li><a href="#" class="tablet">Concept Collections</a></li>
			<li><a href="#" class="flask">Networks</a></li>
			<li><a href="#" class="chart">Dictionaries</a></li>
		</ul>
		<header>
			<h2>${username}, welcome to Quadriga</h2>
			<span class="byline">manage your network projects here</span>
		</header>

		<!-- Link visible only to Quadriga Admins -->
		<sec:authorize access="hasRole('ROLE_QUADRIGA_USER_ADMIN')">
			<p>
				<a href='users/manage' >Manage
					Users</a>
			</p>
		</sec:authorize>
	</section>
	<!-- /Highlight -->


</article>

<!-- /Content -->

