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
			    <li ><a href="#">Manage Dataset <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
			    <li class="active"><a href="#">Identify Errors <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
			    <li ><a href="#">Clean Dataset </a></li>
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
			  </ul>
			</nav>
			
    		<h1>Select Dataset:</h1>
    		
    		<br>
    		
    		<g:form controller="dataQuality" action="selectData" method="POST">
	    		<table class="table table-hover">
				  <tr class="active">
				  	<td>Selection</td>
				    <td>Dataset</td> 
				    <td>Constraint</td>
				    <td>Master Agent</td>
				    <td>Master Dataset</td>
				  </tr>
				  <g:each in="${datasets}" var="dataset" >
				  		<tr>
				  			<td>
		                    	<input type="radio" name="dataset" value="${dataset.datasetName}" checked>
		                    </td>
		                    <td><p>${dataset.datasetName}</p></td>
		                    <td><p>${dataset.conName}</p></td>
		                    <td><p>${dataset.masterAgent}</p></td>
		                    <td><p>${dataset.masterDataset}</p></td>
	                	</tr>
	               </g:each>
				</table>
				<br>
				
				<input type="submit" class="btn btn-success" name="func" value="Violation Detection">
			 </g:form>
			
    	</g:if>
    	<g:else>
    		<h1>Please login first!</h1>
    	</g:else>
    	
    	<g:if test="${flash.message}">
        	<div class="message">${flash.message}</div>
      	</g:if>
      
  </body>
</html>