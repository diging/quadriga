<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="edu.asu.spring.quadriga.domain.enums.ETextAccessibility" %>

<style>
a { 
cursor: pointer; 
}
</style>

<script>
    $(document).ready(function() {
        $("input[type=button]").button().click(function(event) {
            return;
        });

        $("input[type=submit]").button().click(function(event) {
            event.preventDefault();
        });

    });

    $(document).ready(function() {
        $('#txtModal').on('show.bs.modal',function(event) {
                                            
            var link = $(event.relatedTarget);
            var txtid = link.data('txtid');
            var txtname = link.data('txtname');
            var title = link.data('txttitle');
            var author = link.data('txtauthor');
            var date = link.data('txtdate');
            
            var header = "No author and title information provided."
                if (title != '' || author != '' || date != '') {
                    header = '';
                    if (author != null) {
                        header += author + ", ";
                    }
                    if (title != null) {
                        header += "<em>" + title + "</em> ";
                    }
                    if (date != null) {
                        header += "(" + date + ")";
                    }
                }
                header += "<br><small>" + txtname + "</small>";
                
                $.ajax({
                    type : "GET",
                    url : "${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/viewtext?txtid="
                            + txtid,
                    contentType : "text/plain",
                    success : function(
                            details) {
                        $('.modal-title')
                                .html(header);
                        $('.modal-body')
                                .html(details);
                    },
                    error : function(xhr,ajaxOptions) {
                        if (xhr.status == 404) {
                            $('.modal-body').text("Error while retrieving the text content.");
                        }
                    }
                });     
        });
        
        $(".modal").on("hidden.bs.modal", function(){
            $(".modal-body").html("");
            $(".modal-title").html("");
        });
        
    });
                                           
</script>


