<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.lang.*"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet"
    href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
    src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<!--  
    Author Jaydatta Nagarkar, Jaya-Venkata Vutukuri  
    
-->
<script type="text/javascript" charset="utf8">
    //@ sourceURL=filename.js
    function checkCheckboxes(id, pID, status) {
        $('#' + pID).find(':checkbox').each(function() {
            jQuery(this).prop('checked', status);
        });
    }

    $(document).ready(function() {

        $("input[type=button]").button().click(function(event) {
            event.preventDefault();
        });

        $(".checkbox1 input[type='checkbox']").change(function() {
            status = $(this).is(':checked');
            parent = $(this).parents('.panel');
            ckBoxes = parent.find(':checkbox');
            ckBoxes.each(function() {
                if (status === "true") {
                    this.checked = true;
                } else {
                    this.checked = false;
                }
            });
         });

         $("#selectAllProjects").change(function() {
             $(".checkbox1 input[type='checkbox']").prop('checked', $(this).prop('checked'));
             $(".checkbox1 input[type='checkbox']").trigger("change");
         });

         $("#selectAllTransformations").change(function() {
             $(".transformationList").prop('checked',$(this).prop('checked'));
         });
    });
</script>

<script type="text/javascript">
    $(document).ready(function() {
        var networkID;
        var networkIDvariable;
        $('#confirmationTransformation').click(function() {
            if ((jQuery('#divProjectList input[type=checkbox]:checked').length || jQuery('#individualNetworks input[type=checkbox]:checked').length)
                    && jQuery('#headingTwo input[type=checkbox]:checked').length) {

                var transformations = [];
                $.each($("input[name='transformation']:checked"),function() {
                transformations.push($(this).data("transformtitle"));
                });

                var networks = [];
                networkID = [];                                     
                networkIDvariable = "";                             
        
                $.each($("input[name='individualnetwork']:checked"),function() {
                    var arrayofNetworks = $(this).val().split(",");
                    networks.push(arrayofNetworks[0] + " {" + arrayofNetworks[1] + "}");
                    networkID.push(arrayofNetworks[2]);                                                             
                    networkIDvariable =  networkIDvariable + "," + arrayofNetworks[2];                                                      
                });

                $('#confirmationTransformation').attr('data-target','#confirm');
                jQuery("#contentholder").html("<h3>Transformations:</h3> "+ transformations.join("<br/>") 
                + "<br/><h3>Networks</h3> " + networks.join("<br/>"));
            } else {
                $('#confirmationTransformation').attr('data-target','#alert');
            	    jQuery("#alertholder").html("Please select at least one transformation and project.");
            }});
           
            $('#submit-btn').click(function(event) {
                document.getElementById('sanitized_network_id').value = networkID;
            $('#sanitized_network_id').val(networkIDvariable);
                document.getElementById('form1').submit();
            })
    });
</script>

<style>
#project div {
    display: inline;
    margin: 0 1em 0 1em;
    width: 30%;
}

.ui-dialog {
    position: absolute;
    top: 70px;
    left: 600px;
    z-index: 999;
}
</style>

