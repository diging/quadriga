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
	
	$(function() {
		
		$("input[name='deleteproj']").button().click(function(event){
			if(!$("input[name='projchecked']").is(":checked")) {
				$.alert("Select record to delete", "Oops !!!");
				event.preventDefault();
				return;
			}
		});
		
		$("input[name='selectall']").button().click(function(event){
			$("input[name='projchecked']").prop("checked",true);
			event.preventDefault();
			return;
		});
		
		$("input[name='deselectall']").button().click(function(event){
			$("input[name='projchecked']").prop("checked",false);
			event.preventDefault();
			return;
		});
	});
</script>
<article class="is-page-content">
	<form:form modelAttribute="project" method="POST"
		action="/auth/workbench/deleteproject">
		<c:if test="${not empty projectlist}">
			<span class="byline">Select the projects to be deleted:</span>
			<c:choose>
				<c:when test="${success=='0'}">
					<span class="byline" style="color: #f00;"><c:out
							value="${errormsg}"></c:out></span>
					<br />
				</c:when>
			</c:choose>
			<input class="command" type="submit" value='Delete' name="deleteproj">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<table style="width: 100%" class="display dataTable" id="projectlist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th width="21%">Project Name</th>
						<th width="75%">Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${projectlist}">
						<tr>
							<td><input type="checkbox" name="projchecked"
								value="${project.internalid}"></td>
							<td><font size="3"> <c:out value="${project.name}"></c:out>
							</font></td>
							<td><font size="3"> <c:out
										value="${project.description}"></c:out>
							</font></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input class="command" type="submit" value='Delete' name="deleteproj">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
		</c:if>
		<c:if test="${empty projectlist}">
			You don't have any projects to delete.
		</c:if>
	</form:form>
</article>