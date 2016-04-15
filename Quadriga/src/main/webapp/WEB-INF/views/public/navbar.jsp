<li ${currentPage == "home" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a href="">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a href="">Search</a></li>
<li> <a class="fa fa-user" style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80%">${project.owner.name} </a>
<a class="fa fa-user" href="/quadriga/j_spring_security_logout" style=" margin:2px 10px; padding:0px 0px 0px 0px; font-size:80% "> Logout </a></li>
