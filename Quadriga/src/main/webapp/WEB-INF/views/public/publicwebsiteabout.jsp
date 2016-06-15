<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty aboutText.description}">
<center>
    <div width: 1136px;">
        <h2>${aboutText.title}</h2>
        <article style="margin-top:20px; text-align: left;">${aboutText.description}</article>
    </div>
</center>
</c:if>

<c:if test="${empty aboutText.description}">
<h1>About Project "${project.projectName}"</h1>
<p>
This page is under construction. Please check back soon for more information about this project.
</p>
</c:if>
