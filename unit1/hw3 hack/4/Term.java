import java.util.ArrayList;

public class Term implements Factor {
    private final ArrayList<Factor> factors;
    private Boolean sign = true;

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public Term() {
        this.factors = new ArrayList<>();
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }

    public Poly toPoly() {
        Poly basicPoly = new Poly(factors.get(0).toPoly().getUnitlist());
        for (int i = 1; i < factors.size(); i++) {
            basicPoly = basicPoly.mulPoly(factors.get(i).toPoly());
        }
        return sign ? basicPoly : basicPoly.negPloy(basicPoly);
    }
}
