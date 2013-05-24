<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">

		<p>
			Users-active List...
		</p>

<ol>
    <c:if test="${not empty activeUserList}">
    <c:forEach var="user" items="${activeUserList}">
	<li><c:out value="${user.name}"></c:out>  <button onclick="location.href='/quadriga/auth/users/deactivate/${user.userName}'" value="${user.userName}">Deactivate</button></li><br>
	</c:forEach></c:if>
	
</ol>
		
</article>