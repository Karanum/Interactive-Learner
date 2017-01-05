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
    HashMap<String, HashMap<String, Integer>> files = new HashMap<String, HashMap<String, Integer>>();

    public HashMap createList() {
        File dir = new File(DirectoryPath);
        File[] dirList = dir.listFiles();
        if (dirList != null) {
            for (File folder : dirList) {
                //System.out.println(folder.getName());
                if (folder.listFiles() != null) {
                    for (File file : folder.listFiles()) {
                        //System.out.println(file.getName());
                        if (!files.containsKey(file.getName())) {
                            files.put(file.getName(), readFile(file));
                        }
                    }
                }

            }
        }
//        Set<String> keys = words.keySet();
//        int totalValue = 0;
//        for (String key : keys) {
//            int value = words.get(key);
//            totalValue = totalValue + value;
//            System.out.println("woord: " + key + " aantal: " + value);
//        }
//        //System.out.println("woord aantal: " + keys.size());
//        System.out.println("aantal woorden in totaal: " + totalValue);
        return files;
    }
    public HashMap readFile(File file) {
        HashMap<String, Integer> words = new HashMap<String, Integer>();
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
            //System.out.println(notNormalized);
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
        return words;
    }

    public void getFilesInfo() {
        Set<String> keys = files.keySet();
        for (String key : keys) {
            HashMap<String, Integer> result = files.get(key);
            Set<String> words = result.keySet();
            for (String word : words) {
                int value = result.get(word);
                System.out.println("FileName: " + key + "       Woord: " + word + "      Aantal voorkomen: " + value);
            }
        }
    }
}
