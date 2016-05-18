<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<section id="home" name="home"></section>
    <div id="headerwrap">
        <div class="container">
            <div class="row centered">
                <div class="col-lg-12">
                    <h1>Welcome To <b>Quadriga</b></h1>
                    <h3>Showcase your network projects for the Epistemic Web.</h3>
                    <br>
                </div>
                
                <div class="col-lg-12">
                    <img class="img-responsive" src="${pageContext.servletContext.contextPath}/resources/bootstrap-theme/assets/img/networks-dark4.png" alt="">
                </div>
                
            </div>
        </div> <!--/ .container -->
    </div><!--/ #headerwrap -->


    <section id="desc" name="desc"></section>
    <!-- INTRO WRAP -->
    <div id="intro">
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-sm-12">
                    <h1>Realizing the Epistemic Web</h1>
                    <p>The Epistemic Web is the next step to create "a universe of knowledge on the Web that parallels human knowledge". By "federating documents" (either by hand or automatically) different sources of knowledge will be combined in order to provide access to several sources at once and to qualify the relationships of different sources. As central repository to store datasets from different projects, Quadriga aims to achieve federation of knowledge as a first step towards the Epistemic Web. (Hyman, Malcolm D. and Jürgen Renn (2012). "Toward an Epistemic Web.")</p>
                </div>
                <div class="col-md-6 col-sm-12">
                    <h1>Quadruples</h1>
                    <p>Quadriga is based on so-called "Quadruples," also referred to as "contextualized triples." Quadruples are subject, predicate, object triples with a context that includes metadata such as by whom or when a Quadruple was created. You can annotate texts with Quadruples representing your interpretation of a text using <a href="http://vogonweb.net">VogonWeb</a>. VogonWeb will submit your Quadruples to Quadriga persisting the relationships you create between concepts along with information about who, when, and for what text they were created.
                </div>
            </div>
            
            <hr>
        </div> <!--/ .container -->
    </div><!--/ #introwrap -->
    
    <!-- FEATURES WRAP -->
    <div id="features">
        <div class="container">
            <div class="row">
                <h1 class="centered">Explore the Projects</h1>
                <br>
                <br>
                <div class="col-lg-6 centered">
                    <!-- ACCORDION -->
                    <div class="accordion ac" id="accordion2">
                         <div class="accordion-group">
                            <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                                Awesome Support
                                </a>
                            </div>
                            <div id="collapseThree" class="accordion-body collapse">
                                <div class="accordion-inner">
                                <p>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                                </div><!-- /accordion-inner -->
                            </div><!-- /collapse -->
                        </div><!-- /accordion-group -->
                        <br>
                        
                         <div class="accordion-group">
                            <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseFour">
                                Responsive Design
                                </a>
                            </div>
                            <div id="collapseFour" class="accordion-body collapse">
                                <div class="accordion-inner">
                                <p>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                                </div><!-- /accordion-inner -->
                            </div><!-- /collapse -->
                        </div><!-- /accordion-group -->
                        <br>            
                    </div><!-- Accordion -->
                </div>
                
                <div class="col-lg-6">
                    
                <!-- ACCORDION -->
                    <div class="accordion-group">
                            <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                                Awesome Support
                                </a>
                            </div>
                            <div id="collapseThree" class="accordion-body collapse">
                                <div class="accordion-inner">
                                <p>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                                </div><!-- /accordion-inner -->
                            </div><!-- /collapse -->
                        </div><!-- /accordion-group -->
                        <br>
                        
                         <div class="accordion-group">
                            <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseFour">
                                Responsive Design
                                </a>
                            </div>
                            <div id="collapseFour" class="accordion-body collapse">
                                <div class="accordion-inner">
                                <p>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                                </div><!-- /accordion-inner -->
                            </div><!-- /collapse -->
                        </div><!-- /accordion-group -->
                        <br>            
                    </div><!-- Accordion -->
                </div>
            </div>
        </div><!--/ .container -->
    </div><!--/ #features -->