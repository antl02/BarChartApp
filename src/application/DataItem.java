package application;

import java.util.Hashtable;

public class DataItem {
	private String name;
	private Hashtable<String, Integer> itemData;
	
	public DataItem(String name) {
		this.name = name;
		itemData = new Hashtable<String, Integer>();
	}
	
	public DataItem(String name, Integer value) {
		this.name = name;
		itemData = new Hashtable<String, Integer>();
		itemData.put(name, value); // For testing
	}
	
	public String toString() {
		String message = "";
		for (String key: itemData.keySet()) {
			message += key + ": " + itemData.get(key) + "\n";
		}
		
		return message;
	}
}
