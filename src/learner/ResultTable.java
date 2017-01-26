package learner;

import java.util.HashMap;

/**
 * Created by Karanum on 26-1-2017.
 */
public class ResultTable {
    private HashMap<DataClass, HashMap<DataClass, Integer>> table;
    private int total;

    public ResultTable() {
        total = 0;
        table = new HashMap<>();
        for (DataClass outerClass : DataClass.getClasses()) {
            table.put(outerClass, new HashMap<>());
            for (DataClass innerClass : DataClass.getClasses()) {
                table.get(outerClass).put(innerClass, 0);
            }
        }
    }

    public void addResult(DataClass expectedClass, DataClass classifiedAs) {
        HashMap<DataClass, Integer> innerMap = table.get(expectedClass);
        int n = innerMap.get(classifiedAs);
        innerMap.put(classifiedAs, n + 1);
        ++total;
    }

    public int getTotalResults() {
        return total;
    }

    public int getCorrect(DataClass dataClass) {
        return table.get(dataClass).get(dataClass);
    }

    public int getTrueAmount(DataClass dataClass) {
        int result = 0;
        for (DataClass c : DataClass.getClasses()) {
            result += table.get(dataClass).get(c);
        }
        return result;
    }

    public int getClassifiedAmount(DataClass dataClass) {
        int result = 0;
        for (DataClass c : DataClass.getClasses()) {
            result += table.get(c).get(dataClass);
        }
        return result;
    }

    public float getAccuracy() {
        int correct = 0;
        for (DataClass c : DataClass.getClasses()) {
            correct += table.get(c).get(c);
        }
        return correct / (float) total;
    }

    public float getPrecision(DataClass dataClass) {
        int correct = table.get(dataClass).get(dataClass);
        int results = getClassifiedAmount(dataClass);
        return correct / (float) results;
    }

    public float getRecall(DataClass dataClass) {
        int correct = table.get(dataClass).get(dataClass);
        int results = getTrueAmount(dataClass);
        return correct / (float) results;
    }
}