<div class="row">
    <div class="col-md-9">
        <!-- Display workspace details -->
        <h2>
            <i class="ion-filing icons"></i> Workspace:
            ${workspacedetails.workspaceName}
        </h2>
        <c:if test="${isDeactivated == true }">
            <div style="margin-bottom: 15px;">
                <span class="label label-default"
                    title="This workspace is deactivated."><i
                    class="fa fa-toggle-off"></i> Deactivated</span>
            </div>
        </c:if>
        <div>${workspacedetails.description}</div>
        <c:if test="${owner=='1' || wsadmin=='1'}">
            <div style="text-align: right">
                <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/update">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    Workspace
                </a>
            </div>
        </c:if>
        <hr>
        <div class="user">
            Owned by: ${workspacedetails.owner.name}
            <c:if test="${owner=='1'}">(<a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/transfer">Change</a>)</c:if>
        </div>
        <c:if test="${owner=='1' and editoraccess=='0' }">
            <img
                src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Workspace 
                        (<a
                href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/assignEditorRoleToOwner/${workspaceid}">Become
                an Editor</a>)
                        
                    </c:if>
        <c:if
            test="${owner=='1' and editoraccess=='1' and projectinherit == '0' }">
            <img
                src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Workspace
                    (<a
                href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/deleteEditorRoleToOwner/${workspaceid}">Remove
                me as Editor</a>)
                    </c:if>
        <hr>
        <!--  messages for assigning editor access -->
        <c:choose>
            <c:when test="${AssignEditorSuccess=='1'}">
                <font color="blue"> <spring:message
                        code="workspace.assign.owner.editor.success" /></font>

            </c:when>
            <c:when test="${AssignEditorSuccess=='0'}">
                <font color="red"> <spring:message
                        code="workspace.assign.owner.editor.failure" /></font>
            </c:when>
            <c:when test="${AssignEditorSuccess=='2'}">
                <font color="red"> <spring:message
                        code="workspace.assign.owner.editor.assigned" /></font>
            </c:when>
        </c:choose>
        <!-- messages for deleting editor access -->
        <c:choose>
            <c:when test="${DeleteEditorSuccess=='1'}">
                <font color="blue"> <spring:message
                        code="workspace.delete.owner.editor.success" /></font>

            </c:when>
            <c:when test="${DeleteEditorSuccess=='0'}">
                <font color="red"> <spring:message
                        code="workspace.delete.owner.editor.failure" /></font>
            </c:when>
            <c:when test="${DeleteEditorSuccess=='2'}">
                <font color="red"> <spring:message
                        code="workspace.delete.owner.editor.assigned" /></font>
            </c:when>

        </c:choose>
        <br />

        <hr>
        <h4>Text files in this workspace:</h4>
        <c:if test="${owner=='1' || wsadmin=='1'}">
        <div class="pull-right">        
            <a
                href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myprojectid}/${workspaceid}/addtext">
                <i class="fa fa-plus-circle" aria-hidden="true"></i> Add Textfile
            </a>
        </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty textFileList}">

                <div class="panel panel-default" style="clear: both;">
                    <table style="width: 100%" class="table">
                        <thead>
                            <tr>
                                <th width="55%">Text File</th>
                                <th>URIs</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="textfile" items="${textFileList}">
                                <tr>
                                    <td align="left">
                                    <a data-toggle="modal"
                                        data-target="#txtModal" data-txtid="${textfile.textId}"
                                        data-txtname="${textfile.fileName}" data-txttitle="${textfile.title}"
                                        data-txtauthor="${textfile.author}" data-txtdate="${textfile.creationDate}">
                                        <c:if test="${not empty textfile.author}">${textfile.author}, </c:if>
                                        <c:if test="${not empty textfile.title}"><em>${textfile.title}</em></c:if>
                                        <c:if test="${not empty textfile.creationDate}"> (${textfile.creationDate})</c:if>
                                        <c:if test="${empty textfile.author and empty textfile.title and empty textfile.creationDate}">No author and title information provided.</c:if></a>
                                        <br><small>${textfile.fileName}</small>
                                        <br>
                                        <c:if test="${textfile.accessibility == 'PUBLIC'}">
                                        <span class="label label-success pointer label">Public</span>
                                        <a data-toggle="modal" data-target="#changeaccess-tf" id="${textfile.textId}" data-textaccess="${textfile.accessibility}" title="Make text private">
                                        <i class="fa fa-unlock"></i>
                                        </a>
                                        </c:if>
                                        <c:if test="${textfile.accessibility == 'PRIVATE'}">
                                        <span class="label label-danger pointerlabel">Private</span>
                                        <a data-toggle="modal" data-target="#changeaccess-tf" id="${textfile.textId}" data-textaccess="${textfile.accessibility}" title="Make text public">
                                        <i class="fa fa-lock"></i>
                                        </a>
                                        </c:if>
                                    </td>
                                    <td align="left"><small><strong>URI:</strong> ${textfile.textFileURI}<br>
                                    <strong>Original URI:</strong> <a target="_blank" href="${textfile.refId}">${textfile.refId}</a></small></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.textfiles" />
            </c:otherwise>
        </c:choose>
        <hr>
        <!-- Display Networks -->
        <c:choose>
            <c:when test="${not empty networks}">
                <h4>Networks belonging to this workspace:</h4>

                <div class="panel panel-default">
                    <table style="width: 100%" class="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Created by</th>
                                <th>Status</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="network" items="${networks}">
                                <tr>
                                    <td width="25%"><a
                                        href="${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.networkId}"><i
                                            class="fa fa-star"></i> <c:out
                                                value="${network.networkName}"></c:out></a></td>
                                    <td width="25%"><c:out
                                            value="${network.creator.userName}"></c:out></td>
                                    <td width="25%"><c:out value="${network.status}"></c:out></td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.networks" />
            </c:otherwise>
        </c:choose>
    </div>

    <div class="col-md-3">
        <h3 class="major">
            <span>Collaborators</span>
        </h3>
        <c:if test="${not empty workspacedetails.workspaceCollaborators}">
            <div style="padding: 5px;">
                <c:forEach var="workspaceCollaborator"
                    items="${workspacedetails.workspaceCollaborators}">
                    <i class="fa fa-user" aria-hidden="true"></i>
                    <c:out value="${workspaceCollaborator.collaborator.userObj.name}"></c:out>
                    <br>

                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty workspacedetails.workspaceCollaborators}">
                There are no collaborators.
            </c:if>
        <c:if test="${owner=='1' || wsadmin=='1'}">
            <div style="border-top: dashed 1px #e7eae8; padding: 5px;">
                <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addcollaborators">
                    <i class="fa fa-user-plus" aria-hidden="true"></i> Add
                </a><br> <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletecollaborators">
                    <i class="fa fa-user-times" aria-hidden="true"></i> Delete
                </a><br> <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/updatecollaborators">
                    <i class="fa fa-users" aria-hidden="true"></i> Update
                </a>
            </div>
        </c:if>
    </div>
    
    
    <div class="col-md-3">
        <h5 class="major">
            <span>Below users have complete access to workspace</span>
        </h5>
        <div style="padding: 5px;">
                <i class="fa fa-user" aria-hidden="true"></i> ${projectOwner.userName} (Project Owner)
                <br>
                <c:forEach var="collaborator"
                    items="${projectAdmins}">
                    <i class="fa fa-user" aria-hidden="true"></i>
                    <c:out value="${collaborator.collaborator.userObj.name}"></c:out>
                    <br>
                </c:forEach>
        </div>
    </div>
