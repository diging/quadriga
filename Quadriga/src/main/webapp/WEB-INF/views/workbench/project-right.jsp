<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>

	<section>
	<c:if test="${not empty project.collaborators}">
	<h2 class="major"><span>Collaborators</span></h2>
		
		<ul class="collaborators">
			<c:forEach var="projectcollaborator" items="${project.collaborators}">
				<li>
						<c:out value="${projectcollaborator.userObj.name}"></c:out>
				</li>
			</c:forEach>
		</ul>
		</c:if>
<!--  
<section>
	<ul class="noStyle">
		<li>
			<article class="is-post-summary">
				<h3>
					» <a href="/quadriga/auth/workbench/${project.internalid}/showCollaborators">Manage Collaborators</a>
				</h3>
			</article>
		</li>	
	</ul>
</section>   -->
</section>

