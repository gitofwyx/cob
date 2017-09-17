package com.kelan.core.util;

import java.util.*;
import java.util.Map.Entry;

public class MapUtil {
	
	/**
	 * Map按值降序排序
	 * @param oriMap
	 * @return
	 */
	public static Map<String, Double> sortMapByValue(Map<String, Double> oriMap) {
		Map<String, Double> sortedMap = new LinkedHashMap<>();
	    if (oriMap != null && !oriMap.isEmpty()) {
	        List<Entry<String, Double>> entryList = new ArrayList<>(oriMap.entrySet());
	        Collections.sort(entryList, new Comparator<Entry<String, Double>>() {
                public int compare(Entry<String, Double> entry1,
                        Entry<String, Double> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue());
                }
            });
	        Iterator<Entry<String, Double>> iter = entryList.iterator();
	        Entry<String, Double> tmpEntry = null;
	        while (iter.hasNext()) {
	            tmpEntry = iter.next();
	            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
	        }
	    }
	    return sortedMap;
	}

}
