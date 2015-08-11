package dataPriClean.data

class TargetDataset extends Dataset{
	String masterAgent
	String masterDataset

    static constraints = {
		masterAgent(nullable: false)
		masterDataset(nullable: false)
    }
}
