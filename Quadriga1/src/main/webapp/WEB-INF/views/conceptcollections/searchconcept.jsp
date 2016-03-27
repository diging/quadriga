<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
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

$(document).ready(function () {
    $('#selectall').click(function () {
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

</script>
	<h2>
			Concept Search
		</h2><hr>
<form  action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/searchitems" method="get">
<table>
<tr>
		<td>Word:</td>
		<td>
		<input type="text"  name="name" id ="name" placeholder="enter a word">
		</td></tr>
		<tr>
		<td>
		POS:
		</td>
		<td>
		<select name="pos">
						<option value="noun">Nouns</option>
						<option value="verb">Verb</option>
						<option value="adverb">Adverb</option>
						<option value="adjective">Adjective</option>
						<option value="other">Other</option>
		</select></td>
		</tr>
		<tr>
		<td colspan="2">
		<input type="submit" value="Search" >
		</td></tr>
</table>		
</form>

<c:if test="${not empty result}">
		<h3>Results Of the search</h3>
		<form action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addItems" method="post">
		<input type="submit"  value="Select & Save" /><br><br>
		<table   style="width: 100%" class="display dataTable"  id="conceptSearch">
			<thead>
				<tr>
					<th><input type="checkbox" id="selectall"></input></th>
					<th>Lemma</th>
					<th>ID</th>
					<th>POS</th>
					<th>Description</th>
					<th>Type</th>
					<th>ConceptList</th>
					
				</tr>
			</thead>
			<tbody>
			
				<c:forEach var="concept" items="${result}">
					<tr>
						<td width="5%" > <input type="checkbox"  class="selected" name = "selected" value='<c:out value="${concept.id}"></c:out>' /></td>
						<td width="10%"  align="justify">
						  <font size="2">
						  <c:out value="${concept.lemma}"></c:out>
						  </font>
						  <input type="hidden" name="lemma" value="${concept.lemma}"/>
						</td>
						<td width="25%"  align="justify">
						  <font size="2">
						  <c:out value="${concept.id}"></c:out>
						  <input type="hidden" name="id" value="${concept.id}"/>
						  </font>
						</td>
						<td width="5%"  class="center" align="justify">
						<font size="2"><c:out
									value="${concept.pos}"></c:out>
						<input type="hidden" name="pos" value="${concept.pos}"/>
						</font>
						</td>
						<td width="30%" align="justify">
						<font size="2"><c:out
									value="${concept.description}"></c:out>
						<input type="hidden" name="description" value="${concept.description}"/>
						</font>
						</td>
						<td width="10%" class="center" align="justify">
						<font size="2"><c:out
									value="${concept.type}"></c:out>
						<input type="hidden" name="type" value="${concept.type}"/>
						</font>
						</td>
						<td width="15%" class="center" align="justify">
						<font size="2"><c:out
									value="${concept.conceptList}"></c:out>
						<input type="hidden" name="conceptList" value="${concept.conceptList}"/>
						</font>
						</td>		
					</tr>
				</c:forEach>
				
			</tbody>
			
		</table>
		
				</form>
	</c:if>

