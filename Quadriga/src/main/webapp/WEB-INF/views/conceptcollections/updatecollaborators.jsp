<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<style type="text/css">
table,td,th,caption {
	border: 1px solid black;
}

th {
	background-color: #E9EEF6;
	color: black;
	font-weight: bold;
}

td {
	background-color: white;
	color: black;
	white-space: wrap;
	overflow: wrap;
	text-overflow: ellipsis;
}

.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
}

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
		
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>
<article class="is-page-content">
	<form:form commandName="collaboratorform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/updatecollaborators"
		id="updateprojcollabform">

<c:choose> 
    <c:when test="${success == '0'}">
		<c:if test="${not empty collaboratorform.collaborators}">
			<header>
			<h2>Modify concept collection collaborator roles</h2>
			<span class="byline">Select roles for the collaborator:</span>
			</header>
			<input type="submit" value='Update' name="updateprojcollab">
			<input type="button" onClick="submitClick(this.id);" value='Cancel'>
			<table style="width: 100%" class="display dataTable"
				id="cccollablist">
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
							<form:input path="collaborators[${status.index}].name" id="collaborators[${status.index}].name" type="hidden"/>
							</td>
										
							<td align="left"><font size="3"> <form:checkboxes
										path="collaborators[${status.index}].collaboratorRoles"
										class="roles" items="${cccollabroles}" itemValue="roleid"
										itemLabel="displayName" /></font>
							<form:errors path="collaborators[${status.index}].collaboratorRoles" cssClass="error"></form:errors>
							</td>			
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty collaboratorform.collaborators}">
		  <span class="byline">No collaborators associated to concept collection</span>
		   <ul>
		  		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Back'></li>
	</ul>
		</c:if>
		 </c:when>
		     <c:otherwise> 
		     <span class="byline">Successfully updated collaborators</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Back'></li>
	</ul>
		     </c:otherwise>
		     	
</c:choose>
	</form:form>
</article>
