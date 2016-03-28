<<<<<<< HEAD
<li ${currentPage == "home" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a href="">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a href="">Search</a></li>
<li> <a class="fa fa-user" style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80%">${project.owner.name} </a>
<a class="fa fa-user" href="/quadriga/j_spring_security_logout" style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80% "> Logout </a></li>

=======
<li ${currentPage == "home" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/search">Search</a></li>
<li ${currentPage == "getprojectstatistics" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/statistics">Statistics</a></li>
>>>>>>> 175a7db19186d3aed94a26e87b1590e78d3839e4
