import java.util.ArrayList;

public class Expr implements Factor {
    private ArrayList<Term> terms;

    public ArrayList<Term> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public Expr() {
        this.terms = new ArrayList<>();
    }

    @Override
    public Polynomial toPoly(int index) {
        Polynomial result = new Polynomial();
        for (Term term : this.terms) {
            if (term.getSymbol().equals("+")) {
                for (Monomial monomial : term.toPoly(1).getMonomials()) {
                    int flag = 0;
                    for (Monomial monomial1 : result.getMonomials()) {
                        if (monomial1.like(monomial)) {
                            monomial1.setCoefficient(monomial1.getCoefficient()
                                    .add(monomial.getCoefficient()));
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        result.getMonomials().add(monomial);
                    }
                }
            } else {
                for (Monomial monomial : term.toPoly(1).getMonomials()) {
                    monomial.setCoefficient(monomial.getCoefficient().negate());
                    int flag = 0;
                    for (Monomial monomial1 : result.getMonomials()) {
                        if (monomial1.like(monomial)) {
                            monomial1.setCoefficient(monomial1.getCoefficient()
                                    .add(monomial.getCoefficient()));
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        result.getMonomials().add(monomial);
                    }
                }
            }
        }
        return result;
    }
}
