package dataPriClean.data

import dataPriClean.user.Agent
import dataPriClean.data.TargetDataset
import dataPriClean.data.MasterDataset

class DatasetController {
	def dataCleanService
	
	def index () {
		def user = session.user
		
		//user login
		if (user) {
			//current user is target, show target datasets
			if (user.role.equals("Target")) {
				redirect (controller: "dataset", action: "targetDataset")
			}
			//current user is master, show master datasets
			else {
				redirect (controller: "dataset", action: "masterDataset")
			}
		}
		//user does not login
		else {
			println ("Please login first.")
			flash.message = "Please login first."
		}
	}
	
	//show target datasets
	def targetDataset () {
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
		}
	}
	
	//show master datsets
	def masterDataset () {
		def user = session.user
		
		//user login
		if (user) {
			def datasets = MasterDataset.findAllByMasterAgent(user)
			def datasetsList = []
			
			datasets.each {
				def dataset = [:]
				dataset["datasetName"] = it.name
				dataset["conName"] = it.dbConstraint.name
				datasetsList.add(dataset)
			}
			
			[datasets: datasetsList]
		}
		//user does not login
		else {
			println ("Please login first.")
			flash.message = "Please login first."
		}
	}

    //upload master data and constraint page
	def uploadMasterDataset () {
	}
	
	//upload target data and constraint page
	def uploadTargetDataset () {
		
	}
	
	//save uploaded data and constraint info to the database
	def uploadData() {
		def user = session.user
		def dataFile = params.dataFile
		def conFile = params.conFile
		
		if (!user) {
			flash.message = "Please login first!"
			redirect(action: "uploadMasterDataset")
			return
		}
		
	    if (dataFile.isEmpty()) {
	        flash.message = "Dataset file cannot be empty!"
	        redirect(action: "uploadMasterDataset")
	        return
	    }
		
		if (conFile.isEmpty()) {
			flash.message = "Constraint file cannot be empty!"
			redirect(action: "uploadMasterDataset")
			return
		}
		
		//get current time
		Date date = new Date()
		def dateString = date.format("yyyy-MM-dd_hh:mm:ss")
		
		//save dataset & constraint files
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
		
		//upload Master Dataset and create record in the db
		if (user.role.equals("Master")) {
			DbConstraint constraintT = new DbConstraint(
				name: originalConFielName,
				url: conFileName).save(flush: true)
				
			MasterDataset datasetT = new MasterDataset(
				name: originalDataFileName,
				url: dataFileName,
				dbConstraint: constraintT,
				masterAgent: user).save(flush: true)
		}
		//upload Target Datset and create record in the db
		else {
			def masterAgent = params.masterAgent
			def masterDataset = params.masterDataset
			
			if (!masterAgent) {
				flash.message = "Master Agent cannot be empty!"
				redirect(action: "uploadMasterDataset")
				return
			}
			
			if (!masterDataset) {
				flash.message = "Master Agent cannot be empty!"
				redirect(action: "uploadMasterDataset")
				return
			}
			
			DbConstraint constraintT = new DbConstraint(
				name: originalConFielName,
				url: conFileName).save(flush: true)
			
			TargetDataset datasetT = new TargetDataset(
				name: originalDataFileName,
				url: dataFileName,
				dbConstraint: constraintT,
				masterAgent: masterAgent,
				masterDataset: masterDataset,
				targetAgent: user).save(flush: true)
		}
		
		redirect(controller:"dataset", action:"index")
	}
	
	//manage dataset for data quality & deletion
	def datasetManagement () {
		//inspect data quality
		if (params.func == "Violation Detection") {
			redirect(controller:"agent",action:"findViolations",params:params)
		}
		//data cleaning
		else if (params.func == "Clean Data") {
			session.targetDataset = params.dataset
			redirect(controller:"agent",action:"cleanDataUserInput")
		}
		//delete data
		else if (params.func == "Delete Data") {
			deleteDataset(params)
		}
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
			
			//delete the dataset records in the database
			dataset.delete(flush: true)
			
			//delete the dataset & constraint & parent dir files
			dataCleanService.deleteDatasetFile(datasetUrl, conUrl)
			
			println("Delete dataset: " + dataset.name + " successfully!")
			redirect(controller:"dataset", action:"index")
		}
		else {
			flash.message = "Dataset Not Found!"
			println ("Dataset Not Found!")
			redirect (controller:"dataset", action:"index")
		}
	}
}
