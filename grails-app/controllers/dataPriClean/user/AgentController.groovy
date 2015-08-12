package dataPriClean.user

import dataPriClean.data.DbConstraint
import dataPriClean.data.Dataset
import dataPriClean.user.Agent

class AgentController {
	def dataCleanService
	
//	def scaffold = Agent

	//index page
    def index () {
	}
	
	//login page
	def login () {}
	
	//register page
	def register () {
		def username = params.username
		def password = params.password
		def name = params.name
		def role = params.role
		
		Date date = new Date()
		
		//params are valid, create a new agent
		if (username && password && name && role) {
			Agent agent = new Agent(
				username:username,
				password:password,
				name:name,
				role:role,
				dateCreated:date,
				dateUpdated:date)
			
			if (!agent.save(flush:true)) {
				agent.errors.each {
					println it
				}
			}
			else {
				println "New agent created sucessfully!"
				session.user = agent
				redirect(controller:"agent", action:"index")
			}
		}
		//params are not valid
		else {
			println "Some parameters are missing."
			flash.message = "Some parameters are missing."
		}
	}
	
	//validate the user
	def authenticate () {
	  def username = params.username
	  def password = params.password
	  
	  //find valid user, login
	  if (verifyUser(username, password)) {
		def user = Agent.findByUsernameAndPassword(username, password)
		println "${user.name} login!"
		  
		session.user = user
		flash.message = "Welcome ${user.name}!"
		redirect(controller:"agent",action:"index")
	  }
	  //username and password combo is invalid, login again
	  else {
		flash.message = "Sorry, ${params.username}. Your information is invalid. Please try again."
		redirect(controller:"agent", action:"login")
	  }
	}
	
	//verify user is valid or not
	def verifyUser (def username, def password) {
		def user = Agent.findByUsernameAndPassword(username, password)
		
		//find valid user, login
		if (user) {
		  return true
		}
		//username and password combo is invalid, login again
		else {
		  return false
		}
	}
	
	//logout current user
	def logout () {
	  def user = session.user
	  if (user) {
		  println "${user.name} logout!"
			
		  flash.message = "${user.name} logout!"
		  session.user = null
		  redirect(controller:"agent", action:"login")
	  }
	}
	
	//show user's profile
	def profile () {
		def user = session.user
		
		if (user) {
			def curUser = Agent.findByUsernameAndPassword(user.username, user.password)
			
			if (curUser) {
				[user: curUser]
			}
			//invalid user info
			else {
				println("invalid user info.")
				flash.message = "invalid user info."
			}
		}
		//user does not login
		else {
			println("Please login first.")
			flash.message = "Please login first."
		}
	}
	
	//upload data and constraint page
	def upload() {
	}
	
	//save uploaded data and constraint info to the database
	def uploadData() {
		def user = session.user
		def dataFile = params.dataFile
		def conFile = params.conFile
		def masterAgent = params.masterAgent
		def masterDataset = params.masterDataset
		
		if (!user) {
			flash.message = "Please login first!"
			redirect(action: "upload")
			return
		}
				
	    if (dataFile.isEmpty()) {
	        flash.message = "Dataset file cannot be empty!"
	        redirect(action: "upload")
	        return
	    }
		
		if (conFile.isEmpty()) {
			flash.message = "Constraint file cannot be empty!"
			redirect(action: "upload")
			return
		}
		
		if (!masterAgent) {
			flash.message = "Master Agent cannot be empty!"
			redirect(action: "upload")
			return
		}
		
		if (!masterDataset) {
			flash.message = "Master Agent cannot be empty!"
			redirect(action: "upload")
			return
		}
		
		Date date = new Date()
		def dateString = date.format("yyyy-MM-dd_hh:mm:ss")
		def webrootDir = servletContext.getRealPath("/") //app directory
		def fileDir = webrootDir + "dataset/" + user.username + "/" + dateString + "/"
		
		def dataFileName = fileDir + dataFile.getOriginalFilename()
		def conFileName = fileDir + conFile.getOriginalFilename()
		
		File file = new File(dataFileName)
		if (!file.exists()) {
			if (!file.mkdirs()) {
				println "create file dir failed!"
			}
			if (!file.createNewFile()) {
				println "create file failed! file alrady exists!"
			}
		}
		dataFile.transferTo(file)
		
		file = new File(conFileName)
		if (!file.exists()) {
			if (!file.mkdirs()) {
				println "create file dir failed!"
			}
			if (!file.createNewFile()) {
				println "create file failed! file alrady exists!"
			}
		}
		conFile.transferTo(file)
		
		String originalDataFileName = dataFile.getOriginalFilename()
		String originalConFielName = conFile.getOriginalFilename()
		
		DbConstraint constraintT = new DbConstraint(
			name: originalConFielName, 
			url: conFileName).save(flush: true)
			
		Dataset datasetT = new Dataset(
			name: originalDataFileName,
			url: dataFileName,
			dbConstraint: constraintT).save(flush: true)
		
		def curUser = Agent.findByUsernameAndPassword(user.username, user.password)
		if (curUser) {
			curUser.datasets.add(datasetT)
			if (!curUser.save(flush: true)) {
				println "user info saving failed!"
				curUser.errors.each {
					println it
				}
			}
		}
		else {
			println "current user info is invalid! load info failed!"
		}
		
		redirect(action:"index")
	}
	
//	//show user's profile
//	def profile () {
//		def user = session.user
//		
//		def datasets
//		def dataInfo = []
//		
//		if (user) {
//			def curUser = Agent.findByUsernameAndPassword(user.username, user.password)
//			
//			if (curUser) {
//				datasets = curUser.datasets
//				if (datasets) {
//					for (Dataset dataset in datasets) {
//						def dataInfoTemp = [dataName: dataset.name, conName: dataset.dbConstraint.name]
//						dataInfo.add(dataInfoTemp)
//					}
//				}
//			}
//		}
//		
//		[dataInfo:dataInfo]
//	}
	
