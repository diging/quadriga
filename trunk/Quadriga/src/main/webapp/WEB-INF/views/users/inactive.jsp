<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">

		<p>
			Users-Inactive List...
		</p>
<ol>
    <c:if test="${not empty inactiveUserList}">
    <c:forEach var="user" items="${inactiveUserList}">
	<li><c:out value="${user.name}"></c:out>  <button onclick="location.href='/quadriga/auth/users/activate/${user.userName}'" value="${user.userName}">Activate</button></li><br>
	</c:forEach></c:if>
	
</ol>		
		
</article>