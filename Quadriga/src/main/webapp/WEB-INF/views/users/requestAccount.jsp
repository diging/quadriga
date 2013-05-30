<header>
	<h2>Hi ${username}, you don't have an account yet.</h2>
	<span class="byline">Do you want to request
	one?</span>
</header>

<section>
<form name='f' action="${pageContext.servletContext.contextPath}/requests/submitAccountRequest"
		method='POST'>
	<input type="submit" type="submit"
					value="Request Account" >

</form>
</section>