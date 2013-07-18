<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});
		
		$("input[type=submit]").button().click(function(event) {
			event.preventDefault();
		});
	});
	
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false,
			"iDisplayLength": 3
		});
	});
	
	function submitClick()
	{
		if($('input:checkbox').is(':checked'))
			{
				$('#bitstream').submit();
			}
		else
			{
				$.alert("Please select atleast one file", "Oops !!!");
				return;
			}
		
	}
</script>

<h2>Workspace: ${workspacedetails.name}</h2>
<div>${workspacedetails.description}</div>
<hr>
<div class="user">Owned by: ${workspacedetails.owner.name}</div>
<hr>
<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/updateworkspacedetails/${workspaceid}">
<input type="button" name="Edit" value="Edit"/>
</a>
<br><br>
<c:choose>
	<c:when test="${not empty workspacedetails.bitstreams}">
	<input type="submit" value="Update Values" onclick="location.href='/quadriga/auth/workbench/workspace/${workspacedetails.id}/updatebitstreams'"> 
	<form id="bitstream" method="POST" action="/quadriga/auth/workbench/workspace/${workspacedetails.id}/deletebitstreams">
		<table cellpadding="0" cellspacing="0" border="0"
			class="display dataTable" width="100%">
			<thead>
				<tr>
					<th></th>
					<th>Community</th>
					<th>Collection</th>
					<th>Item</th>
					<th>File</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bitstream" items="${workspacedetails.bitstreams}">
					<tr>
						<td><input type="checkbox" class="checkbox" name="bitstreamids" value="${bitstream.id}"></td>
						<td><font size="1"><c:out value="${bitstream.communityName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.collectionName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.itemName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.name}"></c:out></font></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th></th>
					<th>Community</th>
					<th>Collection</th>
					<th>Item</th>
					<th>File</th>
				</tr>
			</tfoot>
		</table>
		<font size="2"><input type="submit" onclick="submitClick();" value="Delete Dspace Files" /></font>
		</form>
	</c:when>
	<c:otherwise>
					Workspace does not contain any files from dspace !
				</c:otherwise>
</c:choose>
