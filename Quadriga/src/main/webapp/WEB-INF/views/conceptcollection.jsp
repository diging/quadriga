<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
<!--
table.its
{
	width: 100%;
}
table.its thead tr {
	font-weight: 700;
	color: rgb(107, 119, 112);
	text-align: center;
	background-color: #e3daa8;
	
	}
	table.its thead tr a:LINK {
		text-decoration:none;
	}
	table.its thead tr a:HOVER {
		text-decoration:underline;
	}

table.its tr.even {
	background-color: #e3daa8;
	color: rgb(255, 255, 255);
	text-align: justify;
}
.pagelinks {
	float: right;
	text-align: right;
}

table.its tr.odd {
	text-align: justify;
}
-->
</style>
<header>
	<h3 align="center">Concept lists of  <span><b>${username}</b></span> </h3>
</header>


	
	<div  style="float: left; width: 45%; border-radius:5px; border:2px solid #e3daa8; padding: 20px;">
	<display:table class="its" name="conceptlist" keepStatus="true" requestURI="/auth/conceptcollections" uid="1" pagesize = "10">
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
	<div  style="float: right; width: 45%; border-radius:5px; border:2px solid #e3daa8; padding: 20px;">	
	<display:table class="its" name="collaborationlist" requestURI="/auth/conceptcollections" pagesize = "10" keepStatus="true" uid="2">
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