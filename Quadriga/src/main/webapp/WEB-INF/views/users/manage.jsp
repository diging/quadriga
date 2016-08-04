<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- Content -->

<h2>User Management</h2>
<p>You can manage Quadriga users and their permissions here.</p>



<script>
<!-- Script for UI validation of user requests -->
	$(document).ready(function() {
		$('#registered-users').DataTable();
		$('#deactivated-users').DataTable();
		$('#delete-users').DataTable();
	});
</script>


<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_ADMIN')">
	<c:if test="${not empty userRequestsList}">
		<h3>User Account Requests</h3>
		<p>The following users requested accounts:</p>
		<c:forEach var="userreq" items="${userRequestsList}">

			<div class="panel panel-default">
				<div class="panel-heading">
					${userreq.name} (${userreq.userName})
				</div>

				<div class="panel-body">
					<form:form commandName="approveAccount" method="POST"
						action="${pageContext.servletContext.contextPath}/auth/users/access/handleRequest">
						<form:input type="hidden" path="username" value="${userreq.userName}" />

						<div>
							<label class="radio-inline"><form:radiobutton
									path="action" value="approve" /> Approve</label> <br> <span
								style="margin-left: 40px;"></span>
							<c:forEach var="role" items="${userRoles}">
								<label class="checkbox-inline"><form:checkbox
										path="roles" label="${role.displayName}"
										value="${role.DBid}" /></label>
							</c:forEach>
						</div>


						<div class="radio">
							<label><form:radiobutton path="action" value="deny" />
								Deny </label>
						</div>
						<br>
						<input class="btn btn-primary btn-sm" type="submit" value="Submit"></input>
					</form:form>
				</div>
			</div>
		</c:forEach>
	</c:if>


	<c:if test="${not empty activeUserList}">
		<h3>Current Active Users</h3>
		<a style="margin-bottom: 15px;" class="btn btn-primary"
			href='${pageContext.servletContext.contextPath}/auth/users/updateroles'>Update
			Roles</a>



		<table id="registered-users"
			class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Username</th>
					<th>Email</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${activeUserList}">
					<tr>
						<td width="20%"><font size="3"><c:out
									value="${user.userName}"></c:out></font></td>
						<td>${user.email}</td>
						<td class="center"><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Admin')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td class="center"><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Standard User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td class="center"><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Restricted User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td class="center"><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Collaborating User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td class="center"><font size="1"> <input
								type="submit" class="btn btn-primary btn-sm"
								onclick="location.href='${pageContext.servletContext.contextPath}/auth/users/deactivate/${user.userName}'"
								value="Deactivate"></font></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th>Username</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</tfoot>
		</table>
	</c:if>

	<br />


	<c:if test="${not empty inactiveUserList}">
		<h3>Deactivated User Accounts</h3>
		<table id="deactivated-users"
            class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Username</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${inactiveUserList}">
					<tr>
						<td width="20%"><font size="3"><c:out
									value="${user.userName}"></c:out></font></td>
						<td ><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Admin')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if>
						</td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">
								<c:if test="${role.name eq ('Quadriga Standard User')}">
									<c:set var="flag" value="1" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">
								<c:if test="${role.name eq ('Quadriga Restricted User')}">
									<c:set var="flag" value="1" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">
								<c:if test="${role.name eq ('Quadriga Collaborating User')}">
									<c:set var="flag" value="1" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if>
						</td>
						<td><font size="1"> <input
								type="submit" class="btn btn-primary btn-sm"
								onclick="location.href='${pageContext.servletContext.contextPath}/auth/users/activate/${user.userName}'"
								value="Activate"></font></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th>Username</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</tfoot>
		</table>
	</c:if>
	<br />
	<c:if test="${not empty inactiveUserList}">
		<h3>Delete User Accounts</h3>
		<table id="delete-users"
            class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Username</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${inactiveUserList}">
					<tr>
						<td width="20%"><font size="3"><c:out
									value="${user.userName}"></c:out></font></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Admin')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Standard User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Restricted User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><c:set var="flag" value="0" /> <c:forEach
								items="${user.quadrigaRoles}" var="role">

								<c:if test="${role.name eq ('Quadriga Collaborating User')}">
									<c:set var="flag" value="1" scope="page" />
								</c:if>
							</c:forEach> <c:if test="${flag==0}">No</c:if> <c:if test="${flag==1}">Yes</c:if></td>
						<td><input type="submit" class="btn btn-primary btn-sm"
							onclick="location.href='${pageContext.servletContext.contextPath}/auth/users/delete/${user.userName}'"
							value="Delete"></font></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th>Username</th>
					<th>Admin</th>
					<th>Standard User</th>
					<th>Restricted User</th>
					<th>Collaborator</th>
					<th>Action</th>
				</tr>
			</tfoot>
		</table>
	</c:if>

</sec:authorize>

<!-- /Content -->