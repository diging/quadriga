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
		
		$("#dlgConfirm").hide();
	});
	
	function submitClick(id){
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench';
	}

	$(function() {
		
		$("input[name='Back']").button().click(function(event) {
		});
		<%--
		$("input[name='deleteproj']").button().click(function(event){
			if(!$("input[name='projchecked']").is(":checked")) {
				$.alert("Select record to delete", "Oops !!!");
				event.preventDefault();
				return;
			}
		});
		
		
		$("input[name='deleteproj']").button().click(function(event) {
			if ($("input[name='projchecked']").is(":checked")) {
				event.preventDefault();
				$("#dlgConfirm").dialog({
					resizable : false,
					height : 'auto',
					width : 350,
					modal : true,
					buttons : {
						Submit : function() {
							$(this).dialog("close");
							$("#deleteprojform")[0].submit();
						},
						Cancel : function() {
							$(this).dialog("close");
						}
					}
				});
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
		--%>
	});
</script>
<article class="is-page-content">
	<form:form modelAttribute="projectform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/deleteproject" id="deleteprojform">
		<c:choose> 
		 <c:when test="${success == '0'}">
		 		<c:if test="${not empty projectform.projectList}">
			<span class="byline">Select the projects to be deleted:</span>
			<input class="command" type="submit" value='Delete' name="deleteproj">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
			<table style="width: 100%" class="display dataTable" id="projectlist">
				<thead>
					<tr>
						<th width="3%" align="center">Action</th>
						<th width="20%">Project Name</th>
						<th width="77%">Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${projectform.projectList}" varStatus="status">
						<tr>
						<td>
						<form:checkbox path="projectList[${status.index}].internalid" value="${project.internalid }"/>
							</td>
							<td><font size="3">
							<form:label path="projectList[${status.index}].name">
							<c:out value="${project.name}"></c:out>
							</form:label> 
							</font></td>
							<td><font size="3">
								<form:label path="projectList[${status.index}].description">
							<c:out value="${project.description}"></c:out>
							</form:label>  
							<form:errors path="projectList[${status.index}].internalid" cssClass="error"></form:errors>
							</font>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input class="command" type="submit" value='Delete' name="deleteproj">
			<input type="button" value="Select All" name="selectall">
			<input type="button" value="DeSelect All" name="deselectall">
		</c:if>
		 
		 		<c:if test="${empty projectform.projectList}">
			<ul>
				<li><input type="submit" onClick="submitClick(this.id);"
					value='Back' name="Back"></li>
			</ul>
			You don't have any projects to delete.
		</c:if>
		</c:when>
		     <c:otherwise> 
		     <span class="byline">Successfully deleted projects</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Back'></li>
	</ul>
		     </c:otherwise>
		</c:choose>
				<div id="dlgConfirm" title="Confirmation">
			Are you sure?
		</div>
	</form:form>
</article>