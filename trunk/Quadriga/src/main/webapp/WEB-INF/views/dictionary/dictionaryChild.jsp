<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<script>
		function generatenew(){
		    document.addItem.itemName.style.visibility="visible";
		    document.addItem.submit.style.visibility="visible";
		}
	</script>
	
 	<header>
		<span class="byline">Manage Dictionary : <c:out value="${dictName}"></c:out></span>
		<!-- 	<a href="/quadriga/auth/dictionaries/addDictionaryItems">Add Dictionary Items</a> -->
		
	</header>
	<article class="is-page-content">
			<form name='addItem' method="post" action="">
				<div id="div"></div>
				<input type="button"  value="Add new Item" onclick="generatenew();">  
				<input type="text" style="visibility:hidden"  name="itemName">
				<input type="submit"  style="visibility:hidden" value="submit" name="submit">  
			</form>
	</article>
		
	<ul>
	<li>
    <c:choose>
    <c:when test="${not empty dictionaryItemList}">
    <b>Dictionary Items of <c:out value="${dictName}"></c:out>:</b>
    <c:forEach var="dictionaryItem" items="${dictionaryItemList}">
	<li><a href="dictionaries/${dictionaryItem.dictionaryId}">
	<c:out value="${dictionaryItem.dictionaryId}"></c:out></a> &nbsp;&nbsp;
	<c:out value="${dictionaryItem.items}"></c:out>   
	</li>
	</c:forEach>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
	</li>
	</ul>