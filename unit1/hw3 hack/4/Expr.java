import java.util.ArrayList;

public class Expr implements Factor {
    private final ArrayList<Term> terms;

    public Expr() {
        this.terms = new ArrayList<>();
    }

    public void addTerm(Term term) {
        this.terms.add(term);
    }

    public Poly toPoly() {
        Poly basicPoly = new Poly(terms.get(0).toPoly().getUnitlist());
        for (int i = 1; i < terms.size(); i++) {
            basicPoly = basicPoly.addPoly(terms.get(i).toPoly());
        }
        return basicPoly;
    }
}
