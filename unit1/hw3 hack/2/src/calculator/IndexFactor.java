package calculator;

import java.util.Comparator;

public abstract class IndexFactor extends Factor implements Comparable<IndexFactor> {
    private final int index;

    public IndexFactor(FactorKind factorKind, int index) {
        super(factorKind);
        this.index = index;
    }

    public static final class JoinComparator implements Comparator<IndexFactor> {
        @Override
        public int compare(IndexFactor o1, IndexFactor o2) {
            if (o1.getFactorKind() == o2.getFactorKind()) {
                return o1.joinCompare(o2);
            } else {
                return o1.getFactorKind().compareTo(o2.getFactorKind());
            }
        }
    }

    public abstract IndexFactor multi(IndexFactor indexFactor);

    public int getIndex() {
        return index;
    }

    protected abstract int compare(IndexFactor o);

    public int joinCompare(IndexFactor o) {
        return 0;
    }

    @Override
    public int compareTo(IndexFactor o) {
        if (getFactorKind() == o.getFactorKind()) {
            return compare(o);
        } else {
            return getFactorKind().compareTo(o.getFactorKind());
        }
    }
}
