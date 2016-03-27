<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to add the items into a dictionary
	and search for items from word power	
-->
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
		$("input[type=submit]").button().click(function(event) {

		});
	});
	$(document).ready(function() {
		$("input[type=a]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>

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
<H1>Word Search</H1>
<hr>
<br>
<div id="myGrid">
	<ul>
		<li>
			<form name='searchItem' method="POST">
				<!-- <form name='searchItem' method="POST" action="dictionary/wordSearch/${dictionaryid}"> -->
				<font color="black">Word: </font>&nbsp;&nbsp; <input type="text"
					name="itemName" id="itemname" placeholder=" Enter a word"><br>


				<font color="black">POS: </font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select
					name="posdropdown">
					<option value="noun">Nouns</option>
					<option value="verb">Verb</option>
					<option value="adverb">Adverb</option>
					<option value="adjective">Adjective</option>
					<option value="other">Other</option>
				</select> <br>
				
				<c:choose>
					<c:when test="${collab=='1'}">
						<input type="submit" value="Search"
							onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/dictionarycollab/wordSearch/${dictionaryid}'">
					</c:when>
					<c:otherwise>

						<input type="submit" value="Search"
							onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/dictionary/wordSearch/${dictionaryid}'">
					</c:otherwise>
				</c:choose>
			</form> <c:choose>
				<c:when test="${errorstatus=='1'}">
					<font color="red"><spring:message code="term.not.found" /></font>
				</c:when>
			</c:choose> <c:choose>
				<c:when test="${success=='1'}">
					<font color="blue"> <c:out value="${successmsg}"></c:out></font>

				</c:when>

				<c:otherwise>
					<font color="red"><c:out value="${errormsg}"></c:out></font>

				</c:otherwise>
			</c:choose>
		</li>
	</ul>
</div>


<div class="container">
	<c:choose>
		<c:when test="${status=='1'}">
			<c:choose>
				<c:when test="${not empty dictionaryEntryList}">

					<H3>Results</H3>
					<hr>
					<br>
					<form method="POST">
						<c:choose>
							<c:when test="${collab=='1'}">
								<input type="submit" value="Select & Save"
									onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItemsCollab/${dictionaryid}'" />
							</c:when>
							<c:otherwise>

								<input type="submit" value="Select & Save"
									onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'">
							</c:otherwise>
						</c:choose>
						<br> <br>
						<table cellpadding="0" cellspacing="0" border="0"
							class="display dataTable" width="70%">
							<!-- <table  class="dataTable" id="pagination"> -->
							<thead>
								<tr>
									<th align="left"><input type="checkbox" id="selectall">
										All</th>
									<th width="75" height="20" align="center">Term</th>
									<th width="75" height="20" align="center">ID</th>
									<th width="75" height="20" align="center">POS</th>
									<th width="90" height="20" align="center">Vocabulary</th>
									<th width="500" height="20" align="center">Description</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dictionaryEntry" items="${dictionaryEntryList}">
									<tr>
										<td><input type="checkbox" class="selected"
											name="selected"
											value='<c:out value="${dictionaryEntry.id}"></c:out>' /></td>
										<td align="center"><input name="term" type="hidden"
											value="<c:out value="${dictionaryEntry.lemma}"></c:out>" />
											<c:out value="${dictionaryEntry.lemma}"></c:out></td>
										<td align="center"><input name="dictionaryItemId" type="hidden"
											value="<c:out value="${dictionaryEntry.id}"></c:out>" /> <c:out
												value="${dictionaryEntry.id}"></c:out></td>
										<td align="center"><input name="pos" type="hidden"
											value="<c:out value="${dictionaryEntry.pos}"></c:out>" /> <c:out
												value="${dictionaryEntry.pos}"></c:out></td>
										<td align="center"><input name="vocabulary" type="hidden"
											value="<c:out value="${dictionaryEntry.vocabulary}"></c:out>" />
											<c:out value="${dictionaryEntry.vocabulary}"></c:out></td>
										<td align="left"><input name="description" type="hidden"
											value="<c:out value="${dictionaryEntry.description}"></c:out>" />
											<c:out value="${dictionaryEntry.description}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</c:when>

			</c:choose>

		</c:when>

		<c:otherwise>
			<c:choose>
				<c:when test="${status=='0'}">
					<font color="red"><spring:message code="term.not.found" /></font>
				</c:when>

			</c:choose>
		</c:otherwise>
	</c:choose>
</div>