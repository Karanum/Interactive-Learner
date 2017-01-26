package learner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class DataFile {

	HashMap<String, Integer> words = new HashMap<String, Integer>();

	public DataFile(File file) {
		if (file == null) return;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while(line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			String notNormalized = sb.toString();
			notNormalized = notNormalized.toLowerCase();
			notNormalized = notNormalized.replaceAll("[^A-Za-z0-9]", "");
			//System.out.println(notNormalized);
			StringTokenizer tokenizer = new StringTokenizer(notNormalized);
			while (tokenizer.hasMoreElements()) {
				String nextElement = (String) tokenizer.nextElement();
				if (!words.containsKey(nextElement)) {
					words.put(nextElement, new Integer(1));
				} else {
					words.put(nextElement, words.get(nextElement) + 1);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Integer> getTokenizedWords() {
		return words;
	}
	
}
