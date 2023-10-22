package application;

import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DataSet {
	private String name;
	private Hashtable<String, DataItem> collection;
	
	public DataSet(String name) {
		this.name = name;
		collection = new Hashtable<String, DataItem>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hashtable<String, DataItem> getCollection() {
		return collection;
	}

	public void setCollection(Hashtable<String, DataItem> collection) {
		this.collection = collection;
	}
	
	public String toString() {
		String res = name;
		res += "\n";
		if (collection.size() > 0) {
			for (DataItem item: collection.values()) {
				res += item.toString();
			}
		}
		else {
			res += "Empty Data Set";
		}
		
		return res;
	}
	
	
}
