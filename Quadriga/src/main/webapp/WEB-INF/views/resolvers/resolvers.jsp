<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
form { display: inline; }
</style>

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
	</tr>

<c:forEach items="${resolvers}" var="resolver">
    <tr>
        <td>${resolver.projectName}</td>
        <td>${resolver.description}</td>
        <td>${resolver.projectUrl}</td>
        <td>${resolver.handlePattern}</td>
        <td>${resolver.resolvedHandlePattern}</td>
        <td>
        <a href="${pageContext.servletContext.contextPath}/auth/resolvers/${resolver.id}/edit">
        <i class="fa fa-cog" aria-hidden="true"></i>
        </a>
        
        <a href="${pageContext.servletContext.contextPath}/auth/resolvers/${resolver.id}/test">
        <i class="fa fa-check-square" aria-hidden="true"></i>
        </a>
        
   		<a href="${pageContext.servletContext.contextPath}/auth/resolvers/${resolver.id}/delete">
        <i class="fa fa-times" aria-hidden="true"></i>
        </a>
        
    	</td>
    </tr>    
</c:forEach>

</table>