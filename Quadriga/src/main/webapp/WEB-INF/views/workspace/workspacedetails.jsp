<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:choose>
	<c:when test="${not empty workspacedetails.workspaceBitStreams}">
		<script>
			function submitClick() {
				if ($('input:checkbox').is(':checked')) {
					$('#bitstream').submit();
				} else {
					$.alert("Please select atleast one file", "Oops !!!");
					return;
				}

			}

			$(document)
					.ready(
							function() {

								function loadItemName() {
									var divIDs = $("div[class^='item']") // find divs with ID attribute
									.map(function() {
										return this.id;
									}) // convert to set of IDs
									.get();

									var i = 0;
									var IDs = [];
									for (i = 0; i < divIDs.length; i++) {
										if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_item" />') {
											IDs.push(divIDs[i]);
										}
									}

									$
											.each(
													$.unique(IDs),
													function() {
														var collectionid = this
																.split("_");
														var ajaxCallback = getItemName(collectionid[1]);

														//Do this once the data is available
														ajaxCallback
																.success(function(
																		data) {
																	//Load the new text in the corresponding div tag
																	if (data != 'Loading...') {
																		data = '<font size="1">'
																				+ data
																				+ '</font>';
																		$(
																				'.item_'
																						+ collectionid[1])
																				.html(
																						data);
																	}
																});//End of ajax callback
													});
								}

								loadItemName();

								function loadBitStreamName() {
									var divIDs = $("div[class^='bitstream']") // find divs with ID attribute
									.map(function() {
										return this.id;
									}) // convert to set of IDs
									.get();

									var i = 0;
									var IDs = [];
									for (i = 0; i < divIDs.length; i++) {
										if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_bitstream" />') {
											IDs.push(divIDs[i]);
										}
									}

									$
											.each(
													$.unique(IDs),
													function() {
														var collectionid = this
																.split("_");
														var ajaxCallback = getBitStreamName(collectionid[1]);

														//Do this once the data is available
														ajaxCallback
																.success(function(
																		data) {
																	//Load the new text in the corresponding div tag
																	if (data != 'Loading...') {
																		if (data != 'No Access to File') {
																			$(
																					'.checkbox_'
																							+ collectionid[1])
																					.html(
																							'<input type="checkbox" class="checkbox" name="bitstreamids" value="'+collectionid[1]+'" />');
																		}
																		data = '<font size="1">'
																				+ data
																				+ '</font>';
																		$(
																				'.bitstream_'
																						+ collectionid[1])
																				.html(
																						data);

																	}
																});//End of ajax callback
													});
								}

								loadBitStreamName();

								/**
								 * Function to check if there is any item name yet to be loaded.
								 * If yes, then it will invoke the loadItemName() after a wait period of 5 seconds.
								 * Author: Ram Kumar Kumaresan
								 */
								function checkItemDiv() {
									var divIDs = $("div[id^='item']") // find divs with ID attribute
									.map(function() {
										return this.id;
									}) // convert to set of IDs
									.get();

									var i = 0;
									var IDs = [];
									for (i = 0; i < divIDs.length; i++) {
										if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_item" />') {
											IDs.push(divIDs[i]);
										}
									}
									if (IDs.length > 0) {
										setTimeout(loadItemName, 5000);
										setTimeout(checkItemDiv, 7000);
									}
								}
								setTimeout(checkItemDiv, 1000);

								/**
								 * Function to check if there is any bitstream name yet to be loaded.
								 * If yes, then it will invoke the loadItemName() after a wait period of 5 seconds.
								 * Author: Ram Kumar Kumaresan
								 */
								function checkBitStreamDiv() {
									var divIDs = $("div[id^='bitstream']") // find divs with ID attribute
									.map(function() {
										return this.id;
									}) // convert to set of IDs
									.get();

									var i = 0;
									var IDs = [];
									for (i = 0; i < divIDs.length; i++) {
										if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_bitstream" />') {
											IDs.push(divIDs[i]);
										}
									}
									if (IDs.length > 0) {
										setTimeout(loadBitStreamName, 5000);
										setTimeout(checkBitStreamDiv, 7000);
									}
								}
								setTimeout(checkBitStreamDiv, 1000);
							});

			/*
			 * Function used to make an ajax call to the controller, inorder to get the item name
			 */
			function getItemName(bitstreamid) {
				return $
						.ajax({
							type : 'GET',
							url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/itemstatus?bitstreamid='
									+ bitstreamid,
							error : function(jqXHR, textStatus, errorThrown) {
								$('#item_' + bitstreamid).html(
										"Server not responding...");
							}
						});
			}

			/*
			 * Function used to make an ajax call to the controller, inorder to get the bitstream name
			 */
			function getBitStreamName(bitstreamid) {
				return $
						.ajax({
							type : 'GET',
							url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/bitstreamaccessstatus?bitstreamid='
									+ bitstreamid,
							error : function(jqXHR, textStatus, errorThrown) {
								$('#bitstream_' + bitstreamid).html(
										"Server not responding...");
							}
						});
			}
		</script>
	</c:when>
