<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3>Username : ${username}</h3>
<h3>Role : ${role}</h3>

<a href="<c:url value="j_spring_security_logout" />"> Logout</a>
