class UrlMappings {

	static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }

		//agent pages
        "/"(controller:"agent",action:"index")
		"/login"(controller:"agent",action:"login")
		"/logout"(controller:"agent",action:"logout")
		"/profile"(controller:"agent",action:"profile")
		"/register"(controller:"agent",action:"register")
		
		"/auth"(controller:"agent",action:"authenticate")
		
		//dataset pages
		"/dataset"(controller:"dataset",action:"index")
		"/datasetManagement"(controller:"dataset",action:"datasetManagement")
		"/uploadData"(controller:"dataset",action:"uploadData")
		"/uploadTargetDataset"(controller:"dataset",action:"uploadTargetDataset")
		"/uploadMasterDataset"(controller:"dataset",action:"uploadMasterDataset")
		"/targetDataset"(controller:"dataset",action:"targetDataset")
		"/masterDataset"(controller:"dataset",action:"masterDataset")
		"/showDataset"(controller:"dataset",action:"showDataset")
		
		//data quality pages
		"/datasetSelectionQua"(controller:"dataQuality",action:"datasetSelectionQua")
		"/selectDataQua"(controller:"dataQuality",action:"selectData")
		
		"/findViolations"(controller:"dataQuality",action:"findViolations")
		
		//data cleaning pages
		"/datasetSelectionClean"(controller:"dataCleaning",action:"datasetSelectionClean")
		"/selectDataClean"(controller:"dataCleaning",action:"selectData")
		
		"/dataCleaningConfig"(controller:"dataCleaning",action:"dataCleaningConfig")
		"/getRecommendations"(controller:"dataCleaning",action:"getRecommendations")
		
		"/weightedSASetting"(controller:"dataCleaning",action:"weightedSASetting")
		"/constrainedSASetting"(controller:"dataCleaning",action:"constrainedSASetting")
		"/dynamicSASetting"(controller:"dataCleaning",action:"dynamicSASetting")
		"/lexicalSASetting"(controller:"dataCleaning",action:"lexicalSASetting")
		
		"/saveConfig"(controller:"dataCleaning",action:"saveConfig")
		"/saveConfigWeighted"(controller:"dataCleaning",action:"saveConfigWeighted")
		"/saveConfigConstrained"(controller:"dataCleaning",action:"saveConfigConstrained")
		"/saveConfigDynamic"(controller:"dataCleaning",action:"saveConfigDynamic")
		"/saveConfigLexical"(controller:"dataCleaning",action:"saveConfigLexical")
		
//		"/datasets"(controller:"agent",action:"datasetMan")
//		"/upload"(controller:"agent",action:"upload")
//		"/uploadData"(controller:"agent",action:"uploadData")	
		
//		"/findViolations"(controller:"agent",action:"findViolations")
//		"/dataAnalyze"(controller:"agent",action:"dataAnalyze")
//		"/cleanDataUserInput"(controller:"agent",action:"cleanDataUserInput")
//		"/getRecommendations"(controller:"agent",action:"getRecommendations")
//		
//		"/weightedSASetting"(controller:"agent",action:"weightedSASetting")
//		"/constrainedSASetting"(controller:"agent",action:"constrainedSASetting")
//		"/dynamicSASetting"(controller:"agent",action:"dynamicSASetting")
//		"/lexicalSASetting"(controller:"agent",action:"lexicalSASetting")
//		
//		"/saveConfig"(controller:"agent",action:"saveConfig")
//		"/saveConfigWeighted"(controller:"agent",action:"saveConfigWeighted")
//		"/saveConfigConstrained"(controller:"agent",action:"saveConfigConstrained")
//		"/saveConfigDynamic"(controller:"agent",action:"saveConfigDynamic")
//		"/saveConfigLexical"(controller:"agent",action:"saveConfigLexical")
			
        "500"(view:'/error')
	}
}
