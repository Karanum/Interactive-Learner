import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Dylan on 4-1-2017.
 */
public class DocumentLearner {

    String DirectoryPath = "D:\\school\\module 6 Intelligent Interaction Design\\AI\\Interactive Learner\\blogs";
    HashMap<String, Integer> words = new HashMap<String, Integer>();

    public HashMap createList() {
        File dir = new File(DirectoryPath);
        File[] dirList = dir.listFiles();
        if (dirList != null) {
            for (File folder : dirList) {
                System.out.println(folder.getName());
                for (File file : folder.listFiles()) {
                    System.out.println(file.getName());
                    readFile(file);
                }
            }
        }
        Set<String> keys = words.keySet();
        int totalValue = 0;
        for (String key : keys) {
            int value = words.get(key);
            totalValue = totalValue + value;
            System.out.println("woord: " + key + " aantal: " + value);
        }
        //System.out.println("woord aantal: " + keys.size());
        System.out.println("aantal woorden in totaal: " + totalValue);
        return words;
    }
    public void readFile(File file) {
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
            notNormalized = notNormalized.replaceAll("[^A-Za-z0-9 ]", "");
            System.out.println(notNormalized);
            StringTokenizer tokenizer = new StringTokenizer(notNormalized);
            while (tokenizer.hasMoreElements()) {
                String nextElement = (String) tokenizer.nextElement();
                if (!words.containsKey(nextElement)) {
                    words.put(nextElement, new Integer(1));
                } else {
                    words.put(nextElement, words.get(nextElement) + 1);
                }
                //System.out.println(nextElement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
