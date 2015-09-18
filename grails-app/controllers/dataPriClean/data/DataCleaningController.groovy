package dataPriClean.data

import dataPriClean.user.Agent
import dataPriClean.data.TargetDataset
import dataPriClean.data.MasterDataset

class DataCleaningController {
	def dataCleanService

    //data cleaning config homepage
	def dataCleaningConfig () {}
	
	//select dataset for data quality
	def datasetSelectionClean () {
		def user = session.user
		
		//user login
		if (user) {
			def datasets = TargetDataset.findAllByTargetAgent(user)
			def datasetsList = []
			
			datasets.each {
				def dataset = [:]
				dataset["datasetName"] = it.name
				dataset["conName"] = it.dbConstraint.name
				dataset["masterAgent"] = it.masterAgent
				dataset["masterDataset"] = it.masterDataset
				datasetsList.add(dataset)
			}
			
			[datasets: datasetsList]
		}
		//user does not login
		else {
			println ("Please login first.")
			render(view: "/loginWarning.gsp")
		}
	}
	
	//get the selected data and execute vio detection
	def selectData () {
		def datasetName = params.dataset
		def dataset = TargetDataset.findByName(datasetName)
		
		if (dataset) {
			//select the dataset for data cleaning
			session.targetDataset = dataset.name
			redirect(controller:"dataCleaning", action:"dataCleaningConfig")
		}
		//cannot find target dataset
		else {
			println ("Cannot find target dataset. ")
			flash.message = "Cannot find target dataset. "
			redirect(controller:"dataCleaning", action:"datasetSelectionClean")
		}
	}
	
	
	//get the result for data cleaning
	def getRecommendations () {
		def recommendations = []
		
		//get the settings & config for data cleaning
		def simThreshold = params.simThreshold
		def searchObj = params.searchObj
		def config = session.config
		
		//get the target dataset for data cleaning
		def targetDatasetName = session.targetDataset
		def targetDataset = TargetDataset.findByName(targetDatasetName)
		
		if (!targetDataset) {
			flash.message = "Target dataset not found, please upload the dataset!"
			println("Target dataset not found, please upload the dataset!")
			return
		}
		
		//get the master dataset info for data cleaning
		def masterAgent = targetDataset.masterAgent
		def masterDataset = targetDataset.masterDataset
		
		def mAgent
		def mDataset
		
		if (masterAgent) {
			mAgent = Agent.findByName(masterAgent)
			if (!mAgent || mAgent.role != "Master") {
				flash.message = "Agent not found, please enter the right name!"
				println("Agent not found, please enter the right name!")
				return
			}
		}
		else {
			flash.message = "Please input master agent information!"
			println("Please input master agent information!")
			return
		}
		
		if (masterDataset) {
			mDataset = MasterDataset.findByName(masterDataset)
			if (!mDataset) {
				flash.message = "Master dataset not found, please enter the right dataset name!"
				println("Master dataset not found, please enter the right dataset name!")
				return
			}
		}
		else {
			flash.message = "Please input master dataset information!"
			println("Please input master dataset information!")
			return
		}
		
		//execute the data cleaning process
		if (searchObj) {
			//only one searchObj as string, convert the string to the list
			if (searchObj.getClass().equals("".getClass())) {
				searchObj = [searchObj]
			}
			
//			println("getRec()::searchObj: " + searchObj)
//			println("getRec()::config: " + config)
//			recommendations = dataCleanService.getRecommendations(targetDataset, mDataset, simThreshold, searchObj, config)
			
			//get the result of the data cleaning
			def recommendationsMap = dataCleanService.getRecommendationsList(targetDataset, mDataset, simThreshold, searchObj, config)
			
			//transfer the result format to the specific one
			//result for each search
			for (def rec: recommendationsMap) {
				def newRec = [:]
				
				// the target & master dataset name
				newRec["targetDataset"] = targetDatasetName
				newRec["masterDataset"] = mDataset.name
				
				// simThreshold
				newRec["simThreshold"] = simThreshold
				
				newRec["search"] = rec["search"]
				
				// get the search setting
				if (config && config[rec["search"]]) {
					newRec["searchConfig"] = config[rec["search"]]
				}
				else {
					switch (rec["search"]){
						case "weighted":
							newRec["searchConfig"] = resetWeightedSASetting()
							break
						case "constrained":
							newRec["searchConfig"] = resetConstrainedSASetting()
							break
						case "dynamic":
							newRec["searchConfig"] = resetDynamicSASetting()
							break
						case "lexical":
							newRec["searchConfig"] = resetLexicalSASetting()
							break
					}
				}
				
				def recommendation = []
				//result for each constraint under the search result
				for (def con: rec["recommendation"].keySet()) {
					//the recommendation result for each constraint for every search
					def map = [:]
					
					//constraint info
					map["constraint"] = con.toString()
					map["constraintAttrs"] = con.getColsInConstraint()
					
					// candidates
					def candidates = []
					
					//convert the Set<Candidat> data type to List<List<String>> 
					def setCandidate = rec["recommendation"].get(con)
					def i = setCandidate.iterator()
					while (i.hasNext()) {
						//recommendation content info
						def recContentList = []
						
						// candidate info
						def candidateMap = [:]
						
						//convert the Candidate data type to List<String>
						def candidate = i.next()
						for (def recmmendationTemp:candidate.getRecommendations()) {
							def recmmendationRecord = []
							recmmendationRecord.add(recmmendationTemp.gettRid())
							recmmendationRecord.add(recmmendationTemp.getCol())
							recmmendationRecord.add(recmmendationTemp.getVal())
							// list of recommendation
							recContentList.add(recmmendationRecord)
						}
						
						candidateMap["recommendationList"] = recContentList
						candidateMap["pvt"] = candidate.getPvtOut()
						candidateMap["ind"] = candidate.getIndOut()
						candidateMap["changes"] = candidate.getChangesOut()
						
						candidates.add(candidateMap)
					}
					
					map["recContent"] = candidates
					
					recommendation.add(map)
				}
				newRec["recommendation"] = recommendation
				recommendations.add(newRec)
			}
		}
		else {
			flash.message = "Please indicate searching algorithms!"
			println("Please indicate searching algorithms!")
			return
		}
		
//		println(recommendations)
		
		[recs: recommendations]
	}
	
