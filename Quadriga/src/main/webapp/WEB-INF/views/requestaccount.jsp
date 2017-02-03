<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<header>
	<pre><h2>Create Quadriga Account</h2></pre>
	<span class="byline">Please provide some information about yourself.</span>
</header>

<section>
	<p>Place the request for a Quadriga Account by using the button below.</p>
	<form:form modelAttribute='accountRequest'
		action="${pageContext.servletContext.contextPath}/submitCreateAccount"
		method='POST'>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table>
		<tr>
			<td>Name: </td><td><form:input  path="name" /></td>
		</tr>
		<tr>
			<td>Username:</td><td><form:input  path="username"/></td>
		</tr>
		<tr>
			<td>Email:</td><td><form:input path="email" /></td>
		</tr>
		<tr>
			<td>Password: </td><td><form:password  path="password" /></td>
		</tr>
		<tr>
			<td>Repeat password: </td><td><form:password path="repeatedPassword" /></td>
		</tr>
		
		</table>
		<input type="submit" value="Create Account">
	</form:form>
</section>