<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="body">
	    <p>Recommendations:</p>
	    	<br>
	    	<textarea>${recs}</textarea>
		</div>
		<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  </body>
</html>