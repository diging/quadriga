<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>

<script type="text/javascript">
$(document).ready(function() {
				$('#conceptSearch').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth": false
				});
				
			});


</script>

		<style>
		table.display thead tr {
	font-weight: 700;
	color: rgb(107, 119, 112);
	text-align: center;
	background-color: #e3daa8;
	
	}
	table.its thead tr a:LINK {
		text-decoration:none;
	}
	table.its thead tr a:HOVER {
		text-decoration:underline;
		
	}
	
	</style>
<div style="height: 50%; vertical-align: top; " >
<header>
		<h3>Collection Name: <span   contenteditable="true">  ${concept.name}</span> </h3>
		
</header>
	 <h3>Description: </h3><wbr>
	<c:out value="${concept.description }"></c:out>
	<table style="width: 100%"	class="display dataTable"  id="conceptSearch">
			<thead>
				<tr>
					
					<th>Lemma</th>
					<th>ID</th>
					<th>POS</th>
					<th>Description</th>
					
				</tr>
			</thead>
			<tbody>
			
				<c:forEach var="conceptItem" items="${concept.items}">
					<tr class="gradeX">
						
						<td  align="justify"><font size="2"><c:out
									value="${conceptItem.lemma}"></c:out></font></td>
						<td width="25%"  align="justify"><font size="2"><c:out
									value="${conceptItem.name}"></c:out></font></td>
						<td  class="center" align="justify"><font size="2"><c:out
									value="${conceptItem.pos}"></c:out></font></td>
						<td width="30%"  align="justify"><font size="2"><c:out
									value="${conceptItem.description}"></c:out></font></td>
						
						
								
					</tr>
				</c:forEach>
				
			</tbody>
			
		</table>
	
	<%-- 
	<display:table  class="display" name="${concept.items}" requestURI="/auth/conceptdetails"  keepStatus="true" uid="1" pagesize = "5" cellspacing="2" cellpadding="2">
	<display:column property="lemma" sortable="false" title="Lemma"></display:column>
	<display:column property="name" sortable="false" title="Id"/>
	<display:column property="description" title="Description"/>
	<display:column property="pos" title="Pos"/>
	</display:table>
	 --%>
	<a href='${pageContext.servletContext.contextPath}/auth/searchitems' > addItems</a>
</div>

	
</html>