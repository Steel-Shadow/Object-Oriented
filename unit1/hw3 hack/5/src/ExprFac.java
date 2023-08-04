import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class ExprFac implements Factor {
    private Expr expr;
    private int index = 1;

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Polynomial toPoly(int index) {
        Polynomial result;
        if (index == 1) {
            result = this.expr.toPoly(1);
        } else if (index == 0) {
            result = new Polynomial();
            Monomial temp = new Monomial(new BigInteger("1"),
                    new ArrayList<>(), new HashMap<>(), new ArrayList<>());
            result.getMonomials().add(temp);
        } else {
            result = new Polynomial();
            Monomial temp = new Monomial(new BigInteger("1"),
                    new ArrayList<>(), new HashMap<>(), new ArrayList<>());
            result.getMonomials().add(temp);
            int i = 1;
            Polynomial store = this.expr.toPoly(1);
            while (i <= index) {
                Polynomial calRes = new Polynomial();
                for (Monomial mono1 : result.getMonomials()) {
                    for (Monomial mono2 : store.getMonomials()) {
                        Multi multi = new Multi();
                        Monomial monomial = multi.monomult(mono1, mono2);
                        int flag = 0;
                        for (Monomial monomial1 : calRes.getMonomials()) {
                            if (monomial1.like(monomial)) {
                                monomial1.setCoefficient(monomial1.getCoefficient().add(
                                        monomial.getCoefficient()));
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            calRes.getMonomials().add(monomial);
                        }
                    }
                }
                result.setMonomials(new ArrayList<>());
                result.getMonomials().addAll(calRes.getMonomials());
                i++;
            }
        }
        return result;
    }
}

