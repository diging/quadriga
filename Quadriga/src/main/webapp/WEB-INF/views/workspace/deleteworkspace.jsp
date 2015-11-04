<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Content -->
<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
	$("#dlgConfirm").hide();
	
	<%-->Default uncheck the checkbox <--%>
	$("form input:checkbox").prop("checked",false);
});

function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}';
}
$(function() {
	
	$("input[name='Back']").button().click(function(event) {
		event.preventDefault();
	});
	
	$("input[name='deletews']").button().click(function(event){
	});
	
	$("input[name='deletews']").button().click(function(event) {
		if ($("form input:checkbox").is(":checked")) {
			event.preventDefault();
			$("#dlgConfirm").dialog({
				resizable : false,
				height : 'auto',
				width : 350,
				modal : true,
				buttons : {
					Submit : function() {
						$(this).dialog("close");
						$("#deletewsform")[0].submit();
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});
		}
	});
	
	$("input[name='selectall']").button().click(function(event){
		$("form input:checkbox").prop("checked",true);
		event.preventDefault();
		return;
	});
	
	$("input[name='deselectall']").button().click(function(event){
		$("form input:checkbox").prop("checked",false);
		event.preventDefault();
		return;
	});
});
</script>
<article class="is-page-content">
	<header>
		<h2>Delete workspace</h2>
	</header>
	<form:form modelAttribute="workspaceform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}/deleteworkspace"
		id="deletewsform">
		<c:choose>
			<c:when test="${success == '1'}">
				<span class="byline">Successfully deleted selected workspace</span>
				<ul>
					<li><input type="button" onClick="submitClick(this.id);"
						value='Okay' name="Back"></li>
				</ul>
			</c:when>
		</c:choose>
	</form:form>
</article>