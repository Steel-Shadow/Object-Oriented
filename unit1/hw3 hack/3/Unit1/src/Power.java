import java.math.BigInteger;

public class Power implements Comparable {
    private AtomFunc func;
    private BigInteger mi;

    public Power(AtomFunc func, BigInteger mi) {
        this.func = func;
        this.mi = mi;
    }

    public Poly derivative(char target) {
        if (mi.equals(new BigInteger("0"))) {
            return new Poly();
        }
        if (mi.equals(new BigInteger("1"))) {
            Poly rval = func.derivative(target);
            return rval;
        } else {
            Poly lval = new Poly(new Unit(mi, new Power(func, mi.subtract(new BigInteger("1")))));
            Poly rval = func.derivative(target);
            return Poly.multiply(lval, rval);
        }
    }

    @Override
    public int compareTo(Object o) {
        Power other = (Power) o;
        int flag = func.compareTo(other.func);
        if (flag != 0) { return flag; }
        return getMi().compareTo(other.getMi());
    }

    @Override
    public boolean equals(Object o) {
        Power other = (Power) o;
        if (!mi.equals(other.mi)) {
            return false;
        }
        return func.equals(other.func);
    }

    public boolean partialEquals(Power other) {
        return func.equals(other.func);
    }

    public BigInteger getMi() {
        return mi;
    }

    public AtomFunc getAtomFunc() {
        return func;
    }

    @Override
    public String toString() {
        if (mi.equals(new BigInteger("1"))) {
            return func.toString();
        } else {
            return func.toString() + "**" + mi.toString();
        }
    }
}
