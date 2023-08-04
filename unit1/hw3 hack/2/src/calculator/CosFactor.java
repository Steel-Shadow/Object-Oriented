package calculator;

public final class CosFactor extends TriFuncFactor {
    public CosFactor(int index, Vector vector) {
        super(FactorKind.COS, index, vector);
    }

    public CosFactor(Vector vector) {
        super(FactorKind.COS, 1, vector);
    }

    @Override
    public Factor pow(int index) {
        return new CosFactor(getIndex() * index, getContent());
    }

    @Override
    public IndexFactor multi(IndexFactor indexFactor) {
        return new CosFactor(getIndex() + indexFactor.getIndex(), getContent());
    }

    @Override
    public Vector differentiate(char var) {
        return new Vector(new ConstantFactor(getIndex()))
                .multi(new Vector(new CosFactor(1, getContent()))
                        .pow(new Vector(new ConstantFactor(getIndex() - 1))))
                .multi(new Vector(ConstantFactor.NEGATIVE_ONE))
                .multi(new Vector(new SinFactor(getContent())))
                .multi(getContent().differentiate(var));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getContent().isZero()) {
            sb.append("*1");
        } else {
            sb.append("*cos(");
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
