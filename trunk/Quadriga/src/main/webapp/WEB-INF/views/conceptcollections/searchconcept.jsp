<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
form input.chk
{-webkit-appearance: checkbox;}
</style>
<script type="text/javascript">
$(document).ready(function() {
				$('#conceptSearch').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth": false
				});
				
			});

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
		
	});
});

</script>
	<h3>
			Concept Search
		</h3><hr>
<form  action="${pageContext.servletContext.contextPath}/auth/searchitems" method="get">
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
		<form action="conceptcollections/addItems" method="post">
		<input type="submit"  value="Select & Save" /><br><br>
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
					
				</tr>
			</thead>
			<tbody>
			
				<c:forEach var="concept" items="${result}">
					<tr class="gradeX">
						<td> <input type="checkbox"  class="chk" name = "selected" value='<c:out value="${concept.id}"></c:out>' /></td>
						<td  align="justify"><font size="2"><c:out
									value="${concept.lemma}"></c:out></font></td>
						<td width="25%"  align="justify"><font size="2"><c:out
									value="${concept.id}"></c:out></font></td>
						<td  class="center" align="justify"><font size="2"><c:out
									value="${concept.pos}"></c:out></font></td>
						<td width="30%" align="justify"><font size="2"><c:out
									value="${concept.description}"></c:out></font></td>
						<td class="center" align="justify"><font size="2"><c:out
									value="${concept.type}"></c:out></font></td>
						<td class="center" align="justify"><font size="2"><c:out
									value="${concept.conceptList}"></c:out></font></td>
						
						
								
					</tr>
				</c:forEach>
				
			</tbody>
			
		</table>
		
				</form>
	</c:if>

