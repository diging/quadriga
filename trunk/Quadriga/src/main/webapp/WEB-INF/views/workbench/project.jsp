<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});

		$("ul.pagination1").quickPagination({
			pageSize : "3"
		});
	});
</script>
<h2>Project: ${project.name}</h2>
<div>${project.description}</div>
<hr>
<div class="user">Owned by: ${project.owner.name}</div>
<hr>
<div>
	<c:if test="${not empty workspaceList}">
    	Project associates these workspaces:
    		<ul class="style2 pagination1">
			<c:forEach var="workspace" items="${workspaceList}">
				<li><a href="workspacedetails/${workspace.id}"><c:out
							value="${workspace.name}"></c:out></a> <br> <c:out
						value="${workspace.description}"></c:out></li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty workspaceList}">
			No workspaces are associated yet. You should create one!
		</c:if>
</div>
<hr>
<a href="modifyproject/${project.internalid}"> <input type="button"
	name="Edit" value="Edit" />
</a>
<a href="workspace/${project.internalid}"> <input type="button"
	name="Workspace" value="List Workspace" />
</a>

<section>
	<c:choose>
		<c:when test="${success=='1'}">
			<span class="byline" style="color: #f00;"><c:out
					value="collaborator added successfully"></c:out></span>
			<br />
		</c:when>
	</c:choose>
</section>







