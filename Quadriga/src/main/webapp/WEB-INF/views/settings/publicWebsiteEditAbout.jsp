<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="//cdn.tinymce.com/4/tinymce.min.js" type="text/javascript"></script>
<script>
    tinymce
            .init({
                selector : '#description',
                height : 300,
                fontsize_formats : "8pt 9pt 10pt 11pt 12pt 14pt 16pt 18pt 20pt 22pt 24pt 48pt 72pt",
                theme : 'modern',
                plugins : 'advlist autolink save link image lists charmap print preview',
                menubar : false,
                toolbar : 'undo redo | fontsizeselect | fontselect | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons',
                setup : function(ed) {
                    ed.on('init', function() {
                        this.getDoc().body.style.fontSize = '14px';
                    });
                }
            });
</script>
<script type="text/javascript">
    tinymce
            .init({
                selector : '.editable',
                height : 300,
                plugins : 'advlist autolink save link lists charmap print',
                menubar : false,
                save_enablewhendirty : false,
                toolbar : 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons | save'
            });
    $("#dlgConfirm")
            .dialog(
                    {
                        resizable : false,
                        height : 'auto',
                        width : 350,
                        modal : true,
                        buttons : {
                            Submit : function() {
                                $(this).dialog("close");
                                //$("#deletewsform")[0].submit();
                                $
                                        .ajax({
                                            url : "${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/saveabout",
                                            type : "POST",
                                            data : "selected="
                                                    + $('#hidden').val(),
                                            success : function() {
                                                location.reload();
                                                //alert("done");
                                            },
                                            error : function() {
                                                alert("error");
                                            }
                                        });
                                event.preventDefault();
                            },
                            Cancel : function() {
                                $(this).dialog("close");
                            }
                        }
                    });
</script>

<script type="text/javascript"></script>

<h2>Project : ${project.unixName}</h2>
<span class="byline">Edit about text for ${project.unixName}</span>
<form:form method="post"
    action="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/saveabout"
    modelAttribute="aboutTextBean">
    <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
    <form:input type="hidden" path="id" />
    <table style="width: 100%">
        <tr>
            <td style="color: red;">
                <!-- Create project blog entry button at top right corner -->
            </td>
            <td style="width: 15%"><div style="text-align: right;">
                    <input class="btn btn-primary" type="submit"
                        value="Save About Text"
                        style="width: 100%; align: center;">
                </div> <br></td>
        </tr>

        <tr>
            <td colspan="2"><form:textarea path="title" id="title"
                    placeholder="Enter Title"
                    style="width: 100%; border : solid 1px; height: 84px; border-color : #D3D3D3; font-weight: bold; font-size: 24px; vertical-align: bottom; align: center; text-align: center; padding : 20px 0"></form:textarea>
                <form:errors path="title" class="ui-state-error-text" />
                <br></td>
        </tr>
        <tr>
            <td colspan="2"><form:textarea path="description"
                    id="description"></form:textarea> <form:errors
                    path="description" class="ui-state-error-text" /></td>

        </tr>
        <tr>
            <td colspan="1"><button class="btn btn-primary"
                    type="button" data-toggle="collapse"
                    data-target="#networkTable">
                    <i class="fa fa-plus-circle" aria-hidden="true"></i>
                    Add a Network<a></a>
                </button></td>
        </tr>
    </table>
    <div id="networkTable" class="collapse">
        <c:choose>
            <c:when test="${not empty networks}">
                <div class="table-responsive">
                    <table class="table table-striped networks">
                        <thead>
                            <tr>
                                <th width="80%">Name</th>
                                <th>Action</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="network" items="${networks}">
                                <tr>
                                    <td>${network.networkName}</td>
                                    <td><a data-toggle="modal"
                                        class="btn btn-primary"
                                        data-target="#nwModal"
                                        aria-expanded="false"
                                        aria-controls="collapseExample"
                                        value="${network.networkId}"
                                        onclick="loadNetwork(this)">View
                                            Network</a></td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>


                </div>
            </c:when>
            <c:when test="${empty networks}">
                <p>There are no networks in this project.</p>
            </c:when>
        </c:choose>
    </div>
</form:form>
<div class="modal nw-modal" id="nwModal" tabindex="-1" role="dialog"
    aria-labelledby="nwModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content ">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body"
                style="height: 500px; overflow-y: scroll;">
                <div id="networkBox"
                    style="min-height: 500px; width: 100%; text-align: left;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                    data-dismiss="modal">Close</button>
                <button class="btn btn-primary" id="addImage"
                    type="button" onclick="genImage()">
                    <i class="fa fa-plus-circle" aria-hidden="true"></i>
                    Add This Network To Editor<a></a>
                </button>
            </div>
        </div>
    </div>
</div>
<script
    src="https://cdn.rawgit.com/cytoscape/cytoscape.js-cose-bilkent/1.0.2/cytoscape-cose-bilkent.js"
    type="text/javascript"></script>
<script
    src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/dist/cytoscape.js"
    type="text/javascript"></script>
<script type="text/javascript">
    function loadNetwork(selectedNW) {
        $('#networkBox').empty();
        var nwid = selectedNW.getAttribute('value');
        $
                .ajax({
                    type : "GET",
                    contentType : "application/json",
                    datatype : 'text',
                    url : "${pageContext.servletContext.contextPath}/auth/workbench/projects/"
                            + '${project.projectId}'
                            + "/settings/editabout/visualize/" + nwid,
                    timeout : 100000,
                    success : function(data) {
                        if (data === '') {
                            loadErrorMessage()
                        } else
                            visualizeNetwork(data);
                    },
                    error : function(e) {
                        loadErrorMessage();
                    }
                });
    }
    function loadErrorMessage() {
        $('#networkBox')
                .append(
                        "<p>There was an error while loading this network. Please contact an administrator.</p>");
    }
    function visualizeNetwork(jsonString) {
        container = document.getElementById('networkBox');        
        cyte = null;
        cyte = cytoscape({
            container : container, // container to render in
            layout : {
                name : 'cose',
                idealEdgeLength : 5
            },
            elements : eval(jsonString),
            style : [
                    {
                        selector : 'node',
                        style : {
                            'background-color' : 'mapData(group, 0, 1, #E1CE7A, #FDD692)',
                            'border-color' : '#B98F88',
                            'border-width' : 1,
                            'font-family' : 'Open Sans',
                            'font-size' : '12px',
                            'font-weight' : 'bold',
                            'color' : 'black',
                            'label' : 'data(conceptName)',
                            'width' : 'mapData(group, 0, 1, 40, 55)',
                            "height" : "mapData(group, 0, 1, 40, 55)",
                            'text-valign' : 'center',
                        }
                    }, {
                        selector : 'edge',
                        style : {
                            'width' : 1,
                            'line-color' : '#754F44',
                            'target-arrow-shape' : 'none'
                        }
                    } ]
        });
    }
</script>
<script>
function genImage(){
                var png = null;
                png = cyte.png({
                    'scale' : 0.75,
                    'full' : false
                });
                tinyMCE.execCommand('mceInsertContent', false,
                        '<img src="' + png + '"/>');
               
}
</script>
<script src="/quadriga/resources/js/d3.min.js" charset="utf-8"
    type="text/javascript"></script>