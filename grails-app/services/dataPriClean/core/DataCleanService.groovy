package dataPriClean.core

import grails.transaction.Transactional

import data.cleaning.core.service.dataset.impl.MasterDataset
import data.cleaning.core.service.dataset.impl.TargetDataset
import data.cleaning.core.service.dataset.impl.Constraint
import data.cleaning.core.service.repair.impl.Violation
import data.cleaning.core.service.repair.impl.Violations

import data.cleaning.core.service.dataset.DatasetService
import data.cleaning.core.service.repair.RepairService
import data.cleaning.core.service.dataset.impl.DatasetServiceImpl
import data.cleaning.core.service.repair.impl.RepairServiceImpl
import data.cleaning.core.utils.search.SearchType

import data.cleaning.webapp.core.DataCleaningUtils
import data.cleaning.webapp.core.implementation.DataCleaningUtilsImpl

import dataPriClean.data.DbConstraint
import dataPriClean.data.Dataset
import dataPriClean.user.Agent

@Transactional
class DataCleanService {
	
	TargetDataset loadTargetDataset (String url, 
									String fileName,
									String fdUrl, 
									char separator = ',', 
									char quoteChar = '"') {
		DatasetService datasetService = new DatasetServiceImpl ()
		return datasetService.loadTargetDataset(url, fileName, fdUrl, separator, quoteChar)
	}
									
	MasterDataset loadMasterDataset (String url,
									String fileName,
									String fdUrl,
									Long tid,
									char separator = ',',
									char quoteChar = '"') {
        DatasetService datasetService = new DatasetServiceImpl ()
		return datasetService.loadMasterDataset(url, fileName, fdUrl, tid, separator, quoteChar)
	}

   String findViolations (TargetDataset dataset) {
		RepairService repairService = new RepairServiceImpl()
		DataCleaningUtils dataCleanUtil = new DataCleaningUtilsImpl()
		
		List<Constraint> constraints = dataset.getConstraints()
		
		StringBuilder sb = new StringBuilder()

		for (Constraint constraint : constraints) {
			sb.append("========================================\n")
			sb.append("Constraint: ")
			sb.append(constraint.toString() + "\n")
			Violations v = repairService.calcViolations(dataset.records, constraint)
//			def s = dataCleanUtil.outputViolations(v, constraint)
//			sb.append(s + "\n")
			sb.append(v.toString() + "\n")
		}

		return sb.toString()
	}
   
   def findViolationsList (TargetDataset dataset) {
	   RepairService repairService = new RepairServiceImpl()
	   DataCleaningUtils dataCleanUtil = new DataCleaningUtilsImpl()
	   
	   List<Constraint> constraints = dataset.getConstraints()
	   
	   def result = []

	   for (Constraint constraint : constraints) {
		   def subResult = [:]
		   subResult["constraint"] = constraint.toString()
		   subResult["constraintAttrs"] = constraint.getColsInConstraint()
		   Violations v = repairService.calcViolations(dataset.records, constraint)
		   List<List<String>> s = dataCleanUtil.outputViolationsList(v, constraint)
		   subResult["violatons"] = s
		   result.add(subResult)
	   }

	   return result
   }
	
	//get recommendation result for individual searching type and config
	String getRecommendationsInd(def targetDataset, def masterDataset, def simThreshold, def searchObj, Map<String, Double> config) {
		DataCleaningUtils dataCleanUtil = new DataCleaningUtilsImpl()
		
		String result = ""
		
		TargetDataset target = loadTargetDataset(targetDataset.url, targetDataset.name, targetDataset.dbConstraint.url)
		MasterDataset master = loadMasterDataset(masterDataset.url, masterDataset.name, masterDataset.dbConstraint.url, targetDataset.id)
		
		if (target && master) {
		
			float simThresholdF = Float.parseFloat(simThreshold);
			
			SearchType searchType
			
			switch (searchObj) {
				case "weighted":
					searchType = SearchType.SA_WEIGHTED
					break
				case "constrained":
					searchType = SearchType.SA_EPS_FLEX
					break
				case "dynamic":
					searchType = SearchType.SA_EPS_DYNAMIC
					break
				case "lexical":
					searchType = SearchType.SA_EPS_LEX
					break
				default:
					searchType = SearchType.SA_EPS
					break
			}
			
			result = dataCleanUtil.runDataCleaning(target, master, simThresholdF, searchType, config)
		}
		else {
			println("Target & Master Dataset Not Found!")
		}
		
		return result
	}
	
	//get recommendation result for multiple searching type and config
	String getRecommendations(def targetDataset, def masterDataset, def simThreshold, def searchObj, def config) {
		StringBuilder sb = new StringBuilder()
		
		if (searchObj) {
			searchObj.each {
				switch (it) {
					case "weighted":
						def s
						if (config && config["weighted"]) {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, config["weighted"])
						}
						else {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, null)
						}
						sb.append("====================================== \n")
						sb.append("For weighted algorithm: \n")
						sb.append(s + "\n")
						break
					case "dynamic":
						def s
						if (config && config["dynamic"]) {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, config["dynamic"])
						}
						else {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, null)
						}
						sb.append("====================================== \n")
						sb.append("For dynamic algorithm: \n")
						sb.append(s + "\n")
						break
					case "lexical":
						def s
						if (config && config["lexical"]) {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, config["lexical"])
						}
						else {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, null)
						}
						sb.append("====================================== \n")
						sb.append("For lexical algorithm: \n")
						sb.append(s + "\n")
						break
					case "constrained":
						def s
						if (config && config["constrained"]) {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, config["constrained"])
						}
						else {
							s = getRecommendationsInd(targetDataset, masterDataset, simThreshold, it, null)
						}
						sb.append("====================================== \n")
						sb.append("For constrained algorithm: \n")
						sb.append(s + "\n")
						break
				}
			}
		}
		
		return sb.toString()
	}
	
	Map<String, Double> mapConvert (def map) {
		Map<String, Double> mapJava = new HashMap<String, Double>();
		
		map.each { key, value ->
			mapJava.put(key, value)
		}
		
		return mapJava
	}
	
	//delete individual file
	void deleteFileByUrl (String url) {
		File file = new File(url)
		
		if (!file.exists()) {
			println ("File does not exist, cannot delete the file!")
		}
		else {
			file.delete()
			println ("The file " + file.toString() + " was deleted succesfully!")
		}
	}
	
	//delete the dataset file, its constraint file and parent directory 
	void deleteDatasetFile (String datasetUrl, String conUrl) {
		File datasetFile = new File(datasetUrl)
		File parentDir
		if (!datasetFile.exists()) {
			println ("File does not exist, cannot delete the file!")
			return
		}
		else {
		 parentDir = datasetFile.getParentFile()
		}
		
		deleteFileByUrl(datasetUrl)
		deleteFileByUrl(conUrl)
		parentDir.delete()
		
		println ("Delete dataset: " + datasetFile.toString() + 
			" and its constraint file successfully!")
	}
}
