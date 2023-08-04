package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Unit implements Comparable<Unit> {
    private final ConstantFactor constantFactor;
    private final ArrayList<IndexFactor> indexFactors;

    public static final class JoinComparator implements Comparator<Unit> {
        @Override
        public int compare(Unit o1, Unit o2) {
            return indexFactorListCompare(o1.indexFactors, o2.indexFactors);
        }
    }

    public Unit(ConstantFactor constantFactor) {
        this.constantFactor = constantFactor;
        indexFactors = new ArrayList<>();
    }

    public Unit(IndexFactor indexFactor) {
        constantFactor = ConstantFactor.ONE;
        indexFactors = new ArrayList<>();
        indexFactors.add(indexFactor);
    }

    private Unit(ConstantFactor constantFactor, ArrayList<IndexFactor> indexFactors) {
        this.constantFactor = constantFactor;
        if (constantFactor.isZero()) {
            this.indexFactors = new ArrayList<>();
        } else {
            this.indexFactors = new ArrayList<>(indexFactors);
        }
    }

    private void insertIndexFactor(IndexFactor indexFactor) {
        int index = Collections.binarySearch(indexFactors, indexFactor,
                new IndexFactor.JoinComparator());
        if (index >= 0) {
            IndexFactor tar = indexFactors.get(index).multi(indexFactor);
            if (tar.getIndex() == 0) {
                indexFactors.remove(index);
            } else {
                indexFactors.set(index, tar);
            }
        } else {
            indexFactors.add(-index - 1, indexFactor);
        }
    }

    private void insertAllIndexFactor(ArrayList<IndexFactor> indexFactors) {
        for (IndexFactor indexFactor:indexFactors) {
            insertIndexFactor(indexFactor);
        }
    }

    public boolean isZero() {
        return constantFactor.isZero();
    }

    public Unit negate() {
        return new Unit(constantFactor.negate(), indexFactors);
    }

    public boolean isNegative() {
        return constantFactor.isNegative();
    }

    public Unit multi(Unit unit) {
        ConstantFactor resultConstantFactor = constantFactor.multi(unit.constantFactor);
        if (resultConstantFactor.isZero()) {
            return new Unit(resultConstantFactor);
        } else {
            Unit resultUnit = new Unit(resultConstantFactor, indexFactors);
            resultUnit.insertAllIndexFactor(unit.indexFactors);
            return resultUnit;
        }
    }

    public Unit join(Unit unit) {
        return new Unit(constantFactor.add(unit.constantFactor), indexFactors);
    }

    public Vector differentiate(char var) {
        Vector resultVector = new Vector(ConstantFactor.ZERO);
        for (int i = 0;i < indexFactors.size();i++) {
            Vector tempVector = new Vector(constantFactor);
            for (int j = 0;j < i;j++) {
                tempVector = tempVector.multi(new Vector(indexFactors.get(j)));
            }
            tempVector = tempVector.multi(indexFactors.get(i).differentiate(var));
            for (int j = i + 1;j < indexFactors.size();j++) {
                tempVector = tempVector.multi(new Vector(indexFactors.get(j)));
            }
            resultVector = resultVector.add(tempVector);
        }
        return resultVector;
    }

    public int transToIndex() {
        return constantFactor.getIntValue();
    }

    public ConstantFactor getConstantFactor() {
        return constantFactor;
    }

    private static int indexFactorListCompare(ArrayList<IndexFactor> src,
                                              ArrayList<IndexFactor> tar) {
        if (src.size() == tar.size()) {
            for (int i = 0;i < src.size(); i++) {
                int result = src.get(i).compareTo(tar.get(i));
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        } else {
            return Integer.compare(src.size(), tar.size());
        }
    }

    @Override
    public int compareTo(Unit o) {
        int result1 = constantFactor.compareTo(o.constantFactor);
        if (result1 == 0) {
            return indexFactorListCompare(indexFactors, o.indexFactors);
        } else {
            return result1;
        }
    }

    protected int sizeOfIndexFactors() {
        return indexFactors.size();
    }

    public IndexFactor getIndexFactors(int index) {
        return indexFactors.get(index);
    }

    public String indexToString() {
        StringBuilder sb = new StringBuilder();
        for (IndexFactor indexFactor : indexFactors) {
            sb.append(indexFactor.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getConstantFactor().isZero()) {
            sb.append("0");
        } else if (getConstantFactor().getIntValue() == 1) {
            if (indexFactors.size() > 0) {
                sb.append(indexToString().substring(1));
            } else {
                sb.append("1");
            }
        } else if (getConstantFactor().getIntValue() == -1) {
            if (indexFactors.size() > 0) {
                if (!indexToString().equals("*0")) {
                    sb.append("-");
                }
                sb.append(indexToString().substring(1));
            } else {
                sb.append("-1");
            }
        } else {
            sb.append(getConstantFactor().toString());
            sb.append(indexToString());
        }
        return sb.toString();
    }
}
