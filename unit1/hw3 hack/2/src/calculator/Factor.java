package calculator;

public abstract class Factor {
    private final FactorKind factorKind;

    public Factor(FactorKind factorKind) {
        this.factorKind = factorKind;
    }

    public abstract Factor pow(int index);

    public abstract Vector differentiate(char var);

    public FactorKind getFactorKind() {
        return factorKind;
    }
}
