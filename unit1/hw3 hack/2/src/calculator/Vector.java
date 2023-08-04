package calculator;

import java.util.ArrayList;
import java.util.Collections;

import static calculator.ConstantFactor.ONE;
import static calculator.ConstantFactor.ZERO;

public class Vector implements Comparable<Vector> {
    private final ArrayList<Unit> units;

    public Vector() {
        units = new ArrayList<>();
    }

    public Vector(Unit unit) {
        units = new ArrayList<>();
        units.add(unit);
    }

    public Vector(ConstantFactor constantFactor) {
        this(new Unit(constantFactor));
    }

    public Vector(IndexFactor indexFactor) {
        this(new Unit(indexFactor));
    }

    private void insert(Unit unit) {
        if (!units.isEmpty() && units.get(0).isZero()) {
            units.remove(0);
        }
        int index = Collections.binarySearch(units, unit, new Unit.JoinComparator());
        if (index >= 0) {
            Unit tar = units.get(index).join(unit);
            if (tar.isZero()) {
                units.remove(index);
                if (units.isEmpty()) {
                    units.add(0, new Unit(ZERO));
                }
            } else {
                units.set(index, tar);
            }
        } else {
            units.add(-index - 1, unit);
        }
    }

    private void insertAll(ArrayList<Unit> units) {
        for (Unit unit : units) {
            insert(unit);
        }
    }

    public Vector add(Vector vector) {
        Vector result = new Vector();
        result.insertAll(units);
        result.insertAll(vector.units);
        return result;
    }

    public Vector subtract(Vector vector) {
        return add(vector.negate());
    }

    public Vector negate() {
        Vector result = new Vector();
        for (Unit unit : units) {
            result.insert(unit.negate());
        }
        return result;
    }

    public Vector multi(Vector vector) {
        Vector result = new Vector();
        for (Unit unit1 : units) {
            for (Unit unit2 : vector.units) {
                result.insert(unit1.multi(unit2));
            }
        }
        return result;
    }

    public Vector pow(Vector vector) {
        Vector result = new Vector();
        int index = vector.units.get(0).transToIndex();
        if (index == 0) {
            result.insert(new Unit(ONE));
            return result;
        }
        result.insertAll(units);
        for (int i = 1;i < index;i++) {
            result = result.multi(this);
        }
        return result;
    }

    public int getSize() {
        return units.size();
    }

    public boolean isZero() {
        return units.size() == 1 && units.get(0).isZero();
    }

    public boolean isNegative() {
        return units.size() == 1 && units.get(0).isNegative();
    }

    public Vector differentiate(char x) {
        Vector result = new Vector();
        for (Unit unit:units) {
            result = result.add(unit.differentiate(x));
        }
        return result;
    }

    @Override
    public int compareTo(Vector o) {
        if (units.size() == o.units.size()) {
            for (int i = 0;i < units.size();i++) {
                if (units.get(i).compareTo(o.units.get(i)) != 0) {
                    return units.get(i).compareTo(o.units.get(i));
                }

            }
            return 0;
        } else {
            return Integer.compare(units.size(), o.units.size());
        }
    }

    @Override
    public String toString() {
        ArrayList<Unit> output = new ArrayList<>(units);
        if (output.size() > 1) {
            for (Unit unit : units) {
                if (unit.getConstantFactor().getIntValue() == 0
                        || (unit.sizeOfIndexFactors() >= 1
                        && unit.getIndexFactors(0).toString().equals("*0"))) {
                    output.remove(unit);
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < output.size(); i++) {
            if (output.get(i).getConstantFactor().compareTo(ZERO) >= 0) {
                sb.append(output.get(i).toString());
                output.remove(i);
                break;
            }
        }

        for (Unit unit : output) {
            if (unit.getConstantFactor().compareTo(ZERO) < 0) {
                sb.append(unit);
            } else {
                sb.append("+");
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    protected boolean isFactor() {
        if (units.size() == 1) {
            Unit unit = units.get(0);
            if (unit.indexToString().equals("")) {
                return true;
            } else {
                if (unit.getConstantFactor().getIntValue() == 1 && unit.sizeOfIndexFactors() == 1) {
                    if (unit.getIndexFactors(0).getFactorKind().equals(FactorKind.X)
                            || unit.getIndexFactors(0).getFactorKind().equals(FactorKind.Y)
                            || unit.getIndexFactors(0).getFactorKind().equals(FactorKind.Z)) {
                        return unit.getIndexFactors(0).getIndex() != 2;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
