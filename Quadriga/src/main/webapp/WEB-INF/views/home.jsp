<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Content -->
<script>
$(document).ready(function() {
	
    $(document).tooltip();
	
});
</script>

<article class="is-page-content">


<header>
	<h2>&nbsp;Welcome back <sec:authentication property="principal.username" />!</h2>
	<span class="byline">&nbsp;&nbsp;The following projects have recently been updated</span>
</header>

<!--  if there are projects -->
<c:if test="${not empty projects}">

<c:forEach items="${projects}" var="project" varStatus="status">
	<c:if test="${status.index % 2 == 0}">
	<div class="projectContainer">
	</c:if>
	<div class="recentProjectList">
	<i class="fa fa-th-list" ></i>
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}">${project.projectName}</a> 
		<span class="project_owner">
		Owned by: ${project.owner.name}
		</span><br>
    <input type="hidden" value="${project.updatedDate.time}" class="project-date-value">
		<span class="project_last_updated_date">
		Last Updated Date:  <span class="project-date"></span>
		</span>
		
		<hr style="clear: right">
		${project.description}
		
		<c:if test="${not empty project.projectCollaborators}">
			<div>Collaborators:</div>
			<ul>
			<c:forEach items="${project.projectCollaborators}" var="collaborators">
				<li>${collaborators.collaborator.userObj.name}</li>
			</c:forEach>
			</ul>
		</c:if>
		
	<c:if test="${status.index % 2 == 1}">
	</div>
	</c:if>
</div>
</c:forEach>

</c:if>
<!--  end if there are projects -->

<!--  if there are no projects -->
<c:if test="${empty projects}">

    <p>
    You don't have any projects yet. You should <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject"><i class="fa fa-plus-circle"></i> create one</a>!
    </p>
</c:if>
 
 
	</section>
	<!-- /Highlight -->
</article>

<script>
    function getLocalDate(date) {
        var dt = new Date(date);
        var minutes = dt.getTimezoneOffset();
        dt = new Date(dt.getTime() + minutes * 60000);
        return dt.toLocaleDateString() + ' ' + dt.toLocaleTimeString();
    }
    $('.recentProjectList').each(function(key, val) {
        var date = $(val).find('.project-date-value').val()
        var dateString = getLocalDate(parseInt(date))
        $(val).find('.project-date').html(dateString)
    })
</script>

<!-- /Content -->

