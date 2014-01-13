
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
	<h2>Networks</h2>
	<span class="byline">View your Networks here.</span>
</header>



<br />
<div class="container">
		<c:when test="${not empty networkList}">
			<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
				class="display dataTable">
				<thead>
					<tr>
						<th align="left">Name</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="network" items="${networkList}">
						<tr>
							<td><c:out value="${network}" /></td>
							<td width="25%" align="center"><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.id}'" value='Visualize'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>	
</div>
