<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    
<style>
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
.error {
	color: #ff0000;
	font-style: italic;
}
</style>

<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
	
	<%-->Default uncheck the checkbox <--%>
	$("form input:checkbox").prop("checked",false);
});

function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
}

$(function() {
	
	$("input[name='Back']").button().click(function(event) {
	});
	
	$("input[name='deletecc']").button().click(function(event) {
	});
	
	$("input[name='selectall']").button().click(function(event){
		$("form input:checkbox").prop("checked",true);
		event.preventDefault();
		return;
	});
	
	$("input[name='deselectall']").button().click(function(event){
		$("form input:checkbox").prop("checked",false);
		event.preventDefault();
		return;
	});
});
</script> 
<article class="is-page-content">
<form:form method="POST" commandName="collaboratorForm" 
action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/deleteCollaborator">
		<c:choose>
			<c:when test="${success == '0'}">
					<c:if test="${not empty collaboratorForm.collaborators}">
					<span class="byline">Select concept collection collaborator to be deleted:</span>
					<c:choose>
						<c:when test="${error == '1'}">
							<span class="error"> <spring:message
									code="collaborator_user_selection.required" />
							</span>
							<br>
						</c:when>
					</c:choose>
			<input class="command" type="submit" value='Delete' name="deletecc">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<input type="button"
			onClick="submitClick(this.id);"
			value='Cancel' name="Back">
			<table style="width: 100%" class="display dataTable" id="cccollaboratorlist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th width="21%">Collaborator</th>
						<th width="75%">Collaborator Roles</th>
					</tr>
				</thead>
						<tbody>
							<c:forEach var="collabUser"
								items="${collaboratorForm.collaborators}" varStatus="status">
								<tr>
								   <td>
								   <form:checkbox path="collaborators[${status.index}].userName" value="${collabUser.userName}"/>
								   </td>
								<td><font size="3">
							<form:label path="collaborators[${status.index}].userName">
							    <c:out value="${collabUser.userName}"></c:out>
							</form:label> 
							</font></td>
							<td><font size="3">
								<form:label path="collaborators[${status.index}].collaboratorRoles">
								 <c:forEach var="roles" items="${collabUser.collaboratorRoles}" varStatus="loop" >
								 <c:out value="${roles.displayName}" />||
								 </c:forEach>
							</form:label>  
							</font>
							</td>
							
							</tr>
							</c:forEach>
						</tbody>
					</table>
			<input class="command" type="submit" value='Delete' name="deletecc">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<input type="button"
			onClick="submitClick(this.id);"
			value='Cancel' name="Back">
				</c:if>
				<c:if test="${empty collaboratorForm.collaborators}">
					<ul>
				<li><input type=button onClick="submitClick(this.id);"
					value='Back' name="Back"></li>
			</ul>
			You don't have associated collaborators to delete.
				</c:if>
			</c:when>
				     <c:when test="${success == '1'}"> 
		     <span class="byline">Successfully deleted selected collaborators</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Back' name="Back"></li>
	</ul>
          </c:when>
		</c:choose>

</form:form>
</article>