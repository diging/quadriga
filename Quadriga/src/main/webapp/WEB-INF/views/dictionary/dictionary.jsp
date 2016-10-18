
<!--  
	Author Lohith Dwaraka  
	Used to list the items in a dictionary
	and search for items	
-->

<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />

<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
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

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});

	function addDictToProjects(id, name) {

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
									var token = $("meta[name='_csrf']").attr("content");
								    var header = $("meta[name='_csrf_header']").attr("content");
									//$("#deletewsform")[0].submit();
									$
											.ajax({
												url : "${pageContext.servletContext.contextPath}/auth/workbench/"
														+ id
														+ "/adddictionaries",
												type : "POST",
												beforeSend: function(xhr) {
									                xhr.setRequestHeader(header, token);
									            },
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
	function addDictToWorkspace(id, name) {

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
									 var token = $("meta[name='_csrf']").attr("content");
								     var header = $("meta[name='_csrf_header']").attr("content");
									//$("#deletewsform")[0].submit();
									$
											.ajax({
												url : "${pageContext.servletContext.contextPath}/auth/workbench/workspace/"
														+ id
														+ "/adddictionaries",
												type : "POST",
												beforeSend: function(xhr) {
									                xhr.setRequestHeader(header, token);
									            },
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

<script>
	$("input[name='selectall']").button().click(function(event) {
		$.alert("help");
		$("input[name='selected']").prop("checked", true);
		event.preventDefault();
		return;
	});
	$(function() {
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				"Delete all items" : function() {
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	function frmSubmitDelete() {
		form.target = this.form.action = '${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}';
		form.submit();
	}

	function frmSubmitUpdate() {
		$.alert(hello);
		form.target = this.form.action = '${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}';
		form.submit();
	}

	function deleteItem(item) {
		$("#dialog-message")
				.dialog(
						{
							resizable : false,
							height : 180,
							modal : true,
							buttons : {
								"Delete" : function() {
									$(this).dialog("close");
									location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}?item='
											+ item;
								},
								Cancel : function() {
									$(this).dialog("close");
								}
							}
						});
	}
</script>

<div id="dialog-message" title="Confirm ?"></div>


<div class="row">
	<div class="col-md-9">
		<div>
			<h2>Dictionary: ${dictionary.dictionaryName}</h2>
			<div>${dictionary.description}</div>
			<c:if test="${isAdmin}">
			<div style="text-align: right">
				<a
					href="${pageContext.servletContext.contextPath}/auth/dictionaries/updatedictionary/${dictionary.dictionaryId}">
					<i class="fa fa-pencil-square-o"></i> Edit Dictionary
				</a>
			</div>
			</c:if>
			<br />
			<div class="user">
				Owned by: ${dictionary.owner.name}
				<c:if test="${owner}">(<a
						href="${pageContext.servletContext.contextPath}/auth/dictionaries/transfer/${dictionary.dictionaryId}">Change</a>)</c:if>
			</div>
			<div id="html"></div>
			<input type="hidden" id="hidden" value="${dictionary.dictionaryId}" />
		</div>
		<hr>

		<div>
			<c:choose>
				<c:when test="${not empty dictionaryItemList}">

					<form method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <c:if test="${hasWrite}">
						<a class="btn btn-primary btn-sm"
							href="${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}"><i
							class="fa fa-plus"></i> Add Words</a>
						<button class="btn btn-primary btn-sm" class="btn btn-primary"
							onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}'">
							<i class="fa fa-minus"></i> Delete Words
						</button>
            
						<br /> <br />
						</c:if>
						
						<table class="table" style="font-size: 13px;">
							<!-- <table  class="dataTable" id="pagination1"> -->
							<thead>
								<tr>
									 <c:if test="${hasWrite}"><th width="50px" align="left"><input type="checkbox"
										title="Select/Deselect All" id="selectall"></th></c:if>
									<th>Items</th>
									<th>ID</th>
									<th>Pos</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="dictionaryItem" items="${dictionaryItemList}">
									<tr>
										 <c:if test="${hasWrite}"><td><input type="checkbox" class="selected"
											name="selected"
											value='<c:out value="${dictionaryItem.dictionaryItem.dictionaryItemId}"></c:out>' /></td></c:if>
										<td width="25%"><input name="items" type="hidden"
											value="<c:out value="${dictionaryItem.dictionaryItem.term}"></c:out>" />
											<c:out value="${dictionaryItem.dictionaryItem.term}"></c:out></td>
										<td width="25%"><c:out
												value="${dictionaryItem.dictionaryItem.dictionaryItemId}"></c:out></td>
										<td width="25%"><c:out
												value="${dictionaryItem.dictionaryItem.pos}"></c:out></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<p>
						<spring:message code="empty.dictionary.items" /> <c:if test="${hasWrite}"><spring:message code="empty.dictionary.items.click_add" /></c:if>
					</p>
                    <c:if test="${hasWrite}">
					<input type=button class="btn btn-primary"
						onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'"
						value='Add Items' />
					<br>
					</c:if>

				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="col-md-3">
		<h3 class="major">
			<span>Collaborators</span>
		</h3>
		<c:forEach var="collab" items="${collaboratingUsers}">
			<i class="fa fa-user"></i>
			<c:out value="${collab.collaborator.userObj.name}" />
			<br>
		</c:forEach>
		<c:if test="${isAdmin}">
		<div style="border-top: dashed 1px #e7eae8; padding: 5px;">
			<a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/add"><i
				class="fa fa-plus-circle"></i> Add</a><br> <a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/delete"><i
				class="fa fa-minus-circle"></i> Delete</a><br> <a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/update"><i
				class="fa fa-pencil"></i> Update</a>
		</div>
		</c:if>
	</div>

</div>

<div id="dlgConfirm" title="Confirmation" style="display: none">
	<p>Do you wanna add concept collection to this project?</p>
</div>
<div id="dlgConfirm1" title="Confirmation" style="display: none">
	<p>Do you wanna add concept collection to this workspace?</p>
</div>