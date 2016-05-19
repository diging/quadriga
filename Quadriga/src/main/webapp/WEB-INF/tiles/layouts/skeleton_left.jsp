<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title><tiles:insertAttribute name="title" /></title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Quadriga">
    
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/css/main.css" rel="stylesheet">
    
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>
    
    <link rel="stylesheet"
        href="${pageContext.servletContext.contextPath}/resources/css/jquery-ui.css" />
    <link rel="stylesheet"
        href="${pageContext.servletContext.contextPath}/resources/css/jquery.dataTables_themeroller.css" />
    
    <script src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/jquery-ui.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/jquery-alert.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/jquery.dataTables.js"></script>
    <script
        src="${pageContext.servletContext.contextPath}/resources/js/jquery.quick.pagination.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<body data-spy="scroll" data-offset="0" data-target="#navigation">
    <tiles:importAttribute name="currentPage" scope="request" />
 
    <!-- Fixed navbar -->
    <div id="navigation" class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand navbar-img" href="#"><img src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/img/QuadrigaLogo-grey.png" height="40px"></a>
        </div>
        <div>
        <div class="navbar-collapse collapse navbar-right">
          <ul class="nav navbar-nav">
            <tiles:insertAttribute name="navigation" />
          </ul>
        </div><!--/.nav-collapse -->
        <sec:authorize access="not isAuthenticated()">
            <form name='f' action="<c:url value='/login' />" method='POST' class="navbar-form navbar-right">
                 <div class="form-group">
                     <input type="text" class="form-control" name="username" placeholder="Username">
                 </div>
                 <div class="form-group">
                     <input type="text" class="form-control" name="password" placeholder="Password">
                 </div>
                 <button type="submit" class="btn btn-default">Sign In</button>
             </form>
        </sec:authorize>
        </div>
        
      </div>
    </div>
    
    <sec:authorize access="isAuthenticated()">
        <div class="container">
         <div class="row" style="padding-top: 15px;">
             <div class="col-md-10">
                 Welcome <i class="fa fa-user"></i><span style="margin-left: 5px;"><sec:authentication
                         property="principal.username" /></span>!
             </div>
             <div class="col-md-2">
                 <a class="pull-right" href="<c:url value='/logout' />">Logout</a>
             </div>
         </div>
         <hr>
         </div>
     </sec:authorize>

    <c:choose>
       <c:when test="${show_success_alert}">
       <div class="alert alert-success">
           <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${success_alert_msg}
       </div>
       </c:when>
       <c:when test="${show_error_alert}">
       <div class="alert alert-danger">
       <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${error_alert_msg}
       </div>
       </c:when>
       <c:when test="${show_info_alert}">
       <div class="alert alert-info">
       <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${info_alert_msg}
       </div>
      </c:when>
    </c:choose>
        
        
    <!--  Main -->
    
    <div id="main">
        <div class="container">
        <div class="row">
            <div class="col-md-3">
                <tiles:insertAttribute name="left-navigation" />
            </div>
            <div class="col-md-7">
                <tiles:insertAttribute name="content" />
            </div>
        </div> 
        </div>
    </div>  

    <!-- /Main -->

    <section id="contact" name="contact"></section>
    <div id="footerwrap">
        <div class="container">
            <div class="col-lg-5">
                <h3>The Digital Innovation Group</h3>
                <p>
                Quadriga is being developed by the <a href="http://diging.asu.edu/">Digital Innovation Group</a> in the <a href="http://cbs.asu.edu/">Center for Biology and Society</a> at <a href="http://www.asu.edu/">Arizona State University</a>.
                </p>
            </div>
            <div class="col-lg-2">
            </div>
            <div class="col-lg-5">
                <h3>Find us</h3>
                <p>Quadriga is open-source software hosted on <a href="http://diging.github.io/quadriga/">GitHub</a>.
            </div>
        </div>
    </div>
    <div id="c">
        <div class="container">
            <p>Design by <a href="http://www.blacktie.co">BLACKTIE.CO</a></p>
        
        </div>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/js/bootstrap.js"></script>
    
  </body>
</html>