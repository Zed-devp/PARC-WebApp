<html>
  <head>
    <meta name="layout" content="main" />
    <title>Profile</title>         
  </head>
  <body>
    <div class="body">
    	<g:if test="${session.user}">
    		<p>Hello ${session.user.name}!</p>
    		<br>
    		<p>Profile:</p>
    		<br>
    		<p>Name: ${session.user.name}</p>
    		<p>Role: ${session.user.role}</p>
    		<br>
    		<p>Datasets:</p>
    		<g:if test="${dataInfo}">
                <g:each in="${dataInfo}" var="dataset" >
                    <br>
                    <form action="dataAnalyze" method="POST">
	                    <p>Dataset: <input name="dataset" type="text" value="${dataset.dataName}" readonly></p>
	                    <p>Constraint: <input name="con" type="text" value="${dataset.conName}" readonly></p>
	                    <input type="submit" name="func" value="Violation Detection">
	                    <input type="submit" name="func" value="Clean Data">
	                </form>
                    <br>
                </g:each>
    		</g:if>
            <br>
    		<p><g:link controller="agent" action="upload">Upload your dataset and constraint files!</g:link></p>
    	</g:if>
    	<g:else>
    		<h>Please login first!</h>
    	</g:else>
	</div>
  </body>
</html>