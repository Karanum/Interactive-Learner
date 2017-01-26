package test;

import learner.DataFile;

import java.util.HashMap;

/**
 * Created by Karanum on 26-1-2017.
 */
public class FakeDataFile extends DataFile {

    HashMap<String, Integer> words;

    public FakeDataFile() {
        super(null);
        words = new HashMap<>();
    }

    public FakeDataFile addWord(String word, int n) {
        words.put(word, n);
        return this;
    }

    @Override
    public HashMap<String, Integer> getTokenizedWords() {
        return words;
    }

}
