<li ${currentPage == "home" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a href="">Search</a></li>
<li ${currentPage == "projectblog" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/projectblog">Project Blog</a></li>