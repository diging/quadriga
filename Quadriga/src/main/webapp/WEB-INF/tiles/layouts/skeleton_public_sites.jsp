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
    <title><tiles:insertAttribute name="title" /></title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.servletContext.contextPath}/resources/js/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.servletContext.contextPath}/resources/public/css/navbar.css" rel="stylesheet">
  
	<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.1/css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.1/jquery.dataTables.min.js"></script>

	<script type="text/javascript" charset="utf8">
			$(document).ready(function(){
			  $('#example').dataTable({
				"sPaginationType" : "full_numbers",
				"bLengthChange": false,
				"bAutoWidth" : false
			  });
			});
			
			$(document).ready(function() {
			    // Configure/customize these variables.
			    var showChar = 100;  // How many characters are shown by default
			    var ellipsestext = "...";
			    var moretext = "Show more >";
			    var lesstext = "Show less";
			    
			    $('.more').each(function() {
			        var content = $(this).html();
			        if(content.length > showChar) {
			            var c = content.substr(0, showChar);
			            var h = content.substr(showChar, content.length - showChar);			 
			            var html = c + '<span class="moreellipses">' + ellipsestext+ '&nbsp;</span><span class="morecontent"><span>' + h + '</span>&nbsp;&nbsp;<a href="" class="morelink">' + moretext + '</a></span>';
			 
			            $(this).html(html);
			        }
			    });
			 
			    $(".morelink").click(function(){
			        if($(this).hasClass("less")) {
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
			.morecontent span {  display: none; }
			.morelink {  display: block; }
		</style>				
  </head>

  <body>
  <tiles:importAttribute name="currentPage" scope="request" />
    <div class="container">
      <!-- Static navbar -->
      <nav class="navbar navbar-default navbar-colored">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">${project.projectName}</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <tiles:insertAttribute name="navigationSites" /> 
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <tiles:insertAttribute name="navigation" />  
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>
      <tiles:insertAttribute name="content" />
    </div> <!-- /container -->
  </body>
</html>
