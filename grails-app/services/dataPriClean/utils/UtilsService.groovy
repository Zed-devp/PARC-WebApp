package dataPriClean.utils

import java.util.Map;

import grails.transaction.Transactional

@Transactional
class UtilsService {

    Map<String, Double> mapConvert (def map) {
		Map<String, Double> mapJava = new HashMap<String, Double>();
		
		map.each { key, value ->
			mapJava.put(key, value)
		}
		
		return mapJava
	}
}
