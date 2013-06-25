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
	    	<c:if test="${not empty workspaceList}">
    	Project associates these workspaces:
    		<ul class="style2 pagination1">
   	 			<c:forEach var="workspace" items="${workspaceList}">
					<li>
						<a href="workspace/${workspace.id}"><c:out value="${workspace.name}"></c:out></a>
						<br><c:out value="${workspace.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty workspaceList}">
			No workspaces are associated yet. You should create one!
		</c:if>