package dataPriClean.user

import dataPriClean.data.Dataset

class Agent {
	String name
	String username
	String password
	
	Date dateCreated
	Date dateUpdated
	
	String role = "Target"
	
	static hasMany = [datasets: Dataset]
	
    static constraints = {
		datasets(nullable: true)
		
		username(unique: true)
		password(password: true)
		role(inList: ["Target", "Master"])
    }
	
	String toString () {
		name
	}
}
