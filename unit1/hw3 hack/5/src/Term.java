import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Term implements Factor {
    private String symbol;
    private ArrayList<Factor> factors;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ArrayList<Factor> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<Factor> factors) {
        this.factors = factors;
    }

    public Term() {
        this.factors = new ArrayList<>();
    }

    @Override
    public Polynomial toPoly(int index) {
        Monomial base = new Monomial(new BigInteger("1"), new ArrayList<>(),
                new HashMap<>(), new ArrayList<>());
        Polynomial result = new Polynomial();
        result.getMonomials().add(base);
        for (Factor factor1 : this.factors) {
            Polynomial polynomial;
            if (factor1 instanceof ExprFac) {
                polynomial = factor1.toPoly(((ExprFac) factor1).getIndex());
            } else {
                polynomial = factor1.toPoly(1);
            }
            Polynomial temp = new Polynomial();
            for (Monomial mono1 : result.getMonomials()) {
                for (Monomial momo2 : polynomial.getMonomials()) {
                    Multi multi = new Multi();
                    Monomial calResult = multi.monomult(mono1, momo2);
                    int flag = 0;
                    for (Monomial mono3 : temp.getMonomials()) {
                        if (mono3.like(calResult)) {
                            mono3.setCoefficient(mono3.getCoefficient()
                                    .add(calResult.getCoefficient()));
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        temp.getMonomials().add(calResult);
                    }
                }
            }
            result.setMonomials(new ArrayList<>());
            result.getMonomials().addAll(temp.getMonomials());
        }
        return result;
    }
}
