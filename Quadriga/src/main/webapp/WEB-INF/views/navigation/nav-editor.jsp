<%@ page language="java" contentType="text/html;"%>

<script>
	$(function() {
		$("#menu").menu();
	});
</script>
 <style>
.ui-menu {
	width: 150px;
	font-size:14px;
	position:relative;
    z-index:9999;
    margin:1;
}
</style>

<ul id="menu">
	<li>
		<h2 class="major">
			<span>Toolbox</span>
		</h2>
	</li>
	<li><a href="#">List of Approved and rejected Network</a>

	</li>
	<li><a href="#">Networks assigned to other editors</a>

	</li>
</ul>  