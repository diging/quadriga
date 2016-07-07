<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "edu.asu.spring.quadriga.domain.impl.workspace.TextFile" %>


<div class="container">
    <div class="row">
        <div class="col-sm-6 search-wrapper" style="position: relative">
            <h2>Text Search</h2>
    
            <div id="search-form" style="margin-top: 20px;">
                <div class="form-group search-input">
                    <label for="search-term">What concept are you looking for?</label>
                    <input placeholder="Enter search term" type="text" class="form-control" id="search-term" autocomplete="off">
                    <span style="background: url('${pageContext.servletContext.contextPath}/resources/txt-layout/images/throbber.gif');"
                          id="ajax-loader" class="search-loader"></span>
                </div>
            </div>
            <div id="search-results-wrapper" style="display: none;">
                <div class="col-sm-12 search-results" style="margin-top: -35px;">
                    <div class="list-group" style="border: 1px solid #dddddd; max-height: 300px; overflow-y:scroll;" id="search-results-items">
                    </div>
                    <div style="display: none;">
                        <a href="#" class="list-group-item" id="search-item-template">
                            <span class="search-name text-primary"><strong></strong></span> <span class="search-type label label-primary"></span> 
                            <span class="search-pos label label-info text-lowercase"></span>
                            <small><br><span class="search-desc text-muted"></span></small>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-sm-12"> 
        
        <c:if test="${not empty concept}">
            <h3 style="margin-bottom: 0px;">Results for:</h3>
            <h4 style="margin-bottom: 20px;">${concept.lemma}
            <small><span class="label label-default">${concept.type}</span>
            <br>${concept.description}
            </small>
            </h4>
        </c:if>
        <div class="list-group">
        <c:if test="${empty texts}">
        <p>Your search has no results.</p>
        </c:if>
        <c:forEach items="${texts}" var="textfile">
            <div class="list-group-item">
            <p class="pull-right">
               <a href="${textfile.refId}" title="Go to original" target="_blank">
                  <i class="fa fa-share" aria-hidden="true"></i>
               </a>
            </p>
            <h4>
            <a href="#" data-toggle="modal"
               data-target="#txtModal" data-txtid="${textfile.textId}"
               data-txtname="${textfile.fileName}" data-txttitle="${textfile.title}"
               data-txtauthor="${textfile.author}" data-txtdate="${textfile.creationDate}">
               <i class="fa fa-file-text-o" aria-hidden="true"></i>
		        <c:if test="${not empty textfile.author}">${textfile.author}, </c:if>
		        <c:if test="${not empty textfile.title}"><em>${textfile.title}</em></c:if>
		        <c:if test="${not empty textfile.creationDate}"> (${textfile.creationDate})</c:if>
		    </a>
		    </h4>
		    
		    <p style="margin-bottom: 0px; line-height: 1.3"><small>${textfile.snippet}</small></p>
		    </div>
		</c:forEach>
		
		<h4 style="margin-top: 30px;">External Resources</h4>
		<c:forEach items="${references}" var="ref">
		  <div class="list-group-item">
		      <h4>
                <a href="${ref}" target="_blank"><i class="fa fa-share" aria-hidden="true"></i> ${ref}</a>
              </h4>
		  </div>
		</c:forEach>
        </div>
        </div>
    </div>
</div>

<div class="modal text-modal" id="txtModal" tabindex="-1" role="dialog"
    aria-labelledby="txtModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content ">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" style="height: 500px; overflow-y: scroll;"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script>
