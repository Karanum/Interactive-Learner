package learner;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DataClass {

	//=================================================================================
	// Static members
	//=================================================================================
	private static HashMap<String, DataClass> classes = new HashMap<String, DataClass>();
	
	public static Collection<DataClass> getClasses() {
		return classes.values();
	}
	
	public static DataClass getClass(String name) {
		return classes.get(name);
	}
	
	public static int getTotalFileCount() {
		int amount = 0;
		for (DataClass c : classes.values()) {
			amount += c.getFileCount();
		}
		return amount;
	}

	public static HashMap<String, Integer> getCombinedVocabulary() {
		HashMap<String, Integer> result = new HashMap<>();
		for (DataClass c : getClasses()) {
			HashMap<String, Integer> vocab = c.getVocabulary();
			for (String word : vocab.keySet()) {
				int n = 0;
				if (result.containsKey(word)) n = result.get(word);
				result.put(word, n + vocab.get(word));
			}
		}
		return result;
	}
	
	public static void reset() {
		classes.clear();
	}
	
	//=================================================================================
	// Instance members
	//=================================================================================
	private String name;
	private HashSet<DataFile> files;
	
	public DataClass(String name) {
		this.name = name;
		classes.put(name, this);
		files = new HashSet<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addFile(DataFile file) {
		files.add(file);
	}
	
	public void removeFile(DataFile file) {
		files.remove(file);
	}
	
	public int getFileCount() {
		return files.size();
	}
	
	public HashMap<String, Integer> getVocabulary() {
		HashMap<String, Integer> result = new HashMap<>();
		for (DataFile file : files) {
			HashMap<String, Integer> tokens = file.getTokenizedWords();
			for (String word : tokens.keySet()) {
				int amount = tokens.get(word);
				if (result.containsKey(word)) {
					amount += result.get(word);
				}
				result.put(word, amount);
			}
		}
		return result;
	}
	
}
