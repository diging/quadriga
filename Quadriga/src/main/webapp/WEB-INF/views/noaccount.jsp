<header>
	<pre><h2>${username}, you don't have a Quadriga account yet.</h2></pre>
	<span class="byline">Please request an account</span>
</header>

<script>
$(function() {
	$("input[type=submit]").button().click(function(event) {
		event.preventDefault();
		$('#requestForm').submit();
	});
});
</script>
<section>
	<p>Place the request for a Quadriga Account by using the button below.</p>
	<form id='requestForm'
		action="${pageContext.servletContext.contextPath}/requests/submitAccountRequest"
		method='POST'>
		<input type="submit" type="submit" value="Request Account">
	</form>
</section>