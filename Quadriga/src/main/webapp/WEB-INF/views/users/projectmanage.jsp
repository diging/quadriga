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


<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_ADMIN')">
	
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
						<td class="center"><font size="1"> <input
								type="submit" class="btn btn-primary btn-sm"
								onclick="location.href='${pageContext.servletContext.contextPath}/auth/users/project/addadmin/${project.projectId}"
								value="Add as Admin"></font></td>
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
				</tr>
			</tfoot>
		</table>
	</c:if>
</sec:authorize>

<!-- /Content -->