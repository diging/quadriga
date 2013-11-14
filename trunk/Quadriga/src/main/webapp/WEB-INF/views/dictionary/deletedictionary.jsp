
<!--  
	Author Lohith Dwaraka  
	Used to list and delete the dictionaries	
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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

	<header>
			<h2> Delete dictionary </h2>
	</header>
	

	<input type=button
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'"
		value='Okay'>


<br/>
<br/>
<span class="byline">Select the dictionary you want to delete</span>

<c:choose>
	<c:when test="${delsuccess=='1'}">
		<font color="blue"> <spring:message code="delete.items.success" /></font>

	</c:when>

	<c:when test="${delsuccess=='0'}">
		<font color="red"><spring:message code="delete.items.fail" /></font>

	</c:when>
	<c:otherwise>
		

	</c:otherwise>
</c:choose>


<c:choose>
	<c:when test="${selection=='0'}">
		<font color="blue"> <spring:message code="delete.dictionary.noselection" /></font>

	</c:when>
</c:choose>
<div class="container">
	<c:choose>
		<c:when test="${not empty dictinarylist}">

			<form method="POST">


				 <input type="submit" value="Delete Items"
					onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionary'" />

				<br /> <br />
				<table style="width: 100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">
					<!-- <table  class="dataTable" id="pagination1"> -->
					<thead>
						<tr>
							<th align="left"><input type="checkbox" id="selectall">Select
								All</th>
							<th>Dictionary Name</th>
							<th>Dictionary Description</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="dictionary" items="${dictinarylist}">
							<tr>
								<td width="10%"><input type="checkbox" class="selected" name="selected"
									value='<c:out value="${dictionary.id}"></c:out>' /></td>
								<td align="center"><input name="items"
									type="hidden"
									value="<c:out value="${dictionary.name}"></c:out>" /> <c:out
										value="${dictionary.name}"></c:out></td>
								<td  align="justify"><c:out
										value="${dictionary.description}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</form>
		</c:when>

		<c:otherwise>
			<br><spring:message code="empty.dictionary" />
	</c:otherwise>
	</c:choose>
</div>