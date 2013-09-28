<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML>
<!--
	TXT 2.0 by HTML5 UP
	html5up.net | @n33co
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/jquery-ui.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/jquery.dataTables_themeroller.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/demo_table_jui.css" />
	<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ul-pagination-styles.css" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,700|Open+Sans+Condensed:700"
	rel="stylesheet" />
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/config.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/skel.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/skel-ui.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-ui.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-alert.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery.dataTables.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery.quick.pagination.min.js"></script>


<noscript>
	<link rel="stylesheet"
		href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/skel-noscript.css" />
	<link rel="stylesheet"
		href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.css" />
	<link rel="stylesheet"
		href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style-desktop.css" />
		
</noscript>
<!--[if lte IE 9]><link rel="stylesheet" href="resources/txt-layout/css/ie9.css" /><![endif]-->
<!--[if lte IE 8]><script src="resources/txt-layout/js/html5shiv.js"></script><link rel="stylesheet" href="resources/txt-layout/css/ie8.css" /><![endif]-->
<!--[if lte IE 7]><link rel="stylesheet" href="resources/txt-layout/css/ie7.css" /><![endif]-->
</head>
<body>
	<tiles:importAttribute name="currentPage" scope="request" />

	<!-- Header -->
	<header id="header">
		<div class="logo">
			<div>
				<h1>
					<a href="#" id="logo">Quadriga</a>
				</h1>
				<span class="byline">- Network Management</span>
			</div>
		</div>
	</header>
	<!-- /Header -->

	<!-- Nav -->
	<tiles:insertAttribute name="navigation" />
	<!-- /Nav -->

	<!-- Main -->
	<div id="main-wrapper">
		<div id="main" class="container">
			<sec:authorize access="isAuthenticated()">
				<div>
					<div class="loggedInMsg">
						Welcome <span class="user" style="margin-left: 5px;"><sec:authentication property="principal.username" /></span>!
						<span><a class="inText" href="${pageContext.servletContext.contextPath}/auth/profile">(Your Profile)</a></span>
					</div>
					<div class="loggedOutLink">
						<a href="<c:url value='/j_spring_security_logout' />">Logout</a>
					</div>
					<hr class="clearLoggedIn">
				</div>
			</sec:authorize>
			<div class="row">
				<div class="12u">

					<!-- Content -->

					<article class="is-page-content">

						<tiles:insertAttribute name="content" />

					</article>

					<!-- /Content -->

				</div>
			</div>
		</div>
	</div>

	<!-- /Main -->

	<!-- Footer -->
	<footer id="footer" class="container">

		<div class="row">

			<!-- Copyright -->
			<div id="copyright">
				&copy; 2013 Digital Innovation Group | Images: <a
					href="http://flickr.com/people/freakyman/">freakyman</a> + <a
					href="http://iconify.it">Iconify.it</a> | Design: <a
					href="http://html5up.net/">HTML5 UP</a>
			</div>
			<!-- /Copyright -->

		</div>
	</footer>
	<!-- /Footer -->

</body>
</html>