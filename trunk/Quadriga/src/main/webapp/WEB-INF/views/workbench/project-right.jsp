<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>

	<section>
		<h2 class="major"><span>Collaborators</span></h2>
	
		<%--<TR> 
 <TD align="center"><A HREF="ConnectJspToMysql.jsp"> 
<font size="4" color="blue">show data from table</font> 
</A></TD> 
</TR> --%>

	
	<ul class="collaborators">

			<c:forEach var="projectcollaborator" items="${project.collaborators}">
				<li>
						<c:out value="${projectcollaborator.userObj.name}"></c:out>
					</li>
				<!-- <li><ul><c:forEach var="roles"
						items="${projectcollaborator.collaboratorRoles}">
						<li><c:out value="${roles.roleid}"></c:out></li>
					</c:forEach></ul></li> -->
			</c:forEach>
		</ul>
</section>