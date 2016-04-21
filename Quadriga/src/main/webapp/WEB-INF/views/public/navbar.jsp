<li ${currentPage == "home" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks">Explore</a></li>
<li ${currentPage == "statistics" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/statistics">Statistics</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/search">Search</a></li>
<li> <a style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80%">${project.owner.name} </a>
<a href="/quadriga/logout" style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80% "> Logout </a></li>
