

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
<title><tiles:insertAttribute name="title" /></title>


<!-- Bootstrap core CSS -->
<link
	href="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link
	href="${pageContext.servletContext.contextPath}/resources/public/css/navbar.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<tiles:importAttribute name="currentPage" scope="request" />
	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default navbar-colored">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand"
						href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">${project.projectName}</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<tiles:insertAttribute name="navigation" />

					</ul>


				</div>
				<!--/.nav-collapse -->


			</div>
			<!--/.container-fluid -->



		</nav>


		<%-- 
 <tiles:insertAttribute name="content" /> is  added for displaying content which is
  specific only to publicnetworkvisual.jsp page.  --%>


			<div  class="col-md-8" style="text-align:center">				
					<div class="content content=left">
					<!-- Content -->
					<article class="is-page-content">
						<tiles:insertAttribute name="content" />
					</article>
					<!-- /Content -->				
			</div>
			</div>
			<div  class="col-md-4" style="text-align:right">
					<!-- Sidebar -->
					<!-- Recent Posts -->
					<div class="sidebar"><tiles:insertAttribute name="right-navigation" /></div>
					<!-- /Recent Posts -->
					<!-- /Sidebar -->
			</div>
			</div>	

	</div>

	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script src="../../dist/js/bootstrap.min.js"></script>

	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-1.9.1.min.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/config.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/skel.min.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/skel-ui.min.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/networkvisualize.js"></script>
	 <script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-ui.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery-alert.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/d3networkvisualize.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/d3networkspublic.js"></script>
	<!--  <script type="text/javascript" src="http://mbostock.github.com/d3/d3.js"></script>-->
	<script src="http://d3js.org/d3.v3.js" charset="utf-8"></script>
	<script
		src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jquery.quick.pagination.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
