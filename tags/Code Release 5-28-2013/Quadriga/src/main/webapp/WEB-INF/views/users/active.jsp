<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">

		<p>
			Users-active List...
		</p>

 <script>
  $(function() {
    $( "input[type=submit]" )
      .button()
      .click(function( event ) {
        event.preventDefault();
      });
  });
  </script>

<ol>
    <c:if test="${not empty activeUserList}">
    <c:forEach var="user" items="${activeUserList}">
	<li><font size="3"><c:out value="${user.name}"></c:out></font><font size="1"> <input type="submit" onclick="location.href='/quadriga/auth/users/deactivate/${user.userName}'" value="Deactivate"></font></li><br>
	</c:forEach></c:if>
	
</ol>
		
</article>