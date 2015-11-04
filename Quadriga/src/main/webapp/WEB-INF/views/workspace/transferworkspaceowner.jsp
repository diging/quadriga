<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${projectid}';
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
<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/transferworkspaceowner">
		<c:choose>
		<c:when test="${success=='0'}">
			<c:if test="${not empty collaboratinguser}">
			   <h2>Workspace: ${wsname}</h2>
			   <hr>
			   <div class="user">Owned by: ${wsowner}</div>
			   <hr>
			   <div>Assign new owner to the workspace</div>
				<form:select path="userName">
					<form:option value="" label="--- Select ---" />
					<form:options items="${collaboratinguser}"
						itemValue="userName" itemLabel="userName" />
				</form:select>
				<form:errors path="userName" class="ui-state-error-text"></form:errors>
				<div>Note:Current owner will become workspace admin</div>
				<td><input type="submit" value="Assign"></td>
			</c:if>
			<c:if test="${empty collaboratinguser}">
          You don't have any collaborators assigned to the project.
          <ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Okay'></li>
			</ul>	   
		</c:if>
		</c:when>
		<c:otherwise>
			<span class="byline">Workspace Ownership transferred successfully.</span>
			<br />
			<ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Okay'></li>
			</ul>
		</c:otherwise>
	</c:choose>
</form:form>