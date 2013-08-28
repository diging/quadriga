<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function() {
				$('#conceptSearch').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth": false
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

<header>
	<h2>Concept Collection: <span contenteditable="true"> ${concept.name}</span></h2>
	<span class="byline">These are all the concepts in this collection.</span>
</header>

<div >
	
	<input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections'"
		value='Back to all collections'>
		<br><br>
		
	
<hr>
<br>
<form method="post">
	<input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.id}/searchitems'"
		value='Add Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/deleteitems'" value='Delete Items'>
		<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/updateitems'" value="Update Items">
<br><br>

	<table cellpadding="0" cellspacing="0"	class="display dataTable" id="conceptSearch">
		<thead>
			<tr>
				<th><input type="checkbox" id="selectall"></input></th>
				<th>Lemma</th>
				<th>ID</th>
				<th>POS</th>
				<th>Description</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach var="conceptItem" items="${concept.items}">
				<tr class="gradeX">
					<td><input type="checkbox" class="selected" name="selected" value="${conceptItem.id}"/></td>
					<td align="justify"><font size="2"><c:out
								value="${conceptItem.lemma}"></c:out></font></td>
					<td width="25%" align="justify"><font size="2"><c:out
								value="${conceptItem.id}"></c:out></font></td>
					<td class="center" align="justify"><font size="2"><c:out
								value="${conceptItem.pos}"></c:out></font></td>
					<td width="30%" align="justify"><font size="2"><c:out
								value="${conceptItem.description}"></c:out></font></td>



				</tr>
			</c:forEach>

		</tbody>

	</table>
	</form>
</div>
