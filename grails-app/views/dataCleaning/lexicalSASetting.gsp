<html>
  <head>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'range.css')}" type="text/css">
    <title>Lexical Simulated Annealing Advanced Setting</title>         
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
	
    	<h3>Lexical Simulated Annealing Advanced Setting</h3>
    	<br>
    	
		<g:uploadForm controller="dataCleaning" action="saveConfigLexical" method="POST">
			<g:render template="/dataCleaning/searchConfigSetting" />
		
			<div class="panel panel-success">
				<div class="panel-heading">Privacy & Cleaning Utility Objective Configuration:</div>
				<div class="panel-body">
					<div class="row">
      					<div class="col-xs-6">
					        <label>Privacy:</label> 
					        <div class="range range-success">
					        	<input id="privacy" type="range" name="privacy" min="0.0" max="1.0" value="1" step="0.1" onchange="privacyText.value=value" />
					        	<output id="privacyText">1</output>
					        </div>
				        </div>
			        
			        	<div class="col-xs-6">
					        <label>Cleaning:</label> 
					        <div class="range range-success">
					        	<input id="cleaning" type="range" name="cleaning" min="0.0" max="1.0" value="1" step="0.1" onchange="cleaningText.value=value" />
					        	<output id="cleaningText">1</output>
					        </div>
				        </div>
			        </div>
		        </div>
	        </div>
	        
	        <input class="btn btn-primary" type="submit" value="Save"/>
		
		<!--  
			<p>Searching Configuration:</p>
	        <p>Start Temperature: <input id="stTemp" type="range" name="stTemp" min="0.0" max="1.0" value="0.0009" step="0.00001" oninput="showRangeValue(id)" /><span id="stTempText"></span></p>
	        <p>Final Temperature: <input id="endTemp" type="range" name="endTemp" min="0.0" max="1.0" value="0.00005" step="0.00001" oninput="showRangeValue(id)" /><span id="endTempText"></span></p>
	        <p>Alpha Temperature: <input id="alpTemp" type="range" name="alpTemp" min="0.0" max="1.0" value="0.99" step="0.001" oninput="showRangeValue(id)" /><span id="alpTempText"></span></p>
	        <p>Step per Temperature: <input id="stepTemp" type="range" name="stepTemp" min="0.0" max="10" value="1" step="1" oninput="showRangeValue(id)" /><span id="stepTempText"></span></p>
	        <p>Best Energy: <input id="bestEn" type="range" name="bestEn" min="0.0" max="1.0" value="0.00001" step="0.00001" oninput="showRangeValue(id)" /><span id="bestEnText"></span></p>
	        <br>
	        <p>Privacy & Cleaning Utility Objective Configuration:</p>
	        <p>Privacy: <input id="privacy" type="range" name="privacy" min="0.0" max="1.0" value="1" step="0.1" oninput="showRangeValue(id)" /><span id="privacyText"></span></p>
	        <p>Cleaning: <input id="cleaning" type="range" name="cleaning" min="0.0" max="1.0" value="1" step="0.1" oninput="showRangeValue(id)" /><span id="cleaningText"></span></p>
	        <br>
	        <p><input type="submit" value="Submit"/></p>
        -->
	    </g:uploadForm>
	    <br>
	    <g:if test="${flash.message}">
	  		<p>${flash.message}</p>
	  	</g:if>
	
		<!--  
		<script>
			showRangeValue("stTemp");
			showRangeValue("endTemp");
			showRangeValue("alpTemp");
			showRangeValue("stepTemp");
			showRangeValue("bestEn");

			showRangeValue("privacy");
			showRangeValue("cleaning");
		
			function showRangeValue(id) {
			    var range = document.getElementById(id);
			    var rangeVal = range.value;
			    var rangeValBox = document.getElementById(id + "Text");
			    rangeValBox.innerHTML = rangeVal;
			}
		</script>
		-->
  </body>
</html>