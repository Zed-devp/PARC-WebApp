<html>
  <head>
    <meta name="layout" content="main" />
    <title>Data Quality</title>         
  </head>
  <body>
  	<nav>
	  <ul class="pagination">
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
	    <li ><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li class="active"><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
	    <li ><a href="#">Clean Dataset </a></li>
	    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
	  </ul>
	</nav>
			
   	<h1>Violations:</h1>
   	<br>
   	<!--  <textarea style="width: 600px; height: 300px;">${vio}</textarea>  -->
   	
   	<g:each in="${vio}" var="subVio" >
   		<div class="panel panel-default">
  			<div class="panel-body">
		   		<h4>Constraint: ${subVio["constraint"] }</h4>
		   		
		   		<br>
		   		
		   		<table class="table table-hover">
		   			<tr class="active">
		   				<td><p>Record #</p></td>
					    <g:each in="${subVio["constraintAttrs"]}" var="conAttr">
					    	<td><p>${conAttr }</p></td>
					    </g:each>
					  </tr>
			   		<g:each in="${subVio["violatons"] }" var="record" >
			   			<tr>
			   				<g:each in="${record }" var="recordCol" >
			   					<td><p>${recordCol }</p></td>
		   					</g:each>
			   			</tr>
			   		</g:each>
			   	</table>
		   	</div>
	   	</div>
   		
   		<br>
   	</g:each>
   	
   	<br>
   	<button class="btn btn-success" onclick="location.href='dataCleaningConfig'">Clean Data</button>
   	
   	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>