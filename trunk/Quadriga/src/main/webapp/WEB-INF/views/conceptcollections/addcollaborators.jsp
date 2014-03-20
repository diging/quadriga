<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    

<style>

div.wrap {
	  
		 overflow: auto;
		 margin-bottom:-30px; 
	}
	
	div.ex {
	
		width:250px;
		float:left;
	}
	
	div.ex1{
	
		width:200px;
		margin-top:-29px;
		float:left;
		overflow: auto;
	}
	
	div.rolesError{
	float:left;
	margin-top:-45px;
	}
	
</style>
<script type="text/javascript">
$(document).ready(function() {
    activeTable = $('.dataTable').dataTable({
    	"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
    });
} );

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
		
	});
$("input[type=button]").button().click(function(event) {
		
	});
});

function onSubmit(){
	location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
}
</script>
<form:form  method="POST" name="myForm" commandName="collaborator"
action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addcollaborators"> 
<h2>Associate collaborators to collection:</h2>
<h3>Concept Collection: ${collectionname}</h3>
<div>${collectiondesc}</div>
<c:if test="${not empty nonCollaboratorList}">
<hr>
<div class="wrap">
<div class="ex">
    <h4>select collaborator</h4>
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="----- Select -----"/>
	   	<form:options items="${nonCollaboratorList}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 
	<br>
	<form:errors path="userObj" cssClass="ui-state-error-text"></form:errors> 
</div> 
	<br>
	<div class="ex1">
	<h4>select access rights</h4>	
	<ul><form:checkboxes element="li" path="collaboratorRoles" items="${collaboratorRoles}" itemValue="roleid" itemLabel="displayName"/> </ul>	
	<div class="rolesError"><form:errors path="collaboratorRoles" cssClass="ui-state-error-text"></form:errors></div>
    </div>	
</div>
<br/>
<input type="submit" value="Add">
<input type="button" value="Okay" onClick="onSubmit()">
</c:if>
<c:if test="${empty nonCollaboratorList}">
<hr>
 <span class="byline">All collaborators are associated to concept collection</span>
 <input type="button" value="Okay" onClick="onSubmit()">
</c:if>
<c:if test="${not empty collaboratingUsers}">
<hr>
<span class="byline">Associated concept collection collaborators :</span>
<table style="width:100%" class="display dataTable">					
	<thead>
		<tr>	
			<th align="left">collaborator</th>
			<th align="left">roles</th>	
		</tr>
	</thead>
	
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		 <td><c:out value="${collab.userObj.name}"></c:out></td>
		 <td>
			<c:forEach var="roles" items="${collab.collaboratorRoles}">
		 	<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach>		 
		 </td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>
</form:form>

