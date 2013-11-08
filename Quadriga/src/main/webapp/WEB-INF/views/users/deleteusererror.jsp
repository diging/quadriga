<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/users/manage';
}

$(document).ready(function() {
	$("input[type=button]").button().click(function(event) {
	});
});
</script>
<!-- Content -->
<span class="byline"> 
      <spring:message code="workbech.associated" />
</span>
<input type="button"
			onClick="submitClick(this.id);"
			value='Back' name="Back">
			