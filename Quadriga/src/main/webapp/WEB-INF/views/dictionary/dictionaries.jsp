
<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<h2>Dictionaries</h2>
<span class="byline">Manage your dictionaries here.</span>

<div>
	<c:if test="${not empty dictionarylist}">
	    <h4>You own these Dictionaries:</h4>
	   
			<c:forEach var="dictionary" items="${dictionarylist}">
			<div class="panel panel-default">
                <div class="panel-body">
				<a
					href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionary.dictionaryId}">
						<i class="fa fa-book"></i> <c:out
							value="${dictionary.dictionaryName}"></c:out>
				</a> <br> <c:out value="${dictionary.description}"></c:out>
				</div>
			</div>
			</c:forEach>
	</c:if>
	<c:if test="${empty dictionarylist}"> You don't own any dictionaries.
    </c:if>
</div>

<div style="float: right;">
	<a
		href="${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionary"><i class="fa fa-plus-circle"></i> Add
		Dictionary</a>
</div>
</br>
<c:if test="${not empty dictionaryCollabList}">
    You participate in these Dictionaries:
    <ul>
		<c:forEach var="dictionary" items="${dictionaryCollabList}">
			<li class="dt"><a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/collab/${dictionary.dictionaryId}">
					<i class="fa fa-book"></i> <c:out
						value="${dictionary.dictionaryName}"></c:out>
			</a> <br> <c:out value="${dictionary.description}"></c:out></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${empty dictionaryCollabList}">
    You don't collaborate on any dictionaries.
</c:if>

</div>
</div>