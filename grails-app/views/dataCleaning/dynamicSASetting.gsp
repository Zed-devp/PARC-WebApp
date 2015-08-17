<html>
  <head>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'range.css')}" type="text/css">
    <title>Dynamic Simulated Annealing Advanced Setting</title>         
  </head>
  <body>
  		<nav>
		  <ul class="pagination">
		    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
		    <li ><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
		    <li ><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
		    <li class="active"><a href="#">Clean Dataset </a></li>
		    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
		  </ul>
		</nav>
		
    	<h3>Dynamic Simulated Annealing Advanced Setting</h3>
    	<br>
    	
		<g:uploadForm controller="dataCleaning" action="dynamicSASetting" method="POST">
			<g:render template="/dataCleaning/searchConfigSetting" />
			
			<div class="panel panel-success">
				<div class="panel-heading">Privacy & Cleaning Utility Objective Configuration:</div>
				<div class="panel-body">
					<div class="row">
      					<div class="col-xs-6">
					        <label>Privacy:</label> 
					        <div class="range range-success">
					        	<input id="privacy" type="range" name="privacy" min="0.0" max="1.0" value="${config["privacy"] }" step="0.1" onchange="privacyText.value=value" />
					        	<output id="privacyText">${config["privacy"] }</output>
					        </div>
				        </div>
			        
			        	<div class="col-xs-6">
					        <label>Cleaning:</label> 
					        <div class="range range-success">
					        	<input id="cleaning" type="range" name="cleaning" min="0.0" max="1.0" value="${config["cleaning"] }" step="0.1" onchange="cleaningText.value=value" />
					        	<output id="cleaningText">${config["cleaning"] }</output>
					        </div>
				        </div>
			        </div>
				        
				        <div class="row">
				        	<div class="col-xs-6">
					        <label>Size:</label> 
					        <div class="range range-success">
					        	<input id="size" type="range" name="size" min="0.0" max="1.0" value="${config["size"] }" step="0.1" onchange="sizeText.value=value" />
					        	<output id="sizeText">${config["size"] }</output>
					        </div>
				        </div>
			        </div>
		        </div>
	        </div>
	        
	        <input class="btn btn-success" name="func" type="submit" value="Save"/>
	        <input class="btn btn-primary" name="func" type="submit" value="Reset to default"/>
		
	    </g:uploadForm>
	    <br>
	    <g:if test="${flash.message}">
	  		<p>${flash.message}</p>
	  	</g:if>
	
  </body>
</html>