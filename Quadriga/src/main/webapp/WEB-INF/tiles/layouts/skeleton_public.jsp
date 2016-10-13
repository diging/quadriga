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
    href="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/css/bootstrap.css"
    rel="stylesheet">
<!--  <link href="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">-->

<!-- Custom styles for this template -->
<link
    href="${pageContext.servletContext.contextPath}/resources/public/css/navbar.css"
    rel="stylesheet" type="text/css">
<link
    href="${pageContext.servletContext.contextPath}/resources/public/css/base.css"
    rel="stylesheet" type="text/css">
<link type="text/css"
    href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css"
    rel="stylesheet" />

<link type="text/css"
    href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css"
    rel="stylesheet" />
<!-- Search template -->
<link
    href="${pageContext.servletContext.contextPath}/resources/public/css/search.css"
    rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


<script
    src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/d3networkvisualize.js"
    type="text/javascript"></script>
<script src="https://d3js.org/d3.v3.js" charset="utf-8"
    type="text/javascript"></script>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"
    type="text/javascript"></script>
<script type="text/javascript">
    window.jQuery
            || document
                    .write('_$tag_______________________________________________$tag_____')
</script>
</head>

<body>
    <tiles:importAttribute name="currentPage" scope="request" />
    <tiles:importAttribute name="showNetwork" scope="request" />

    <c:if test="${showNetwork != 'False'}">
        <div class="image-bg"></div>
    </c:if>
    <div class="container main-container">


        <!-- Static navbar -->
        <nav class="navbar navbar-default navbar-colored">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button"
                        class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span
                            class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <c:if test="${not empty project}">
                        <a class="navbar-brand"
                            href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">${project.projectName}</a>
                    </c:if>
                    <c:if test="${empty project}">
                        <a class="navbar-brand" href="">Quadriga</a>
                    </c:if>
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

        <tiles:insertAttribute name="content" />


    </div>
    <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script
        src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/js/bootstrap.min.js"
        type="text/javascript"></script>


</body>
</html>
