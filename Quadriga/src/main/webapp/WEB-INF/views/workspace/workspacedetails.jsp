<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
</script>


<table style="width: 100%">
	<tr>
		<!-- Display workspace details -->
		<td>
			<h2>Workspace: ${workspacedetails.workspaceName}</h2>
			<div>${workspacedetails.description}</div>
			<div style="text-align: right">
				<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/updateworkspacedetails/${workspaceid}">
					<img style="vertical-align: text-top;"
					src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/edit.png">
					Edit Workspace
				</a>
			</div>
			<hr>
			<div class="user">
				Owned by: ${workspacedetails.owner.name}
				<c:if test="${owner=='1'}">(<a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/transferworkspaceowner/${workspaceid}">Change</a>)</c:if>
			</div> <c:if test="${owner=='1' and editoraccess=='0' }">
				<img
					src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Workspace 
						(<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/assignEditorRoleToOwner/${workspaceid}">Become
					an Editor</a>)
						
					</c:if> <c:if
				test="${owner=='1' and editoraccess=='1' and projectinherit == '0' }">
				<img
					src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Workspace
					(<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/deleteEditorRoleToOwner/${workspaceid}">Remove
					me as Editor</a>)
					</c:if>
			<hr> <!--  messages for assigning editor access --> <c:choose>
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
			</c:choose> <!-- messages for deleting editor access --> <c:choose>
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

			</c:choose> <br /> <script>
				$(document)
						.ready(
								function() {
									$('a.login-window')
											.click(
													function() {
														location.href = "${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/communities";
													});
								});
			</script> <script>
						function funConfirmDeletion() {
							var pos = [ $(window).width() / 4, 50 ];
							// Define the Dialog and its properties.

							$("#dialog-confirm")
									.html(
											"Are you sure you want to delete the workspace?")
									.dialog(
											{
												resizable : false,
												modal : true,
												title : "Delete Workspace",
												height : 180,
												width : 650,
												position : pos,
												buttons : {
													"Yes" : function() {
														$(this).dialog('close');
														location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteSingleWorkspace/${workspaceid}?projectId=${myprojectid}';
														return false;
													},
													"No" : function() {
														$(this).dialog('close');
														return false;
													}
												}
											});
						}
					</script>

			<div id="dialog-confirm" title="Confirm ?"></div> <c:if
				test="${isDeactivated == true}">
				<a href="#" onclick="return confirmWorkspaceActivation();">
					Activate Workspace</a>&nbsp;&nbsp; 
                        </c:if> <c:if test="${isDeactivated == false }">
				<font color="#CCCCCC" title="The workspace is already activated.">
					Activate Workspace&nbsp;&nbsp; </font>
			</c:if> <c:if test="${isDeactivated == false}">
				<a href="#" onclick="return confirmWorkspaceDeactivation();">
					Deactivate Workspace</a>&nbsp;&nbsp; 
                        </c:if> <c:if test="${isDeactivated == true }">
				<font color="#CCCCCC" title="The workspace is already deactivated.">
					Deactivate Workspace&nbsp;&nbsp; </font>
			</c:if> <c:if test="${!isDeactivated && isArchived}">
				<a href="#" onclick="return confirmArchive(false);">Unarchive
					Workspace</a>&nbsp;&nbsp;
						</c:if> <c:if test="${!isDeactivated && !isArchived}">
				<a href="#" onclick="return confirmArchive(true);">Archive
					Workspace</a>&nbsp;&nbsp;
						</c:if> <c:if test="${isDeactivated == true}">
				<a href="#" onclick="return funConfirmDeletion();">Delete
					Workspace</a>
			</c:if> <c:if test="${isDeactivated == false }">
				<font color="#CCCCCC"
					title="Only deactivated workspaces can be deleted.">Delete
					Workspace</font>
			</c:if> <script>
				function confirmWorkspaceDeactivation() {
					// Define the Dialog and its properties.
					var pos = [ $(window).width() / 4, 50 ];
					$("#dialog-confirm")
							.html(
									"Are you sure you want to deactivate the workspace?")
							.dialog(
									{
										resizable : false,
										modal : true,
										title : "Deactivate Workspace",
										height : 180,
										width : 650,
										position : pos,
										buttons : {
											"Yes" : function() {
												$(this).dialog('close');
												location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/deactivateworkspace?projectid=${myprojectid}';
												return false;
											},
											"No" : function() {
												$(this).dialog('close');
												return false;
											}
										}
									});
				}
			</script> <script>
				function confirmWorkspaceActivation() {
					// Define the Dialog and its properties.
					var pos = [ $(window).width() / 4, 50 ];
					$("#dialog-confirm")
							.html(
									"Are you sure you want to activate the workspace?")
							.dialog(
									{
										resizable : false,
										modal : true,
										title : "Activate Workspace",
										height : 180,
										width : 650,
										position : pos,
										buttons : {
											"Yes" : function() {
												$(this).dialog('close');
												location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/activateWorkspace?projectid=${myprojectid}';
												return false;
											},
											"No" : function() {
												$(this).dialog('close');
												return false;
											}
										}
									});
				}
				function confirmArchive(isArchive) {
					isArchive = !!isArchive;
					var txt = isArchive ? 'Archive' : 'Unarchive';
					var pos = [ $(window).width() / 4, 50 ];
					var url = '${pageContext.servletContext.contextPath}/auth/workbench/${myprojectid}';
					var path = isArchive ? '/archiveworkspace'
							: '/unarchiveworkspace';
					var title = isArchive ? 'Archive Workspace'
							: 'Unarchive Workspace';
					path += '/${workspaceid}';
					console.log(url + path)
					$('#dialog-confirm').html(
							'Are you sure you want to ' + txt
									+ ' this workspace?').dialog({
						resizable : false,
						modal : true,
						title : title,
						height : 180,
						width : 650,
						position : pos,
						buttons : {
							"Yes" : function() {
								$(this).dialog('close');
								location.href = url + path;
								return false;
							},
							"No" : function() {
								$(this).dialog('close');
								return false;
							}
						}
					});
				}
			</script> <c:choose>
				<c:when test="${not empty workspacedetails.workspaceBitStreams}">
					<form id="bitstream" method="POST"
						action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletebitstreams">
						<font size="2"><input type="submit"
							onclick="submitClick();" value="Delete Dspace Files" /> <c:choose>
								<c:when test="${empty dspaceKeys}"></c:when>
							</c:choose></font> <br>
						<table class="display dataTable" style="width: 100%">
							<thead>
								<tr>
									<th></th>
									<th>Item</th>
									<th>File</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="workspaceBitstream"
									items="${workspacedetails.workspaceBitStreams}">
									<tr bgcolor="#E0F0FF">
										<td>
											<div
												id='checkbox_<c:out value="${workspaceBitstream.bitStream.id}"/>'
												class='checkbox_<c:out value="${workspaceBitstream.bitStream.id}"/>'>
												<c:choose>
													<c:when
														test="${not((workspaceBitstream.bitStream.name == 'No Access to File') or (workspaceBitstream.bitStream.name == 'Wrong Dspace Authentication') or (workspaceBitstream.bitStream.name == 'Dspace is Down...')) }">
														<c:choose>
															<c:when
																test="${not(workspaceBitstream.bitStream.name == 'Checking BitStream Access...')}">
																<input type="checkbox" class="checkbox"
																	name="bitstreamids"
																	value="${workspaceBitstream.bitStream.id}" />
															</c:when>
														</c:choose>
													</c:when>
												</c:choose>
											</div>
										</td>
										<td><div
												class='item_<c:out value="${workspaceBitstream.bitStream.id}"/>'
												id='item_<c:out value="${workspaceBitstream.bitStream.id}"/>'>
												<font size="1"><c:out
														value="${workspaceBitstream.bitStream.itemName}"></c:out></font>
											</div></td>
										<td><div
												class='bitstream_<c:out value="${workspaceBitstream.bitStream.id}"/>'
												id='bitstream_<c:out value="${workspaceBitstream.bitStream.id}"/>'>
												<font size="1"><c:out
														value="${workspaceBitstream.bitStream.name}"></c:out></font>
											</div></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th></th>
									<th>Item</th>
									<th>File</th>
								</tr>
							</tfoot>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<br>Workspace does not contain any files from dspace !
				</c:otherwise>
			</c:choose>
			<hr> <strong>Text files in this workspace:</strong>
			<div style="float: right;">
				<img style="vertical-align: middle; padding-bottom: 4px;"
					src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png">
				<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/addtext">Add
					Textfile</a>
			</div> <c:choose>
				<c:when test="${not empty textFileList}">
					<hr>
					<table style="width: 100%" class="display dataTable">
						<thead>
							<tr>
								<th>Text File Name</th>
								<th>Reference</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="textfile" items="${textFileList}">
								<tr>
									<td width="25%" align="center">
									<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/addtext"><c:out
											value="${textfile.fileName}"></c:out></a></td>
									<td width="25%" align="center"><c:out
											value="${textfile.refId}"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<spring:message code="empty.textfiles" />
				</c:otherwise>
			</c:choose>
		</td>
		<hr>

		<hr>
		<!-- Display Networks -->
		<c:choose>
			<c:when test="${not empty networkList}">
				<span class="byline">Networks belonging to this workspace</span>
				<hr>
				<table style="width: 100%" class="display dataTable">
					<thead>
						<tr>
							<th>Name</th>
							<th>Network Owner</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="network" items="${networkList}">
							<tr>
								<td width="25%" align="center"><input name="items"
									type="hidden"
									value="<c:out value="${network.network.networkName}"></c:out>" />
									<c:out value="${network.network.networkName}"></c:out></td>
								<td width="25%" align="center"><c:out
										value="${network.network.creator.userName}"></c:out></td>
								<td width="25%" align="center"><c:out
										value="${network.network.status}"></c:out></td>
								<td width="25%" align="center"><input type=button
									onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.network.networkId}'"
									value='View'></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<spring:message code="empty.networks" />
			</c:otherwise>
		</c:choose>
		</td>

		<!-- Display collaborators -->
		<td style="width: 200px">
			<h3 class="major">
				<span>Collaborators</span>
			</h3> <c:if test="${not empty workspacedetails.workspaceCollaborators}">
				<ul class="collaborators">
					<c:forEach var="workspaceCollaborator"
						items="${workspacedetails.workspaceCollaborators}">
						<li><c:out
								value="${workspaceCollaborator.collaborator.userObj.name}"></c:out>
						</li>
					</c:forEach>
				</ul>
			</c:if> <c:if test="${empty workspacedetails.workspaceCollaborators}">
				There are no collaborators.
			</c:if>

			<div style="border-top: dashed 1px #e7eae8; padding: 5px;">
				<ul class="colltools">
					<li><img
						src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"
						style="vertical-align: middle; padding-bottom: 2px;"> <a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addcollaborators">Add</a></li>
					<li><img
						src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"
						style="vertical-align: middle; padding-bottom: 2px;"> <a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletecollaborators">Delete</a></li>
					<li><img
						src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/pen.png"
						style="vertical-align: middle; padding-bottom: 2px;"> <a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/updatecollaborators">Update</a></li>
				</ul>
			</div>
		</td>
	</tr>
</table>