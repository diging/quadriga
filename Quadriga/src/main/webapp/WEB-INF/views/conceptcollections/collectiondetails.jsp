<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />

<script type="text/javascript">
	$(document).ready(function() {
		$('.dataTable').dataTable({
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
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {

		});
	});
	$(document).ready(function() {
		$('#selectall').click(function() {
			$('.selected').prop('checked', isChecked('selectall'));
		});
	});
	function isChecked(checkboxId) {
		var id = '#' + checkboxId;
		return $(id).is(":checked");
	}
	function resetSelectAll() {
		// if all checkbox are selected, check the selectall checkbox
		// and viceversa
		if ($(".selected").length == $(".selected:checked").length) {
			$("#selectall").attr("checked", "checked");
		} else {
			$("#selectall").removeAttr("checked");
		}

		if ($(".selected:checked").length > 0) {
			$('#edit').attr("disabled", false);
		} else {
			$('#edit').attr("disabled", true);
		}
	}

	function addCCtoProjects(id, name) {

		var e = document.getElementById("dlgConfirm");
		if (e.style.display == 'block')
			e.style.display = 'none';
		else
			e.style.display = 'block';

		event.preventDefault();
		$("#dlgConfirm")
				.dialog(
						{
							resizable : false,
							height : 'auto',
							width : 350,
							modal : true,
							buttons : {
								Submit : function() {
									$(this).dialog("close");
									//$("#deletewsform")[0].submit();
									$
											.ajax({
												url : "${pageContext.servletContext.contextPath}/auth/workbench/"
														+ id
														+ "/addconceptcollection",
												type : "POST",
												data : "selected="
														+ $('#hidden').val(),
												success : function() {
													location.reload();
													//alert("done");
												},
												error : function() {
													alert("error");
												}
											});
									event.preventDefault();
								},
								Cancel : function() {
									$(this).dialog("close");
								}
							}
						});

	}
	function addCCtoWorkspace(id, name) {

		var e = document.getElementById("dlgConfirm1");
		if (e.style.display == 'block')
			e.style.display = 'none';
		else
			e.style.display = 'block';

		event.preventDefault();
		$("#dlgConfirm1")
				.dialog(
						{
							resizable : false,
							height : 'auto',
							width : 350,
							modal : true,
							buttons : {
								Submit : function() {
									$(this).dialog("close");
									//$("#deletewsform")[0].submit();
									$
											.ajax({
												url : "${pageContext.servletContext.contextPath}/auth/workbench/workspace/"
														+ id
														+ "/addconceptcollection",
												type : "POST",
												data : "selected="
														+ $('#hidden').val(),
												success : function() {
													location.reload();
													//alert("done");
												},
												error : function() {
													alert("error");
												}
											});

									event.preventDefault();
								},
								Cancel : function() {
									$(this).dialog("close");
								}
							}
						});
	}
</script>

<div class="row">
	<div class="col-md-9">
		<h2>Concept Collection: ${concept.conceptCollectionName}</h2>
		<div>${concept.description}</div>
		<c:if test="${isAdmin}">
		<div style="text-align: right">
			<a
				href="${pageContext.servletContext.contextPath}/auth/conceptcollections/updatecollection/${concept.conceptCollectionId}">
				<i class="fa fa-pencil-square-o"></i> Edit Concept Collection
			</a>
		</div>
		</c:if>
		<br>
		<div class="user">
			Owned by: ${concept.owner.name}
			<c:if test="${owner}">(<a
					href="${pageContext.servletContext.contextPath}/auth/conceptcollections/transfer/${concept.conceptCollectionId}">Change</a>)</c:if>
		</div>


		<hr>
		<c:choose>
			<c:when test="${not empty concept.conceptCollectionConcepts}">
				<p>These are all the concepts in this collection.</p>
				<form method="post"
					action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}/deleteitems">
					<c:if test="${isAdmin || hasReadWrite}">
					<button type="button" class="btn btn-primary btn-sm"
						onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}/searchitems'">
						<span class="fa fa-plus"></span> Add Entry
					</button>

					<button type="submit" class="btn btn-primary btn-sm">
						<span class="fa fa-minus"></span> Delete Selected Entries
					</button>
					</c:if>
					<br /> <br />
					<table class="table" id="conceptSearch">
						<thead>
							<tr>
								<c:if test="${isAdmin || hasReadWrite}">
								<th><input type="checkbox" id="selectall"></input></th>
								</c:if>
								<th>Lemma</th>
								<th>ID</th>
								<th>POS</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="conceptItem"
								items="${concept.conceptCollectionConcepts}">
								<tr>
								<c:if test="${isAdmin || hasReadWrite}">
									<td><input type="checkbox" class="selected"
										name="selected" value="${conceptItem.concept.conceptId}" /></td>
								</c:if>
									<td><font size="2"><c:out
												value="${conceptItem.concept.lemma}"></c:out></font></td>
									<td width="25%"><font size="2"><c:out
												value="${conceptItem.concept.conceptId}"></c:out></font></td>
									<td class="center"><font size="2"><c:out
												value="${conceptItem.concept.pos}"></c:out></font></td>
									<td width="30%"><font size="2"><c:out
												value="${conceptItem.concept.description}"></c:out></font></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</c:when>
			<c:otherwise>
			<c:if test="${isAdmin || hasReadWrite}">
				<p>You don't have any entries in this concept collection. Click
					the button to add some.</p>
					</c:if>
					<c:if test="${hasRead}">
					<p>There are no entries in this concept collection.</p>
					</c:if>
				<c:if test="${isAdmin || hasReadWrite}">
				<input type=button class="btn btn-primary"
					onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}/searchitems'"
					value='Add Items' />
				</c:if>
			</c:otherwise>
		</c:choose>

	</div>
	<div class="col-md-3">
		<h3 class="major">
			<span>Collaborators</span>
		</h3>
		<c:if test="${not empty collaboratingUsers}">
			<c:forEach var="collab" items="${collaboratingUsers}">
				<i class="fa fa-user"></i>
				<c:out value="${collab.collaborator.userObj.name}"></c:out>
				<br>
			</c:forEach>
		</c:if>
		<c:if test="${isAdmin}">
			<div
				style="border-top: dashed 1px #e7eae8; padding: 5px; margin-top: 15px;">
				<a
					href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addcollaborators">
					<i class="fa fa-plus-circle"></i> Add
				</a> <br> <a
					href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/deletecollaborators">
					<i class="fa fa-minus-circle"></i> Delete
				</a> <br> <a
					href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/updatecollaborators">
					<i class="fa fa-pencil"></i> Update
				</a>
			</div>
		</c:if>
	</div>
	<div id="dlgConfirm" title="Confirmation" style="display: none">
		<p>Do you wanna add concept collection to this project?</p>
	</div>
	<div id="dlgConfirm1" title="Confirmation" style="display: none">
		<p>Do you wanna add concept collection to this workspace?</p>
	</div>