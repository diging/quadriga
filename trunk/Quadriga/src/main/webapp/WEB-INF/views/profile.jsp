<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script>
function addURI(){
var retValue = prompt("Name of Service:"," ");
//$alert(prompt("Name of Service:"," "),prompt("URI:",""));
var retValue1 = prompt("URI:","");
}
</script>

 <h1>profile</h1>
 ---------------------------------
 <ul>
 <li>Name:<sec:authentication property="principal.username" /></li>
 <li>Email:</li>
 </ul>
 <input type="submit" value="Add URI" onClick="addURI();" />