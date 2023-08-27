package external_mergesort;

import java.util.ArrayList;
import external_mergesort.SortOrder;

public class Comparator <T extends Comparable<T>> {
	private SortOrder order = SortOrder.NONE;
	public Comparator(SortOrder order) {
		this.order = order;
	}
	
	public <T extends Comparable<T>> boolean compare(T left, T right) {
		if (left.compareTo(right) == 0)
			return true;
		
		Boolean condition = left.compareTo(right) > 0;
		return order == SortOrder.DESCENDING ? !condition : condition;
	}
	
	public static <T extends Comparable<T>> int compare2(T t1, T t2){
		return t1.compareTo(t2);
	}

}