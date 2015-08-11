<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Privacy Preserving Data Cleaning"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
  		<asset:stylesheet src="application.css"/>  
		<asset:javascript src="application.js"/>
		
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
		
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		<g:layoutHead/>
		
	</head>
	<body>
		<!-- Fixed navbar -->
	    <nav class="navbar navbar-default navbar-fixed-top">
	      <div class="container">
	        <div class="navbar-header">
	          <!-- The mobile navbar-toggle button can be safely removed since you do not need it in a non-responsive implementation -->
	          <a class="navbar-brand" href="#">McMaster Logo</a>
	        </div>
	        <!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
	        <div id="navbar">
	          <ul class="nav navbar-nav">
	            <li class="active"><a href="#">Home</a></li>
	            <li><a href="#about">Violation Detection</a></li>
	            <li><a href="#contact">Data Cleaning</a></li>
	          </ul>
	          <ul class="nav navbar-nav navbar-right">
	          	<g:if test="${session.user}">
		            <li><a href="#">Welcome ${session.user.name}!</a></li>
		            <li><g:link controller="agent" action="logout">Logout</g:link></li>
	            </g:if>
	            <g:else>
	            	<li><g:link controller="agent" action="login">Login</g:link></li>
		            <li><g:link controller="agent" action="register">Register</g:link></li>
	            </g:else>
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </nav>

	    
	    <div class="container">
		    <div class="jumbotron">
	            <h2><g:link controller="agent" action="index">Privacy Preserving Data Cleaning</g:link></h2>
	            <p>A data cleaning tool</p>
          	</div>
        </div>
	
		<g:layoutBody/>
		
	</body>
</html>
