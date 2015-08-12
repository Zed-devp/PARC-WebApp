package dataPriClean.data

class DataQualityController {
	def dataCleanService

	def findViolations () {
		def vio = []
		
		def fileName = session.targetDataset
		
		def dataset = TargetDataset.findByName(fileName)
		
		if (dataset) {
			def con = dataset.dbConstraint
		
			if (con) {
				def datasetUrl = dataset.url
				def conUrl = con.url
				
				def targetData = dataCleanService.loadTargetDataset(datasetUrl, fileName, conUrl)
				def violations = dataCleanService.findViolations(targetData)
				
				vio = violations//.toString()
			}
			//this dataset does not have constraint
			else {
				println ("this dataset does not have constraint. ")
				flash.message = "this dataset does not have constraint. "
			}
		}
		else {
			flash.message = "Target Dataset Not Found!"
			println("Target Dataset Not Found!")
		}
		
		[vio:vio]
	}
}
