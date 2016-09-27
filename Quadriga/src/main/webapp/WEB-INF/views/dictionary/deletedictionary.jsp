
<!--  
	Author Lohith Dwaraka  
	Used to list and delete the dictionaries	
-->

<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf8">
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
		$("input[type=a]").button().click(function(event) {
			event.preventDefault();
		});
	});

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {

		});
	});
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
		$.alert("hello");
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


<h2>Delete dictionary</h2>
<div class="back-nav">
	<hr>
	<p>
		<a href="${pageContext.servletContext.contextPath}/auth/dictionaries"><i
			class="fa fa-arrow-circle-left"></i> Back to Dictionary List</a>
	</p>
	<hr>
</div>
<h4>Select the dictionaries you want to delete:</h4>

<c:choose>
	<c:when test="${selection=='0'}">
		<font color="blue"> <spring:message
				code="delete.dictionary.noselection" /></font>

	</c:when>
</c:choose>
	<c:choose>
		<c:when test="${not empty dictinarylist}">

			<form method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="panel panel-default">
					<table style="width: 100%" cellpadding="0" cellspacing="0"
						border="0" class="table">
						<!-- <table  class="dataTable" id="pagination1"> -->
						<thead>
							<tr>
								<th width="50px" align="left"><input title="Select All"
									type="checkbox" id="selectall"> <i class="fa fa-check"
									aria-hidden="true"></i></th>
								<th>Dictionary Name</th>
								<th>Dictionary Description</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="dictionary" items="${dictinarylist}">
								<tr>
									<td><input type="checkbox" class="selected"
										name="selected"
										value=' <c:out value="${dictionary.dictionaryId}"></c:out>' /></td>
									<td><input name="items" type="hidden"
										value="<c:out value="${dictionary.dictionaryName}"></c:out>" />
										<c:out value="${dictionary.dictionaryName}"></c:out></td>
									<td><c:out value="${dictionary.description}"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
				
				<div class="alert alert-warning" role="alert">Make sure this is really what you want to do. This action cannot be undone!</div>
	
				<p>
					<input class="btn btn-primary" type="submit" value="Delete"
						onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/delete'" />
				    <a class="btn btn-default" href="${pageContext.servletContext.contextPath}/auth/dictionaries">Cancel</a>
				</p>



			</form>
		</c:when>

		<c:otherwise>
			<br>
			<spring:message code="empty.dictionary" />
		</c:otherwise>
	</c:choose>
