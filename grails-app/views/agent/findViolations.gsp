<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="body">
    	<p>Violations:</p>
    	<br>
    	<textarea>${vio}</textarea>
    	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
  </body>
</html>