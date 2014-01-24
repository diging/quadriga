<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script type="text/javascript">

$(function() {
	$("input[type=submit]").button().click(function(event) {
	});
	
	$("input[type=button]").button().click(function(event) {
	});
});

$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
	
	$("form input:checkbox").prop("checked",false);
});

$(function(){
$("input[name='selectAll']").button().click(function(){
		
		$("form input:checkbox").prop("checked",true);
		event.preventDefault();
		return;
	});
	
$("input[name='deselectAll']").button().click(function(){
	
	$("form input:checkbox").prop("checked",false);
	event.preventDefault();
	return;
	
});


});

</script> 

<head>
<style type="text/css">
h1{
	font-size:50x;
}

</style>
</head>

<c:if test="${not empty project}">
<h3> Details of the project </h3>
<form:form method="GET">

<table>
	<tr>
		<td>
			Name of project:
		</td>
		<td>${project.name}</td>
	</tr>	
	<tr>
		<td>
			Description:
		</td>
		<td>${project.description}</td>
	</tr>
	<tr>
		<td>
			Owner:
		</td>
		<td>${project.owner.name}</td>
	</tr>	
	<tr>
		<td>
			Collaborators:
		</td>
		<td>
			<section>
				<c:if test="${not empty project.collaborators}">
					<ul class="collaborators">
						<c:forEach var="projectcollaborator"
							items="${project.collaborators}">
							<li><c:out value="${projectcollaborator.userObj.name}"></c:out>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</section>
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" onClick="location.href='${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks'" value="Browse Networks"/> 
		</td>
		<td>
			<input type="submit" value="Search Networks"/> 
		</td>
	</tr>
</table>

	
</form:form> 
</c:if>



