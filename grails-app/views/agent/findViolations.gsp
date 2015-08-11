<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    	<p>Violations:</p>
    	<br>
    	<textarea style="width: 600px; height: 300px;">${vio}</textarea>
    	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  </body>
</html>