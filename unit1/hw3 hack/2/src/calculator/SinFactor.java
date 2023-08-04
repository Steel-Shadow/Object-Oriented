package calculator;

public final class SinFactor extends TriFuncFactor {
    public SinFactor(int index, Vector vector) {
        super(FactorKind.SIN, index, vector);
    }

    public SinFactor(Vector vector) {
        super(FactorKind.SIN, 1, vector);
    }

    @Override
    public Factor pow(int index) {
        return new SinFactor(getIndex() * index, getContent());
    }

    @Override
    public IndexFactor multi(IndexFactor indexFactor) {
        return new SinFactor(getIndex() + indexFactor.getIndex(), getContent());
    }

    @Override
    public Vector differentiate(char var) {
        return new Vector(new ConstantFactor(getIndex()))
                .multi(new Vector(new SinFactor(1, getContent()))
                        .pow(new Vector(new ConstantFactor(getIndex() - 1))))
                .multi(new Vector(new CosFactor(getContent())))
                .multi(getContent().differentiate(var));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getContent().isZero()) {
            sb.append("*0");
        } else {
            sb.append("*sin(");
            if (!getContent().isFactor()) {
                sb.append("(");
            }
            sb.append(getContent().toString());
            if (!getContent().isFactor()) {
                sb.append(")");
            }
            sb.append(")");
            if (getIndex() > 1) {
                sb.append("**");
                sb.append(getIndex());
            }
        }
        return sb.toString();
    }
}
