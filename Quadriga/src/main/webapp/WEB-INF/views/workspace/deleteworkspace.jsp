<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- Content -->
<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
	$("#dlgConfirm").hide();
	
	<%-->Default uncheck the checkbox <--%>
	$("form input:checkbox").prop("checked",false);
});

function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}';
}
$(function() {
	
	$("input[name='Back']").button().click(function(event) {
		event.preventDefault();
	});
	
	$("input[name='deletews']").button().click(function(event){
	});
	
	$("input[name='deletews']").button().click(function(event) {
		if ($("form input:checkbox").is(":checked")) {
			event.preventDefault();
			$("#dlgConfirm").dialog({
				resizable : false,
				height : 'auto',
				width : 350,
				modal : true,
				buttons : {
					Submit : function() {
						$(this).dialog("close");
						$("#deletewsform")[0].submit();
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});
		}
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
	<header>
	<h2>Delete workspace</h2>
	</header>
	<form:form modelAttribute="workspaceform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}/deleteworkspace" id="deletewsform">
		<c:choose>
			<c:when test="${success == '0'}">
					<c:if test="${not empty workspaceform.workspaceList}">
					<span class="byline">Select the workspace to be deleted:</span>
					<c:choose>
						<c:when test="${error == '1'}">
							<span class="ui-state-error-text"> <spring:message
									code="workspace_selection.required" />
							</span>
							<br>
						</c:when>
					</c:choose>
			<input class="command" type="submit" value='Delete' name="deletews">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<input type="button"
			onClick="submitClick(this.id);"
			value='Cancel' name="Back">
			<table style="width: 100%" class="display dataTable" id="workspacelist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th width="21%">Workspace Name</th>
						<th width="75%">Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="workspace" items="${workspaceform.workspaceList}" varStatus="status">
						<tr>
						<td>
						<form:checkbox path="workspaceList[${status.index}].id" value="${workspace.id}"/>
							</td>
							<td><font size="3">
							<form:label path="workspaceList[${status.index}].name">
							<c:out value="${workspace.name}"></c:out>
							</form:label> 
							</font></td>
							<td><font size="3">
								<form:label path="workspaceList[${status.index}].description">
							<c:out value="${workspace.description}"></c:out>
							</form:label>  
							</font>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input class="command" type="submit" value='Delete' name="deletews">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<input type="button"
			onClick="submitClick(this.id);"
			value='Cancel' name="Back">
				</c:if>
				<c:if test="${empty workspaceform.workspaceList}">
					<ul>
				<li><input type=button onClick="submitClick(this.id);"
					value='Okay' name="Back"></li>
			</ul>
			You don't have any deactivated workspace to delete.
				</c:if>
				<div id="dlgConfirm" title="Confirmation">
			Are you sure?
		</div>
			</c:when>
				     <c:when test="${success == '1'}"> 
		     <span class="byline">Successfully deleted selected workspace</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay' name="Back"></li>
	</ul>
          </c:when>
		</c:choose>
	</form:form>
</article>