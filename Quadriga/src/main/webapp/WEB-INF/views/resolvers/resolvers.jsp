<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h2>Project Handle Resolvers</h2>

<p>This a list of all the project handle resolvers you have registered. A project handle resolvers 
resolves the handle of a text to a presentation URL of a text. This way you can map, for instance,
handles of a repository to the URLs of a project website.</p>

<p>
<a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/auth/resolvers/add">Add Project Handle Resolver</a>
</p>

<table class="table table-striped table-bordered table-white">
	<tr>
	    <th>Project</th>
	    <th>Description</th>
	    <th>Project URL</th>
	    <th>Handle Pattern</th>
	    <th>Resolved Handle Pattern</th>
	    <th>Options</th>
	    <th>Options</th>
	</tr>

<c:forEach items="${resolvers}" var="resolver">
    <tr>
        <td>${resolver.projectName}</td>
        <td>${resolver.description}</td>
        <td>${resolver.projectUrl}</td>
        <td>${resolver.handlePattern}</td>
        <td>${resolver.resolvedHandlePattern}</td>
        <td>
        </td>
        
        <td>
        <form action="${pageContext.servletContext.contextPath}/auth/resolvers/edit" method="POST">
        <button type="submit" title="Edit Resolver" value="${resolver.id}" name="resolverId">
        <i class="fa fa-cog" aria-hidden="true"></i>
        </button>
   		</form>
   		
   		<form action="${pageContext.servletContext.contextPath}/auth/resolvers/test" method="POST">
        <button type="submit" title="Test Resolver" value="${resolver.id}" name="resolverId">
        <i class="fa fa-check-square" aria-hidden="true"></i>
        </button>
   		</form>
   		
   		<form action="${pageContext.servletContext.contextPath}/auth/resolvers/delete" method="POST">
        <button type="submit" title="Delete Resolver" value="${resolver.id}" name="resolverId">
        <span class="glyphicon glyphicon-remove"></span>
        </button>
   		</form>
   		
   		</td>
    </tr>    
</c:forEach>

</table>