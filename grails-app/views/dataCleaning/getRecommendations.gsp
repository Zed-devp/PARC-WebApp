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
   	
   	<div class="row">
   		<div class="col-md-8">
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
				  			
				  				<g:each in="${recBySearch["recommendation"] }" var="rec">
				  					<div class="panel panel-success">
				  						<div class="panel-heading">Constraint: ${rec["constraint"] }</div>
				  						<div class="panel-body">
									   		<table class="table table-hover">
									   			<tr class="active">
									   				<td><p>Record #</p></td>
												    <td><p>Attribute</p></td>
												    <td><p>Change to</p></td>
												  </tr>
										   		<g:each in="${rec["recContent"] }" var="record" >
										   			<tr>
										   				<g:each in="${record }" var="recordCol" >
										   					<td><p>${recordCol }</p></td>
									   					</g:each>
										   			</tr>
										   		</g:each>
										   	</table>
									   	</div>
								   	</div>
							   	</g:each>
						   	</div>
					   	</div>
				   	</div>
			   	</g:each>
		   	</div>
	   	</div>
	   	
	   	<div class="col-md-4">
	   		<div class="panel panel-default">
			  <div class="panel-heading">Objective Scores</div>
			  <div class="panel-body">
		    	<svg class="chart"></svg>
	  			<script src="${resource(dir: 'js', file: 'multi_bars.js')}"></script>
			  </div>
			</div>
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