<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<span onload='document.f.j_username.focus();'>
    <h3>Register</h3>
 
    <c:if test="${not empty errormsg_username_in_use}">
        <div class="ui-state-error-text">
            Sorry, but the username you chose is already in use.
        </div>
    </c:if>
    <c:if test="${not empty errormsg_failure}">
        <div class="ui-state-error-text">
            Sorry, we encountered an unexpected error.
        </div>
    </c:if>
    <form
    action="${pageContext.servletContext.contextPath}/register-user"
    method='post' model='request'>
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username" id="username" size="50" value="${request.username}"></td>
        </tr>

        <tr>
            <td>Full Name:</td>
            <td><input type="text" name="name" id="name" size="50" value="${request.name}"></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email" id="email" size="50" value="${request.email}" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" id="password" size="50"></td>
        </tr>
        <tr>
            <td>Repeat Password:</td>
            <td><input type="password" name="repeat-pw" id="repeat-pw" size="50"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Register"
                class="button"></td>
        </tr>
    </table>

</form>
</span>