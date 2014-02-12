<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function() {
				$('.dataTable').dataTable({
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
<table style="width:100%">
<tr>
<td style="width:90%">
<div>
    <h2>Concept Collection: ${concept.name}</h2>
    <div>${concept.description}</div>
    <br>
    <div class="user">Owned by: ${concept.owner.name}</div>
    <br />
    <input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections'"
		value='Okay'>
	<input type="button"  onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/updatecollection/${concept.id}'"
	   value = "Edit">
	</div>
	<hr>
	<c:choose >
	<c:when test="${not empty concept.items}">
		<span class="byline">These are all the concepts in this collection.</span>
<form method="post">
	<input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.id}/searchitems'"
		value='Add Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/deleteitems'" value='Delete Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/updateitems'" value="Update Items">
<br><br>
	<table class="display dataTable" id="conceptSearch">
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
	</c:when>
			<c:otherwise>
			You don't have any items. Click on button to add items.
			<br />
			<input type=button
				onClick="location.href='${pageContext.servletContext.contextPath}//auth/conceptcollections/${concept.id}/searchitems'"
				value='Add Items' />
	</c:otherwise>
	</c:choose>

</td>
<td style="width:10%">
<c:if test="${not empty collaboratingUsers}">
	<section>
		<h3 class="major"><span>Collaborators</span></h3>
		<ul class="collaborators">
			<c:forEach var="collab" items="${collaboratingUsers}">
				<li><c:out value="${collab.userObj.name}"></c:out></li>
			</c:forEach>
		</ul>
	</section>
</c:if>
</td>
</tr>
</table>
