<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<h1>Search Networks</h1>
<p>Search networks of project <em><c:out value="${project.projectName}"/></em>.</p>

<div class="container">
	<div class="row">
		<div class="col-sm-6 search-wrapper" style="position: relative">
			<form action="<c:url value="/sites/${project.unixName}/search" />" id="search-form"
				method="post">
				<div class="form-group">
					<label for="search-term">Enter the search term</label>
					<input type="text" class="form-control" id="search-term" autocomplete="off">
				</div>
			</form>
			<div class="row" id="search-results-wrapper" style="display: none;">
				<div class="col-sm-12 search-results">
					<div class="list-group" id="search-results-items">
					</div>
					<div style="display: none;">
						<a href="#" class="list-group-item" id="search-item-template"></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function init() {
		var $searchInput = $('#search-term');
		var $resWrapper = $('#search-results-wrapper');
		var $items = $('#search-results-items');
		var $list = $resWrapper.find('.list-group-item:first');
		var $curReq;
		var url = $('#search-form').attr('action'); // action URL
		var triggerChange = (function() {
			var timeout;
			var timeoutInt = 600;
			var prevVal = '';
			var change = function() {
				$searchInput.trigger('change');
			};
			return function(ev) {
				var val = $searchInput.val().trim();
				if (val === prevVal) {
					return;
				}
				prevVal = val;

				// clear this interval
				clearTimeout(timeout);
				timeout = setTimeout(change, timeoutInt);
			}
		})();

		var reqSuccess = function(data) {
			if (data && data.status === 200 && data.terms) {
				addTerms(data.terms);
			}
		};

		var reqFail = function(err) {
			// triggered even when abort is called
			console.log(err);
		};

		var reqAlways = function(obj) {
			// this triggered always
		};


		/**
		 * This function adds all the search terms to the search results dom
		 * @param terms 	array of terms
		 */
		var addTerms = (function() {
			// clear all the terms
			var $a = $('#search-item-template');
			return function(terms) {
				// if terms are empty hide the search wrapper
				if (terms && terms.length === 0) {
					$resWrapper.hide();
					return;
				}

				var $link;
				$items.html('');
				for (var i = 0; i < terms.length; i++) {
					$link = $a.clone();
					$link.text(terms[i]);
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
				// start a new request
				$xhr = $.ajax({
					method: 'post',
					dataType: 'json',
					url: url,
					data: {
						searchTerm: $searchInput.val()
					}
				}).done(done).fail(fail).always(always);
			};
		})();

		// custom event for value change
		// check if change works for input
		$searchInput.on('keyup', triggerChange)
				.on('change', onChange);
	}
	window.onload = init;
</script>