package learner;

import java.io.*;
import java.util.*;

/**
 * Created by Karanum on 23-1-2017.
 */
public class NaiveBayesClassifier implements Classifier {

    public static boolean verbose = false;

    private Random random;
    private int maxVocabSize = -1;

    private Set<String> vocabulary = null;
    private List<Map.Entry<String, Float>> chiValues = null;
    private HashMap<DataClass, HashMap<String, Integer>> trainingData = null;

    private HashMap<DataClass, Integer> wordCounts = null;
    private HashMap<DataClass, Float> classProbabilities = null;
    private HashMap<DataClass, HashMap<String, Float>> likelihoodTables = null;

    public NaiveBayesClassifier() {
        random = new Random(System.currentTimeMillis());
    }

    public void setTrainingData(Collection<DataClass> classes) {
        vocabulary = new HashSet<>();
        for (DataClass c : classes) {
            vocabulary.addAll(c.getVocabulary().keySet());
        }
        cullVocabulary();
        calculateTables();
    }

    public void setMaxVocabSize(int vocabSize) {
        maxVocabSize = vocabSize;
    }

    public void outputChiValues() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("chi-values.txt"), "utf-8"));
            for (Map.Entry<String, Float> entry : chiValues) {
                String line = String.format("%3.3f - %s%n", entry.getValue(), entry.getKey());
                out.write(line);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataClass classify(DataFile file) {
        if (trainingData == null) return null;

        DataClass bestClass = null;
        float bestProbability = -1.0f;

        HashMap<String, Integer> fileVocab = file.getTokenizedWords();
        for (DataClass c : DataClass.getClasses()) {
            float probability = classProbabilities.get(c);
            System.out.println("p = " + probability);
            for (String word : fileVocab.keySet()) {
                if (vocabulary.contains(word)) {
                    probability *= likelihoodTables.get(c).get(word);
                    System.out.println(word + ": " + likelihoodTables.get(c).get(word));
                }
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

    private void cullVocabulary() {
        HashMap<String, Integer> combinedVocab = DataClass.getCombinedVocabulary();

        HashMap<String, Float> chiValueMap = new HashMap<>();
        for (String word : vocabulary) {
            float chi = 0;
            float expectedFreq = combinedVocab.get(word) / (float) (DataClass.getClasses().size());
            for (DataClass c : DataClass.getClasses()) {
                if (c.getVocabulary().containsKey(word)) {
                    chi += Math.pow((c.getVocabulary().get(word) - expectedFreq), 2) / expectedFreq;
                }
            }
            chiValueMap.put(word, chi);
        }

        chiValues = new ArrayList<>(chiValueMap.entrySet());
        Collections.sort(chiValues, (Map.Entry<String, Float> a, Map.Entry<String, Float> b)
                                        -> b.getValue().compareTo(a.getValue()));
        if (maxVocabSize > 0 && maxVocabSize < chiValues.size()) {
            chiValues = chiValues.subList(0, maxVocabSize);
        }

        trainingData = new HashMap<>();
        for (DataClass c : DataClass.getClasses()) {
            trainingData.put(c, new HashMap<>());
        }

        vocabulary = new HashSet<>();
        for (Map.Entry<String, Float> entry : chiValues) {
            String word = entry.getKey();
            vocabulary.add(word);
            for (DataClass c : DataClass.getClasses()) {
                HashMap<String, Integer> classVocab = c.getVocabulary();
                if (classVocab.containsKey(word)) {
                    trainingData.get(c).put(word, classVocab.get(word));
                }
            }
        }
    }

    private void calculateTables() {
        wordCounts = new HashMap<>();
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
                int occurrences = 1;
                if (trainingData.get(c).containsKey(word)) occurrences += trainingData.get(c).get(word);
                float likelihood = (occurrences) / (float) (wordCounts.get(c) + vocabulary.size() + 1);
                likelihoodTables.get(c).put(word, likelihood);
            }
        }
    }

}