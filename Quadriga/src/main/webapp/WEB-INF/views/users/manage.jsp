<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">


	<!-- Highlight -->
	<section class="is-highlight">
		<header>
			<h2>Hello ${username},</h2>
		</header>
		<p>Huurahhhh.</p>

  <script>
  $(function() {
    $( "#tabs" ).tabs({
      beforeLoad: function( event, ui ) {
        ui.jqXHR.error(function() {
          ui.panel.html(
            "Couldn't load this tab. Please contact the Administrator" );
        });
      }
    });
  });
  </script>
 
<div id="tabs">
  <ul>
    <li><a href="user requests">User Requests</a></li>
    <li><a href="active">Active Users</a></li>
    <li><a href="inactive">Deactivated Users</a></li>
  </ul>
</div>
		
		
	</section>


</article>

<!-- /Content -->

