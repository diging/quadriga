<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<article class="is-page-content">
	<form:form commandName="textfile" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myProjectId}/${workspaceId}/addtext">
				<header>
					<h2>Add Text to Workspace</h2>
					<span class="byline">Please fill in the following
						information:</span>
				</header>
				
		  		<table style="width: 100%">
					<tr>
						<td style="width: 170px">File Name:</td>
						<td><form:input path="fileName" size="60" id="fileName" /></td>
						<td><form:errors path="fileName" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td style="width: 170px">Reference:</td>
						<td><form:input path="refId" size="60" id="refID" /></td>
						<td><form:errors path="refId" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td style="vertical-align: top">Add File Content:</td>
						<td><form:textarea path="fileContent" cols="44" rows="6"
								id="fileContent" /></td>
						<td><form:errors path="fileContent" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td><input type="submit" value="Save Textfile"></td>
					</tr>
					<tr>
						<td><input type="hidden" id="workspaceId"
							value=<c:out value="${workspaceId}"></c:out> /></td>
					</tr>
					<tr>
						<td><input type="hidden" id="success"
							value=<c:out value="${success}"></c:out> /></td>
					</tr>
				</table>
				
		  <c:if test="${success == '1'}">
		  <span class="byline">Textfile saved successfully.</span>
		  </c:if>
		  		  
	</form:form>
</article>

<!-- /Content -->
