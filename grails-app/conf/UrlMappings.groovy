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
		"/dataAnalyze"(controller:"agent",action:"dataAnalyze")
		"/cleanDataUserInput"(controller:"agent",action:"cleanDataUserInput")
		"/getRecommendations"(controller:"agent",action:"getRecommendations")
		
		"/weightedSASetting"(controller:"agent",action:"weightedSASetting")
		"/constrainedSASetting"(controller:"agent",action:"constrainedSASetting")
		"/dynamicSASetting"(controller:"agent",action:"dynamicSASetting")
		"/lexicalSASetting"(controller:"agent",action:"lexicalSASetting")
		"/saveConfig"(controller:"agent",action:"saveConfig")
			
        "500"(view:'/error')
	}
}
