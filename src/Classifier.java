import java.util.Collection;

public class Classifier {

	public void setTrainingData(Collection<DataClass> classes) {
		//TODO: Determine relevant vocabulary and store only these words
		calculateTables();
	}
	
	public DataClass classify(DataFile file) {
		//TODO: Calculate total probability for each class and determine the highest
		return null;
	}
	
	private void calculateTables() {
		//TODO: Calculate probability tables
	}
	
}