	def dataAnalyze () {
		if (params.func == "Violation Detection") {
			redirect(controller:"agent",action:"findViolations",params:params)
		}
		else if (params.func == "Clean Data") {
			session.targetDataset = params.dataset
			redirect(controller:"agent",action:"cleanDataUserInput")
		}
		else if (params.func == "Delete Data") {
			deleteDataset(params)
		}
	}
	
	def findViolations () {
		def vio = []
		
		def fileName = params.dataset  //"address"
		def conName = params.con
		
		def dataset = Dataset.findByName(fileName)
		def con = DbConstraint.findByName(conName)
		
		if (dataset && con) {
		
			def datasetUrl = dataset.url  //"/Users/thomas/Documents/Programming/DataPrivacy/resource/data/address.csv"
			def conUrl = con.url  //"/Users/thomas/Documents/Programming/DataPrivacy/resource/data/address_fd.csv"
			
			def targetData = dataCleanService.loadTargetDataset(datasetUrl, fileName, conUrl)
			def violations = dataCleanService.findViolations(targetData)
			
			vio = violations//.toString()
		}
		else {
			flash.message = "Target Dataset Not Found!"
			print("Target Dataset Not Found!")
		}
		
		[vio:vio]
		
	}
	
	def cleanDataUserInput () {
	}
	
	def getRecommendations () {
		def recommendations = ""
		
		def masterAgent = params.masterAgent
		def masterDataset = params.masterDataset
		def simThreshold = params.simThreshold
		def searchObj = params.searchObj
		def config = session.config
		
		def targetDatasetName = session.targetDataset
		def targetDataset = Dataset.findByName(targetDatasetName)
		
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
			mDataset = Dataset.findByName(masterDataset)
			if (!mDataset || !mAgent.datasets.contains(mDataset)) {
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
			recommendations = dataCleanService.getRecommendations(targetDataset, mDataset, simThreshold, searchObj, config)
		}
		else {
			flash.message = "Please indicate searching algorithms!"
			println("Please indicate searching algorithms!")
			return
		}
		
		println(recommendations)
		
		[recs: recommendations]
	}
	
	def deleteDataset (def params) {
		def user = Agent.findByName(session.user.name)
		def fileName = params.dataset
		def conName = params.con
		
		def dataset = Dataset.findByName(fileName)
		def con = DbConstraint.findByName(conName)
		
		if (dataset && con && user) {
			//TODO: handle error when deleting fails
			String datasetUrl = dataset.url
			String conUrl = con.url
			
			//delete the records in the database
			user.removeFromDatasets(dataset)
			dataset.delete(flush: true)
			
			//delete the dataset & constraint & parent dir files
			dataCleanService.deleteDatasetFile(datasetUrl, conUrl)
			
			println("Delete dataset: " + dataset.name + " successfully!")
			redirect(controller:"agent",action:"index")
		}
		else {
			flash.message = "Target Dataset Not Found!"
			print("Target Dataset Not Found!")
			redirect(controller:"agent",action:"index")
		}
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
	
	def datasetMan () {
		def user = session.user
		
		def datasets
		def dataInfo = []
		
		if (user) {
			def curUser = Agent.findByUsernameAndPassword(user.username, user.password)
			
			if (curUser) {
				datasets = curUser.datasets
				if (datasets) {
					for (Dataset dataset in datasets) {
						def dataInfoTemp = [dataName: dataset.name, conName: dataset.dbConstraint.name]
						dataInfo.add(dataInfoTemp)
					}
				}
			}
		}
		else {
			render("Please login first!")
		}
		
		[dataInfo:dataInfo]
	}
}
