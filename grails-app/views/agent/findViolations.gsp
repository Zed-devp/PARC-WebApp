<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="container">
    	<p>Violations:</p>
    	<br>
    	<textarea style="width: 600px; height: 300px;">${vio}</textarea>
    	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
  </body>
</html>