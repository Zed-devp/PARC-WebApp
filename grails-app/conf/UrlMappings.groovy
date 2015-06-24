class UrlMappings {

	static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }

        "/"(controller:"agent",action:"index")
		"/login"(controller:"agent",action:"login")
		"/logout"(controller:"agent",action:"logout")
		"/profile"(controller:"agent",action:"profile")
		"/register"(controller:"agent",action:"register")
		"/auth"(controller:"agent",action:"authenticate")
		"/upload"(controller:"agent",action:"upload")
		"/uploadData"(controller:"agent",action:"uploadData")	
		
		"/findViolations"(controller:"agent",action:"findViolations")
			
        "500"(view:'/error')
	}
}
