<html>
  <head>
    <meta name="layout" content="main" />
    
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    
    <title>Data Cleaning</title>   
    
  </head>
  <body>
  	<g:render template="/dataCleaning/recommendationHeader" />
	
    <h3>Repair Details:</h3>
   	<br>
			   		
	<div class="col-md-8">
   		<table class="table table-hover">
   			<tr class="active">
	   			<td>Attribute</td>
	   			<td>Original Value</td>
	   			<td>Change to</td>
	   			<td># Repaired Records</td>
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
		   	<g:each in="${recommendations }" var="record" >
	   			<tr>
	   				<g:each in="${record }" var="recordCol" >
	   					<td><p>${recordCol }</p></td>
   					</g:each>
	   			</tr>
	   		</g:each>
	   	</table>
   	</div>
   	
	<g:if test="${flash.message}">
  		<p>${flash.message}</p>
  	</g:if>
  	
  </body>
</html>