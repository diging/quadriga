<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<c:if test="${not empty project}">
<div class="jumbotron mainpage">
    <div class="mainpage-jumbo">
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
		<i class="fa fa-commenting-o"></i> When citing this data set please use the following URL: <i>${project_baseurl}/sites/${project.unixName}</i>.
		</p>
	</div>
</div>

<div class="row">
<c:if test="${not empty blocks[0]}">
  <div class="col-lg-4">
    <h2>${blocks[0].title}</h2>
    <p>${blocks[0].description}</p>
    <p><a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/${blocks[0].linkTo}" role="button">${blocks[0].linkText}</a></p>
  </div>
</c:if>
<c:if test="${not empty blocks[1]}">
  <div class="col-lg-4">
    <h2>${blocks[1].title}</h2>
    <p>${blocks[1].description}</p>
    <p><a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/${blocks[1].linkTo}" role="button">${blocks[1].linkText}</a></p>
 </div>
 </c:if>
 <c:if test="${not empty blocks[2]}">
  <div class="col-lg-4">
    <h2>${blocks[2].title}</h2>
    <p>${blocks[2].description}</p>
    <p><a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/${blocks[2].linkTo}" role="button">${blocks[2].linkText}</a></p>
  </div>
</c:if>
</div>

</c:if>




