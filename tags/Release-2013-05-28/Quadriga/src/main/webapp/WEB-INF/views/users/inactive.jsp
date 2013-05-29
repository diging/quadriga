<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
 <script>
  $(function() {
    $( "input[type=submit]" )
      .button()
      .click(function( event ) {
        event.preventDefault();
      });
  });
  </script>
  
		<p>
			Users-Inactive List...
		</p>
<ol>
    <c:if test="${not empty inactiveUserList}">
    <c:forEach var="user" items="${inactiveUserList}">
    <li><font size="3"><c:out value="${user.name}"></c:out></font><font size="1"> <input type="submit" onclick="location.href='/quadriga/auth/users/activate/${user.userName}'" value="Activate"></font></li><br>
	</c:forEach></c:if>
	
</ol>		
		
</article>