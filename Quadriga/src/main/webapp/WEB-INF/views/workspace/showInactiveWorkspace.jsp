<%@ page language="java" contentType="text/html;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<title>List of Deactivated Workspaces</title>
<script>

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



</script>

<h2>List of Inactive Workspaces</h2>
<br>
<c:forEach var="workspace" items="${deactivatedWSList}">
    <div class="panel panel-default">
        <div class="panel-body">
            <a
                href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.id}"><i
                class="ion-filing icons"></i> <c:out
                    value="${workspace.name}"></c:out></a> (Owner) <br>
            <c:out value="${workspace.description}"></c:out>
        </div>
    </div>
</c:forEach>
<br>

<a class="btn btn-primary"
    href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${deactivatedWSProjectId}">Go Back</a>