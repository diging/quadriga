<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
	function submitCollabClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/collab/${dictionaryid}';
	}

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});

	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Dictionary: ${dictionaryname}</h2>
<div class="back-nav">
    <hr>
    <p>
        <a href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"><i
            class="fa fa-arrow-circle-left"></i> Back to Dictionary</a>
    </p>
    <hr>
</div>

<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/changedictionaryowner/${dictionaryid}">
	<c:choose>
		<c:when test="${success=='0'}">
			<c:if test="${not empty collaboratinguser}">
			
			 <div class="alert alert-info" role="alert">Dictionary is
            currently owned by: ${dictionaryowner}</div>

				
				<p>Select a new owner for the dictionary:</p>
				<p>
				<form:select path="userName" class="form-control">
					<form:option value="" label="--- Select ---" />
					<form:options items="${collaboratinguser}" itemValue="userName"
						itemLabel="userName" />
				</form:select>
				<form:errors path="userName" cssClass="error"></form:errors>
				</p>
				<div class="alert alert-warning" role="alert">Note: The current
            owner of this dictionary will become a dictionary
            admin and will not be able to undo ownership transfer.</div>
            
				<p><input class="btn btn-primary" type="submit" value="Assign">
				<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Cancel</a></p>
			</c:if>
			<c:if test="${empty collaboratinguser}">
				<p>You don't have any collaborators assigned to this dictionary.
					To transfer ownership of this dictionary, first add another user as
					collaborator to the dictionary.</p>
				<p>
					<a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Back</a>
				    
				</p>
			</c:if>
		</c:when>
		<c:otherwise>
			<span class="byline">Dictionary Ownership transferred
				successfully.</span>
			<br />
			<ul>
				<li><input type="button" onClick="submitCollabClick(this.id);"
					value='Okay'></li>
			</ul>
		</c:otherwise>
	</c:choose>
</form:form>
