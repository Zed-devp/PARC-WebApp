<html>
  <head>
    <meta name="layout" content="main" />
    <title>Lexical Simulated Annealing Advanced Setting</title>         
  </head>
  <body>
    <div class="container">
    	<h>Lexical Simulated Annealing Advanced Setting</h>
    	<h>Please fill in the forms!</h>
		<g:uploadForm controller="agent" action="saveConfigLexical" method="POST">
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
	    </g:uploadForm>
	    <br>
	    <g:if test="${flash.message}">
	  		<p>${flash.message}</p>
	  	</g:if>
		</div>
	
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
  </body>
</html>