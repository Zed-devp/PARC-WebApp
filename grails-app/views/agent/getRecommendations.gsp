<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="container">
	    <p>Recommendations:</p>
	    	<br>
	    	<textarea style="width: 600px; height: 300px;">${recs}</textarea>
		</div>
		<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  </body>
</html>