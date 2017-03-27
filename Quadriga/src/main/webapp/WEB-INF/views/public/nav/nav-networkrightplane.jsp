<%@ page language="java" contentType="text/html;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "edu.asu.spring.quadriga.domain.impl.workspace.TextFile" %>

<script src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/publicNetwork.js" ></script>


<c:if test="${not empty network}">
    <div class="panel-group" id="info" role="tablist" aria-multiselectable="true">
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="headingInfo">
		    <a role="button" data-toggle="collapse" data-parent="#info" href="#bodyInfo" aria-expanded="true" aria-controls="bodyInfo">
		    <small>Network Details <i class="fa fa-caret-down" aria-hidden="true"></i></small>
		    </a> 
		</div>
		<div id="bodyInfo" class="panel-body panel-collapse collapse in" style="text-align: left;" role="tabpanel" aria-labelledby="headingInfo">
			<p><small><span class="text-muted">Network name:</span><br>
			${network.networkName}</small></p>
			
			<p><small><span class="text-muted">Submitted by:</span><br>
			${network.creator.name}</small></p>
			
			<p><small><span class="text-muted">Submitted on: </span><br>
			${network.createdDate}</small></p>
			
			<p><small><span class="text-muted">Annotated text:</span><br>
			${network.textUrl}</small></p>
		</div>
	</div>
	</div>
</c:if>
<div class="panel panel-default" id="concept" role="tablist" aria-multiselectable="true">
	<div class="panel-heading" role="tab" id="headingConcept">
	    <a role="button" data-toggle="collapse" data-parent="#concept" href="#bodyConcept" aria-expanded="true" aria-controls="bodyConcept">
           <small>Concept: <span id="lemma_name">Select a node</span> <i class="fa fa-caret-down" aria-hidden="true"></i></small>
        </a>
	</div>
	<div id="bodyConcept" class="panel-body panel-collapse collapse in" role="tabpanel" aria-labelledby="headingConcept">
	<div id="loading" style="display:none">
	  			<p><img src="${pageContext.servletContext.contextPath}/resources/public/imgs/ajax-loader.gif" /><small>Loading...</small></p>
	</div>
	<small>
	<p class="text-left" style="overflow: scroll;"><span id="concept_desc">...to display concept information.</span></p>
    </small>
	</div>
</div>

<div class="panel panel-default" id="textsPanel" role="tablist" aria-multiselectable="true">
	<div class="panel-heading" role="tab" id="headingTexts">
	   <a role="button" data-toggle="collapse" data-parent="#textsPanel" href="#bodyTexts" aria-expanded="true" aria-controls="bodyTexts">
	   <small>Text occurrences <i class="fa fa-caret-down" aria-hidden="true"></i></small>
	   </a>
	</div>
	<div id="bodyTexts" class="panel-body panel-collapse collapse in" style="text-align: left; max-height: 200px; overflow-y: scroll;" role="tabpanel" aria-labelledby="headingTexts">
	<center>
	<div id="loading1" style="display:none">
 		<p><img src="${pageContext.servletContext.contextPath}/resources/public/imgs/ajax-loader.gif" /> <small>Loading...</small></p>
	</div>
	</center>
	<small>
    <div id="texts" class="text-left">Select node to display text.</div>        
    </div>   
    </small>
	</div>
</div>

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


                
<script>

// text modal
$(document).ready(function() {
	$('#txtModal').on('show.bs.modal', function(event) {
                                            var link = $(event.relatedTarget);

                                            var txtid = link.data('${texts}.textId');
                                            var txtname = link.data('${texts}.txtname');
                                            var title = link.data('${texts}.txttitle');
                                            var author = link.data('${texts}.txtauthor');
                                            var date = link.data('${texts}txtdate');
                                            
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
                                            $
                                                    .ajax({
                                                        type : "GET",
                                                        url : "${pageContext.servletContext.contextPath}/public/text/view?conceptUri=${concept.id}&txtid="
                                                                + txtid,
                                                        contentType : "text/plain",
                                                        success : function(details) {
                                                            $('.modal-title')
                                                                    .html(header);
                                                            $('.modal-body')
                                                                    .html(details);
                                                        },
                                                    });

                                        });
                    });
</script>


<div class="text-left">
<h5><a href="#" id="exportJson"><i class="fa fa-download" aria-hidden="true"
	title="Download as JSON"></i> Download network as JSON</a><br>
<small>Right-click and select "Save Link As...", then enter a filename and use a '.json' extension. <br>You can import the saved JSON file into Cytoscape.</small></h5>
</div>

<div class="text-left">
<h5><a href="#" id="exportPng"><i class="fa fa-file-image-o" aria-hidden="true"
    title="Download as PNG"></i> Download network as PNG</a><br>
<small>Image will open in a new tab or window.</small></h5>
</div>
