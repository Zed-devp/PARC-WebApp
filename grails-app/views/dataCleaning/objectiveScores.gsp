<html>
  <head>
    <meta name="layout" content="main" />
    
	<script src="http://d3js.org/d3.v3.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
    
    <style>

.bar {
  fill: steelblue;
}

.bar:hover {
  fill: brown;
}

.axis {
  font: 10px sans-serif;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.x.axis path {
  display: none;
}

</style>
    
    <title>Data Cleaning</title>   
    
  </head>
  <body>
  	<g:render template="/dataCleaning/recommendationHeader" />
  	
  	<div class="row">
  		<div class="col-md-3">
    		<h3>Repair Details:</h3>
   		</div>
   		<div class="col-md-5">
   		 	<h3 class="text-right">FD: [ ${constraint["antecedent"] } ] &#8594; ${constraint["consequent"] }</h3>
 		</div>
   	</div>
   	
	<div class="col-md-8">
		<div class="row">
			<p>Privacy Loss</p>
			<svg class="chart" id="pvt"></svg>
			<script>
	    	var select = "#pvt"
	    	var dataset = {data: ${pvtMapList}}
	    	</script>
			<script src="${resource(dir: 'js', file: 'bar_chart.js')}"></script>
		</div>
		<div class="row">
			<p>Data Utility</p>
			<svg class="chart" id="ind"></svg>
			<script>
	    	var select = "#ind"
	    	var dataset = {data: ${indMapList}}
	    	</script>
			<script src="${resource(dir: 'js', file: 'bar_chart.js')}"></script>
		</div>
		<div class="row">
			<p># Changes</p>
			<svg class="chart" id="changes"></svg>
			<script>
	    	var select = "#changes"
	    	var dataset = {data: ${changesMapList}}
	    	</script>
			<script src="${resource(dir: 'js', file: 'bar_chart.js')}"></script>
		</div>
   	</div>
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>