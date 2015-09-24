<html>
  <head>
    <meta name="layout" content="main" />
    
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    
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
	
    <h1>Data Cleaning Recommendation:</h1>
   	<br>
			   		
	<div class="col-md-8">
   		<table class="table table-hover">
   			<tr>
	   			<td>[Record #, Attribute, Change to]</td>
   			</tr>
   			<%--<tr class="active">
   				<td><p>Record #</p></td>
			    <td><p>Attribute</p></td>
			    <td><p>Change to</p></td>
			  </tr>
	   		--%><%--<g:each in="${recommendationList }" var="record" >
	   			<tr>
	   				<g:each in="${record }" var="recordCol" >
	   					<td><p>${recordCol }</p></td>
   					</g:each>
	   			</tr>
	   		</g:each>
	   	--%>
		   	<tr>
		   		<td>"${recommendationList }"</td>
	   		</tr>
	   	
	   	</table>
   	</div>
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>