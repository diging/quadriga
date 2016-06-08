<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function(){
    $('#conceptdesctextarea').scrollTop($('#conceptdesctextarea').scrollHeight);
    $('#annotationtextarea').scrollTop($('#annotationtextarea').scrollHeight);   
    
});
</script>

<style>
	textarea {
		border-style: none;
		border-color: Transparent;
		background: #CEE7F5;
		color: #000000;
		overflow: auto;
		left: 0;
	}
</style>

<c:if test="${!isNetworkEmpty}">
<div>
	<h4>You have searched for:</h4>
	<div><c:out value="${searchNodeLabel}"/></div>
	<div class="text-muted"> <c:out value="${description}"></c:out> </div>
</div>
<br>

    <div class="panel panel-default">
        <div class="panel-heading" id="lemma_name">Select a node</div>
        <div class="panel-body">
            <div id="concept_desc">...to display node information</div>
        </div>
    </div>
    
    <div class="panel panel-default">
        <div class="panel-heading" id="lemma_name">Select a node</div>
        <div class="panel-body">
            <div id="texts">...to display text.</div>
        </div>
    </div>

</c:if>