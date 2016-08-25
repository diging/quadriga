<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Content -->
<script>
	$(document).ready(function() {

		$(document).tooltip();

	});
</script>
<div id="main">
	<div class="container">
		

				<h2>
					&nbsp;Welcome back
					<sec:authentication property="principal.username" />!
				</h2>
				<span class="byline">&nbsp;&nbsp;The following projects have
					recently been updated</span>

			<!--  if there are projects -->
			<c:if test="${not empty projects}">

				<c:forEach items="${projects}" var="project" varStatus="status">
					<c:if test="${status.index % 2 == 0}">
						<div class="row project-boxes">
					</c:if>
					<div class="col-md-6 project-outside">
					   <div class="project-box">
						 <a
							href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><i class="ion-planet"></i> ${project.projectName}</a>
						<div class="project_owner" style="font-size: 14px"> Owned by:
							${project.owner.name} </div> 
							<input type="hidden" value="${project.updatedDate.time}" class="project-date-value">
						<div class="project_last_updated_date pull-right" style="font-size: 12px"> Last Updated Date:
							<span class="project-date"></span>
						</div>

						<hr style="margin-top: 20px;">
						${project.description}
                        
						<c:if test="${not empty project.projectCollaborators}">
						    <hr style="margin-bottom: 5px;">
							<div style="font-size: 14px">Collaborators:<br>
							     <c:forEach items="${project.projectCollaborators}"
									var="collaborators">
									<i class="fa fa-user" aria-hidden="true"></i> ${collaborators.collaborator.userObj.name} &nbsp;
								</c:forEach>
							</div>
						</c:if>
						</div>
					</div>

					<c:if test="${status.index % 2 == 1}">
					   </div>
			        </c:if>
		</c:forEach>

		</c:if>
		<!--  end if there are projects -->

		<!--  if there are no projects -->
		<c:if test="${empty projects}">

			<p>
				You don't have any projects yet. You should <a
					href="${pageContext.servletContext.contextPath}/auth/workbench/addproject"><i
					class="fa fa-plus-circle"></i> create one</a>!
			</p>
		</c:if>


		</section>
		<!-- /Highlight -->
	</div>
</div>
</div>

<script>
	function getLocalDate(date) {
		var dt = new Date(date);
		var minutes = dt.getTimezoneOffset();
		dt = new Date(dt.getTime() + minutes * 60000);
		return dt.toLocaleDateString() + ' ' + dt.toLocaleTimeString();
	}
	$('.project-box').each(function(key, val) {
		var date = $(val).find('.project-date-value').val()
		var dateString = getLocalDate(parseInt(date))
		$(val).find('.project-date').html(dateString)
	})
</script>

<!-- /Content -->

