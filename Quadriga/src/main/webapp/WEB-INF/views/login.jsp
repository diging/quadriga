<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<section id="home" name="home"></section>
<div id="headerwrap">
	<div class="container">
		<div class="row centered">
			<div class="col-lg-12">
				<c:if test="${not empty successmsg }">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${ successmsg }
					</div>
				</c:if>
				<c:if test="${not empty error}">
				<div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        Your login attempt was not successful, try again.<br /> Caused :
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
				</c:if>
				<c:if test="${not empty socialsigninmessage && type == '1'}">
				<div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        ${socialsigninmessage}
                    </div>
				</c:if>
				<c:if test="${not empty socialsigninmessage && type != '1'}">
				<div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        ${socialsigninmessage}
                    </div>
				</c:if>
				<h1>
					Welcome To <b>Quadriga</b>
				</h1>
				<h3>Showcase your network projects for the Epistemic Web.</h3>
				<br>
			</div>

			<div class="col-lg-12">
				<img class="img-responsive"
					src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/img/networks-dark4.png"
					alt="">
			</div>

		</div>
	</div>
	<!--/ .container -->
</div>
<!--/ #headerwrap -->


<section id="desc" name="desc"></section>
<!-- INTRO WRAP -->
<div id="intro">
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-sm-12">
				<h1>Realizing the Epistemic Web</h1>
				<p>The Epistemic Web is the next step to create "a universe of
					knowledge on the Web that parallels human knowledge". By
					"federating documents" (either by hand or automatically) different
					sources of knowledge will be combined in order to provide access to
					several sources at once and to qualify the relationships of
					different sources. As central repository to store datasets from
					different projects, Quadriga aims to achieve federation of
					knowledge as a first step towards the Epistemic Web. (Hyman,
					Malcolm D. and Jürgen Renn (2012). "Toward an Epistemic Web.")</p>
			</div>
			<div class="col-md-6 col-sm-12">
				<h1>Quadruples</h1>
				<p>
					Quadriga is based on so-called "Quadruples," also referred to as
					"contextualized triples." Quadruples are subject, predicate, object
					triples with a context that includes metadata such as by whom or
					when a Quadruple was created. You can annotate texts with
					Quadruples representing your interpretation of a text using <a
						href="http://vogonweb.net">VogonWeb</a>. VogonWeb will submit your
					Quadruples to Quadriga persisting the relationships you create
					between concepts along with information about who, when, and for
					what text they were created.
			</div>
		</div>

		<hr>
	</div>
	<!--/ .container -->
</div>
<!--/ #introwrap -->

<!-- FEATURES WRAP -->
<div id="features">
	<div class="container">
		<div class="row">
			<h1 class="centered">Explore the Projects</h1>
			<br> <br>
			<div class="col-lg-6 centered">
				<!-- ACCORDION -->
				<div class="accordion ac" id="accordion2">
					<c:if test="${not empty latestProjects[0]}" >
                    <div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseOne"> ${latestProjects[0].projectName} </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse">
							<div class="accordion-inner">
								<p>${latestProjects[0].description}
								<br><a style="font-size: 16px;" href="${pageContext.servletContext.contextPath}/sites/${latestProjects[0].unixName}">Explore project...</a></p>
							</div>
							<!-- /accordion-inner -->
						</div>
						<!-- /collapse -->
					</div>
					<!-- /accordion-group -->
					<br>
                    </c:if>
                    <c:if test="${not empty latestProjects[1]}" >
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseTwo"> ${latestProjects[1].projectName} </a>
						</div>
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner">
								<p>${latestProjects[1].description} <br><a style="font-size: 16px;" href="${pageContext.servletContext.contextPath}/sites/${latestProjects[1].unixName}">Explore project...</a>
							     </p>
							</div>
							<!-- /accordion-inner -->
						</div>
						<!-- /collapse -->
					</div>
					<!-- /accordion-group -->
					<br>
					</c:if>
				</div>
				<!-- Accordion -->
			</div>

			<div class="col-lg-6 centered">
                <div class="accordion ac" id="accordion3">
                
				<c:if test="${not empty latestProjects[2]}" >
                    <!-- ACCORDION -->
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseThree"> ${latestProjects[2].projectName} </a>
					</div>
					<div id="collapseThree" class="accordion-body collapse">
						<div class="accordion-inner">
							<p>${latestProjects[2].description}
							<br><a style="font-size: 16px;" href="${pageContext.servletContext.contextPath}/sites/${latestProjects[2].unixName}">Explore project...</a></p>
						</div>
						<!-- /accordion-inner -->
					</div>
					<!-- /collapse -->
				</div>
				<!-- /accordion-group -->
				<br>
                </c:if>
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseFour"> ${latestProjects[3].projectName} </a>
					</div>
					<div id="collapseFour" class="accordion-body collapse">
						<div class="accordion-inner">
							<p>
							${latestProjects[3].description}
                            <br><a style="font-size: 16px;" href="${pageContext.servletContext.contextPath}/sites/${latestProjects[3].unixName}">Explore project...</a> 
                            </p>
						</div>
						<!-- /accordion-inner -->
					</div>
					<!-- /collapse -->
				</div>
				
				</div>
				<!-- /accordion-group -->
				<br>
			</div>
			    
			<!-- Accordion -->
			
			<div class="centered" style="padding-bottom: 40px;">
	          <a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/sites/">See all Project</a>
	        </div>
        
		</div>
		
		
		 
        <div class="row">
            <hr style="padding-top: 20px;">
            <h1 class="centered">Search Texts</h1>
            
            <p>Quadruples contain references to the exact position in a text where a concept is used or where a statement is made. 
            We can use this feature to implement search engines that do not use string comparison techniques to find texts of interest,
            but rather search for the actual concept or statement in a text that the user is interested. To try out our prototype for such
            a search, head over to our <a href="${pageContext.servletContext.contextPath}/search/texts"><i class="fa fa-search" aria-hidden="true"></i>
             text search interface</a>.
        </div>
        
	</div>
</div>
<!--/ .container -->
</div>
<!--/ #features -->