<form
    action="${pageContext.servletContext.contextPath}/auth/transformation/start"
    method="POST" id="form1">

    <div>
        <header>
            <h2>
                <label><input type="checkbox" id="selectAllProjects"></label>
                Projects
            </h2>
        </header>
    </div>

    <%
        String ids = "";
    %>



    <div class="panel-group" id="accordion" role="tablist"
        aria-multiselectable="true">
        <c:choose>
            <c:when test="${not empty projects}">
                <ul class="pagination1">
                    <c:forEach var="project" items="${projects}">
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingOne">
                                <h4 class="panel-title">
                                    <div class="checkbox1" id="divProjectList">
                                        <label> <input type="checkbox"
                                            value="${project.projectName}" id="checkAllNetworks"
                                            name="project" class="projectList"
                                            data-param1="${project.projectName}">
                                        </label> <a role="button" data-toggle="collapse"
                                            data-parent="#accordion" href="#${project}"
                                            aria-expanded="true" aria-controls="${project}">
                                            ${project.projectName} </a>
                                    </div>
                                </h4>
                            </div>
                            <div id="${project.projectName}"
                                class="panel-collapse collapse in" role="tabpanel"
                                aria-labelledby="headingOne">
                                <div class="panel-body">
                                    <ul class="networkToggleList">
                                        <li><details>
                                                <ul>
                                                    <div class="container-fluid">
                                                        <div class="row">

                                                            <c:if test="${not empty networkMap[project.projectName]}">
                                                                <c:forEach var="network"
                                                                    items="${networkMap[project.projectName]}">
                                                                    <div class="col-md-4">
                                                                        <summary>
                                                                            <div class="selectAllNetworks"
                                                                                id="individualNetworks">
                                                                                <input type="checkbox"
                                                                                    value="${network.networkName},${project.projectName},${network.networkId}"
                                                                                    name="individualnetwork" class="networks">
                                                                                <input type="hidden" value="${network.networkId}"
                                                                                    name="individualnetworks" class="networks">
                                                                                <a
                                                                                    href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
                                                                                    <c:out value="${network.networkName}"></c:out>
                                                                                </a>
                                                                            </div>
                                                                        </summary>
                                                                        <li>Workspace: <c:out
                                                                                value="${network.networkWorkspace.workspace.workspaceName}"></c:out></li>
                                                                        <li>Submitted by: <c:out
                                                                                value="${network.creator.userName}"></c:out>
                                                                        </li> <br>
                                                                    </div>
                                                                </c:forEach>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </ul>
                                            </details></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.networks" text="No Networks available" />
            </c:otherwise>
        </c:choose>
    </div>

    <div class="row">
        <div class="col-md-8">
            <span class="byline"><input type="checkbox"
                id="selectAllTransformations"> List of available
                transformations:</span>
        </div>
    </div>

    <div class="panel-group" id="accordion" role="tablist"
        aria-multiselectable="true">
        <c:choose>
            <c:when test="${not empty transformationsList}">
                <c:forEach var="transformations" items="${transformationsList}">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">

                                <div class="panel-heading" role="tab" id="headingOne">
                                    <h1 class="panel-title">
                                        <div class="checkbox_transformation"
                                            id="divTransformationList">
                                            <label> <input type="checkbox"
                                                value="${transformations.id}" name="transformation"
                                                class="transformationList" data-transformtitle="${transformations.title}"> &nbsp;&nbsp;&nbsp;&nbsp;<c:out
                                                    value="${transformations.title}"></c:out>

                                            </label>
                                        </div>
                                    </h1>
                                </div>

                            </h4>
                        </div>
                        <div id="${transformations.title}"
                            class="panel-collapse collapse in" role="tabpanel"
                            aria-labelledby="headingOne">
                            <div class="panel-body">

                                <div class="container-fluid">
                                    <div class="row">
                                        <summary>
                                            <div class="PatternsAndMappingsTransformations"
                                                id="${transformations.mappingFileName},${transformations.patternFileName}">
                                                Mapping File:
                                                <c:out value="${transformations.mappingFileName}"></c:out>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern File:
                                                <c:out value="${transformations.patternFileName}"></c:out>
                                            </div>
                                        </summary>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.Transformations"
                    text="No Transformations available" />
            </c:otherwise>
        </c:choose>
    </div>

    <p>
        <input class="btn btn-primary" type="button" data-toggle="modal"
            value='Submit Project and Transformations'
            id="confirmationTransformation" /> <input type="hidden"
            id="sanitized_network_id" name="sanitized_network_id" value="1234">
    </p>


    <div class="modal fade" tabindex="-1" role="dialog" id="alert">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Transformation cannot be processed.</h4>
                </div>
                <div class="modal-body">
                    <h4>No transformations or projects selected.</h4>
                    <p id="alertholder"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Okay</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" tabindex="-1" role="dialog" id="confirm">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Confirm transformation</h4>
                </div>
                <div class="modal-body">
                    <h4>
                        Are you sure you want to transform the following <br>networks
                        with below transformation files?
                    </h4>
                    <p id="contentholder"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="submit-btn">Submit</button>
                </div>
            </div>
        </div>
    </div>

</form>