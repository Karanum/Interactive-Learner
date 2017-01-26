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
    private float chiThreshold = -1;

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
        this.setTrainingData(classes, false);
    }

    public void setTrainingData(Collection<DataClass> classes, boolean outputChiValues) {
        chiValues = new ArrayList<>();
        trainingData = new HashMap<>();
        wordCounts = new HashMap<>();
        classProbabilities = new HashMap<>();
        likelihoodTables = new HashMap<>();

        vocabulary = new HashSet<>();
        for (DataClass c : classes) {
            vocabulary.addAll(c.getVocabulary().keySet());
        }
        cullVocabulary();
        calculateTables();
        if (outputChiValues) outputChiValues();
    }

    public void setMaxVocabSize(int vocabSize) {
        maxVocabSize = vocabSize;
    }

    public void setChiThreshold(float threshold) {
        chiThreshold = threshold;
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
        float bestProbability = 0.0f;

        HashMap<String, Integer> fileVocab = file.getTokenizedWords();
        for (DataClass c : DataClass.getClasses()) {
            float probability = classProbabilities.get(c);
            for (String word : fileVocab.keySet()) {
                if (vocabulary.contains(word)) {
                    //Sum of logarithms, since product of likelihoods would become too small for Java
                    probability += Math.log(likelihoodTables.get(c).get(word));
                }
            }
            if (verbose) System.out.println("Probability for " + c.getName() + " = " + probability);

            if (probability > bestProbability || bestProbability == 0) {
                bestProbability = probability;
                bestClass = c;
            } else if (probability == bestProbability && random.nextInt(2) == 0) {
                bestClass = c;
                if (verbose) System.out.println("Class " + c.getName() + " won the equal probability situation");
            }
        }
        return bestClass;
    }

    private void calculateChiValues(HashMap<DataClass, HashMap<String, Integer>> classVocabs) {
        HashMap<String, Integer> combinedVocab = DataClass.getCombinedVocabulary();
        HashMap<String, Float> chiValueMap = new HashMap<>();
        Collection<DataClass> classes = DataClass.getClasses();

        float numClasses = (float) classes.size();
        for (String word : vocabulary) {
            float chi = 0;
            float expectedFreq = combinedVocab.get(word) / numClasses;
            for (DataClass c : classes) {
                HashMap<String, Integer> classVocab = classVocabs.get(c);
                if (classVocab.containsKey(word)) {
                    chi += Math.pow((classVocab.get(word) - expectedFreq), 2) / expectedFreq;
                }
            }
            chiValueMap.put(word, chi);
        }

        chiValues = new ArrayList<>(chiValueMap.entrySet());
        Collections.sort(chiValues, (Map.Entry<String, Float> a, Map.Entry<String, Float> b)
                -> b.getValue().compareTo(a.getValue()));
    }

    private void cullVocabulary() {
        Collection<DataClass> classes = DataClass.getClasses();
        HashMap<DataClass, HashMap<String, Integer>> classVocabs = new HashMap<>();
        for (DataClass c : classes) {
            classVocabs.put(c, c.getVocabulary());
        }

        calculateChiValues(classVocabs);

        if (maxVocabSize > 0 && maxVocabSize < chiValues.size()) {
            chiValues = chiValues.subList(0, maxVocabSize);
        }
        if (chiThreshold > 0) {
            int thresholdIndex = -1;
            int i = 0;
            while (thresholdIndex < 0 && i < chiValues.size()) {
                if (chiValues.get(i).getValue() < chiThreshold) {
                    thresholdIndex = i;
                }
                ++i;
            }
            if (thresholdIndex != -1) {
                chiValues = chiValues.subList(0, thresholdIndex);
            }
        }

        trainingData = new HashMap<>();
        for (DataClass c : DataClass.getClasses()) {
            trainingData.put(c, new HashMap<>());
        }

        vocabulary = new HashSet<>();
        for (Map.Entry<String, Float> entry : chiValues) {
            String word = entry.getKey();
            vocabulary.add(word);
            for (DataClass c : classes) {
                HashMap<String, Integer> classVocab = classVocabs.get(c);
                if (classVocab.containsKey(word)) {
                    trainingData.get(c).put(word, classVocab.get(word));
                }
            }
        }
    }

    private void calculateTables() {
        wordCounts = new HashMap<>();
        classProbabilities = new HashMap<>();
        Collection<DataClass> classes = DataClass.getClasses();

        float totalFiles = (float) DataClass.getTotalFileCount();

        for (DataClass c : classes) {
            float probability = c.getFileCount() / totalFiles;
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
        for (DataClass c : classes) {
            likelihoodTables.put(c, new HashMap<>());
            float smoothing = 0.01f;
            for (String word : vocabulary) {
                float occurrences = smoothing;
                if (trainingData.get(c).containsKey(word)) occurrences += trainingData.get(c).get(word);
                float likelihood = occurrences / (wordCounts.get(c) + vocabulary.size() + smoothing);
                likelihoodTables.get(c).put(word, likelihood);
            }
        }
    }

}
