<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/users/updateroles';
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
	<form:form commandName="userrolesform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/users/updateroles"
		id="updatequadrigarolesform">
<c:choose> 
    <c:when test="${success == '0'}">
		<c:if test="${not empty userrolesform.users}">
			<h2>Modify Quadriga roles</h2>
            <hr>
			<span class="byline">Select quadriga roles for the user:</span>
			<input type="submit" value='Update' name="updatequdrigaroles">
			<input type="button" onClick="submitClick(this.id);" value='Cancel'>
			<table style="width: 100%" class="display dataTable"
				id="quadrigarolelist">
				<thead>
					<tr>
						<th width="25%">User</th>
						<th width="75%">Quadriga Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="myuser"
						items="${userrolesform.users}" varStatus="status">
						<tr>
							<td><font size="3">
							<form:label path="users[${status.index}].name">
							<c:out value="${myuser.name}"></c:out>
							</form:label>
							</font>
							<form:input path="users[${status.index}].userName" id="users[${status.index}].userName" value="${myuser.userName}" type="hidden"/>
							</td>
										
							<td align="left"><font size="3"> <form:checkboxes
										path="users[${status.index}].quadrigaRoles"
										class="roles" items="${quadrigaroles}" itemValue="DBid"
										itemLabel="name" /></font>
							<form:errors path="users[${status.index}].quadrigaRoles" class="ui-state-error-text"></form:errors>
							</td>			
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty userrolesform.users}">
		   <span class="byline">There are no active users</span>
		        <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay'></li>
	</ul>
		</c:if>
		 </c:when>
		     <c:otherwise> 
		     <span class="byline">Successfully updated quadriga roles</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay'></li>
	</ul>
		     </c:otherwise>
		     	
</c:choose>
	</form:form>
</article>
