package external_mergesort;

public class Comparator<T extends Comparable<T>> {
    private SortOrder order = SortOrder.NONE;

    public Comparator(SortOrder order) {
        this.order = order;
    }

    public boolean compare(T left, T right) {
        int comparisonResult = left.compareTo(right);

        if (comparisonResult == 0)
            return true;

        boolean condition = comparisonResult > 0;
        return order == SortOrder.DESCENDING ? !condition : condition;
    }

    public static <T extends Comparable<T>> int compare2(T t1, T t2) {
        return t1.compareTo(t2);
    }
}