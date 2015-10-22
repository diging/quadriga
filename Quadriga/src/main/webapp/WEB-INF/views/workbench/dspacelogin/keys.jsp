<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<article class="is-page-content">
<script>
$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
		event.preventDefault();
	});
	
	$("input[type=button]").button().click(function(event) {
		event.preventDefault();
	});
	
	$("#keysSubmit").click(function()
			{
				$('#formKeys').submit();
			});
});
</script>
	<h2>Manage Dspace Access Keys</h2>
	A more secure way to access Dspace repository. Generate new keys <a href="https://import.hps.ubio.org/api_keys" target="_blank">here.</a>
	<form:form method="POST" id="formKeys" action="${pageContext.servletContext.contextPath}/auth/workbench/updatekeys">
		<table>
			<tr>
				<td><form:label path="publicKey">Public Key :</form:label></td>
				<td><form:input path="publicKey" autocomplete="false"/><form:errors path="publicKey" cssClass="error"></form:errors><c:choose>
						<c:when test="${not empty dspaceKeys}">
		Existing Public Key ends in: ${dspaceKeys.publicKey }
		</c:when>
						<c:otherwise>
		No Public Key stored in the system !
		</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td><form:label path="privateKey">Private Key :</form:label></td>
				<td><form:input path="privateKey" autocomplete="false" /><form:errors path="privateKey" cssClass="error"></form:errors><c:choose>
						<c:when test="${not empty dspaceKeys}">
		Existing Public Key ends in: ${dspaceKeys.privateKey }
		</c:when>
						<c:otherwise>
		No Private Key stored in the system !
		</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td colspan="2"><c:choose>
						<c:when test="${not empty dspaceKeys}">		
		<script>
		$(document).ready(function() {			
			$("#dialog-confirm").dialog({
				open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
				autoOpen: false,
			    modal: false,
			    resizable: false,
			    buttons: {
			        Yes: function () {
			        	$( this ).dialog( "close" );
			        	location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deletekeys';
			        	return false;
			        },
			        No: function() {
			        	$( this ).dialog( "close" );
		                return false;
			        }
			    }
			});
			
		});
		
		function deleteCheck()
		{			
			 $( "#dialog-confirm" ).dialog( "open" );
		}
		</script>
		<div id="dialog-confirm" title="Delete record" style="display:none;">
  			<span style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?
		</div>
		<input type="submit" id="keysSubmit" value="Update the keys" />
		<input type="submit" onclick="deleteCheck()" value="Remove the keys" />
		<input type="button" value="Cancel" onClick="location.href='${pageContext.servletContext.contextPath}/auth/workbench'">
		</c:when>
						<c:otherwise>
		<input type="submit" id="keysSubmit" value="Store new keys" />
		<input type="button" value="Cancel" onClick="location.href='${pageContext.servletContext.contextPath}/auth/workbench'">
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->