<html>
  <head>
    <meta name="layout" content="main" />
    
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'multi_bars.css')}" type="text/css">
    
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    
    <script type="text/javascript">
	$(document).ready(function(){ 
	    $("#myTab a").click(function(e){
	    	e.preventDefault();
	    	$(this).tab('show');
	    });
	});
	</script>
    
    <title>Data Cleaning</title>   
    
  </head>
  <body>
  	<g:render template="/dataCleaning/recommendationHeader" />
  
    <h3>Recommended Repairs:</h3>
    <div class="col-md-8">
   	
		   	<ul class="nav nav-tabs" id="myTab">
		      <li class="active"><a href="#weighted"><b>Weighted Search</b></a></li>
			  <li><a href="#constrained"><b>Constrained Search</b></a></li>
			  <li><a href="#dynamic"><b>Dynamic Search</b></a></li>
			  <li><a href="#lexical"><b>Lexical Search</b></a></li>
			</ul>
		   	
		   	<div class="tab-content">
			   	<g:each in="${recs}" var="recBySearch" >
			   		<div id="${recBySearch["search"] }" class="tab-pane fade">
			   		
				   		<div class="panel panel-default">
				  			<div class="panel-body">
				  				<div class="panel panel-default">
								  <div class="panel-body">
								  	<div class="col-md-6">
									    <b>Master Dataset</b>: ${recBySearch["masterDataset"] } <br>
						   				<b>Target Dataset</b>: ${recBySearch["targetDataset"] } <br>
						   				<b>Similarity Threshold (&#964)</b>: ${recBySearch["simThreshold"] }
					   				</div>
					   				<div class="col-md-6">
					   					<b>Privacy Loss (Pvt)</b>: ${recBySearch["searchConfig"]["alphaPvt"] }<br>
						   				<b>Cleaning Utility (InD)</b>: ${recBySearch["searchConfig"]["betaInd"] }<br>
						   				<b># Changes</b>: ${recBySearch["searchConfig"]["gamaSize"] }
					   				</div>
								  </div>
								</div>
				  			
				  				<g:set var="fdCounter" value="${0}" />
				  				<g:each in="${recBySearch["recommendation"] }" var="rec">
				  				<g:set var="fdCounter" value="${fdCounter + 1}" />
				  					<div class="panel panel-success">
				  						<div class="panel-heading">FD: ${rec["constraint"] }</div>
				  						<div class="panel-body">
		  								<g:set var="counter" value="${0}" />
		  									<g:each in="${rec["recContent"] }" var="candidate" >
		  									<g:set var="counter" value="${counter + 1}" />
		  										<g:if test="${(counter % 2) == 1}">
			  									<div class="row">
			  									</g:if>
			  										<div class="col-sm-1 col-md-1">
			  											<h4 class="text-center"><g:link controller="dataCleaning" action="recommendationDetails" params="[recommendationList: "${candidate["recommendationList"] }", repairId: "r${counter }" ]">r${counter }</g:link></h4>
			  										</div>
												   	<div class="col-sm-5 col-md-5">
												   		<svg class="chart" id="chart${fdCounter }${counter }"></svg>
												   	</div>
											    	<script>
											    	var select = "#chart${fdCounter }${counter }"
											    	var data = {labels: [''],
										    			         series: [
										    			           {
										    			             label: 'Pvt',
										    			             values: ["${candidate["pvt"]}"]
										    			           },
										    			           {
										    			             label: 'InD',
										    			             values: ["${candidate["ind"]}"]
										    			           },
										    			           {
										    			             label: 'Changes',
										    			             values: ["${candidate["changes"]}"]
										    			           },]};
											    	</script>
										  			<script src="${resource(dir: 'js', file: 'multi_bars.js')}"></script>
										  		<g:if test="${(counter % 2) == 0}">
									  			</div>
									  			</g:if>
										   	</g:each>
									   	</div>
								   	</div>
							   	</g:each>
						   	</div>
					   	</div>
				   	</div>
			   	</g:each>
   			</div>
		</div>
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  	<!-- 
	  	<svg class="chart"></svg>
	  	<script src="${resource(dir: 'js', file: 'multi_bars.js')}"></script>
  	 -->
  	
  </body>
</html>