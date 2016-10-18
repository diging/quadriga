<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- CSS Files -->
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/base.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/ForceDirected.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/d3.css"
	rel="stylesheet" />
	
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script
	src="${pageContext.servletContext.contextPath}/resources/js/d3/common_functions.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/d3networkvisualize.js"></script>
<script src="https://d3js.org/d3.v3.js" charset="utf-8"></script>

<script type="text/javascript">
function changeLayout(json,networkid,path,type)
{
	d3init(json,networkid,path,type);
}

</script>
<script>
$("input[type=button]").button().click(function(event) {
	event.preventDefault();
});</script>
<script type="text/javascript">
//# sourceURL=dynamic.js
  $(function() {
	  
	var availableTags = [];
	<c:forEach var="node" items="${nodeList}">
		availableTags.push("${node.nodeName}");                                  
	</c:forEach>
	
    $( "#tags" ).autocomplete({
      source: availableTags
    });
    
    $('#saveAnnotationBtn').click(function(event) {
        var annottext = $('#annot_form>textarea[name="annotText"]').val();  
        var objecttype = "node";
        var dId = $('#nodeid').val();
        var dName = $("#nodename").val();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url : $('#annot_form').attr("action"),
            type : "POST",
            data :"nodename="+dName+"&nodeid="+dId+"&annotText="+annottext+"&objecttype="+objecttype,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function() {
            	$('#annotationModal').modal('hide');
                displayAllAnnotationsNew(${networkid}, '${pageContext.servletContext.contextPath}');
            },
            error: function() {
                alert("Annotation could not be saved.");
            }
        });
        event.preventDefault();
    });
    
    $('#saveRelAnnotationBtn').click(function(event) {
        var annottext = $('#annot_form_rel>textarea[name="annotTextRel"]').val(); 
        var selected = $("input[type='radio'][name='objecttype']:checked");
        var objecttype = "node";
        if (selected.length > 0) {
        	objecttype = selected.val();
        }
        var dId = $('#nodeidRel').val();
        var dName = $("#nodenameRel").val();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url : $('#annot_form').attr("action"),
            type : "POST",
            data :"objecttype="+objecttype+"&nodename="+dName+"&nodeid="+dId+"&annotText="+annottext,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function() {
            	$('#annotationModalPredicate').modal('hide');
            	displayAllAnnotationsNew(${networkid}, '${pageContext.servletContext.contextPath}');
            },
            error: function() {
                alert("Annotation could not be saved.");
            }
        });

        event.preventDefault();

    });
    
    $('#annotationModal').on('hidden.bs.modal', function (e) {
	    $('#annot_form>textarea[name="annotText"]').val('');
	    $("#nodeid").attr("value", '');
	    $("#nodename").attr("value", '');
    });
  });
</script>

<body
	onload="d3init(<c:out value='${jsonstring}'></c:out>,<c:out value='${networkid}'></c:out>,<c:out value='"${pageContext.servletContext.contextPath}"'></c:out>,'force');" />




<!-- <div id="dspace_metadata"></div>  -->


<div id="chart"></div>

<div id="inner-details"></div>
<div id="allannot_details">
	<table class="table table-striped table-bordered table-white"
		id="annotationsTable"></table>
</div>


<div id="log"></div>

<div class="modal fade" id="annotationModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Add Annotation to Subject or Object</h4>
			</div>
			<div class="modal-body">
				<form id='annot_form'
					action="${pageContext.servletContext.contextPath}/auth/editing/saveAnnotation/${networkId}"
             method='POST'>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<textarea name='annotText' class='form-control' cols='15' rows='5'></textarea>
					<input type='hidden' name='nodeid' id='nodeid' value="" /> <input
						type='hidden' name='nodename' id='nodename' value="" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" id="saveAnnotationBtn" class="btn btn-primary">Save
					Annotation</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="annotationModalPredicate" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Add Annotation to Predicate</h4>
            </div>
            <div class="modal-body">
                <form id='annot_form_rel'
                    action="${pageContext.servletContext.contextPath}/auth/editing/saveAnnotation/${networkId}"
             method='POST'>
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    What do you want to annotate?
				    <div class="btn-group" data-toggle="buttons">
					  <label class="btn btn-default btn-sm active">
					    <input type="radio" name="objecttype" value="node" autocomplete="off" checked> Annotate Node
					  </label>
					  <label class="btn btn-default btn-sm">
					    <input type="radio" name="objecttype" value="relation" autocomplete="off"> Annotate Relation
					  </label>
					</div>
					 <textarea style="margin-top: 10px" name='annotTextRel' class='form-control' cols='15' rows='5'></textarea>
                   <input type='hidden' name='nodeidRel' id='nodeidRel' value="" /> 
                    <input
                        type='hidden' name='nodenameRel' id='nodenameRel' value="" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="saveRelAnnotationBtn" class="btn btn-primary">Save
                    Annotation</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
