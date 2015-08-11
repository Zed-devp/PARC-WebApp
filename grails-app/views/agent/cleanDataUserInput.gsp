<html>
  <head>
    <meta name="layout" content="main" />
    <title>Cleaning Parameter Setting</title>         
  </head>
  <body>
    <div class="container">
    	<h>Please fill in the forms!</h>
		<g:uploadForm action="getRecommendations" method="POST">
		<br>
        <p>Master Agent: <input type="text" name="masterAgent" /></p>
        <br>
        <p>Master Dataset: <input type="text" name="masterDataset" /></p>
        <br>
        <p>Similarity Threshold (0-1.0): <input id="smthRange" type="range" name="simThreshold" min="0.0" max="1.0" value="0.8" step="0.01" oninput="showRangeValue()" /><span id="rangeVal"></span></p>
        <br>
        <p>Searching Objective:</p>
        <p><input type="checkbox" name="searchObj" value="weighted" checked>Weighted Simulated Annealing      <g:link controller="agent" action="weightedSASetting">[Advanced Setting]</g:link></p>
        <p><input type="checkbox" name="searchObj" value="constrained">Constrained Simulated Annealing      <g:link controller="agent" action="constrainedSASetting">[Advanced Setting]</g:link></p>
        <p><input type="checkbox" name="searchObj" value="dynamic">Dynamic Simulated Annealing      <g:link controller="agent" action="dynamicSASetting">[Advanced Setting]</g:link></p>
        <p><input type="checkbox" name="searchObj" value="lexical">Lexical Simulated Annealing      <g:link controller="agent" action="lexicalSASetting">[Advanced Setting]</g:link></p>
        <br>
        <p><input type="submit" value="Clean Data"/></p>
    </g:uploadForm>
    <br>
    <g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
	
	<script>
		showRangeValue();
		function showRangeValue() {
		    var range = document.getElementById("smthRange");
		    var rangeVal = range.value;
		    var rangeValBox = document.getElementById("rangeVal");
		    rangeValBox.innerHTML = rangeVal;
		}
	</script>

  </body>
</html>