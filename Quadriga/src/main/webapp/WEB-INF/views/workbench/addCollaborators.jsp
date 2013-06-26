<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
<article class="is-page-content">
<form:form method="POST" action="/auth/workbench/${projectid}/addcollaborator">
		
		<input type="submit" value="Add collaborator">
	</form:form>

</article>