	//show weightedSA config setting page
	def weightedSASetting () {
		//current setting
		def config
		//user defined setting
		def configAll = session.config
		
		//user has defined this setting
		if (configAll && configAll["weighted"]) {
			config = configAll["weighted"]
		}
		//user has not defined this setting
		else {
			config = resetWeightedSASetting()
		}
		
		//reset the setting
		if (params.func == "Reset to default")
		{
			//clear the session config setting saved by the user
			if (configAll && configAll["weighted"]) {
				configAll["weighted"] = null
			}
			
			//reset the settings
			config = resetWeightedSASetting()
		}
		//save the setting
		else if (params.func == "Save"){
			redirect(controller:"dataCleaning", action:"saveConfigWeighted", params:params)
		}
		
		[config:config]
	}
	
	//show constrainedSA config setting page
	def constrainedSASetting () {
		//current setting
		def config
		//user defined setting
		def configAll = session.config
		
		//user has defined this setting
		if (configAll && configAll["constrained"]) {
			config = configAll["constrained"]
		}
		//user has not defined this setting
		else {
			config = resetConstrainedSASetting()
		}
		
		//reset the setting
		if (params.func == "Reset to default")
		{
			//clear the session config setting saved by the user
			if (configAll && configAll["constrained"]) {
				configAll["constrained"] = null
			}
			
			//reset the settings
			config = resetConstrainedSASetting()
		}
		//save the setting
		else if (params.func == "Save"){
			redirect(controller:"dataCleaning", action:"saveConfigConstrained", params:params)
		}
		
		[config:config]
	}
	
