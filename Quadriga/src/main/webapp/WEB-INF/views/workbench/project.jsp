<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<h2>welcome to ${project.name}</h2>
<div style="opacity:0.5;position:relative;left:50px;width:170px;height:210px;background-color:#ffffcc">
<ul>
<li><h6><FONT FACE="arial">Project name:</FONT></h6><c:out value="${project.name}"></c:out></li>
<li><h5><FONT FACE="arial">What is it about: </FONT></h5><c:out value="${project.description}"></c:out>
<li><h5><FONT FACE="arial">Owned by: </FONT></h5><c:out value="${project.owner.name}"></c:out>
</ul>
</div>

	




