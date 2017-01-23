import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Created by Karanum on 23-1-2017.
 */
public class NaiveBayesClassifier implements Classifier {

    public static boolean verbose = false;

    private Random random;
    private float chiThreshold = 0.05f;

    private Set<String> vocabulary = null;
    private HashMap<DataClass, HashMap<String, Integer>> trainingData = null;

    private HashMap<DataClass, Integer> wordCounts = null;
    private HashMap<DataClass, Float> classProbabilities = null;
    private HashMap<DataClass, HashMap<String, Float>> likelihoodTables = null;

    public NaiveBayesClassifier() {
        random = new Random(System.currentTimeMillis());
    }

    public void setTrainingData(Collection<DataClass> classes) {
        //TODO: For each class, store a copy as training data
        //TODO: For each class vocabulary, calculate and sort by chi^2 values
        //TODO: Cull vocabulary lists based on chi^2 threshold or n words
        calculateTables();
    }

    public void setChiThreshold(float threshold) {
        chiThreshold = threshold;
    }

    public DataClass classify(DataFile file) {
        if (trainingData == null) return null;

        DataClass bestClass = null;
        float bestProbability = -1.0f;

        HashMap<String, Integer> fileVocab = file.getTokenizedWords();
        for (DataClass c : DataClass.getClasses()) {
            float probability = classProbabilities.get(c);
            for (String word : fileVocab.keySet()) {
                probability *= likelihoodTables.get(c).get(word);
            }
            if (verbose) System.out.println("Probability for " + c.getName() + " = " + probability);

            if (probability > bestProbability) {
                bestProbability = probability;
                bestClass = c;
            } else if (probability == bestProbability && random.nextInt(2) == 0) {
                bestClass = c;
                if (verbose) System.out.println("Class " + c.getName() + " won the equal probability situation");
            }
        }
        return bestClass;
    }

    private void calculateTables() {
        classProbabilities = new HashMap<>();
        for (DataClass c : DataClass.getClasses()) {
            float probability = c.getFileCount() / (float) DataClass.getTotalFileCount();
            classProbabilities.put(c, probability);

            HashMap<String, Integer> words = trainingData.get(c);
            Collection<Integer> countPerWord = words.values();
            int wordCount = 0;
            for (int n : countPerWord) {
                wordCount += n;
            }
            wordCounts.put(c, wordCount);
        }

        likelihoodTables = new HashMap<>();
        for (DataClass c : DataClass.getClasses()) {
            likelihoodTables.put(c, new HashMap<>());
            for (String word : vocabulary) {
                float likelihood = (trainingData.get(c).get(word) + 1) / (float) (wordCounts.get(c) + vocabulary.size() + 1);
                likelihoodTables.get(c).put(word, likelihood);
            }
        }
    }

}
