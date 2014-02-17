<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/base.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/ForceDirected.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/d3.css" rel="stylesheet" />

</head>
<script type="text/javascript">
function changeLayout(json,networkid,path,type)
{
	d3init(json,networkid,path,type);
}
</script>

<button onclick="changeLayout(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'tree')">Tree layout</button>

<body onload="d3init(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />  

   
    
  

<div id="container">


<div id="chart"></div>



<div id="inner-details"></div>

</div>

<div id="log"></div>

