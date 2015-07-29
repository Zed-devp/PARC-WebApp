package dataPriClean.data

class Dataset {
	String name
	String url
	DbConstraint dbConstraint

    static constraints = {
		dbConstraint(nullable: true)
    }
	
	static mapping = {
		dbConstraint cascade: 'all-delete-orphan'
	}
}
