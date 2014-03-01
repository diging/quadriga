
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

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

	$(function() {
		$('#html1').jstree();
		$('#html2').jstree();
		var data = ${core};
		$('#html3').jstree(data
		);
	});
</script>


<header>
	<h2>Networks</h2>
	<span class="byline">View your Networks here.</span>
</header>

<div id="html3"> </div>
<br />
<br />

<br />
<div class="container">
	<c:choose>
		<c:when test="${not empty networkList}">
			<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
				class="display dataTable">
				<thead>
					<tr>
						<th align="left">Name</th>
						<th>Project</th>
						<th>Workspace</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="network" items="${networkList}">
						<tr>
							<td width="25%" align="left"><img
								style="vertical-align: middle;"
								src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/network.png">
								<input name="items" type="hidden"
								value="<c:out value="${network.name}"></c:out>" /> <c:out
									value="${network.name}"></c:out></td>
							<td width="25%" align="center"><c:out
									value="${network.project.name}"></c:out></td>
							<td width="25%" align="center"><c:out
									value="${network.workspace.name}"></c:out></td>
							<td width="25%" align="center"><c:out
									value="${network.status}"></c:out></td>
							<td width="25%" align="center"><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.id}'"
								value='Visualize'></td>
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


<div id="html2">
  <ul>
    <li>Root node 1
      <ul>
        <li>Child node 1</li>
        <li><a href="#">Child node 2</a></li>
      </ul>
    </li>
  </ul>
</div>






