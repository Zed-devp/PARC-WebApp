<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js"/>
		<g:layoutHead/>
		
		<style>
			.header-back {
				background: #abbf78;
				font-family: "Times New Roman", Times, serif;
				font-size: 200%;
				font-weight: bold;
				color: #FFFFFF;
			}
			
			.header {
				margin-top: 100px;
			    margin-right: 150px;
			    margin-left: 50px;
			}
			
			#header-sub {
				font-size: 60%;
				font-weight: normal;
			}
			
			#loginHeader {
			  float: right;
			  font-size: 40%;
			  font-weight: normal;
			}
		</style>
	</head>
	<body>
		<div class="header-back">
			<div class="header">
			  <p><g:link controller="agent" action="index" style="text-decoration:none">Privacy Preserved Data Cleaning</g:link></p>
			  <p id="header-sub">A data cleaning tool</p>
			  <span style="font-size:60%; float:right; font-weight:normal">
				  <g:if test="${session.user}">
				  	<p>Hello ${session.user.name}![<g:link controller="agent" action="logout">Logout</g:link>]</p>
				  </g:if>
				  <g:else>
				    <p>[<g:link controller="agent" action="login">Login</g:link>]</p>
				  </g:else>
			  </span>
			  <br>
			</div>
		</div>
		
		<g:layoutBody/>
		
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
