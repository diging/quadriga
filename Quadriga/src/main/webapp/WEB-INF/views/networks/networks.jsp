
<%@ page language="java" contentType="text/html;"%>
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
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});

	function clicknetwork(id, name) {
		window.location.href = "${pageContext.servletContext.contextPath}/auth/networks/visualize/"
				+ id;
	}
</script>



<h2>Networks</h2>
<p>You have access to the following networks.</p>

<p>Are you an editor on a project? You might want to head over to the <a href="${pageContext.servletContext.contextPath}/auth/editing"><span class="glyphicon glyphicon-sunglasses"></span>editing workbench</a>.</p>

<div class="container">
	<c:choose>
		<c:when test="${not empty networkList}">
			<div class="panel panel-default">
				<table style="width: 100%" cellpadding="0" cellspacing="0"
					border="0" class="table">
					<thead>
						<tr>
							<th align="left">Name</th>
							<th>Project</th>
							<th>Workspace</th>
							<th>Status</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="network" items="${networkList}">
							<tr>
								<td width="25%" align="left"><a
									href="${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.networkId}">
								<i class="fa fa-star"></i> <input name="items" type="hidden"
									value="<c:out value="${network.networkName}"></c:out>" /> <c:out
										value="${network.networkName}"></c:out> </a></td>
								<td width="25%"><c:out
										value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out>
								</td>
								<td width="25%"><c:out
										value="${network.networkWorkspace.workspace.workspaceName}"></c:out></td>
								<td width="25%"><c:out value="${network.status}"></c:out></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:when>
		<c:otherwise>
			<spring:message code="empty.networks" />
		</c:otherwise>
	</c:choose>
</div>







