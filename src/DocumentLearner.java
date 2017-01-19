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

    public DocumentLearner() {
        Classifier classifier = new Classifier();
        createList();
        classifier.setTrainingData(DataClass.getClasses());
    }

    //String DirectoryPath = "D:\\school\\module 6 Intelligent Interaction Design\\AI\\Interactive Learner\\blogs";
    String DirectoryPath = GUI.getDirectoryPathPath();
    HashMap<String, HashMap<String, Integer>> files = new HashMap<String, HashMap<String, Integer>>();

    public void createList() {
        File dir = new File(DirectoryPath);
        File[] classes = dir.listFiles();
        if (classes != null) {
            for (File classDir : classes) {
                DataClass dataClass;
                if (!DataClass.getClasses().contains(classDir.getName())) {
                    dataClass =  new DataClass(dir.getName());
                } else {
                    dataClass = DataClass.getClass(classDir.getName());
                }
                //System.out.println(dataClass.getName());
                if (classDir.listFiles() != null) {
                    for (File file : classDir.listFiles()) {
                        dataClass.addFile(new DataFile(file));
                        //System.out.println(file.getName());
//                        if (!files.containsKey(file.getName())) {
//                            files.put(file.getName(), readFile(file));
//                        }

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
    }

//    public HashMap getFilesInfo() {
//        Set<String> keys = files.keySet();
//        for (String key : keys) {
//            HashMap<String, Integer> result = files.get(key);
//            Set<String> words = result.keySet();
//            for (String word : words) {
//                int value = result.get(word);
//                System.out.println("FileName: " + key + "       Woord: " + word + "      Aantal voorkomen: " + value);
//            }
//        }
//        return files;
//    }
}
