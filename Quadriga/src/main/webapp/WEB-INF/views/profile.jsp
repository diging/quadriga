<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
function onSubmit(){
	
	location.href='${pageContext.servletContext.contextPath}/auth/profile/adduri';	
}

</script>

<form>
<ul>
<li>name:</li>
<li>email:</li>
</ul>
<input type="submit" value="add uri" onclick="onSubmit();">
</form>