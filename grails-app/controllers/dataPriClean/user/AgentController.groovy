package dataPriClean.user

import dataPriClean.data.DbConstraint
import dataPriClean.data.Dataset
import dataPriClean.user.Agent

class AgentController {
	def dataCleanService
	
//	def scaffold = Agent

	//index page
    def index () {
		redirect(controller:"agent",action:"profile")
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
				println "new agent created sucessfully!"
				session.user = agent
				redirect(action:"index")
			}
		}
	}
	
	//validate the user
	def authenticate () {
	  def username = params.login
	  def password = params.password
	  
	  //find valid user, login
	  if (verifyUser(username, password)) {
		def user = Agent.findByUsernameAndPassword(username, password)
		println "${user.name} login!"
		  
		session.user = user
		flash.message = "Hello ${user.name}!"
		redirect(controller:"agent",action:"index")
	  }
	  //username and password combo is invalid, login again
	  else {
		flash.message = "Sorry, ${params.login}. Your information is invalid. Please try again."
		redirect(controller:"agent",action:"login")
	  }
	}
	
	//verify user is valid or not
	def verifyUser (username, password) {
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
			
		  flash.message = "${session.user.name} logout!"
		  session.user = null
		  redirect(controller:"agent",action:"login")
	  }
	}
	
	//upload data and constraint
	def upload() {
	}
	
	def uploadData() {
		def user = session.user
		def dataFile = params.dataFile
		def conFile = params.conFile
		
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
	
	//show user's profile
	def profile () {
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
		
		[dataInfo:dataInfo]
	}
	
	def dataAnalyze () {
		if (params.func == "Violation Detection") {
			redirect(controller:"agent",action:"findViolations",params:params)
		}
		else if (params.func == "Clean Data") {
			session.targetDataset = params.dataset
			redirect(controller:"agent",action:"cleanDataUserInput")
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
		
		def targetDatasetName = session.targetDataset
		def targetDataset = Dataset.findByName(targetDatasetName)
		
		if (!targetDataset) {
			flash.message = "Target dataset not found, please upload the dataset!"
			println("Target dataset not found, please upload the dataset!")
			return
		}
		
		if (masterAgent && masterDataset && simThreshold && searchObj) {
			def mAgent = Agent.findByName(masterAgent)
			if (!mAgent || mAgent.role != "Master") {
				flash.message = "Agent not found, please enter the right name!"
				println("Agent not found, please enter the right name!")
				return
			}
			
			def mDataset = Dataset.findByName(masterDataset)
			if (!mDataset || !mAgent.datasets.contains(mDataset)) {
				flash.message = "Master dataset not found, please enter the right dataset name!"
				println("Master dataset not found, please enter the right dataset name!")
				return
			}
			
			recommendations = dataCleanService.getRecommendations(targetDataset, mDataset, simThreshold, searchObj)
			println(recommendations)
		}
		
		[recs: recommendations]
	}
}
