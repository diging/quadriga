
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to list the networks
-->

<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "10"
		});
		$("ul.pagination2").quickPagination({
			pageSize : "10"
		});

	});
</script>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>


<header>
	<h2>Editors</h2>
	<span class="byline">List of Networks you could edit.</span>
</header>

<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/approvedandrejectednetworksofuser'"
	value='List of Approved and rejected Network'>

<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/networksAssginedToOtherUsers'"
	value='Networks assigned to other editors'>
<br />

<br />
<span class="byline">Networks you are working on.</span>
<c:choose>
	<c:when test="${not empty assignedNetworkList}">
		<ul class="pagination1">
			<c:forEach var="network" items="${assignedNetworkList}">
				<li><details>
						<summary>
							<a
								href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
								<c:out value="${network.networkName}"></c:out>
							</a>
						</summary>
						<ul>
							<li>Project : <c:out value="${network.networkWorkspace.workspace.workspaceProject.project.projectName}"></c:out></li>
							<li>Workspace : <c:out value="${network.networkWorkspace.workspace.workspaceName}"></c:out></li>
							<li>Submitted by : <c:out
									value="${network.creator.userName}"></c:out>
							</li>
							<li>Status : <c:out value="${network.status}"></c:out></li>
							<li><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/rejectnetwork/${network.networkId}'"
								name='Reject' value='Reject'> <input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/approvenetwork/${network.networkId}'"
								name='Approve' value='Approve'>  <%-- <input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/editnetworks/${network.id}'"
								value='Edit Network'> --%>
								<input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/editnetworks/${network.networkId}'"
								value='Edit Network'>
								<input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/versionhistory/${network.networkId}'"
								value='View History'>
								</li>
						</ul>
					</details></li>
			</c:forEach>
		</ul>

	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

<span class="byline">List of unassigned Networks available for
	all editors.</span>
<c:choose>
	<c:when test="${not empty networkList}">
		<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
			class="display dataTable">
			<thead>
				<tr>
					<th>Name</th>
					<th>Project Name</th>
					<th>Workspace Name</th>
					<th>Status</th>
					<th>Visualize</th>
					<th>Action</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="network" items="${networkList}">
					<tr>
						<td width="15%" align="center"><input name="items"
							type="hidden" value="<c:out value="${network.networkName}"></c:out>" />
							<c:out value="${network.networkName}"></c:out></td>
						<td width="15%" align="center"><c:out
								value="${network.networkWorkspace.workspace.workspaceProject.project.projectName}"></c:out></td>
						<td width="15%" align="center"><c:out
								value="${network.networkWorkspace.workspace.workspaceName}"></c:out></td>
						<td width="15%" align="center"><c:out
								value="${network.status}"></c:out></td>
						<td width="15%" align="center"><input type=button
							onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}'"
							value='View Networks'></td>
						<td width="15%" align="center"><input type=button
							onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/assignuser/${network.networkId}'"
							value='Assign'></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>
