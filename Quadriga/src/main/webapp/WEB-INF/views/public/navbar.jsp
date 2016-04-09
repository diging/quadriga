<li ${currentPage == "home" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "about" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/about">About</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/search">Search</a></li>
<li ${currentPage == "getprojectstatistics" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/statistics">Statistics</a></li>
