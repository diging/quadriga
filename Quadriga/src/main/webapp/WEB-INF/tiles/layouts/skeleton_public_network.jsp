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
<link href="${pageContext.servletContext.contextPath}/resources/public/css/base.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script src="${pageContext.servletContext.contextPath}/resources/js/d3.min.js" charset="utf-8"></script>

<title><tiles:insertAttribute name="title" /></title>


<!-- Bootstrap core CSS -->
<link
	href="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link
	href="${pageContext.servletContext.contextPath}/resources/public/css/navbar.css"
	rel="stylesheet">
	
<link
    href="${pageContext.servletContext.contextPath}/resources/public/css/footer.css"
    rel="stylesheet" type="text/css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style> 
li{
list-style-type: none;}
</style>
</head>

<body>
	<tiles:importAttribute name="currentPage" scope="request" />
	<div class="container" style="min-height:90vh">

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




			<div  class="col-md-9" style="text-align:center">				
					<div class="content content=left">
					<!-- Content -->
					
						<tiles:insertAttribute name="content" />
					
					<!-- /Content -->				
			</div>
			</div>
			<div  class="col-md-3" style="text-align:center">
					<!-- Sidebar -->
					<!-- Recent Posts -->
					<tiles:insertAttribute name="right-navigation" />
					<!-- /Recent Posts -->
					<!-- /Sidebar -->
			</div>
	</div>	
			
	<div class="container">
        
		<div class="footer">
			<div class="container-fluid">
            	
				<div class="footer-col-lg-4 " align="left">
					<c:set var="PR" value="${pullrequest}" />
						Version: ${buildNumber}
						<c:if test="${not empty PR}">, Pull Request: ${pullrequest}</c:if>
				</div>
                
				<div class="footer-col-lg-4 " align="center">
					<a class="href-brand" href="${pageContext.servletContext.contextPath}/">
					<i class="fa fa-home" aria-hidden="true"></i></a>
				</div>
                
				<div class="footer-col-lg-4 " align="right">
					<a class="href-brand" href="${pageContext.servletContext.contextPath}/sites/" style="color: white">Public Sites</a>
				</div>
                
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
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<!-- <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script> -->
	
	<script 
	      src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	
</body>
</html>