</c:choose>
<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});

		$("input[type=submit]").button().click(function(event) {
			event.preventDefault();
		});

		$('#dspacePublicAccess').click(function publicAccess() {
			if ($(this).is(':checked')) {
				$("#username").val('');
				$("#password").val('');
				$("#username").attr("disabled", "disabled");
				$("#username").attr("placeholder", "Input Disabled");
				$("#password").attr("disabled", "disabled");
				$("#password").attr("placeholder", "Input Disabled");
			} else {
				$("#username").removeAttr("disabled");
				$("#username").attr("placeholder", "Username");
				$("#password").removeAttr("disabled");
				$("#password").attr("placeholder", "Password");
			}
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
			</c:choose> <br /> <c:choose>
				<c:when test="${empty dspaceKeys}">
					<!-- Dspace Login popup -->
					<script>
						$(document)
								.ready(
										function() {

											$('a.login-window')
													.click(
															function() {
																location.href = "${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/communities";
															});

										});
					</script>
				</c:when>
				<c:otherwise>
					<script>
						$(document)
								.ready(
										function() {

											$('a.login-window')
													.click(
															function() {
																location.href = "${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/communities";
															});

										});
					</script>
				</c:otherwise>
			</c:choose> <c:if test="${isDeactivated == false}">
                <a href="#login-box" class="login-window"><input
                    type="submit" value="Add text from Dspace"></a>
                <!-- DSpace Login credentials -->
                </a>
            </c:if>
            </li> <c:choose>
                <c:when test="${empty dspaceKeys}">
                    <!-- Allow the user to change the dspace login credentials -->
                    <c:if test="${isDeactivated == false}">
                        <a href="#change-login" class="change-login">Change
                            Dspace Login<c:choose>
                                <c:when
                                    test="${not empty wrongDspaceLogin}">*</c:when>
                            </c:choose>
                    </c:if>
                    </a>


                    <div style="text-align: right">

                        <script>
							function funConfirmDeletion() {
								var pos = [ $(window).width() / 4, 50 ];
								// Define the Dialog and its properties.
								
								$("#dialog-confirm")
										.html("Are you sure you want to delete the workspace?")
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
															$(this).dialog(
																	'close');
															location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteSingleWorkspace/${workspaceid}?projectId=${myprojectid}';
															return false;
														},
														"No" : function() {
															$(this).dialog(
																	'close');
															return false;
														}
													}
												});
							}
						</script>

                        <div id="dialog-confirm" title="Confirm ?"></div>
                    </div>


                    <div id="login-box" class="login-popup"
                        title="Dspace Authentication">
                        <form id="dspaceLogin" method="post"
                            class="signin">
                            <fieldset class="textbox">
                                <label class="username"><span>Dspace
                                        UserName:</span> <input id="username"
                                    name="username" value="" type="text"
                                    autocomplete="on"
                                    placeholder="Username" /> </label> <label
                                    class="password"><span>Dspace
                                        Password: </span> <input id="password"
                                    name="password" value=""
                                    type="password"
                                    placeholder="Password" /> </label>
                            </fieldset>
                            <label><input type="checkbox"
                                name="dspacePublicAccess"
                                id="dspacePublicAccess" value="public" /><font
                                size="2">Use Public Access</font></label>
                        </form>
                        <font size="1">We recommend setting up
                            Dspace Access keys <a
                            href="${pageContext.servletContext.contextPath}/auth/workbench/keys">here</a>.
                            It's more secure !
                        </font>
                    </div>
                    <script>
						$(document)
								.ready(
										function() {
											$("#login-box")
													.dialog(
															{
																autoOpen : false,
																modal : false,
																resizable : false,
																buttons : {
																	Login : function() {
																		var bValid = true;
																		var $username = $('#username');
																		var $password = $('#password');

																		if (!$(
																				'#dspacePublicAccess')
																				.is(
																						':checked')) {
																			if ($
																					.trim($username
																							.val()) === '') {
																				$username
																						.effect(
																								"shake",
																								{
																									times : 1
																								},
																								300);
																				$username
																						.focus();
																				bValid = false;
																			}
																			if ($
																					.trim($password
																							.val()) === '') {
																				$password
																						.effect(
																								"shake",
																								{
																									times : 1
																								},
																								300);
																				if (bValid)
																					$password
																							.focus();
																				bValid = false;
																			}
																		}

																		if (bValid) {
																			$(
																					'#dspaceLogin')
																					.submit();
																		}
																	}
																}
															});

											$('a.change-login')
													.click(
															function() {
																$(
																		'#dspaceLogin')
																		.attr(
																				'action',
																				'${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/changedspacelogin');
																$("#login-box")
																		.dialog(
																				"open");
															});
										})
					</script>
                </c:when>
            </c:choose> <br> <br> <c:choose>
				<c:when test="${not empty wrongDspaceLogin}">*Invalid dspace login credentails. Please provide the correct details to view all files.</c:when>
			</c:choose> <script>
				function confirmWorkspaceDeactivation() {
					// Define the Dialog and its properties.
					var pos = [ $(window).width() / 4, 50 ];
					$("#dialog-confirm")
							.html("Are you sure you want to deactivate the workspace?")
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
			</script>
			<script>
				function confirmWorkspaceActivation() {
					// Define the Dialog and its properties.
					var pos = [ $(window).width() / 4, 50 ];
					$("#dialog-confirm")
							.html("Are you sure you want to activate the workspace?")
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
			<hr> <!-- Display Networks --> <c:choose>
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
										type="hidden" value="<c:out value="${network.name}"></c:out>" />
										<c:out value="${network.name}"></c:out></td>
									<td width="25%" align="center"><c:out
											value="${network.creator.userName}"></c:out></td>
									<td width="25%" align="center"><c:out
											value="${network.status}"></c:out></td>
									<td width="25%" align="center"><input type=button
										onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.id}'"
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







