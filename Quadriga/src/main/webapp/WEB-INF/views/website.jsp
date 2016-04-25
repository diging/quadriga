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

<div class="row">
	<h2>Recent Project Blog</h2>
	<h3>${projectBlogEntryList.title}</h3>
	<p>
	<i>Posted by ${projectBlogEntryList.author.name} on ${projectBlogEntryList.createdDate}</i>
	</p>
	<p>${projectBlogEntryList.description} ... </p>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/projectblogdetails">Read
			More</a>
	</p>
</div>

