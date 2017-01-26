package learner;

import java.util.Collection;

public interface Classifier {

	void setTrainingData(Collection<DataClass> classes);
	void setMaxVocabSize(int vocabSize);

	DataClass classify(DataFile file);

	void outputChiValues();
	
}
