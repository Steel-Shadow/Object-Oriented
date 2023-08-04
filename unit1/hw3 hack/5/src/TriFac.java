import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class TriFac implements Factor {
    private int type;//1 represent sin,2 represent cos
    private Expr expr;
    private int index = 1;
    private Polynomial expPoly;

    public Polynomial getExpPoly() {
        return expPoly;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TriFac() {

    }

    public TriFac(int type, int index, Polynomial expPoly) {
        this.type = type;
        this.index = index;
        Polynomial poly = new Polynomial();
        poly.getMonomials().addAll(expPoly.getMonomials());
        this.expPoly = poly;
    }

    @Override
    public Polynomial toPoly(int index) {
        Monomial mono = new Monomial();
        if (this.index == 0) {
            mono.setCoefficient(new BigInteger("1"));
            mono.setTriFacs(new ArrayList<>());
        } else {
            this.expPoly = expr.toPoly(1);
            int flag = 0;
            if (expPoly.getMonomials().size() == 1) {
                for (Monomial monomial : expPoly.getMonomials()) {
                    if (monomial.getCoefficient().equals(new BigInteger("0"))) {
                        flag = 1;
                    }
                }
            }
            if (flag == 1) {
                if (this.type == 1) {
                    mono.setCoefficient(new BigInteger("0"));
                } else {
                    mono.setCoefficient(new BigInteger("1"));
                }
                mono.setTriFacs(new ArrayList<>());
            } else {
                mono.setCoefficient(new BigInteger("1"));
                ArrayList<TriFac> triFacs = new ArrayList<>();
                triFacs.add(this);
                mono.setTriFacs(triFacs);
            }
        }
        mono.setVariables(new ArrayList<>());
        mono.setIndexTable(new HashMap<>());
        Polynomial poly = new Polynomial();
        poly.getMonomials().add(mono);
        return poly;
    }

}
