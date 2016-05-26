
<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to list the networks
-->

<script type="text/javascript" charset="utf8">
//# sourceURL=filename.js
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
		
		$('#approve-network').on('show.bs.modal', function (event) {
	        var button = $(event.relatedTarget);
	        $("#approve-btn").on("click", function(e) {
	            var href = button.attr("data-href");
	            window.location = href;
	            $("#approve-network").modal('hide');     // dismiss the dialog
	        });
	    });
		
		$('#reject-network').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            $("#reject-btn").on("click", function(e) {
                var href = button.attr("data-href");
                window.location = href;
                $("#reject-network").modal('hide');     // dismiss the dialog
            });
        });
	});
</script>
<script>
$(document).ready(function() {
    $('#unassignedNetworks').DataTable();
} );
</script>

<h2>Network Editing Workbench</h2>

<p>This network shows all the networks that are under review by you or that still need to be assigned. You can also see a <a href="${pageContext.servletContext.contextPath}/auth/editing/completed">list of approved and rejected networks</a> or 
<a href="${pageContext.servletContext.contextPath}/auth/editing/assigned/others">networks assigned to other editors</a>.

<h3>Networks you are working on:</h3>
<c:choose>
	<c:when test="${not empty assignedNetworkList}">
		<table class="table table-striped table-bordered table-white">
		  <thead>
                <tr>
                    <th>Name</th>
                    <th>Project Name</th>
                    <th>Workspace Name</th>
                    <th>Status</th>
                    <th>Submitted By</th>
                    <th>Review</th>
                    <th>Action</th>
                </tr>
            </thead>
		    <tbody>
			<c:forEach var="network" items="${assignedNetworkList}">
				<tr>
				    <td>
							<a
								href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}"><i class="fa fa-star"></i> 
								<c:out value="${network.networkName}"></c:out>
							</a>
					</td>
					<td>
						 <c:out value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out>
                    </td>
                    <td>
						 <c:out value="${network.networkWorkspace.workspace.workspaceName}"></c:out>
				    </td>
				    <td>
				        <c:out value="${network.status}"></c:out>
				    </td>
					<td>
					    <c:out value="${network.creator.userName}"></c:out>
					</td>
					<td>
						<a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/auth/editing/editnetworks/${network.networkId}"><i class="fa fa-comments"></i> Review</a>
						<a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/auth/editing/versionhistory/${network.networkId}"><i class="fa fa-history"></i> Versions</a>
					</td>
					<td>
					    <a class="btn btn-primary" data-toggle="modal" data-target="#approve-network" data-href="${pageContext.servletContext.contextPath}/auth/editing/approvenetwork/${network.networkId}"><i class="fa fa-thumbs-up"></i> Approve</a>
					    <a class="btn btn-primary" data-toggle="modal" data-target="#reject-network" data-href="${pageContext.servletContext.contextPath}/auth/editing/rejectnetwork/${network.networkId}"><i class="fa fa-thumbs-down"></i> Reject</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

<h3>Unassigned networks:</h3>
<c:choose>
	<c:when test="${not empty networkList}">
		<table id="unassignedNetworks"
			class="table table-striped table-bordered table-white">
			<thead>
				<tr>
					<th>Name</th>
					<th>Project Name</th>
					<th>Workspace Name</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="network" items="${networkList}">
					<tr>
						<td width="15%"><input name="items"
							type="hidden" value="<c:out value="${network.networkName}"></c:out>" />
							<a href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}"><i class="fa fa-star"></i> <c:out value="${network.networkName}"></c:out></a></td>
						<td width="15%"><c:out
								value="${network.networkWorkspace.workspace.projectWorkspace.project.projectName}"></c:out></td>
						<td width="15%"><c:out
								value="${network.networkWorkspace.workspace.workspaceName}"></c:out></td>
						<td width="15%"><c:out
								value="${network.status}"></c:out></td>
						<td width="15%"><input class="btn btn-primary" type=button
							onClick="location.href='${pageContext.servletContext.contextPath}/auth/editing/assignuser/${network.networkId}'"
							value='Assign to Me'></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

<!-- Approve Network Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="approve-network">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> Approve Network</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to approve the network? Approving a network publishes it to the public project website.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="approve-btn">Yes, approve!</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Reject Network Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="reject-network">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> Reject Network</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to reject the network? Rejecting a network will require the submitter to make changes and submit it again before the network can be published.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="reject-btn">Yes, reject!</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->