<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<section>
	<h4>You have searched for: <c:out value="${searchNodeLabel}"/></h4>
	<div class="text-muted"> <c:out value="${description}"></c:out> </div>
</section>

<section>
	<h4 class="major"><span>Network details</span></h4>
		
		<ul class="style3">
		<li>
			<article class="is-post-summary">
				<div align="left" id="item_metadata">
				<table id = metadataTable border="1" style="width:300px">
					<tr>
        				<th>Filename</th>
        				<th>Author</th>
        				<th>Last Modified Date</th>
   				 	</tr>
				</table>
				</div>
				
			</article>
		</li>
		
		<li>
			<article class="is-post-summary">
				<div align="left" id="lemma_name"></div>
				<div id="concept_desc"></div>
			</article>
		</li>
		<li>
			<article class="is-post-summary">
				<div align="left" id="annot_desc"></div>
				<div id="annot_details"></div>
			</article>
		</li>
	</ul>
</section>
</c:if>