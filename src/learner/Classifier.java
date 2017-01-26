package learner;

import java.util.Collection;

public interface Classifier {

	void setTrainingData(Collection<DataClass> classes, boolean extraOutput);
	void setMaxVocabSize(int vocabSize);
	void setChiThreshold(float threshold);

	DataClass classify(DataFile file);

	void outputChiValues();
	
}
