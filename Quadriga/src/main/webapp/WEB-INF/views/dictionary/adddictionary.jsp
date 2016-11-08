<!--  
	Author Lohith Dwaraka  
	Used to add an dictionary	
-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="dictionary" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<h2>Add Dictionary</h2>

	<div class="back-nav">
		<hr>
		<p>
			<a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries"><i
				class="fa fa-arrow-circle-left"></i> Back to Dictionary List</a>
		</p>
		<hr>
	</div>

	<table>
		<tr>
			<td>Name:</td>
			<td><form:input class="form-control" path="dictionaryName"
					size="30" id="name" /></td>
			<td><form:errors path="dictionaryName"
					class="ui-state-error-text"></form:errors></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><form:textarea class="form-control" path="description"
					cols="23" rows="4" id="description" /></td>
			<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
		</tr>
	</table>
	<input class="btn btn-primary" type="submit" value="Create Dictionary">
</form:form>


<!-- /Content -->