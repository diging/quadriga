<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>

<html>
<head>
<style>
ul.a {
	list-style-type: circle;
}
</style>
</head>
<head>
<title>display data from the table using jsp</title>
</head>
<body>
	<TABLE border="1" style="background-color: #ffffcc;">
		<TR>
			<TD align="center"><h2>${project.name} Collaborators</h2></TD>
		</TR>

		<%--<TR> 
 <TD align="center"><A HREF="ConnectJspToMysql.jsp"> 
<font size="4" color="blue">show data from table</font> 
</A></TD> 
</TR> --%>

	</TABLE>
	<div
		style="font-family: verdana; padding: 50px; border-radius: 10px; border: 5px solid #EE872A;">
		<ul class="a">
			<c:forEach var="projectcollaborator" items="${project.collaborators}">
				<li><h6>
						<c:out value="${projectcollaborator.userObj.name}"></c:out>
					</h6></li>
			</c:forEach>
		</ul>
	</div>
</body>

</html>