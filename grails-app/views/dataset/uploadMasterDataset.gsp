<html>
  <head>
    <meta name="layout" content="main" />
    <title>Upload Dataset</title>         
  </head>
  <body>
  	<nav>
	  <ul class="pagination">
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
	    <li class="active"><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li ><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li ><a href="#">Clean Dataset </a></li>
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
	  </ul>
	</nav>
  
   	<h1>Upload your dataset and constraint file:</h1>
   	
   	<br>
   	<br>
   	
	<g:uploadForm controller="dataset" action="uploadData">
        <div class="form-group">
		    <label>Dataset File</label>
		    <input type="file" name="dataFile" />
		    <p class="help-block">Dataset file name should be unique and of .csv file. </p>
	    </div>
        <br>
        
        <div class="form-group">
		    <label>Constraint File</label>
		    <input type="file" name="conFile" />
		    <p class="help-block">Constraint file name should be unique and of .csv file. </p>
	    </div>
        <br>
        
	    <div class="form-group">
        	<p><input type="submit" class="btn btn-success" value="Upload"/></p>
       	</div>
    </g:uploadForm>
    <br>
    <g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  </body>
</html>