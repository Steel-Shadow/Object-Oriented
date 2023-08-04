import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Poly implements Comparable {
    private ArrayList<Unit> units = new ArrayList<>();

    public Poly() {

    }

    public Poly(Unit unit) {
        units.add(unit);
    }

    public Poly(AtomFunc func) {
        units.add(new Unit(func));
    }

    public Poly(Power power) {
        units.add(new Unit(power));
    }

    public Poly(int val) {
        units.add(new Unit(val));
    }

    public void flip() {
        for (int i = 0; i < units.size(); i++) {
            units.set(i, units.get(i).flip());
        }
    }

    void simplify() {
        ArrayList<Unit> ans = new ArrayList<>();

        for (int l = 0, r = 0; l < units.size(); l = r + 1) {
            r = l;
            BigInteger sum = units.get(l).getCof();
            Unit now = units.get(l);
            while (r + 1 < units.size() && now.partialEquals(units.get(r + 1))) {
                Unit nxt = units.get(r + 1);
                r++;
                sum = sum.add(nxt.getCof());
            }
            if (!sum.equals(new BigInteger("0"))) {
                ans.add(new Unit(sum, now.getPowers()));
            }
        }
        units = ans;
    }

    public void add(Unit unit) {
        units.add(unit);
    }

    public static Poly add(Poly a, Poly b) {
        Poly c = new Poly();
        c.units.addAll(a.units);
        c.units.addAll(b.units);
        Collections.sort(c.units);
        c.simplify();
        return c;
    }

    public static Poly multiply(Poly a, Poly b) {
        Poly c = new Poly();
        for (int i = 0; i < a.units.size(); i++) {
            for (int j = 0; j < b.units.size(); j++) {
                Unit tmp = Unit.multiply(a.units.get(i), b.units.get(j));
                c.units.add(tmp);
            }
        }
        Collections.sort(c.units);
        c.simplify();
        return c;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (units.size() >= 1) {
            buffer.append(units.get(0).toString());
        } else {
            return "0";
        }
        for (int i = 1; i < units.size(); i++) {
            if (units.get(i).isPositive()) {
                buffer.append('+');
                buffer.append(units.get(i).toString());
            }
            if (units.get(i).isNegative()) {
                buffer.append(units.get(i).toString());
            }
        }
        return new String(buffer);
    }

    int getConstant() {
        if (units.size() == 0) {
            return 0;
        }
        Unit tmp = units.get(0);
        return tmp.getCof().intValue();
    }

    @Override
    public int compareTo(Object o) {
        Poly other = (Poly) o;
        if (units.size() != other.units.size()) {
            return units.size() - other.units.size();
        }
        for (int i = 0; i < units.size(); i++) {
            int flag = units.get(i).compareTo(other.units.get(i));
            if (flag != 0) {
                return flag;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        Poly other = (Poly) o;
        if (units.size() != other.units.size()) {
            return false;
        }
        for (int i = 0; i < units.size(); i++) {
            if (!units.get(i).equals(other.units.get(i))) {
                return false;
            }
        }
        return true;
    }

    public Poly derivative(char target) {
        Poly ans = new Poly();
        for (int i = 0; i < units.size(); i++) {
            ans = Poly.add(ans, units.get(i).derivative(target));
        }
        return ans;
    }
}
