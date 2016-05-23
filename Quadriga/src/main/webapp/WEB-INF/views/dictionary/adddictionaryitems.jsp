<%@ page language="java" contentType="text/html;"%>
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
<H2>Word Search</H2>
<hr>
<br>

<form name='searchItem' method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/dictionary/wordSearch/${dictionaryid}">
	<div class="input-group">
		<span class="input-group-addon" id="basic-addon1">Search term:</span>
		<input type="text" class="form-control" name="itemName" id="itemname"
			placeholder="Enter a word" aria-describedby="basic-addon1">
	</div>
	<br>
	<div class="input-group">
		<span class="input-group-addon" id="basic-addon2">POS:</span> <select
			class="form-control" name="posdropdown"
			aria-describedby="basic-addon2">
			<option value="noun">Nouns</option>
			<option value="verb">Verb</option>
			<option value="adverb">Adverb</option>
			<option value="adjective">Adjective</option>
			<option value="other">Other</option>
		</select>
	</div>

	<br> <input class="btn btn-primary" type="submit" value="Search">

</form>
<c:choose>
	<c:when test="${errorstatus=='1'}">
		<font color="red"><spring:message code="term.not.found" /></font>
	</c:when>
</c:choose>
<c:choose>
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

					<H3>The following words were found:</H3>
					<p>Select the words you want to add and click "Select & Save".</p>
					
					<form method="POST">
                        <p>
						<input type="submit" value="Select & Save" class="btn btn-primary"
							onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'">
                        </p>
						<div class="panel panel-default">
						<table style="font-size: 13px;"
							class="table">
							<!-- <table  class="dataTable" id="pagination"> -->
							<thead>
								<tr>
									<th width="50px"><input type="checkbox"  title="Select/Deselect All" id="selectall">
										<i class="fa fa-check"></i></th>
									<th >Term</th>
									<th >ID</th>
									<th >POS</th>
									<th >Vocabulary</th>
									<th  >Description</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dictionaryEntry" items="${dictionaryEntryList}">
									<tr>
										<td><input type="checkbox" class="selected"
											name="selected"
											value='<c:out value="${dictionaryEntry.id}"></c:out>' /></td>
										<td ><input name="term" type="hidden"
											value="<c:out value="${dictionaryEntry.lemma}"></c:out>" />
											<c:out value="${dictionaryEntry.lemma}"></c:out></td>
										<td ><input name="dictionaryItemId"
											type="hidden"
											value="<c:out value="${dictionaryEntry.id}"></c:out>" /> <c:out
												value="${dictionaryEntry.id}"></c:out></td>
										<td ><input name="pos" type="hidden"
											value="<c:out value="${dictionaryEntry.pos}"></c:out>" /> <c:out
												value="${dictionaryEntry.pos}"></c:out></td>
										<td ><input name="vocabulary" type="hidden"
											value="<c:out value="${dictionaryEntry.vocabulary}"></c:out>" />
											<c:out value="${dictionaryEntry.vocabulary}"></c:out></td>
										<td ><input name="description" type="hidden"
											value="<c:out value="${dictionaryEntry.description}"></c:out>" />
											<c:out value="${dictionaryEntry.description}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
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