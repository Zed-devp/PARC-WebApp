package dataPriClean.data

import dataPriClean.user.Agent
import dataPriClean.data.TargetDataset
import dataPriClean.data.MasterDataset

class DataCleaningController {
	def dataCleanService

    def dataCleaningConfig () {
	}
	
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
			flash.message = "Please login first."
			render(view: "/loginWarning.gsp")
		}
	}
	
	//get the selected data and execute vio detection
	def selectData () {
		def datasetName = params.dataset
		def dataset = TargetDataset.findByName(datasetName)
		
		if (dataset) {
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
	
	def getRecommendations () {
		def recommendations = []
		
		def simThreshold = params.simThreshold
		def searchObj = params.searchObj
		def config = session.config
		
		def targetDatasetName = session.targetDataset
		def targetDataset = TargetDataset.findByName(targetDatasetName)
		
		def masterAgent = targetDataset.masterAgent
		def masterDataset = targetDataset.masterDataset
		
		def mAgent
		def mDataset
		
		if (!targetDataset) {
			flash.message = "Target dataset not found, please upload the dataset!"
			println("Target dataset not found, please upload the dataset!")
			return
		}
		
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
		
		if (searchObj) {
			if (searchObj.getClass().equals("".getClass())) {
				searchObj = [searchObj]
			}
			
			println("getRec()::searchObj: " + searchObj)
			println("getRec()::config: " + config)
//			recommendations = dataCleanService.getRecommendations(targetDataset, mDataset, simThreshold, searchObj, config)
			def recommendationsMap = dataCleanService.getRecommendationsList(targetDataset, mDataset, simThreshold, searchObj, config)
			
			//transfer the result format to the specific one
			for (def rec: recommendationsMap) {
				def newRec = [:]
				newRec["search"] = rec["search"]
				def recommendation = []
				for (def con: rec["recommendation"].keySet()) {
					def map = [:]
					map["constraint"] = con.toString()
					map["constraintAttrs"] = con.getColsInConstraint()
					
					def recContentList = []
					
					def setCandidate = rec["recommendation"].get(con)
					def i = setCandidate.iterator()
					while (i.hasNext()) {
						def candidate = i.next()
						for (def recmmendationTemp:candidate.getRecommendations()) {
							def recmmendationTempList = []
							recmmendationTempList.add(recmmendationTemp.gettRid())
							recmmendationTempList.add(recmmendationTemp.getCol())
							recmmendationTempList.add(recmmendationTemp.getVal())
							recContentList.add(recmmendationTempList)
						}
					}
					
					map["recContent"] = recContentList
					
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
		
		println(recommendations)
		
		[recs: recommendations]
	}
	
	def saveConfig () {
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
		
		//constrained config
		if (params.cleaning) {
			config["cleaning"] = Double.parseDouble(params.cleaning)
		}
		if (params.size) {
			config["size"] = Double.parseDouble(params.size)
		}
		
		//dynamic config
		if (params.privacy) {
			config["privacy"] = Double.parseDouble(params.privacy)
		}
		
		print ("Searching Config: " + config)
		
		session.config = config
		
		redirect(controller:"agent",action:"cleanDataUserInput")
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
		redirect(controller:"agent",action:"cleanDataUserInput")
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
		redirect(controller:"agent",action:"cleanDataUserInput")
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
		redirect(controller:"agent",action:"cleanDataUserInput")
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
		redirect(controller:"agent",action:"cleanDataUserInput")
	}
	
	def weightedSASetting () {
	
	}
	
	def constrainedSASetting () {
		
	}
	
	def dynamicSASetting () {
		
	}
	
	def lexicalSASetting () {
		
	}
}
