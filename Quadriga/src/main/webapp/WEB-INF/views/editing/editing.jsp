
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

<input type=button onClick="location.href=''" value='List of Assigned Network'>
<br/>

<br />
<div class="container">
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
								type="hidden" value="<c:out value="${network.name}"></c:out>" />
								<c:out value="${network.name}"></c:out></td>
							<td width="15%" align="center"><c:out
									value="${network.projectName}"></c:out></td>
							<td width="15%" align="center"><c:out
									value="${network.workspaceName}"></c:out></td>
							<td width="15%" align="center"><c:out
									value="${network.status}"></c:out></td>
							<td width="15%" align="center"><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.id}'" value='Visualize'></td>
							<td width="15%" align="center"><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/assignuser/${network.id}'" value='Assign'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<spring:message code="empty.networks" />
		</c:otherwise>
	</c:choose>
</div>
