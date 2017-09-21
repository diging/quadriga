<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<h1>Search Networks</h1>
<p>Search networks of project <em><c:out value="${project.projectName}"/></em>.</p>

<div class="container">
	<div class="row">
		<div class="col-sm-6 search-wrapper" style="position: relative">
			<div id="search-form">
				<div class="form-group search-input">
					<label for="search-term">Enter the search term</label>
					<input type="text" class="form-control" id="search-term" autocomplete="off">
                    <span style="background: url('<c:url value="/resources/txt-layout/images/throbber.gif" />');"
						  id="ajax-loader" class="search-loader"></span>
				</div>
			</div>
			<div class="row" id="search-results-wrapper" style="display: none;">
				<div class="col-sm-12 search-results">
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
</div>
<script>
	function init() {
		// ajax loader
		var networkURL = '<c:url value="/sites/${project.unixName}/networks/search?conceptId=" />';
		var $loader = $('#ajax-loader');
		var $searchInput = $('#search-term');
		var $resWrapper = $('#search-results-wrapper');
		var $items = $('#search-results-items');
		var $list = $resWrapper.find('.list-group-item:first');
		var url = '<c:url value="/sites/${project.unixName}/search" />';
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
			// console.log(err);
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
			var url = url; // local access
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
					method: 'post',
					dataType: 'json',
					url: url,
					data: {
						searchTerm: searchVal,
					   ${_csrf.parameterName}: '${_csrf.token}',
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
</script>