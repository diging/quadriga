
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


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
	<span class="byline">View versions of the network here.</span>
</header>



<br />
<div class="container">
<c:choose>
		<c:when test="${not empty Versions}">
			<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
				class="display dataTable">
				<thead>
					<tr>
						<th align="left">Version Number</th>
						<th>Name</th>
						<th>Status</th>
						<th>Assigned By</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="version" items="${Versions}">
						<tr>
							<td width="25%" align="center"><c:out
									value="${version.versionNumber}"></c:out></td>
							<td width="25%" align="left"><img style="vertical-align: middle;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/network.png" > <input name="items"
								type="hidden" value="<c:out value="${version.name}"></c:out>" />
								<c:out value="${version.name}"></c:out></td>
							<td width="25%" align="center"><c:out
									value="${version.status}"></c:out></td>
							<td width="25%" align="center"><c:out
									value="${version.assignedUser}"></c:out></td>
							<td width="25%" align="center"><input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/oldversionvisualize/${version.id}/${version.versionNumber}'" value='Visualize'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>	
</c:choose>
</div>
