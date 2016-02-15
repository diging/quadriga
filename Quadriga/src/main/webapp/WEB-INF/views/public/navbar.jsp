<li ${currentPage == "home" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a href="">Explore</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a href="">Search</a></li>
<li><a class="fa fa-user" href=" "><font color="white">${project.owner.name}</font></a>
<a class="fa fa-user" href="/quadriga/j_spring_security_logout">logout</a></li>

