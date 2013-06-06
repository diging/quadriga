<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<header>
	<h3 align="center">Concept lists of  <span><b>${username}</b></span> </h3>
</header>


	
	<div  style="float: left; width: 50%;">
	<display:table name="conceptlist" keepStatus="true">
	<display:column property="name" sortable="false" title="You own these concept collections"
	maxLength="25"   href="conceptDetails" paramId="name" />
	</display:table>
	<%-- <h4 align="center"> You own these concept collections</h4>
	<ul>
    <c:if test="${not empty conceptlist}">
    <c:forEach var="concept" items="${conceptlist}">
	<li><details><summary><a href="conceptcollection/${concept.id}">
	<c:out value="${concept.name}"></c:out>  </a></summary>
	<c:out value="${concept.description}"></c:out></details>
	</li>
	</c:forEach>
	</c:if>
	</ul>  	
 --%>
 	</div>
	<div  style="float: left; width: 50%;">
	<display:table name="collaborationlist" pagesize = "1" keepStatus="true">
	<display:column property="name" sortable="false" title="You also participate in these concept collections"
	maxLength="25"  href="conceptDetails" paramId="name"/>
	</display:table>
	<%-- <h4 align="center"> You also participate in these concept collections</h4>
	<ul>
    <c:if test="${not empty conceptlist}">
    <c:forEach var="concept" items="${collaborationlist}">
	<li><details><summary><a href="conceptcollection/${concept.id}">
	<c:out value="${concept.name}"></c:out>  </a></summary>
	<c:out value="${concept.description}"></c:out></details>
	</li>
	</c:forEach>
	</c:if>
	</ul> 
 --%>	
 	</div> 	
	
	

</html>