//# sourceURL=loader.js
    function init() {
        // ajax loader
        var networkURL = '${pageContext.servletContext.contextPath}/search/texts?conceptId=';
        var $loader = $('#ajax-loader');
        var $searchInput = $('#search-term');
        var $resWrapper = $('#search-results-wrapper');
        var $items = $('#search-results-items');
        var $list = $resWrapper.find('.list-group-item:first');
        var loader = (function() {
            // var isVisible = false;
            var timeout;
            var interval = 400;
            var fn = function() {
                if ($loader.is(':visible')) {
                    $loader.hide();
                }
            };
            return {
                show: function() {
                    $loader.css('display', 'inline-block');
                },
                hide: function() {
                    // clear timeout
                    clearTimeout(timeout);
                    timeout = setTimeout(fn, interval);
                }
            };
        })();
        // ajax loader
        $(document).on({
            ajaxStart: loader.show,
            ajaxStop: loader.hide
        });
        var triggerChange = (function() {
            var timeout;
            var timeoutInt = 400;
            var prevVal = '';
            var minChars = 2;
            var change = function() {
                $searchInput.trigger('textChange');
            };
            return function(ev) {
                var val = $searchInput.val().trim();
                if (val === prevVal || val.length < minChars) {
                    return;
                }
                prevVal = val;
                // clear this interval
                clearTimeout(timeout);
                timeout = setTimeout(change, timeoutInt);
            }
        })();
        var reqSuccess = function(data) {
            var terms = data.terms || [];
            addTerms(terms);
        };
        var reqFail = function(err) {
            // triggered even when abort is called
            console.log(err);
        };
        var reqAlways = function(obj) {
            // this triggered always
        };
        var addTerms = (function() {
            // clear all the terms
            var $a = $('#search-item-template');
            var maxResults = 15;
            return function(terms) {
                // if terms are empty hide the search wrapper
                if (terms && terms.length === 0) {
                    $resWrapper.hide();
                    return;
                }
                var $link;
                var maxNum = Math.min(terms.length, maxResults);
                $items.html('');
                for (var i = 0; i < maxNum; i++) {
                    $link = $a.clone();
                    $link.attr('id', '');
                    $link.find('.search-name strong').text(terms[i].name);
                    $link.attr('href', networkURL + terms[i].id);
                    $link.find('.search-desc').text(terms[i].description);
                    $link.find('.search-type').text(terms[i].type);
                    $link.find('.search-pos').text(terms[i].pos);
                    $items.append($link);
                }
                $resWrapper.show();
            };
        })();
        var onChange = (function(ev) {
            // cancel the original request
            // and make a new request
            var $xhr;
            var done = function(data) {
                reqSuccess(data);
            };
            var fail = function(err) {
                reqFail(err);
            };
            var always = function(obj) {
                reqAlways(obj);
            };
            return function() {
                // cancel the request
                if ($xhr) {
                    $xhr.abort();
                }
                var searchVal = $searchInput.val();
                if (searchVal.trim().length === 0) {
                    // do not make request
                    // send empty data
                    addTerms([]);
                    return;
                }
                // start a new request
                $xhr = $.ajax({
                    method: 'get',
                    dataType: 'json',
                    url: "${pageContext.servletContext.contextPath}/public/concept/search",
                    data: {
                        searchTerm: searchVal
                    }
                }).done(done).fail(fail).always(always);
            };
        })();
        // custom event for value change
        // check if change works for input
        $searchInput.on('keyup', triggerChange)
                .on('textChange', onChange);
    }
    window.onload = init;
    
    
// text modal
$(document)
            .ready(
                    function() {
                        $('#txtModal')
                                .on(
                                        'show.bs.modal',
                                        function(event) {
                                            var link = $(event.relatedTarget);

                                            var txtid = link.data('txtid');
                                            var txtname = link.data('txtname');
                                            var title = link.data('txttitle');
                                            var author = link.data('txtauthor');
                                            var date = link.data('txtdate');
                                            
                                            var header = "No author and title information provided."
                                            if (title != '' || author != '' || date != '') {
                                                header = '';
                                                if (author != null) {
                                                    header += author + ", ";
                                                }
                                                if (title != null) {
                                                    header += "<em>" + title + "</em> ";
                                                }
                                                if (date != null) {
                                                    header += "(" + date + ")";
                                                }
                                            }
                                            header += "<br><small>" + txtname + "</small>";
                                            $
                                                    .ajax({
                                                        type : "GET",
                                                        url : "${pageContext.servletContext.contextPath}/public/text/view?txtid="
                                                                + txtid,
                                                        contentType : "text/plain",
                                                        success : function(
                                                                details) {
                                                            $('.modal-title')
                                                                    .html(header);
                                                            $('.modal-body')
                                                                    .html(details);
                                                        },

                                                        error : function(xhr,
                                                                ajaxOptions) {
                                                            if (xhr.status == 404) {
                                                                $('.modal-body')
                                                                        .text(
                                                                                "Error while retrieving the text content.");
                                                            }
                                                        }
                                                    });

                                        });
                    });
</script>