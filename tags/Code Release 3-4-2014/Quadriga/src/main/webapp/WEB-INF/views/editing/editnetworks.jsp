<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ForceDirected - Force Directed Static Graph</title>

<!-- CSS Files -->
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css" rel="stylesheet" />

</head>



<body onload="d3init(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>);" />  

   
    
  

<div id="container">

<div id="left-container">


       

        <div id="id-list"></div>


</div>
<div id="chart"></div>
<!-- <div id="center-container">
     div id="infovis"></div   
    <div id="chart"></div> 
</div> -->

<div id="right-container">

<div id="inner-details"></div>

</div>

<div id="log"></div>
</div>
</body>
</html>
