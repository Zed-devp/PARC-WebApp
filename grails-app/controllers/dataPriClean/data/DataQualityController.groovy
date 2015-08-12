package dataPriClean.data

class DataQualityController {
	def dataCleanService

	def findViolations () {
		def vio = []
		
		def fileName = params.dataset  //"address"
		def conName = params.con
		
		def dataset = Dataset.findByName(fileName)
		def con = DbConstraint.findByName(conName)
		
		if (dataset && con) {
		
			def datasetUrl = dataset.url
			def conUrl = con.url
			
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
}
