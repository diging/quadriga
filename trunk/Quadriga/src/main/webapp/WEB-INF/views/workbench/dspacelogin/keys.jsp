<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<article class="is-page-content">
	<h3>Manage Dspace Access Keys</h3>
	<form:form method="POST" action="/quadriga/auth/workbench/updatekeys">
		<table>
			<tr>
				<td><form:label path="publicKey">Public Key :</form:label></td>
				<td><form:input path="publicKey" /><c:choose>
						<c:when test="${not empty dspaceKeys}">
		Existing Public Key ends in: ${dspaceKeys.publicKey }
		</c:when>
						<c:otherwise>
		No Public Key stored in the system !
		</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td><form:label path="privateKey">Private Key :</form:label></td>
				<td><form:input path="privateKey" /><c:choose>
						<c:when test="${not empty dspaceKeys}">
		Existing Public Key ends in: ${dspaceKeys.privateKey }
		</c:when>
						<c:otherwise>
		No Public Key stored in the system !
		</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td colspan="2"><c:choose>
						<c:when test="${not empty dspaceKeys}">
		<input type="submit" value="Update the keys" />
		</c:when>
						<c:otherwise>
		<input type="submit" value="Store new keys" />
		</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->