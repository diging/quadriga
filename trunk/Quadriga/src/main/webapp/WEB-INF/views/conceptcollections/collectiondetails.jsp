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
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/searchitems'"
		value='Add Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/deleteitems'" value='Delete Items'>
		<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/updateitems'" value="Update Items">
<br><br>

	<table style="width: 100%" class="display dataTable" id="conceptSearch">
		<thead>
			<tr>
				<th>Select Items</th>
				<th>Lemma</th>
				<th>ID</th>
				<th>POS</th>
				<th>Description</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach var="conceptItem" items="${concept.items}">
				<tr class="gradeX">
					<td> <input type="checkbox" name="selected" value="${conceptItem.id}" /></td>
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
