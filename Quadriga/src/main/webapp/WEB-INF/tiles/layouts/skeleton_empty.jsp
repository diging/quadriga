<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<!--  Color scheme: https://coolors.co/app/2e5266-6e8898-9fb1bc-d3d0cb-c0392b  -->
<head>
<title><tiles:insertAttribute name="title" /></title>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Quadriga">

<!-- Bootstrap core CSS -->
<link
    href="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/css/bootstrap.css"
    rel="stylesheet">

<link
    href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic'
    rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700'
    rel='stylesheet' type='text/css'>

<link rel="stylesheet"
    href="${pageContext.servletContext.contextPath}/resources/css/jquery-ui.css" />

<script
    src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script
    src="${pageContext.servletContext.contextPath}/resources/js/jquery-ui.js"></script>
<script
    src="${pageContext.servletContext.contextPath}/resources/js/jquery-alert.js"></script>
<script
    src="${pageContext.servletContext.contextPath}/resources/js/jquery.quick.pagination.min.js"></script>

<!-- Datatables -->
<link rel="stylesheet" type="text/css"
    href="https://cdn.datatables.net/u/bs/dt-1.10.12/datatables.min.css" />
<script type="text/javascript"
    src="https://cdn.datatables.net/u/bs/dt-1.10.12/datatables.min.js"></script>

<link rel="stylesheet"
    href="${pageContext.servletContext.contextPath}/resources/font-awesome-4.5.0/css/font-awesome.min.css">

<!-- Ionicons -->
<link rel="stylesheet"
    href="${pageContext.servletContext.contextPath}/resources/ionicons/css/ionicons.min.css">


<!-- Custom styles for this template -->
<link
    href="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/css/main.css"
    rel="stylesheet">
<link
    href="${pageContext.servletContext.contextPath}/resources/css/base.css"
    rel="stylesheet">
</head>
<body data-spy="scroll" data-offset="0" data-target="#navigation">
    <tiles:importAttribute name="currentPage" scope="request" />

    <!-- Fixed navbar -->
    <div id="navigation" class="navbar navbar-default navbar-fixed-top navbar-search">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".navbar-collapse">
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a class="navbar-brand navbar-img" href="#"><img
                    src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/img/QuadrigaLogo-grey.png"
                    height="40px"></a>
            </div>
            <div>
                <div class="navbar-collapse collapse navbar-right">
                    <ul class="nav navbar-nav">
                        <tiles:insertAttribute name="navigation" />
                    </ul>
                </div>
                <!--/.nav-collapse -->
                <sec:authorize access="not isAuthenticated()">
                    


                </sec:authorize>
            </div>

        </div>
    </div>

    <c:choose>
        <c:when test="${show_success_alert}">
            <div class="row" style="margin-top: 20px;">
                <div class="col-md-offset-1 col-md-10">
                    <div class="alert alert-success">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${success_alert_msg}
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${show_error_alert}">
            <div class="row" style="margin-top: 20px;">
                <div class="col-md-offset-1 col-md-10">
                    <div class="alert alert-danger">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${error_alert_msg}
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${show_info_alert}">
            <div class="row" style="margin-top: 20px;">
                <div class="col-md-offset-1 col-md-10">
                    <div class="alert alert-info">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${info_alert_msg}
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>


    <!--  Main -->
    <div class="row" style="min-height: 400px;">
    
    <tiles:insertAttribute name="content" />

    </div>
    <!-- /Main -->

    <div id="c" class="footer-search">
        <div class="container">
            <div class="col-md-6" style="text-align: left;">
                <c:set var="PR" value="${pullrequest}" />
                Version: ${buildNumber}
                <c:if test="${not empty PR}">, Pull Request: ${pullrequest}</c:if>
            </div>
            <div class="col-md-6">
                <p>
                    Quadriga is being developed by the <a href="http://diging.asu.edu/">Digital
                        Innovation Group</a>.<br>
                        
                    Design by <a href="http://www.blacktie.co">BLACKTIE.CO</a>
                </p>
            </div>

        </div>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script
        src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/js/bootstrap.js"></script>

</body>
</html>