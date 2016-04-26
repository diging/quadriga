<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>






<c:if test="${not empty project}">
<div class="jumbotron">
	<h1>${project.projectName}</h1>
	<p>
	${project.description}
	</p>
	<p  class="smaller">
	<i class="fa fa-user"></i> ${project.owner.name}
	</p>
	<p  class="smaller">
	<c:if test="${not empty project.projectCollaborators}">
	   <i class="fa fa-users"></i>
       <c:forEach var="projectcollaborator"
                items="${project.projectCollaborators}" varStatus="loop">
        <c:out value="${projectcollaborator.collaborator.userObj.name}"></c:out><c:if test="${!loop.last}">, </c:if>
        </c:forEach>
        </ul>
    </c:if>
	
	</p>
	<p class="smaller">
	<i class="fa fa-commenting-o"></i> When citing this data set please use the following URL: <i>${project_baseurl}${pageContext.servletContext.contextPath}/sites/${project.unixName}</i>.
	</p>
</div>

<div class="row">
  <div class="col-lg-4">
    <h2>Browse the networks of this project!</h2>
    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
    <p><a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks" role="button">Browse</a></p>
  </div>
  <div class="col-lg-4">
    <h2>Explore the combined graph!</h2>
    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
    <p><a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks" role="button">Explore</a></p>
 </div>
  <div class="col-lg-4">
    <h2>Search the graph!</h2>
    <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>
    <p><a class="btn btn-primary" href="#" role="button">Search</a></p>
  </div>
</div>

</c:if>
<c:if test="${blogEntryExists}">
	<div class="row">
		<h2>Recent Project Blog</h2>
		<h3>${latestProjectBlogEntry.title}</h3>
		<p>
			<i>Posted by ${latestProjectBlogEntry.author.name} on
				${latestProjectBlogEntry.createdDate}</i>
		</p>
		<p>${latestProjectBlogEntrySnippet}...</p>
		<p>
		<form:form commandName="projectBlogEntry" method="POST"
				action="${pageContext.servletContext.contextPath}/sites/${project.unixName}/projectblogdetails">				
			<input type="hidden" path="title" id="title" value="${latestProjectBlogEntry.title}" />
			<input type="hidden" path="description" id="description" value="${latestProjectBlogEntry.description}" />
			<input type="hidden" path="createdDate" id="createdDate" value="${latestProjectBlogEntry.createdDate}" />
			<input type="hidden" path="author" id="author" value="${latestProjectBlogEntry.author}" />
			<input class="btn btn-primary" type="submit" value="Read More" style="width: 100%; align: center;">
		</form:form>
		</p>
	</div>
</c:if>
