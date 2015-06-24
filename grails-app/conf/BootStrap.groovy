import dataPriClean.user.Agent

class BootStrap {

    def init = { servletContext ->
		println "bootstrap starts..."
		
		bootstrapDatabase ()
		
		println "bootstrap ends."
		
    }
    def destroy = {
    }
	
	def bootstrapDatabase () {
		Date date = new Date()
		
		def agent = new Agent(
			name: "thomas",
			username: "thomas",
			password: "thomas",
			dateCreated: date,
			dateUpdated: date,
			role: "Target"
			)
		
		if (agent.save()) {
			println "new agent created sucessfully!"
		}
		else {
			println "new agent created failed!"
			agent.errors.each{
				println it
			}
		}
	}
}