</div>

<!--  Modals -->
<!--  add texts modal -->
<div class="modal text-modal" id="txtModal" tabindex="-1" role="dialog"
    aria-labelledby="txtModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content ">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" style="height: 500px; overflow-y: scroll;"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Deactivate workspace modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="deactivate-ws">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Deactivate Workspace</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to deactivate the workspace?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="deactivate-btn">Yes,
                    deactivate!</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Activate Workspace Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="activate-ws">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Activate Workspace</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to activate the workspace?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="activate-btn">Yes,
                    activate!</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Delete Workspace Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="delete-ws">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Delete Workspace</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete the workspace? This action
                    cannot be undone.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="delete-btn">Yes,
                    delete!</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Changing Text Accessibility modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="changeaccess-tf">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
            </div>
            <div class="modal-body">
                <p></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="changeaccess-btn">Yes,
                    change</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<form style="display: hidden" action="" method="POST" id="hiddenform">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<script>
    $(document).ready(function() {
                         var textFileId;
                         $('#deactivate-btn')
                                .click(
                                        function(event) {
                                            location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/deactivateworkspace?projectid=${myprojectid}';
                                        });
                        $('#activate-btn')
                                .click(
                                        function(event) {
                                            location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${workspaceid}/activateWorkspace?projectid=${myprojectid}';
                                        });
                        $('#delete-btn')
                                .click(
                                        function(event) {
                                            location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteSingleWorkspace/${workspaceid}?projectId=${myprojectid}';
                                        });
                        
                        $('#changeaccess-btn')
                                .click(
                                        function(event) {
                                            var url = '${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/' + textFileId + '/' + changedTextFileAccess;
                                            $("#hiddenform").attr("action", url);
                                            $("#hiddenform").submit();
                                        });
                                        
                        
                        $('#changeaccess-tf').on('show.bs.modal', function (e) {
                            textFileId = e.relatedTarget.id;
                            textFileAccess = $("#" + textFileId).data("textaccess");
                            
                            var textFileAccessMap = new Object();
                            textFileAccessMap["PUBLIC"] = "Are you sure you want to make this text file Private?";
                            textFileAccessMap["PRIVATE"] = "Are you sure you want to make this text file Public?";
                            
                            changedTextFileAccess = textFileAccess === "PUBLIC" ? "Private" : "Public";
                            
                            var titlecontent = "Update Text Accessibility";
                            $(this).find('.modal-title').html(titlecontent);
                            $(this).find('.modal-body').html(textFileAccessMap[textFileAccess]); 
                            
                        });
    });
</script>
