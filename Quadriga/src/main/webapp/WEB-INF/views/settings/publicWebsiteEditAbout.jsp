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
    modelAttribute="boutTextBackingBean">
    <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />

    <table style="width: 100%">
        <tr>
            <td style="color: red;"><form:errors path="title"
                    class="ui-state-error-text" /> <br> <form:errors
                    path="description" class="ui-state-error-text" /> <!-- Create project blog entry button at top right corner -->
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
                    id="description"></form:textarea>
                <form:errors path="description"
                    class="ui-state-error-text" /></td>

        </tr>
    </table>
</form:form>
