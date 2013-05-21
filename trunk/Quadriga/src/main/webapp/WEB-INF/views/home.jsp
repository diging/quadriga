<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Content -->

<article class="is-page-content">



	<!-- Highlight -->
	<section class="is-highlight">
		<ul class="special">
			<li><a href="workbench" class="battery">Battery</a></li>
			<li><a href="#" class="tablet">Tablet</a></li>
			<li><a href="#" class="flask">Flask</a></li>
			<li><a href="#" class="chart">Pie Chart?</a></li>
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

		<p>
			Phasellus quam turpis, feugiat sit amet ornare in, hendrerit in
			lectus. Praesent semper mod quis eget mi. Etiam eu<br /> ante risus.
			Aliquam erat volutpat. Aliquam luctus et mattis lectus amet pulvinar.
			Nam nec turpis consequat.
		</p>
	</section>
	<!-- /Highlight -->


</article>

<!-- /Content -->

