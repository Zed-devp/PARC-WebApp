<html>
  <head>
    <meta name="layout" content="main" />
    <title>Data Quality</title>         
  </head>
  <body>
   	<h1>Violations:</h1>
   	<br>
   	<textarea style="width: 600px; height: 300px;">${vio}</textarea>
   	
   	<br>
   	<button class="btn btn-success">Clean Data</button>
   	
   	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>