<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<script type="text/javascript">
    function randomNumberAjax() {
        $.ajax({
        	type: 'GET',
            url : '/quadriga/auth/workbench/workspace/ajaxtest',
            success : function(data) {
                $('#result').html(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
            	$('#result').html("Sorry, Unable to connect to Dspace");
            }

        });
    }
    
    var intervalId = 0;
    intervalId = setInterval(randomNumberAjax, 3000);
</script>

<article class="is-page-content">
	<c:choose>
		<c:when test="${not empty communityList}">
			<pre><h2>Communities in HPS Repository</h2></pre>
			<span class="byline">Select a community to browse its
				collections.</span>
				<c:forEach var="community" items="${communityList}">
				<span style="font-weight:bold"><a href='<c:out value="/quadriga/auth/workspace/community/${community.id}" />'>${community.name}</a></span><br />
				</c:forEach>
		</c:when>

		<c:otherwise>
			<pre>
				<h2>No Communities in HPS Repository</h2>
			</pre>
			<br>
		</c:otherwise>
	</c:choose>

<div id="result"></div>
</article>

<!-- /Content -->