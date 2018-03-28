<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- Content -->

<h2>User Project Management</h2>
<p>You can manage projects belonging to the Quadriga users</p>



<script>
<!-- Script for UI validation of user requests -->
	$(document).ready(function() {
		$('#user-projects').DataTable();
	});
</script>

<form method="POST" action="${pageContext.servletContext.contextPath}/auth/users/manageprojects/addprojectadmin">
<sec:authorize access="hasRole('ROLE_QUADRIGA_USER_ADMIN')">
	
	<c:if test="${not empty projectList}">
		<h3>Projects</h3>
		<table id="user-projects"
			class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Owner</th>
					<th>Created By</th>
					<th>Created Date</th>
					<th>Updated By</th>
					<th>Updated Date</th>
					<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
						<th>Add as Admin</th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="project" items="${projectList}">
					<tr>
						<td>${project.projectName}</td>
						<td>${project.description}</td>
						<td>${project.owner.userName}</td>
						<td>${project.createdBy}</td>
						<td>${project.createdDate}</td>
						<td>${project.updatedBy}</td>
						<td>${project.updatedDate}</td>
						<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
							<td class="center"><input
								type="checkbox" 
								value="${project.projectId}"
								name="projectIds"></td>
						</sec:authorize>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Owner</th>
					<th>Created By</th>
					<th>Created Date</th>
					<th>Updated By</th>
					<th>Updated Date</th>
					<sec:authorize	access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
						<th>Add as Admin</th>
					</sec:authorize>
				</tr>
			</tfoot>
		</table>
	</c:if>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
<input type="submit" class="btn btn-primary btn-sm" value="Add as Admin"/>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</sec:authorize>
</form>
<!-- /Content -->