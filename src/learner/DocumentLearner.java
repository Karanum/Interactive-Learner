package learner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dylan on 4-1-2017.
 */
public class DocumentLearner {
    Classifier classifier = null;
    GUI gui;

    public DocumentLearner(GUI gui) {
        this.gui = gui;
        classifier = new NaiveBayesClassifier();
    }

    //String DirectoryPath = "D:\\school\\module 6 Intelligent Interaction Design\\AI\\Interactive Learner\\blogs";
    // HashMap<String, HashMap<String, Integer>> files = new HashMap<String, HashMap<String, Integer>>();

    public void loadTrainingData(File file, int vocabularySize, float chiValue) {
        DataClass.reset();
        classifier.setMaxVocabSize(vocabularySize);
        classifier.setChiThreshold(chiValue);
        File[] classes = file.listFiles();
        if (classes != null) {
            for (File classDir : classes) {
                DataClass dataClass;
                if (!DataClass.getClasses().contains(classDir.getName())) {
                    dataClass = new DataClass(classDir.getName());
                } else {
                    dataClass = DataClass.getClass(classDir.getName());
                }
                //System.out.println(dataClass.getName());
                if (classDir.listFiles() != null) {
                    for (File f : classDir.listFiles()) {
                        //System.out.println(f.getName());
                        dataClass.addFile(new DataFile(f));
                        //System.out.println(file.getName());
//                        if (!files.containsKey(file.getName())) {
//                            files.put(file.getName(), readFile(file));
//                        }

                    }
                }

            }
        }
        classifier.setTrainingData(DataClass.getClasses(), true);
    }

    public ResultTable classify(File file) {
        ResultTable resultTable = new ResultTable();
        //HashMap<DataClass, ArrayList<DataFile>> files = new HashMap<>();
        if (file.isDirectory()) {
            if (file.isDirectory()) {
                for (File directory : file.listFiles()) {
                    String dirName = directory.getName();
                    DataClass dataClass = DataClass.getClass(dirName);
                    if (dataClass == null) {
                        continue;
                    }
                    for (File textFile : directory.listFiles()) {
                        DataFile dataFile = new DataFile(textFile);
                        DataClass resultClass = classifier.classify(dataFile);
                        resultTable.addResult(dataClass, resultClass);

                        dataClass.addFile(dataFile);
                        classifier.setTrainingData(DataClass.getClasses(), false);
                    }
                }
            }
        }
        return resultTable;
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

