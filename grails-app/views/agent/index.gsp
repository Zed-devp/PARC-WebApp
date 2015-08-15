<html>
  <head>
    <meta name="layout" content="main" />
    <title>Privacy Preserving Data Cleaning</title>         
  </head>
  <body>
  		<div class="container">
		    <div class="jumbotron">
	            <h2>Privacy Preserving Data Cleaning</h2>
	            <p>A data cleaning tool</p>
          	</div>
        </div>
  
    	<div class="container">
	      <!-- Example row of columns -->
	      <div class="row">
	        <div class="col-md-4">
	         <div class="panel panel-primary">
			  <div class="panel-heading">
			    <h1>
			    1. Manage Dataset
			    </h1>
			  </div>
			  <div class="panel-body">
			  	Manage Master&Target dataset.
			  	Upload and delete dataset. 
			  	Define your own FD (Function Dependency).
			  	<br>
			  	<br>
			  	<button type="button" onclick="location.href='dataset'" class="btn btn-primary">Go</button>
			  </div>
			</div>
	        </div>
	        <div class="col-md-4">
	        	<div class="panel panel-success">
				  <div class="panel-heading">
				    <h1>
				    2. Identify Errors
				    </h1>
				  </div>
				  <div class="panel-body">
				  	Inspect your data quality.
				  	Automatically detect errors w.r.t the customized FDs.
				  	<br>
				  	<br>
				  	<button type="button" onclick="location.href='datasetSelectionQua'" class="btn btn-success">Go</button>
				  </div>
				</div>
	       </div>
	        <div class="col-md-4">
	        	<div class="panel panel-info">
				  <div class="panel-heading">
				    <h1>
				    3. Clean Dataset
				    </h1>
				  </div>
				  <div class="panel-body">
				  	Clean the dataset while protecting your privacy.
				  	<br>
				  	<br>
				  	<button type="button" onclick="location.href='datasetSelectionClean'" class="btn btn-info">Go</button>
				  </div>
				</div>
	        </div>
	      </div>
	    </div> <!-- /container -->
  </body>
</html>