<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}';
}

	$(document).ready(function() {
		
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
		
		$("input[type=submit]").button().click(function(event) {
		});
		
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>
<article class="is-page-content">
	<form:form commandName="collaboratorform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/updatecollaborators"
		id="updatewscollabform">
<c:choose> 
    <c:when test="${success == '0'}">
		<c:if test="${not empty collaboratorform.collaborators}">
			<h2>Modify workspace collaborator roles</h2>
		   <h3>Workspace: ${workspacename}</h3>
           <div>${workspacedesc}</div>
            <hr>
			<span class="byline">Select roles for the collaborator:</span>
			<input type="submit" value='Update' name="updatewscollab">
			<input type="button" onClick="submitClick(this.id);" value='Cancel'>
			<table style="width: 100%" class="display dataTable"
				id="wscollablist">
				<thead>
					<tr>
						<th width="25%">Collaborator</th>
						<th width="75%">Collaborator Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collabuser"
						items="${collaboratorform.collaborators}" varStatus="status">
						<tr>
							<td><font size="3">
							<form:label path="collaborators[${status.index}].name">
							<c:out value="${collabuser.name}"></c:out>
							</form:label>
							</font>
							<form:input path="collaborators[${status.index}].userName" id="collaborators[${status.index}].userName" type="hidden"/>
							</td>
										
							<td align="left"><font size="3"> <form:checkboxes
										path="collaborators[${status.index}].collaboratorRoles"
										class="roles" items="${wscollabroles}" itemValue="id"
										itemLabel="displayName" /></font>
							<form:errors path="collaborators[${status.index}].collaboratorRoles" class="ui-state-error-text"></form:errors>
							</td>			
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty collaboratorform.collaborators}">
		   <span class="byline">No collaborators associated to <c:out value="workspacename"></c:out> workspace</span>
		        <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay'></li>
	</ul>
		</c:if>
		 </c:when>
		     <c:otherwise> 
		     <span class="byline">Successfully updated collaborators for <c:out value="workspacename"> workspace</c:out></span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay'></li>
	</ul>
		     </c:otherwise>
		     	
</c:choose>
	</form:form>
</article>
