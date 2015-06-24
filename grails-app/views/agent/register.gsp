<html>
  <head>
    <meta name="layout" content="main" />
    <title>Register</title>         
  </head>
  <body>
    <div class="body">
      <h1>Register</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form controller="agent" action="register" method="post" >
        <div class="dialog">
          <table>
            <tbody>            
              <tr class="prop">
                <td class="name">
                  <label for="login">Username*:</label>
                </td>
                <td>
                  <input type="text" id="username" name="username" required/>
                </td>
              </tr> 
          
              <tr class="prop">
                <td class="name">
                  <label for="password">Password*:</label>
                </td>
                <td>
                  <input type="password" id="password" name="password" required/>
                </td>
              </tr> 
              
              <tr class="prop">
                <td class="name">
                  <label for="name">Name*:</label>
                </td>
                <td>
                  <input type="text" id="name" name="name" required/>
                </td>
              </tr> 
              
              <tr class="prop">
                <td class="name">
                  <label for="role">Role*:</label>
                </td>
                <td>
                  <select name="role" required>
	                  <option value="Target">Target</option>
					  <option value="Master">Master</option>
				  </select>
                </td>
              </tr> 
            </tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Register" />
          </span>
        </div>
      </g:form>
    </div>
  </body>
</html>