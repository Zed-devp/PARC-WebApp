<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
	    <p>Recommendations:</p>
	    	<br>
	    	<textarea style="width: 600px; height: 300px;">${recs}</textarea>
		<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  </body>
</html>