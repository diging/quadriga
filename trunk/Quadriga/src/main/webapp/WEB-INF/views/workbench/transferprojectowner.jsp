<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${projectid}';
	}

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});
	
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<form:form commandName="collaborator" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${projectid}">
	<c:choose>
		<c:when test="${success=='0'}">
			<c:if test="${not empty collaboratinguser}">
			   <h2>Project: ${projectname}</h2>
			   <hr>
			   <div class="user">Owned by: ${projectowner}</div>
			   <hr>
			   <div>Select owner of the project</div>
				<form:select path="userObj" id="userName">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${collaboratinguser}"
						itemValue="userName" itemLabel="userName" />
				</form:select>
				<form:errors path="userObj" cssClass="error"></form:errors>
				<br>
				<hr>
				 <div>Select a role</div>
				<form:checkboxes path="collaboratorRoles" class="roles"
					items="${projcollabroles}" itemValue="roleDBid"
					itemLabel="displayName" />
				<form:errors path="collaboratorRoles" cssClass="error"></form:errors>
				<br>
				<td><input type="submit" value="Assign"></td>
			</c:if>
			<c:if test="${empty collaboratinguser}">
          You don't have any collaborators assigned to the project.
          <ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Back'></li>
			</ul>	   
		</c:if>
		</c:when>
		<c:otherwise>
			<span class="byline"><c:out value="${successmsg}"></c:out></span>
			<br />
			<ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Back'></li>
			</ul>
		</c:otherwise>
	</c:choose>
</form:form>
