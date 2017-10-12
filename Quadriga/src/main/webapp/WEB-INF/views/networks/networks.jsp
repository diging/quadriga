
<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!--  
	Author Lohith Dwaraka  
	Used to list the networks
-->

<script>
$(document).ready(function() {
    $('#networks').DataTable();
} );
</script>


<h2>Networks</h2>
<p>You have access to the following networks.</p>

<p>
	Are you an editor on a project? You might want to head over to the <a
		href="${pageContext.servletContext.contextPath}/auth/editing"><span
		class="ion-ios-glasses-outline icons"></span> editing workbench</a>.
</p>

<div class="container">
	<c:choose>
		<c:when test="${not empty networkList}">
				<table id="networks" class="table table-striped table-bordered table-white">
				<thead>
					<tr>
						<th data-sortable="true">Name</th>
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
										value="${network.networkName}"></c:out>
							</a></td>
							<td width="25%"><c:out
									value="${network.workspace.project.projectName}"></c:out>
							</td>
							<td width="25%"><c:out
									value="${network.workspace.workspaceName}"></c:out></td>
							<td width="25%"><c:out value="${network.status}"></c:out></td>

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







