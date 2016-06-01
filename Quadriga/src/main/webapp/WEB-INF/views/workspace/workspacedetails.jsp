<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});

		$("input[type=submit]").button().click(function(event) {
			event.preventDefault();
		});

	});

	$(document)
			.ready(
					function() {
						$('#txtModal')
								.on(
										'show.bs.modal',
										function(event) {
											var link = $(event.relatedTarget);

											var txtid = link.data('txtid');
											var txtname = link.data('txtname');
											$
													.ajax({
														type : "GET",
														url : "${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/viewtext?txtid="
																+ txtid,
														contentType : "text/plain",
														success : function(
																details) {
															$('.modal-title')
																	.text(
																			txtname);
															$('.modal-body')
																	.text(
																			details);
														},

														error : function(xhr,
																ajaxOptions) {
															if (xhr.status == 404) {
																$('.modal-body')
																		.text(
																				"Error while retrieving the text content.");
															}
														}
													});

										});
					});
</script>


<div class="row">
	<div class="col-md-9">
		<!-- Display workspace details -->
		<h2>
			<i class="ion-filing icons"></i> Workspace:
			${workspacedetails.workspaceName}
		</h2>
		<c:if test="${isDeactivated == true }">
			<div style="margin-bottom: 15px;">
				<span class="label label-default"
					title="This workspace is deactivated."><i
					class="fa fa-toggle-off"></i> Deactivated</span>
			</div>
		</c:if>
		<div>${workspacedetails.description}</div>
		<c:if test="${owner=='1' || wsadmin=='1'}">
			<div style="text-align: right">
				<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/update">
					<i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
					Workspace
				</a>
			</div>
		</c:if>
		<hr>
		<div class="user">
			Owned by: ${workspacedetails.owner.name}
			<c:if test="${owner=='1'}">(<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/transfer">Change</a>)</c:if>
		</div>
		<c:if test="${owner=='1' and editoraccess=='0' }">
			<img
				src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Workspace 
						(<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/assignEditorRoleToOwner/${workspaceid}">Become
				an Editor</a>)
						
					</c:if>
		<c:if
			test="${owner=='1' and editoraccess=='1' and projectinherit == '0' }">
			<img
				src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Workspace
					(<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/deleteEditorRoleToOwner/${workspaceid}">Remove
				me as Editor</a>)
					</c:if>
		<hr>
		<!--  messages for assigning editor access -->
		<c:choose>
			<c:when test="${AssignEditorSuccess=='1'}">
				<font color="blue"> <spring:message
						code="workspace.assign.owner.editor.success" /></font>

			</c:when>
			<c:when test="${AssignEditorSuccess=='0'}">
				<font color="red"> <spring:message
						code="workspace.assign.owner.editor.failure" /></font>
			</c:when>
			<c:when test="${AssignEditorSuccess=='2'}">
				<font color="red"> <spring:message
						code="workspace.assign.owner.editor.assigned" /></font>
			</c:when>
		</c:choose>
		<!-- messages for deleting editor access -->
		<c:choose>
			<c:when test="${DeleteEditorSuccess=='1'}">
				<font color="blue"> <spring:message
						code="workspace.delete.owner.editor.success" /></font>

			</c:when>
			<c:when test="${DeleteEditorSuccess=='0'}">
				<font color="red"> <spring:message
						code="workspace.delete.owner.editor.failure" /></font>
			</c:when>
			<c:when test="${DeleteEditorSuccess=='2'}">
				<font color="red"> <spring:message
						code="workspace.delete.owner.editor.assigned" /></font>
			</c:when>

		</c:choose>
		<br />


		<hr>
		<h4>Text files in this workspace:</h4>
		<div class="pull-right">
			<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/addtext">
				<i class="fa fa-plus-circle" aria-hidden="true"></i> Add Textfile
			</a>
		</div>


		<c:choose>
			<c:when test="${not empty textFileList}">

				<div class="panel panel-default" style="clear: both;">
					<table style="width: 100%" class="table">
						<thead>
							<tr>
								<th>Text File Name</th>
								<th>Reference</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="textfile" items="${textFileList}">
								<tr>
									<td width="25%" align="left"><a data-toggle="modal"
										data-target="#txtModal" data-txtid="${textfile.textId}"
										data-txtname="${textfile.fileName}"><c:out
												value="${textfile.fileName}"></c:out></a></td>
									<td width="25%" align="left"><c:out
											value="${textfile.refId}"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<spring:message code="empty.textfiles" />
			</c:otherwise>
		</c:choose>
		<hr>
		<!-- Display Networks -->
		<c:choose>
			<c:when test="${not empty networkList}">
				<h4>Networks belonging to this workspace:</h4>

				<div class="panel panel-default">
					<table style="width: 100%" class="table">
						<thead>
							<tr>
								<th>Name</th>
								<th>Created by</th>
								<th>Status</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="network" items="${networkList}">
								<tr>
									<td width="25%"><a
										href="${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.network.networkId}"><i
											class="fa fa-star"></i> <c:out
												value="${network.network.networkName}"></c:out></a></td>
									<td width="25%"><c:out
											value="${network.network.creator.userName}"></c:out></td>
									<td width="25%"><c:out value="${network.network.status}"></c:out></td>

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

	<div class="col-md-3">
		<h3 class="major">
			<span>Collaborators</span>
		</h3>
		<c:if test="${not empty workspacedetails.workspaceCollaborators}">
			<div style="padding: 5px;">
				<c:forEach var="workspaceCollaborator"
					items="${workspacedetails.workspaceCollaborators}">
					<i class="fa fa-user" aria-hidden="true"></i>
					<c:out value="${workspaceCollaborator.collaborator.userObj.name}"></c:out>
					<br>

				</c:forEach>
			</div>
		</c:if>
		<c:if test="${empty workspacedetails.workspaceCollaborators}">
				There are no collaborators.
			</c:if>
		<c:if test="${owner=='1' || wsadmin=='1'}">

			<div style="border-top: dashed 1px #e7eae8; padding: 5px;">
				<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addcollaborators">
					<i class="fa fa-user-plus" aria-hidden="true"></i> Add
				</a><br> <a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletecollaborators">
					<i class="fa fa-user-times" aria-hidden="true"></i> Delete
				</a><br> <a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/updatecollaborators">
					<i class="fa fa-users" aria-hidden="true"></i> Update
				</a>
			</div>
		</c:if>
	</div>
</div>

<!--  Modals -->
<!--  add texts modal -->
<div class="modal text-modal" id="txtModal" tabindex="-1" role="dialog"
	aria-labelledby="txtModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content ">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Deactivate workspace modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="deactivate-ws">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Deactivate Workspace</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to deactivate the workspace?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" id="deactivate-btn">Yes,
					deactivate!</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Activate Workspace Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="activate-ws">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Activate Workspace</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to activate the workspace?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" id="activate-btn">Yes,
					activate!</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Delete Workspace Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="delete-ws">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Delete Workspace</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete the workspace? This action
					cannot be undone.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" id="delete-btn">Yes,
					delete!</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
	$(document)
			.ready(
					function() {
						$('#deactivate-btn')
								.click(
										function(event) {
											location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/deactivateworkspace?projectid=${myprojectid}';
										});
						$('#activate-btn')
								.click(
										function(event) {
											location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/activateWorkspace?projectid=${myprojectid}';
										});
						$('#delete-btn')
								.click(
										function(event) {
											location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteSingleWorkspace/${workspaceid}?projectId=${myprojectid}';
										});

					});
</script>