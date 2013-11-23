
<!--  
	Author Lohith Dwaraka  
	Used to list the items in a dictionary
	and search for items	
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

<style type="text/css">
#myGrid {
	height: 5%;
	width: 40%;
	background: white;
	display: -ms-grid;
	-ms-grid-columns: 4;
	-ms-grid-rows: 4;
}
</style>

<table style="width:100%">
  <tr>
	<td style="width:90%">
    <h2>Dictionary: ${dictionary.name}</h2>
    <div>${dictionary.description}</div>
    <br />
    <div class="user">Owned by: ${dictionary.owner.name}</div>
    <br />
    <input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'"
		value='Okay'>
	<input type="button"  onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/updatedictionary/${dictionary.id}'"
	   value = "Edit">
	<hr>
<c:choose>
	<c:when test="${additemsuccess=='1'}">
		<font color="blue"> <spring:message code="add.items.success" /></font>

	</c:when>
	<c:when test="${additemsuccess=='2'}">
		<font color="red"><spring:message code="add.noitems.fail" /></font>

	</c:when>
	<c:when test="${additemsuccess=='0'}">
		<font color="red"><c:out value="${errormsg}"></c:out></font>

	</c:when>

	<c:otherwise>		

	</c:otherwise>
</c:choose>

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
	<c:when test="${updatesuccess=='1'}">
		<font color="blue"> <spring:message code="update.items.success" /></font>

	</c:when>

	<c:when test="${updatesuccess=='0'}">
		<font color="red"><spring:message code="update.items.fail" /></font>

	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
<div>
	<c:choose>
		<c:when test="${not empty dictionaryItemList}">

			<form method="POST">

				<input type=button
					onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'"
					value='Add Items' /> <input type="submit" value="Delete Items"
					onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}'" />

				<input type="submit" value="Update Items"
					onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}'" />
				<br /> <br />
				<table class="display dataTable">
					<!-- <table  class="dataTable" id="pagination1"> -->
					<thead>
						<tr>
							<th align="left"><input type="checkbox" id="selectall">Select
								All</th>
							<th>Items</th>
							<th>ID</th>
							<th>Pos</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="dictionaryItem" items="${dictionaryItemList}">
							<tr>
								<td><input type="checkbox" class="selected" name="selected"
									value='<c:out value="${dictionaryItem.id}"></c:out>' /></td>
								<td width="25%" align="center"><input name="items"
									type="hidden"
									value="<c:out value="${dictionaryItem.items}"></c:out>" /> <c:out
										value="${dictionaryItem.items}"></c:out></td>
								<td width="25%" align="justify"><c:out
										value="${dictionaryItem.id}"></c:out></td>
								<td width="25%" align="center"><c:out
										value="${dictionaryItem.pos}"></c:out></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</c:when>
		<c:otherwise>
			<input type=button
				onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'"
				value='Add Items' />
			<br><spring:message code="empty.dictionary.items" />
	</c:otherwise>
	</c:choose>
</div>
</td>
<td style="width:10%">
<section>
<h3 class="major">
<span>Collaborators</span>
</h3>
<ul class="collaborators">
<c:forEach var="collab" items="${collaboratingUsers}">
<li><c:out value="${collab.userObj.name}"/></li>
</c:forEach>
</ul>
</section>
</td>
</tr>
</table>