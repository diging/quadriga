<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
			if (!$.trim($("#items").val())) {
					$.alert("Please enter a dictionary item ","Oops !!!");
					$("#items").val("");		            
					event.preventDefault();
					return;
			}
		});
	});
</script>

<article class="is-page-content">

	<form:form method="POST" action="/auth/dictionaries/adddictionaryItem">
		<table>
			<tr>
				<td>Item:</td>
				<td><form:input path="items" size="30" id="items"/></td>
			</tr>
		</table>
		<input type="submit" value="Add Dictionary Item">
	</form:form>

</article>

<!-- /Content -->