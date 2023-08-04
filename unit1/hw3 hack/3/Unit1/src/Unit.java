import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Unit implements Comparable {
    private ArrayList<Power> powers = new ArrayList<>();
    private BigInteger cof;

    public BigInteger getCof() {
        return cof;
    }

    public Unit(int cof) {
        this.cof = new BigInteger(new Integer(cof).toString());
    }

    public Unit(Power power) {
        this.cof = new BigInteger("1");
        powers.add(power);
    }

    public Unit(BigInteger cof) {
        this.cof = cof;
    }

    public Unit(BigInteger cof, ArrayList<Power> powers) {
        this.cof = cof;
        this.powers = powers;
    }

    public Unit(BigInteger cof, Power power) {
        this.cof = cof;
        powers.add(power);
    }

    public Unit(AtomFunc func) {
        this.cof = new BigInteger("1");
        powers.add(new Power(func, new BigInteger("1")));
    }

    public ArrayList<Power> getPowers() {
        return powers;
    }

    void simplify() {
        ArrayList<Power> finalPowers = new ArrayList<>();
        for (int l = 0, r; l < powers.size(); l = r + 1) {
            r = l;
            Power power = powers.get(l);
            BigInteger sumMi = power.getMi();
            while (r + 1 < powers.size() && powers.get(r + 1).partialEquals(powers.get(l))) {
                r++;
                sumMi = sumMi.add(powers.get(r).getMi());
            }
            finalPowers.add(new Power(power.getAtomFunc(), sumMi));
        }
        powers = finalPowers;
    }

    public void add(AtomFunc func) {
        powers.add(new Power(func, new BigInteger("1")));
        simplify();
    }

    public static Unit multiply(Unit a, Unit b) {
        ArrayList<Power> powers = new ArrayList<>();
        ArrayList<Power> finalPowers = new ArrayList<>();
        powers.addAll(a.powers);
        powers.addAll(b.powers);
        Collections.sort(powers);
        for (int l = 0, r; l < powers.size(); l = r + 1) {
            r = l;
            Power power = powers.get(l);
            BigInteger sumMi = power.getMi();
            while (r + 1 < powers.size() && powers.get(r + 1).partialEquals(powers.get(l))) {
                r++;
                sumMi = sumMi.add(powers.get(r).getMi());
            }
            finalPowers.add(new Power(power.getAtomFunc(), sumMi));
        }
        BigInteger newCof = a.getCof().multiply(b.getCof());
        return new Unit(newCof, finalPowers);
    }

    public Unit flip() {
        return new Unit(cof.multiply(new BigInteger("-1")), powers);
    }

    public boolean isPositive() {
        return cof.compareTo(new BigInteger("0")) > 0;
    }

    public boolean isNegative() {
        return cof.compareTo(new BigInteger("0")) < 0;
    }

    private boolean isZero(BigInteger x) {
        return x.compareTo(new BigInteger("0")) == 0;
    }

    private boolean isOne(BigInteger x) {
        return x.compareTo(new BigInteger("1")) == 0;
    }

    private boolean absIsOne(BigInteger x) {
        return x.compareTo(new BigInteger("1")) == 0 || x.compareTo(new BigInteger("-1")) == 0;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (isZero(cof)) { return "0"; }
        if (powers.size() == 0) {
            buffer.append(cof.toString());
            return new String(buffer);
        }
        boolean front = false;
        if (!absIsOne(cof)) {
            buffer.append(cof.toString());
            front = true;
        } else { if (isNegative()) { buffer.append('-'); } }
        for (Power power : powers) {
            if (front) {
                buffer.append('*');
            }
            front = true;
            buffer.append(power.toString());
        }
        return new String(buffer);
    }

    @Override
    public boolean equals(Object o) {
        Unit other = (Unit) o;
        if (!getCof().equals(other.getCof())) { return false; }
        if (powers.size() != other.powers.size()) { return false; }
        for (int i = 0; i < powers.size(); i++) {
            if (!powers.get(i).equals(other.powers.get(i))) { return false; }
        }
        return true;
    }

    public boolean partialEquals(Unit other) {
        if (powers.size() != other.powers.size()) { return false; }
        for (int i = 0; i < powers.size(); i++) {
            if (!powers.get(i).equals(other.powers.get(i))) { return false; }
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        Unit other = (Unit) o;
        if (powers.size() != other.powers.size()) {
            return powers.size() - other.powers.size();
        }
        for (int i = 0; i < powers.size(); i++) {
            int flag = powers.get(i).compareTo(other.powers.get(i));
            if (flag != 0) {
                return flag;
            }
        }
        return getCof().compareTo(other.getCof());
    }

    public Poly derivative(char target) {
        Poly ans = new Poly();
        for (int i = 0; i < powers.size(); i++) {
            Poly res = new Poly(1);
            for (int j = 0; j < powers.size(); j++) {
                if (j == i) {
                    continue;
                }
                res = Poly.multiply(res, new Poly(powers.get(j)));
            }
            res = Poly.multiply(res, powers.get(i).derivative(target));
            ans = Poly.add(ans, res);
        }
        ans = Poly.multiply(ans, new Poly(new Unit(getCof())));
        return ans;
    }
}
