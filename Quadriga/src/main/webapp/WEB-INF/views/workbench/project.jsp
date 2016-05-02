<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<sec:authentication var="principal" property="principal" />

<script>
    $(document).ready(function() {
        $("input[type=button]").button().click(function(event) {
            return;
        });
        $("ul.pagination1").quickPagination({
            pageSize : "3"
        });
        
        
    });
    
</script>


<table style="width: 100%">
    <tr>
        <!-- Display project details -->
        <td>
            <h2>Project: ${project.projectName}</h2>
            <div>${project.description}</div>
            <c:if test="${owner=='1' or isProjectAdmin}">
            <div style="text-align:right">
            <a class = "editProject" href="${pageContext.servletContext.contextPath}/auth/workbench/modifyproject/${project.projectId}"><i class="fa fa-pencil-square-o"></i> Edit Project
            </a>
            </div>
            
            <div style="text-align:right">
            <a class = "editProject" href="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}"><i class="fa fa-pencil-square-o"></i> Edit Project URL
            </a>
            </div>
            </c:if>
            
            <hr>
            <div class="user">Owned by: ${project.owner.name} <c:if test="${owner=='1'}">(<a href="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${project.projectId}">Change</a>)</c:if></div>
                    
                    <c:if test="${owner=='1' and editoraccess=='0' }">
                    <img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Project 
                        (<a href="${pageContext.servletContext.contextPath}/auth/workbench/assignownereditor/${project.projectId}">Become an Editor</a>)
                        
                        
                    </c:if>
                    
                    <c:if test="${owner=='1' and editoraccess=='1' }">
                    <img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Project
                    (<a href="${pageContext.servletContext.contextPath}/auth/workbench/deleteownereditor/${project.projectId}">Remove me as Editor</a>)
                    </c:if>
            
            <hr> <!--  Display associated workspace -->
            
            <strong>Workspaces in this project:</strong>
            <ul>
            <c:forEach var="workspace" items="${workspaceList}">
                <li class="ws">
                    <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}">
                    <i class="fa fa-folder-open"></i>
                    <c:out
                            value="${workspace.workspaceName}"></c:out></a> (Owner) <br> 
                        
                    <c:out
                    value="${workspace.description}"></c:out>
                </li>
            </c:forEach>
            
            <c:forEach var="workspace" items="${collabworkspacelist}">
            <li  class="ws">
                 <a
                    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}">
                 <i class="fa fa-folder-open"></i>
                 <c:out
                    value="${workspace.workspaceName}"></c:out></a> (Collaborator)<br> <c:out
                    value="${workspace.description}"></c:out></li>
            </c:forEach>
            </ul>
            
            <c:if test="${owner=='1' or isProjectAdmin}">
            <div style="float:right;">
                <a class="addworkspace" href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addworkspace"><i class="fa fa-plus-circle"></i> Add Workspace</a>
            </div>
            </c:if>
            
            <div style="clear:right;">
            <c:if test="${empty workspaceList}">
            There are no workspaces yet. You should create one!
            </c:if>
            <c:if test="${empty collabworkspacelist}">
                You are not collaborating on any workspace.
            </c:if>
            </div>
            </div>
            <c:choose>
                <c:when test="${AssignEditorSuccess=='1'}">
                    <font color="blue"> <spring:message
                            code="project.assign.owner.editor.success" /></font>

                </c:when>
                <c:when test="${AssignEditorSuccess=='0'}">
                    <font color="red"> <spring:message
                            code="project.assign.owner.editor.failure" /></font>
                </c:when>
                <c:when test="${AssignEditorSuccess=='2'}">
                    <font color="red"> <spring:message
                            code="project.assign.owner.editor.assigned" /></font>
                </c:when>
            </c:choose>
            
            <c:choose>
                <c:when test="${DeleteEditorSuccess=='1'}">
                    <font color="blue"> <spring:message
                            code="project.delete.owner.editor.success" /></font>

                </c:when>
                <c:when test="${DeleteEditorSuccess=='0'}">
                    <font color="red"> <spring:message
                            code="project.delete.owner.editor.failure" /></font>
                </c:when>
                <c:when test="${DeleteEditorSuccess=='2'}">
                    <font color="red"> <spring:message
                            code="project.delete.owner.editor.assigned" /></font>
                </c:when>
            </c:choose>
            <div align="left">
                <hr>
                To go to the public site, click this link <a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">http://quadriga.asu.edu/sites/${project.unixName}</a>
                <hr>
                <c:choose>
                    <c:when test="${owner=='1'}">
                        <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/showinactiveworkspace">
                                <i class="fa fa-archive"></i> Show Inactive Workspace <span><c:out value="(${deactivatedWSSize})" /></span>
                            </a> 
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/showarchivedworkspace">
                                <i class="fa fa-archive"></i> Show Archived Workspace <span><c:out value="(${archivedWSSize})" /></span>
                            </a>
                        </li>
                        </ul>                       
                    </c:when>
                
                </c:choose>
                
            </div>
            <div align="right">
                <c:if test="${owner=='1'}">
                    

                    <i class="fa fa-ban"></i> </span>
                    <a href="#" onclick="return confirmProjectDeletion()">Delete
                        Project</a>
                </c:if>
            </div>
            <script>
                function confirmProjectDeletion() {
                    // Define the Dialog and its properties.
                    $("#dialog-delete-project-confirm")
                            .dialog(
                                    {
                                        resizable : false,
                                        modal : true,
                                        title : "Delete Project",
                                        position: 'top',
                                        height : 300,
                                        width : 500,
                                        buttons : {
                                            "Yes" : function() {
                                                $(this).dialog('close');
                                                location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteproject/${project.projectId}';
                                                return false;
                                            },
                                            "No" : function() {
                                                $(this).dialog('close');
                                                return false;
                                            }
                                        }
                                    });
                }
            </script>
            <div id="dialog-delete-project-confirm" title="Confirm Delete?" style="display: none;">
                You are about to delete a project, this is not reversible.</br> Do you want to proceed?</div>
        </td>
        
        <!-- Display collaborators -->
        <td style="width: 200px">
            <section>
                <h3 class="major">
                    <span>Collaborators</span>
                </h3>
                <c:if test="${not empty project.projectCollaborators}">
                    <ul>
                        <c:forEach var="projectcollaborator"
                            items="${project.projectCollaborators}">
                            <li>
                            <i class="fa fa-user"></i> <c:out value="${projectcollaborator.collaborator.userObj.name}"></c:out>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${owner=='1' or isProjectAdmin}">
                <div style="border-top: dashed 1px #e7eae8; padding: 5px;"> 
                <ul class="colltools">
                    <li><a class = "collabEdit" href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators"><i class="fa fa-plus-circle"></i> Add</a></li>
                    <li><a class = "collabEdit" href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletecollaborators"><i class="fa fa-minus-circle"></i> Delete</a></li>
                    <li><a class = "collabEdit" href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators"><i class="fa fa-pencil"></i> Update</a></li>
                </ul>
                </div>
                </c:if>
            </section>
        </td>
    </tr>
    <tr>
    <td></td>
    </tr>
</table>
