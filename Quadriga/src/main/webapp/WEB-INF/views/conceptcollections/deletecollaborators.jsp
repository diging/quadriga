<%@ page language="java" contentType="text/html;"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
					<h2>Remove Collaborator from Concept Collection: ${collectionname}</h2>
					
					<div class="back-nav">
					<hr>
					<p>
					   <a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
					</p>
					<hr>
					</div>
			<p>
				<input class="btn btn-primary" type="submit" value='Remove' name="deletecc">
				<input type="button" class="btn btn-primary" value="Select All" name="selectall">
				<input type="button" class="btn btn-primary" value="Deselect All" name="deselectall">
			</p>
			<p style="margin-bottom: 5px;">Select concept collection collaborator to be removed:</p>
                    
            <div class="panel panel-default">
			<table style="width: 100%" class="table" id="cccollaboratorlist">
				<thead>
					<tr>
						<th width="4%" ></th>
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
								 <c:out value="${roles.displayName}" /> ||
								 </c:forEach>
							</form:label>  
							</font>
							</td>
							
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<p>
	                <input class="btn btn-primary" type="submit" value='Remove' name="deletecc">
	                <input type="button" class="btn btn-primary" value="Select All" name="selectall">
	                <input type="button" class="btn btn-primary" value="Deselect All" name="deselectall">
	            </p>
				</c:if>
				<c:if test="${empty collaboratorForm.collaborators}">
			<p>There are no collaborators for this concept collection..</p>
								
				<input type=button class="btn btn-primary" onClick="submitClick(this.id);"
					value='Okay' name="Back"></li>
			
				</c:if>
			</c:when>
				     <c:when test="${success == '1'}"> 
		     <span class="byline">Successfully removed selected collaborators</span> 
		     <ul>
		<li><input type="button"
			onClick="submitClick(this.id);"
			value='Okay' name="Back"></li>
	</ul>
          </c:when>
		</c:choose>

</form:form>
</article>