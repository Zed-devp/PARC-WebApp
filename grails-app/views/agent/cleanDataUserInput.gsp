<html>
  <head>
    <meta name="layout" content="main" />
    <title>Upload Data</title>         
  </head>
  <body>
    <div class="body">
    	<h>Please fill in the forms!</h>
		<g:uploadForm action="getRecommendations" method="POSt">
		<br>
        <p>Master Agent: <input type="text" name="masterAgent" /></p>
        <br>
        <p>Master Dataset: <input type="text" name="masterDataset" /></p>
        <br>
        <p>Similarity Threshold: <input type="text" name="simThreshold" value="0.8"/></p>
        <br>
        <p>Searching Objective:</p>
        <p><input type="radio" name="searchObj" value="weighted" checked>Weighted Simulated Annealing</p>
        <p><input type="radio" name="searchObj" value="constrained">Constrained Simulated Annealing</p>
        <p><input type="radio" name="searchObj" value="dynamic">Dynamic Simulated Annealing</p>
        <p><input type="radio" name="searchObj" value="lexical">Lexical Simulated Annealing</p>
        <br>
        <p><input type="submit" value="Clean Data"/></p>
    </g:uploadForm>
    <br>
    <g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
	</div>
  </body>
</html>