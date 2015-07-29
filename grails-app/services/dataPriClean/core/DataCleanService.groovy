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
		
		List<Constraint> constraints = dataset.getConstraints()
		
		StringBuilder sb = new StringBuilder()

		for (Constraint constraint : constraints) {
			sb.append("========================================\n")
			sb.append("Constraint: ")
			sb.append(constraint.toString() + "\n")
			Violations v = repairService.calcViolations(dataset.records, constraint)
			sb.append(v.toString() + "\n")
		}

		return sb.toString()
	}
	
	String getRecommendations(def targetDataset, def masterDataset, def simThreshold, def searchObj) {
		DataCleaningUtils dataCleanUtil = new DataCleaningUtils()
		
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
			
			result = dataCleanUtil.runDataCleaning(target, master, simThresholdF, searchType)
		}
		else {
			println("Target & Master Dataset Not Found!")
		}
		
		return result
	}
	
	String getRecommendations(def targetDataset, def masterDataset, def simThreshold, def searchObj, Map<String, Double> config) {
		DataCleaningUtils dataCleanUtil = new DataCleaningUtils()
		
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
	
	Map<String, Double> mapConvert (def map) {
		Map<String, Double> mapJava = new HashMap<String, Double>();
		
		map.each { key, value ->
			mapJava.put(key, value)
		}
		
		return mapJava
	}
}
