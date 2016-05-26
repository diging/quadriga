
<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>


<h2>Network Version History</h2>

<div class="back-nav">
	<hr>
	<p>
		<a href="${pageContext.servletContext.contextPath}/auth/editing"><i
			class="fa fa-arrow-circle-left"></i> Back to Network Editing
			Workbench</a>
	</p>
	<hr>
</div>

<p>These are all the version of the network.</p>



<c:choose>
	<c:when test="${not empty Versions}">
		<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
			class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th width="150px">Version Number</th>
					<th>Name</th>
					<th>Status</th>
					<th>Assigned To</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="version" items="${Versions}">
					<tr>
						<td><c:out value="${version.versionNumber}"></c:out></td>
						<td><a
							href="${pageContext.servletContext.contextPath}/auth/editing/oldversionvisualize/${version.networkId}/${version.versionNumber}"><i
								class="fa fa-star"></i> <c:out value="${version.networkName}"></c:out></a></td>
						<td><c:out value="${version.status}"></c:out></td>
						<td><c:out value="${version.assignedUser}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
</c:choose>
