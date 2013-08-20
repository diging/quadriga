<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});

		$("ul.pagination1").quickPagination({
			pageSize : "3"
		});
	});
</script>



<table style="width: 100%">
	<tr>
		<!-- Display project details -->
		<td style="width: 90%">
			<h2>Project: ${project.name}</h2>
			<div>${project.description}</div>
			<hr>
			<div class="user">Owned by: ${project.owner.name}</div>
			<hr> <!--  Display associated workspace -->
			<section>
				<c:if test="${not empty workspaceList}">
    	Project associates these workspaces:
    		<ul class="style2 pagination1">
						<c:forEach var="workspace" items="${workspaceList}">
							<li><a
								href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.id}"><c:out
										value="${workspace.name}"></c:out></a> <br> <c:out
									value="${workspace.description}"></c:out></li>
						</c:forEach>
					</ul>
				</c:if>
				<c:if test="${empty workspaceList}">
			No workspaces are associated yet. You should create one!
		</c:if>
			</section> <c:choose>
				<c:when test="${AssignEditorSuccess=='1'}">
					<font color="blue"> <spring:message
							code="project.assign.owner.editor.success" /></font>

				</c:when>
				<c:when test="${AssignEditorSuccess=='0'}">
					<font color="red"> <spring:message
							code="project.assign.owner.editor.failure" /></font>
				</c:when>
				<c:when test="${AssignEditorSuccess=='2'}">
					<font color="red"> <spring:message
							code="project.assign.owner.editor.assigned" /></font>
				</c:when>
			</c:choose>
			<div align="left">
				<hr>
				<a href="modifyproject/${project.internalid}"> <input
					type="button" name="Edit" value="Edit" />
				</a>
				<c:choose>
					<c:when test="${owner=='1'}">
						<c:choose>
							<c:when test="${editoraccess=='0' }">
								<a
									href="${pageContext.servletContext.contextPath}/auth/workbench/assignownereditor/${project.internalid}">
									<input type="button" name="Get Editor Role"
									value="Get Editor Role" />
								</a>
							</c:when>
						</c:choose>

					</c:when>
				</c:choose>
			</div>
		</td>
		<!-- Display collaborators -->
		<td style="width: 10%">
			<section>
				<c:if test="${not empty project.collaborators}">
					<h3 class="major">
						<span>Collaborators</span>
					</h3>
					<ul class="collaborators">
						<c:forEach var="projectcollaborator"
							items="${project.collaborators}">
							<li><c:out value="${projectcollaborator.userObj.name}"></c:out>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</section>
		</td>
	</tr>
</table>