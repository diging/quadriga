
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to list the assigned networks
-->

<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "5"
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

</header>

<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing'"
	value='Back'>

<span class="byline">Networks you have approved.</span>
<c:choose>
	<c:when test="${not empty ApprovedNetworkList}">
		<ul class="pagination1">
			<c:forEach var="network" items="${ApprovedNetworkList}">
				<li>
					<details>
						<summary><a
							href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.id}">
								<c:out value="${network.name}"></c:out>
						</a></summary>
						<ul>
						<li>Project : <c:out value="${network.projectName}"></c:out></li>
						<li>Workspace : <c:out value="${network.workspaceName}"></c:out></li>
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


<span class="byline">Networks you have rejected.</span>
<c:choose>
	<c:when test="${not empty RejectedNetworkList}">
		<ul class="pagination1">
			<c:forEach var="network" items="${RejectedNetworkList}">
				<li>
					<details>
						<summary><a
							href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.id}">
								<c:out value="${network.name}"></c:out>
						</a></summary>
						<ul>
						<li>Project : <c:out value="${network.projectName}"></c:out></li>
						<li>Workspace : <c:out value="${network.workspaceName}"></c:out></li>
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
