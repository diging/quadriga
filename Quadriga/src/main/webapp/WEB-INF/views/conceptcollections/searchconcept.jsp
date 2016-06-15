<%@ page language="java" contentType="text/html;"%>
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

		<div class="input-group">
		  <span class="input-group-addon" id="basic-addon1">Search term:</span>
		  <input type="text" class="form-control" name="name" id ="name" placeholder="Enter a word" aria-describedby="basic-addon1">
		</div>
		<br>
		<div class="input-group">
		     <span class="input-group-addon" id="basic-addon2">POS:</span>
	         <select class="form-control" name="pos"  aria-describedby="basic-addon2">
	            <option value="noun">Nouns</option>
	            <option value="verb">Verb</option>
	            <option value="adverb">Adverb</option>
	            <option value="adjective">Adjective</option>
	            <option value="other">Other</option>
	        </select>
		</div>
		<br>
		
		<input class="btn btn-primary" type="submit" value="Search" >
			
</form>

<c:if test="${not empty result}">
		<h3>The following concepts were found:</h3>
		
		<form action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addItems" method="post">
		<p>Select the concepts you want to add and click "Select & Save".</p>
		<input type="submit"  value="Select & Save" class="btn btn-primary"/><br><br>
		<div class="panel panel-default">
		
		<table   style="width: 100%" class="table"  id="conceptSearch">
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
						<td width="10%">
						  <font size="2">
						  <c:out value="${concept.lemma}"></c:out>
						  </font>
						  <input type="hidden" name="lemma" value="${concept.lemma}"/>
						</td>
						<td width="25%" >
						  <font size="2">
						  <c:out value="${concept.id}"></c:out>
						  <input type="hidden" name="id" value="${concept.id}"/>
						  </font>
						</td>
						<td width="5%"  class="center" >
						<font size="2"><c:out
									value="${concept.pos}"></c:out>
						<input type="hidden" name="pos" value="${concept.pos}"/>
						</font>
						</td>
						<td width="30%">
						<font size="2"><c:out
									value="${concept.description}"></c:out>
						<input type="hidden" name="description" value="${concept.description}"/>
						</font>
						</td>
						<td width="10%" class="center">
						<font size="2"><c:out
									value="${concept.type}"></c:out>
						<input type="hidden" name="type" value="${concept.type}"/>
						</font>
						</td>
						<td width="15%" class="center" >
						<font size="2"><c:out
									value="${concept.conceptList}"></c:out>
						<input type="hidden" name="conceptList" value="${concept.conceptList}"/>
						</font>
						</td>		
					</tr>
				</c:forEach>
				
			</tbody>
			
		</table>
		</div>
				</form>
	</c:if>

