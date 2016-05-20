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
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}';
	}
	
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

<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/changedictionaryowner/${dictionaryid}">
	<c:choose>
		<c:when test="${success=='0'}">
			<c:if test="${not empty collaboratinguser}">
			   <h2>Dictionary: ${dictionaryname}</h2>
			   <hr>
			   <div class="user">Owned by: ${dictionaryowner}</div>
			   <hr>
			   <div>Assign new owner to dictionary</div>
				<form:select path="userName">
					<form:option value="" label="--- Select ---" />
					<form:options items="${collaboratinguser}"
						itemValue="userName" itemLabel="userName" />
				</form:select>
				<form:errors path="userName" cssClass="error"></form:errors>
				<div>Note:Current owner will become dictionary admin</div>
				<td><input type="submit" value="Assign"></td>
			</c:if>
			<c:if test="${empty collaboratinguser}">
          You don't have any collaborators assigned to dictionary.
          <ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Okay'></li>
			</ul>	   
		</c:if>
		</c:when>
		<c:otherwise>
			<span class="byline">Dictionary Ownership transferred successfully.</span>
			<br />
			<ul>
				<li><input type="button" onClick="submitCollabClick(this.id);"
					value='Okay'></li>
			</ul>
		</c:otherwise>
	</c:choose>
</form:form>
