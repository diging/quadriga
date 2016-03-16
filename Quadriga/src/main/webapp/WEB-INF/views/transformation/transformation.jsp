<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Jaydatta Nagarkar, Jaya-Venkata Vutukuri  
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
	<h2>Transformations</h2>
	<span class="byline">List of approved Networks</span>
</header>
<span class="byline">Networks you have approved.</span>
<c:choose>
	<c:when test="${not empty ApprovedNetworkList}">
		<ul class="pagination1">
			<c:forEach var="network" items="${ApprovedNetworkList}">
				<li>
					<details>
						<summary><a
							href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
								<c:out value="${network.networkName}"></c:out>
						</a></summary>
						<ul>
						<li>Project : <c:out value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out></li>
						<li>Workspace : <c:out value="${network.networkWorkspace.workspace.workspaceName}"></c:out></li>
						<li>Submitted by : <c:out value="${network.creator.userName}"></c:out> </li>
						<li>Status : <c:out value="${network.status}"></c:out></li>
						
					</ul></details>
				</li>
			</c:forEach>
		</ul>

	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

<span class="byline">List of available Transformations.</span>
<c:choose>
	<c:when test="${not empty dummyTransformations}">
		<ul class="pagination1">
			<c:forEach var="network" items="${dummyTransformations}">
				<li>
					<c:out value="${network}"></c:out>
				</li>
			</c:forEach>
		</ul>
	</c:when>	
</c:choose>
<br />
<br />
