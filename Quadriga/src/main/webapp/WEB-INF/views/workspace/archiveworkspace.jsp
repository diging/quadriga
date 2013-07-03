<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<style type="text/css">
table,td,th,caption {
	border: 1px solid black;
}

th {
	background-color: #E9EEF6;
	color: black;
	font-weight: bold;
}

td {
	background-color: white;
	color: black;
	white-space: wrap;
	overflow: wrap;
	text-overflow: ellipsis;
}
</style>
<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});

function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/workbench';
}
$(function() {
	
	$("input[name='Back']").button().click(function(event) {
		event.preventDefault();
	});
	
	$("input[name='archivews']").button().click(function(event){
		if(!$("input[name='wschecked']").is(":checked")) {
			$.alert("Select record to archive", "Oops !!!");
			event.preventDefault();
			return;
		}
	});
	
	$("input[name='selectall']").button().click(function(event){
		$("input[name='wschecked']").prop("checked",true);
		event.preventDefault();
		return;
	});
	
	$("input[name='deselectall']").button().click(function(event){
		$("input[name='wschecked']").prop("checked",false);
		event.preventDefault();
		return;
	});
});
</script>
<article class="is-page-content">
	<form:form modelAttribute="workspace" method="POST"
		action="archiveworkspace/${wsprojectid}" id="archivewsform">
		<c:if test="${not empty workspaceList}">
			<span class="byline">Select the workspace to be archived:</span>
			<c:choose>
				<c:when test="${success=='0'}">
					<span class="byline" style="color: #f00;"><c:out
							value="${errormsg}"></c:out></span>
					<br />
				</c:when>
			</c:choose>
			<input class="command" type="submit" value='Archive' name="archivews">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<table style="width: 100%" class="display dataTable" id="workspacelist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th width="21%">Workspace Name</th>
						<th width="75%">Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="workspace" items="${workspaceList}">
						<tr>
							<td><input type="checkbox" name="wschecked"
								value="${workspace.id}"></td>
							<td><font size="3"> <c:out value="${workspace.name}"></c:out>
							</font></td>
							<td><font size="3"> <c:out
										value="${workspace.description}"></c:out>
							</font></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input class="command" type="submit" value='Archive' name="archivews">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
		</c:if>
		<c:if test="${empty workspaceList}">
			<ul>
				<li><input type="submit" onClick="submitClick(this.id);"
					value='Back' name="Back"></li>
			</ul>
			You don't have any workspace to archive.
		</c:if>
	</form:form>
</article>