<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "5"
		});
	});
</script>	
	<header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
	</header>
    	<c:if test="${not empty projectlist}">
    	You are the owner of the following projects:
    		<ul class="style2 pagination1">
   	 			<c:forEach var="project" items="${projectlist}">
					<li>
						<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out value="${project.name}"></c:out></a>
						<br><c:out value="${project.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlist}">
			You don't have any projects yet. You should create one!
		</c:if>
	
	
	
	
	
	
	
	
	
	
	