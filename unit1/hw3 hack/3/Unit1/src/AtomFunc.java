
public class AtomFunc implements Comparable {
    private Poly poly;
    private String type;

    public Poly getPoly() {
        return poly;
    }

    public AtomFunc(String type, Poly poly) {
        this.type = type;
        this.poly = poly;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type.equals("sin") || type.equals("cos")) {
            return type + "((" + poly.toString() + "))";
        } else {
            return type;
        }
    }

    @Override
    public int compareTo(Object o) {
        AtomFunc other = (AtomFunc) o;
        if (!getType().equals(other.getType())) {
            return getType().compareTo(other.getType());
        }
        if (poly == null) {
            return getType().compareTo(other.getType());
        }
        return poly.compareTo(other.poly);
    }

    @Override
    public boolean equals(Object o) {
        AtomFunc other = (AtomFunc) o;
        if (!getType().equals(other.getType())) {
            return false;
        }
        if (poly != null) {
            return poly.equals(other.poly);
        }
        return true;
    }

    public Poly derivative(char target) {
        if (poly == null) {
            if (getType().charAt(0) == target) {
                return new Poly(1);
            }
            return new Poly();
        } else {
            Poly inner = poly.derivative(target);
            Unit unit = new Unit(getType().equals("sin") ? 1 : -1);
            AtomFunc atomFunc = new AtomFunc(getType().equals("sin") ? "cos" : "sin", poly);
            unit.add(atomFunc);
            Poly outer = new Poly(unit);
            return Poly.multiply(inner, outer);
        }
    }
}
