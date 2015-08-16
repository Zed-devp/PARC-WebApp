<html>
  <head>
    <meta name="layout" content="main" />
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
   	<!--  <textarea style="width: 600px; height: 300px;">${recs}</textarea>  -->
   	
   	<g:each in="${recs}" var="recBySearch" >
   		<div class="panel panel-primary">
   			<div class="panel-heading">Search Algorithm: ${recBySearch["search"] }</div>
  			<div class="panel-body">
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
   	</g:each>
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>