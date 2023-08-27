package external_mergesort;

public class StringFileReader extends FileReader {
	public StringFileReader(String path) {
		super(path);
	}

	@Override
	public Object getNextValue() {
		return super.getReader().nextLine();
	}
}

