<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<script type="text/javascript">
$(document).ready(function() {
			activeTable = $('#conceptSearch').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"bAutoWidth": false
			});
		});

</script>
	
<form action="${pageContext.servletContext.contextPath}/auth/searchitems">
		<input type="text"  name="name" id ="name" placeholder="enter a word">
		<select name="pos">
						<option value="noun">Nouns</option>
						<option value="verb">Verb</option>
						<option value="adverb">Adverb</option>
						<option value="adjective">Adjective</option>
						<option value="other">Other</option>
		</select> 
		<input type="submit" value="Search Concepts" >
</form>

<c:if test="${not empty result}">
		<h3>Results Of the search</h3>
		<form action="auth/addItems">
		<table cellpadding="0" cellspacing="0"	class="display dataTable"  id="conceptSearch">
			<thead>
				<tr>
					<th>Select</th>
					<th>Lemma</th>
					<th>ID</th>
					<th>POS</th>
					<th>Description</th>
					<th>Type</th>
					<th>ConceptList</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach var="concept" items="${result}">
					<tr class="gradeX">
						<td> <input type="checkbox" name = "selected" value='<c:out value="${concept.id}"></c:out>'>
						<td  align="justify"><font size="2"><c:out
									value="${concept.lemma}"></c:out></font></td>
						<td width="25%" class="center" align="justify"><font size="2"><c:out
									value="${concept.id}"></c:out></font></td>
						<td  class="center" align="justify"><font size="2"><c:out
									value="${concept.pos}"></c:out></font></td>
						<td width="30%" class="center" align="justify"><font size="2"><c:out
									value="${concept.description}"></c:out></font></td>
						<td class="center" align="justify"><font size="2"><c:out
									value="${concept.type}"></c:out></font></td>
						<td class="center" align="justify"><font size="2"><c:out
									value="${concept.conceptList}"></c:out></font></td>
						
						
								
					</tr>
				</c:forEach>
				
			</tbody>
			<tfoot>
				<tr>
					<th>Select</th>
					<th>Lemma</th>
					<th>ID</th>
					<th>POS</th>
					<th>Description</th>
					<th>Type</th>
					<th>ConceptList</th>
					<th>Action</th>
				</tr>
			</tfoot>
		</table>
		<input type="submit"  value="save" /> 
				</form>
	</c:if>







</html>
