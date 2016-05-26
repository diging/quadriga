<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<span onload='document.f.j_username.focus();'>
    <h2>Register</h2>
 
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
            <td><input class="form-control" type="text" name="username" id="username" size="50" value="${request.username}"></td>
            <td><form:errors path="accountRequest.username"></form:errors></td>
        </tr>

        <tr>
            <td>Full Name:</td>
            <td><input class="form-control"  type="text" name="name" id="name" size="50" value="${request.name}"></td>
            <td><form:errors path="accountRequest.name"></form:errors></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input class="form-control"  type="text" name="email" id="email" size="50" value="${request.email}" /></td>
            <td><form:errors path="accountRequest.email"></form:errors></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input class="form-control"  type="password" name="password" id="password" size="50"></td>
            <td><form:errors path="accountRequest.password"></form:errors></td>
        </tr>
        <tr>
            <td>Repeat Password:</td>
            <td><input class="form-control"  type="password" name="repeatedPassword" id="repeatedPassword" size="50"></td>
            <td><form:errors path="accountRequest.repeatedPassword" ></form:errors></td>
        </tr>
        <tr>
            <td colspan="2">
             <div class="byline-sm">By clicking "Register" you agree to the policies and guidelines of Quadriga.</div>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input class="btn btn-primary" type="submit" value="Register"
                class="button"></td>
        </tr>
    </table>

</form>
</span>