import java.util.Collection;

public interface Classifier {

	void setTrainingData(Collection<DataClass> classes);
	void setChiThreshold(float threshold);

	DataClass classify(DataFile file);
	
}
