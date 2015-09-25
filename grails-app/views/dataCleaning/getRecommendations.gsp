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
  	<nav>
	  <ul class="pagination">
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
	    <li ><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li ><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li class="active"><a href="#">Clean Dataset </a></li>
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
	  </ul>
	</nav>
	
    <h1>Data Cleaning Recommendations:</h1>
   	<br>
   	
		   	<ul class="nav nav-tabs" id="myTab">
		      <li class="active"><a href="#weighted">Weighted Search</a></li>
			  <li><a href="#constrained">Constrained Search</a></li>
			  <li><a href="#dynamic">Dynamic Search</a></li>
			  <li><a href="#lexical">Lexical Search</a></li>
			</ul>
		   	
		   	<div class="tab-content">
			   	<g:each in="${recs}" var="recBySearch" >
			   		<div id="${recBySearch["search"] }" class="tab-pane fade">
			   		
				   		<div class="panel panel-default">
				  			<div class="panel-body">
				  				<div class="panel panel-default">
								  <div class="panel-body">
								    Master Dataset: ${recBySearch["masterDataset"] } <br>
					   				Target Dataset: ${recBySearch["targetDataset"] } <br>
					   				Similarity Threshold: ${recBySearch["simThreshold"] }
								  </div>
								</div>
				  			
					  			<div class="panel panel-success">
						   			<div class="panel-heading">
						   				Search Algorithm Setting:
						   			</div>
						  			<div class="panel-body">
					   					${recBySearch["searchConfig"] }
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
			  											<h4 class="text-center"><g:link controller="dataCleaning" action="recommendationDetails" params="[recommendationList: "${candidate["recommendationList"] }" ]">R${counter }</g:link></h4>
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
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  	<!-- 
	  	<svg class="chart"></svg>
	  	<script src="${resource(dir: 'js', file: 'multi_bars.js')}"></script>
  	 -->
  	
  </body>
</html>