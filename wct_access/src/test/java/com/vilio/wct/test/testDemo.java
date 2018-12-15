package com.vilio.wct.test;

import java.util.HashMap;
import java.util.Map;

public class testDemo {
	
	
	public static void main(String[] args) {
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map1.put("map3", map3);
		map3.put("a", "1");
		map2.put("map3", map3);
		
		Map<String, Object> map4 = (Map<String, Object>) map1.get("map3");
		
		map4.put("b", 3);
		System.out.println(map1);
		
		
	}

}
