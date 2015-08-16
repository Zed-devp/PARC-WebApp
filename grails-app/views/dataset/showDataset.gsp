<html>
  <head>
    <meta name="layout" content="main" />
    <title>Dataset</title>         
  </head>
  <body>
    	<g:if test="${session.user}">
    		<nav>
			  <ul class="pagination">
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			    <li class="active"><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
			    <li ><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
			    <li ><a href="#">Clean Dataset </a></li>
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
			  </ul>
			</nav>
			
    		<h1>Dataset:</h1>
    		
    		<br>
    		<p>Dataset Name: ${dataset["datasetName"] }</p>
    		<p>Constraint Name: ${dataset["conName"] }</p>
    		<br>
    		
    		<table class="table table-hover">
			  <tr class="active">
			  	<td>Record #</td>
			  	<g:each in="${dataset["attrs"] }" var="attr">
				    <td>${attr }</td> 
			    </g:each>
			  </tr>
			  <g:each in="${dataset["data"]}" var="dataRow" >
			  		<tr>
			  			<g:each in="${dataRow }" var="dataCol">
			  				<td>${dataCol }</td>
			  			</g:each>
                	</tr>
               </g:each>
			</table>
			
    	</g:if>
    	<g:else>
    		<h1>Please login first!</h1>
    	</g:else>
    	
    	<g:if test="${flash.message}">
        	<div class="message">${flash.message}</div>
      	</g:if>
      
  </body>
</html>