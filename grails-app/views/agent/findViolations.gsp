<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="body">
    	<p>Violations:</p>
    	<br>
    	<p>${vio}</p>
    	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
  </body>
</html>