package external_mergesort;

public class OutputData <T1> {
	private T1 value;
	private int index;
	
	public OutputData(T1 value, int index) {
		this.value = value;
		this.index = index;
	}
	
	public T1 getValue() { return value; };
	public int getIndex() { return index; };
}
