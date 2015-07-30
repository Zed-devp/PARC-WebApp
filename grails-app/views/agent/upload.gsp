<html>
  <head>
    <meta name="layout" content="main" />
    <title>Upload Data</title>         
  </head>
  <body>
    <div class="body">
    	<h>Upload your dataset and constraint file!</h>
		<g:uploadForm action="uploadData">
		<br>
        <p>Dataset File: (Dataset file name should be unique)<input type="file" name="dataFile" /></p>
        <br>
        <p>Constraint File: (Constraint file name should be unique)<input type="file" name="conFile" /></p>
        <br>
        <p><input type="submit" value="Upload"/></p>
    </g:uploadForm>
    <br>
    <g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
  </body>
</html>