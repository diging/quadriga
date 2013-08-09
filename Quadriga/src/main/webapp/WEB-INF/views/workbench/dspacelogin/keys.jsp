<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<article class="is-page-content">

	Helloooooooo !
	<form:form method="POST" action="/quadriga/auth/workbench/updatekeys">
		<table>
			<tr>
				<td><form:label path="publicKey">Public Key :</form:label></td>
				<td><form:input path="publicKey" /></td>
			</tr>
			<tr>
				<td><form:label path="privateKey">Private Key :</form:label></td>
				<td><form:input path="privateKey" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->