<html>
  <head>
    <meta name="layout" content="main" />
    <title>Upload Data</title>         
  </head>
  <body>
   	<h1>Upload your dataset and constraint file:</h1>
   	
   	<br>
   	<br>
   	
	<g:uploadForm action="uploadData">
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
        	<div>
	        	<label>Master Agent</label>
	        </div>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="masterAgent" placeholder="Master Agent Name">
		    </div>
		    <br>
	    </div>
	    <br>
	    
	    <div class="form-group">
	    	<div>
		    	<label>Master Dataset</label>
	    	</div>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="masterDataset" placeholder="Master Dataset File Name">
		    </div>
		    <br>
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