package dataPriClean.core

import grails.transaction.Transactional

import data.cleaning.core.service.dataset.impl.Dataset
import data.cleaning.core.service.dataset.impl.MasterDataset
import data.cleaning.core.service.dataset.impl.TargetDataset
import data.cleaning.core.service.dataset.impl.Constraint
import data.cleaning.core.service.repair.impl.Violation
import data.cleaning.core.service.repair.impl.Violations

import data.cleaning.core.service.dataset.DatasetService
import data.cleaning.core.service.repair.RepairService
import data.cleaning.core.service.dataset.impl.DatasetServiceImpl
import data.cleaning.core.service.repair.impl.RepairServiceImpl

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
									char separator = ',',
									char quoteChar = '"') {
		DatasetService datasetService = new DatasetServiceImpl ()
		return datasetService.loadMasterDataset(url, fileName, fdUrl, separator, quoteChar)
	}

    List<Violations> findViolations (Dataset dataset) {
		RepairService repairService = new RepairServiceImpl()
		List<Constraint> constraints = dataset.getConstraints()

		List<Violations> violationsList = new ArrayList<Violations>()

		for (Constraint constraint : constraints) {
			Violations v = repairService.calcViolations(dataset.records, constraint)
			violationsList.add(v)
		}

		return violationsList
	}
}
