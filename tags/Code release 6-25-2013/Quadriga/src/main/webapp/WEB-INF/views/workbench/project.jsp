<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function() {
	$("input[type=button]").button().click(function(event) {
            return;
	});
});

</script>

<h2>Project: ${project.name}</h2>
<div>${project.description}</div>
<hr>
<div class="user">Owned by: ${project.owner.name}</div>
<hr>
<a href="modifyproject/${project.internalid}">
<input type="button" name="Edit" value="Edit"/>
</a>
<a href="workspace/${project.internalid}">
<input type="button" name="Workspace" value="List Workspace"/>
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


	