	//show dynamicSA config setting page
	def dynamicSASetting () {
		//current setting
		def config
		//user defined setting
		def configAll = session.config
		
		//user has defined this setting
		if (configAll && configAll["dynamic"]) {
			config = configAll["dynamic"]
		}
		//user has not defined this setting
		else {
			config = resetDynamicSASetting()
		}
		
		//reset the setting
		if (params.func == "Reset to default")
		{
			//clear the session config setting saved by the user
			if (configAll && configAll["dynamic"]) {
				configAll["dynamic"] = null
			}
			
			//reset the settings
			config = resetDynamicSASetting()
		}
		//save the setting
		else if (params.func == "Save"){
			redirect(controller:"dataCleaning", action:"saveConfigDynamic", params:params)
		}
		
		[config:config]
	}
	
	//show lexicalSA config setting page
	def lexicalSASetting () {
		//current setting
		def config
		//user defined setting
		def configAll = session.config
		
		//user has defined this setting
		if (configAll && configAll["lexical"]) {
			config = configAll["lexical"]
		}
		//user has not defined this setting
		else {
			config = resetLexicalSASetting()
		}
		
		//reset the setting
		if (params.func == "Reset to default")
		{
			//clear the session config setting saved by the user
			if (configAll && configAll["lexical"]) {
				configAll["lexical"] = null
			}
			
			//reset the settings
			config = resetLexicalSASetting()
		}
		//save the setting
		else if (params.func == "Save"){
			redirect(controller:"dataCleaning", action:"saveConfigLexical", params:params)
		}
		
		[config:config]
	}
	
	def saveConfigWeighted () {
		def configAll
		if (session.config) {
			configAll = session.config
		}
		else {
			configAll = [:]
		}
		
		def config = [:]
		
		//searching config
		if (params.stTemp) {
			config["stTemp"] = Double.parseDouble(params.stTemp)
		}
		if (params.endTemp) {
			config["endTemp"] = Double.parseDouble(params.endTemp)
		}
		if (params.alpTemp) {
			config["alpTemp"] = Double.parseDouble(params.alpTemp)
		}
		if (params.stepTemp) {
			config["stepTemp"] = Double.parseDouble(params.stepTemp)
		}
		if (params.bestEn) {
			config["bestEn"] = Double.parseDouble(params.bestEn)
		}
		
		//weighted config
		if (params.alphaPvt) {
			config["alphaPvt"] = Double.parseDouble(params.alphaPvt)
		}
		if (params.betaInd) {
			config["betaInd"] = Double.parseDouble(params.betaInd)
		}
		if (params.gamaSize) {
			config["gamaSize"] = Double.parseDouble(params.gamaSize)
		}
		
		configAll["weighted"] = config
		
		session.config = configAll
		redirect(controller:"dataCleaning",action:"dataCleaningConfig")
	}
	
	def saveConfigConstrained () {
		def configAll
		if (session.config) {
			configAll = session.config
		}
		else {
			configAll = [:]
		}
		
		def config = [:]
		
		//searching config
		if (params.stTemp) {
			config["stTemp"] = Double.parseDouble(params.stTemp)
		}
		if (params.endTemp) {
			config["endTemp"] = Double.parseDouble(params.endTemp)
		}
		if (params.alpTemp) {
			config["alpTemp"] = Double.parseDouble(params.alpTemp)
		}
		if (params.stepTemp) {
			config["stepTemp"] = Double.parseDouble(params.stepTemp)
		}
		if (params.bestEn) {
			config["bestEn"] = Double.parseDouble(params.bestEn)
		}
		
		//constrained config
		if (params.cleaning) {
			config["cleaning"] = Double.parseDouble(params.cleaning)
		}
		if (params.size) {
			config["size"] = Double.parseDouble(params.size)
		}
		
		configAll["constrained"] = config
		
		session.config = configAll
		redirect(controller:"dataCleaning",action:"dataCleaningConfig")
	}
	
