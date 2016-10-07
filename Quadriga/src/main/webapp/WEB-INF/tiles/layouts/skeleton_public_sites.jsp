<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />

<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<title><tiles:insertAttribute name="title" /></title>

<!-- Bootstrap core CSS -->
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<link
	href='https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Raleway:400,300,700'
	rel='stylesheet' type='text/css'>

<!-- Custom styles for this template -->
<link
	href="${pageContext.servletContext.contextPath}/resources/css/public-sites.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.11/css/dataTables.bootstrap.min.css">
<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.11/js/dataTables.bootstrap.min.js"></script>

<script type="text/javascript" charset="utf8">
	$(document)
			.ready(
					function() {
						// Configure/customize these variables.
						var showChar = 100; // How many characters are shown by default
						var ellipsestext = "...";
						var moretext = "Show more >";
						var lesstext = "Show less";

						$('.more')
								.each(
										function() {
											var content = $(this).html();
											if (content.length > showChar) {
												var c = content.substr(0,
														showChar);
												var h = content.substr(
														showChar,
														content.length
																- showChar);
												var html = c
														+ '<span class="moreellipses">'
														+ ellipsestext
														+ '&nbsp;</span><span class="morecontent"><span>'
														+ h
														+ '</span>&nbsp;&nbsp;<a href="" class="morelink">'
														+ moretext
														+ '</a></span>';

												$(this).html(html);
											}
										});

						$(".morelink").click(function() {
							if ($(this).hasClass("less")) {
								$(this).removeClass("less");
								$(this).html(moretext);
							} else {
								$(this).addClass("less");
								$(this).html(lesstext);
							}
							$(this).parent().prev().toggle();
							$(this).prev().toggle();
							return false;
						});
					});
</script>
<style type="text/css">
.morecontent span {
	display: none;
}

.morelink {
	display: block;
}
</style>

</head>
<body>
	<tiles:importAttribute name="currentPage" scope="request" />

	<div class="container-fluid">
		<div class="header clearfix">
			<div class="navbar-collapse collapse navbar-right">
				<ul class="nav navbar-nav">
					<tiles:insertAttribute name="navigation" />
				</ul>
			</div>
			<h3 class="text-muted">
				<a class="navbar-brand"
					href="${pageContext.servletContext.contextPath}/sites"><img
					src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/img/QuadrigaLogo-grey.png"
					height="35px" style="margin-top: -5px;"></a>
			</h3>
		</div>

		<tilesx:useAttribute name="right-navigation" id="rightnavigation" />
		<div <c:if test="${!empty rightnavigation}"> class="col-md-9"</c:if>
			style="text-align: center">
			<div class="content">
				<!-- Content -->
				<tiles:insertAttribute name="content" />
				<!-- /Content -->
			</div>
		</div>

		<c:if test="${!empty rightnavigation}">
		<div class="col-md-3" style="text-align: center">
			<!-- Sidebar -->
			<!-- Recent Posts -->
			<tiles:insertAttribute name="right-navigation" />
			<!-- /Recent Posts -->
			<!-- /Sidebar -->
		</div>
		</c:if>
	</div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script 
	      src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
</body>
</html>
