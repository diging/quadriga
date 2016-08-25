
<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to list the assigned networks
-->

<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>

<h2>Networks assigned to other Editors</h2>
<div class="back-nav">
    <hr>
    <p>
        <a href="${pageContext.servletContext.contextPath}/auth/editing"><i
            class="fa fa-arrow-circle-left"></i> Back to Network Editing Workbench</a>
    </p>
    <hr>
</div>

<p>The following networks are assigned to other editors:</p>
<c:choose>
	<c:when test="${not empty networkList}">
		<table 
			id="assignedNetworks" class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Name</th>
					<th>Assigned User</th>
					<th>Project Name</th>
					<th>Workspace Name</th>
					<th>Status</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="network" items="${networkList}">
					<tr>
						<td width="15%" ><input name="items"
							type="hidden" value="<c:out value="${network.networkName}"></c:out>" />
							<a href="${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.networkId}"><c:out value="${network.networkName}"></c:out></a></td>
						<td ><c:out
								value="${network.assignedUser}"></c:out></td>

						<td ><c:out
								value="${network.networkWorkspace.workspace.project.projectName}"></c:out></td>
						<td ><c:out
								value="${network.networkWorkspace.workspace.workspaceName}"></c:out></td>
						<td ><c:out
								value="${network.status}"></c:out></td>
						

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>