	def saveConfigDynamic () {
		def configAll
		if (session.config) {
			configAll = session.config
		}
		else {
			configAll = [:]
		}
		
		def config = [:]
		
		//searching config
		if (params.stTemp) {
			config["stTemp"] = Double.parseDouble(params.stTemp)
		}
		if (params.endTemp) {
			config["endTemp"] = Double.parseDouble(params.endTemp)
		}
		if (params.alpTemp) {
			config["alpTemp"] = Double.parseDouble(params.alpTemp)
		}
		if (params.stepTemp) {
			config["stepTemp"] = Double.parseDouble(params.stepTemp)
		}
		if (params.bestEn) {
			config["bestEn"] = Double.parseDouble(params.bestEn)
		}
		
		//constrained config
		if (params.privacy) {
			config["privacy"] = Double.parseDouble(params.privacy)
		}
		if (params.cleaning) {
			config["cleaning"] = Double.parseDouble(params.cleaning)
		}
		if (params.size) {
			config["size"] = Double.parseDouble(params.size)
		}
		
		configAll["dynamic"] = config
		
		session.config = configAll
		redirect(controller:"dataCleaning",action:"dataCleaningConfig")
	}
	
	def saveConfigLexical () {
		def configAll
		if (session.config) {
			configAll = session.config
		}
		else {
			configAll = [:]
		}
		
		def config = [:]
		
		//searching config
		if (params.stTemp) {
			config["stTemp"] = Double.parseDouble(params.stTemp)
		}
		if (params.endTemp) {
			config["endTemp"] = Double.parseDouble(params.endTemp)
		}
		if (params.alpTemp) {
			config["alpTemp"] = Double.parseDouble(params.alpTemp)
		}
		if (params.stepTemp) {
			config["stepTemp"] = Double.parseDouble(params.stepTemp)
		}
		if (params.bestEn) {
			config["bestEn"] = Double.parseDouble(params.bestEn)
		}
		
		//constrained config
		if (params.privacy) {
			config["privacy"] = Double.parseDouble(params.privacy)
		}
		if (params.cleaning) {
			config["cleaning"] = Double.parseDouble(params.cleaning)
		}
		
		configAll["lexical"] = config
		
		session.config = configAll
		redirect(controller:"dataCleaning",action:"dataCleaningConfig")
	}
	
	//reset weightedSA config setting
	def resetWeightedSASetting () {
		def config = [:]
		
		//searching parameter config
		config["stTemp"] = 0.0009
		config["endTemp"] = 0.00005
		config["alpTemp"] = 0.99
		config["stepTemp"] = 1
		config["bestEn"] = 0.00001
		
		//weighted SA Setting
		config["alphaPvt"] = 0.10
		config["betaInd"] = 0.895
		config["gamaSize"] = 0.005
		
		return config
	}
	
	//reset constrainedSA config setting
	def resetConstrainedSASetting () {
		def config = [:]
		
		//searching parameter config
		config["stTemp"] = 0.0009
		config["endTemp"] = 0.00005
		config["alpTemp"] = 0.99
		config["stepTemp"] = 1
		config["bestEn"] = 0.00001
		
		//weighted SA Setting
		config["cleaning"] = 1
		config["size"] = 1
		
		return config
	}
	
	//reset dynamicSA config setting
	def resetDynamicSASetting () {
		def config = [:]
		
		//searching parameter config
		config["stTemp"] = 0.0009
		config["endTemp"] = 0.00005
		config["alpTemp"] = 0.99
		config["stepTemp"] = 1
		config["bestEn"] = 0.00001
		
		//weighted SA Setting
		config["privacy"] = 1
		config["cleaning"] = 1
		config["size"] = 1
		
		return config
	}
	
	//reset lexicalSA config setting
	def resetLexicalSASetting () {
		def config = [:]
		
		//searching parameter config
		config["stTemp"] = 0.0009
		config["endTemp"] = 0.00005
		config["alpTemp"] = 0.99
		config["stepTemp"] = 1
		config["bestEn"] = 0.00001
		
		//weighted SA Setting
		config["privacy"] = 1
		config["cleaning"] = 1
		
		return config
	}
	
}
