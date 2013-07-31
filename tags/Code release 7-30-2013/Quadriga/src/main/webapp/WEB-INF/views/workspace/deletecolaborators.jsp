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
	}
	
	function submitClick(id){
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}';
	}
</script>
<article class="is-page-content">
	<form:form commandName="collaborator" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/deletecollaborators"
		id="deletewscollabform">
		<c:if test="${not empty collaboratingusers}">
			<span class="byline">Select the collaborator to be deleted:</span>
			<input type="submit" value='Delete' name="deletewscollab">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<table style="width: 100%" class="display dataTable"
				id="collabaratinguserlist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th align="center" width="26%">Collaborator</th>
						<th align="center" width="70%">Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collab" items="${collaboratingusers}">
						<tr>
							<td><input type="checkbox" name="wscollabchecked"
								value="${collab.userObj.userName}"></td>
							<td><font size="3"><c:out
										value="${collab.userObj.userName}"></c:out> </font></td>
							<td><font size="3"> <c:forEach var="roles"
										items="${collab.collaboratorRoles}">
										<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach>
							</font></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="submit" value='Delete' name="deletews">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
		</c:if>
		<c:if test="${empty collaboratingusers}">
			<ul>
				<li><input type="submit" onClick="submitClick(this.id);"
					value='Back' name="Back"></li>
			</ul>
			You don't have any  collaborators associated to the workspace
		</c:if>
	</form:form>
</article>