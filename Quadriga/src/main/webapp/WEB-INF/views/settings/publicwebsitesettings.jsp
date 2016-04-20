<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<title>Public Page Settings</title>
<a
	href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/editabout">Change
	About Project details</a>
