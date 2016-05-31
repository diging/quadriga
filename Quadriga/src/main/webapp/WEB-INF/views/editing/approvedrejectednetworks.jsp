
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
	$(document).ready(function() {
	    $('#approvedNetworks').DataTable();
	    $('#rejectedNetworks').DataTable();
	} );
</script>


<h2>Approved and Rejected Networks</h2>
<div class="back-nav">
	<hr>
	<p>
		<a href="${pageContext.servletContext.contextPath}/auth/editing"><i
			class="fa fa-arrow-circle-left"></i> Back to Network Editing Workbench</a>
	</p>
	<hr>
</div>



<h3>You approved the following networks:</h3>
<c:choose>
	<c:when test="${not empty ApprovedNetworkList}">
		<table id="approvedNetworks" class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Name</th>
					<th>Project Name</th>
					<th>Workspace Name</th>
					<th>Status</th>
					<th>Submitted By</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="network" items="${ApprovedNetworkList}">
					<tr>
						<td><a
							href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
								<i class="fa fa-star"></i> <c:out value="${network.networkName}"></c:out>
						</a></td>
						<td><c:out
								value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out>
						</td>
						<td><c:out
								value="${network.networkWorkspace.workspace.workspaceName}"></c:out>
						</td>
						<td><c:out value="${network.status}"></c:out></td>
						<td><c:out value="${network.creator.userName}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<p>
			<spring:message code="empty.networks" />
		</p>
	</c:otherwise>
</c:choose>


<h3>Networks you have rejected:</h3>
<c:choose>
	<c:when test="${not empty RejectedNetworkList}">
		<table id="rejectedNetworks" class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Name</th>
					<th>Project Name</th>
					<th>Workspace Name</th>
					<th>Status</th>
					<th>Submitted By</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="network" items="${RejectedNetworkList}">
					<tr>
						<td><a
							href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
								<i class="fa fa-star"></i> <c:out value="${network.networkName}"></c:out>
						</a></td>
						<td><c:out
								value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out>
						</td>
						<td><c:out
								value="${network.networkWorkspace.workspace.workspaceName}"></c:out>
						</td>
						<td><c:out value="${network.creator.userName}"></c:out></td>
						<td><c:out value="${network.status}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</c:when>
	<c:otherwise>
		<p>
			<spring:message code="empty.networks" />
		</p>
	</c:otherwise>
</c:choose>
