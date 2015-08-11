<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    	<g:if test="${session.user}">
    		<h1>Datasets:</h1>
    		
    		<br>
    		
    		<table class="table table-hover">
			  <tr class="active">
			    <td>Dataset</td> 
			    <td>Constraint</td>
			    <td>Master Agent</td>
			    <td>Master Dataset</td>
			    <td>Operations</td>
			  </tr>
			  <g:each in="${dataInfo}" var="dataset" >
			  		<tr>
	                    <td><p>${dataset.dataName}</p></td>
	                    <td><p>${dataset.conName}</p></td>
	                    <td></td>
	                    <td></td>
	                    <td>
		                    <form action="dataAnalyze" method="POST">
		                    	<input name="dataset" type="text" value="${dataset.dataName}" hidden>
		                    	<input name="con" type="text" value="${dataset.conName}" hidden>
		                    	<input type="submit" class="btn btn-primary" name="func" value="Violation Detection">
			                    <input type="submit" class="btn btn-success" name="func" value="Clean Data">
			                    <input type="submit" class="btn btn-danger" name="func" value="Delete Data">
		                    </form>
	                    </td>
                	</tr>
               </g:each>
			</table>
			
			<button type="button" onclick="location.href='upload'" class="btn btn-success">Upload Dataset</button>
			
    	</g:if>
    	<g:else>
    		<h1>Please login first!</h1>
    	</g:else>
  </body>
</html>