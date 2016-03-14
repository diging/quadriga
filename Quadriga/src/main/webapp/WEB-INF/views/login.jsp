<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
});
</script>
<span onload='document.f.j_username.focus();'>
	<h3>Login</h3>
 
    <c:if test="${not empty successmsg }">
        <div class="ui-state-success-text">
            ${ successmsg }
        </div>
    </c:if>
	<c:if test="${not empty error}">
		<div class="ui-state-error-text">
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
 <!-- <span style="float: right">
Language : <a href="${pageContext.servletContext.contextPath}/login?siteLanguage=en_US">English</a>|
    <a href="${pageContext.servletContext.contextPath}/login?siteLanguage=zh_CN">Chinese</a>
</span> -->
	<form name='f' action="<c:url value='/login' />"
		method='POST'>
		<table>
			<tr>
				<td><spring:message code="label.user" />:</td>
				<td><input type='text' name='username' value=''>
				</td>
			</tr>
			<tr>
				<td><spring:message code="label.password" />:</td>
				<td><input type='password' name='password' />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" class="button"/>
				</td>
			</tr>
			<tr>
			     <td colspan="2">
			         <a href="register">Register new account...</a>
			     </td>
			</tr>
		</table>
 
	</form>
	
</span>