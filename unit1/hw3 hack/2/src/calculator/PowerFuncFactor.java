package calculator;

public final class PowerFuncFactor extends IndexFactor {
    public PowerFuncFactor(char var, int index) {
        super(transToFactorKind(var), index);
    }

    public PowerFuncFactor(char var) {
        super(transToFactorKind(var), 1);
    }

    private PowerFuncFactor(FactorKind factorKind, int index) {
        super(factorKind, index);
    }

    private static FactorKind transToFactorKind(char var) {
        switch (var) {
            case 'x': return FactorKind.X;
            case 'y': return FactorKind.Y;
            default: return FactorKind.Z;
        }
    }

    @Override
    public Factor pow(int index) {
        return new PowerFuncFactor(getFactorKind(), getIndex() * index);
    }

    @Override
    public IndexFactor multi(IndexFactor indexFactor) {
        return new PowerFuncFactor(getFactorKind(), getIndex() + indexFactor.getIndex());
    }

    @Override
    protected int compare(IndexFactor o) {
        return Integer.compare(getIndex(), o.getIndex());
    }

    @Override
    public Vector differentiate(char var) {
        if (getFactorKind() == transToFactorKind(var)) {
            return new Vector(new ConstantFactor(getIndex())).multi(new Vector(
                    new PowerFuncFactor(getFactorKind(), 1)).
                        pow(new Vector(new ConstantFactor(getIndex() - 1))));
        } else {
            return new Vector(ConstantFactor.ZERO);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (getIndex()) {
            case 1:
                sb.append("*");
                sb.append(getChar());
                return sb.toString();
            case 2:
                sb.append("*");
                sb.append(getChar());
                sb.append("*");
                sb.append(getChar());
                return sb.toString();
            default:
                sb.append("*");
                sb.append(getChar());
                sb.append("**");
                sb.append(getIndex());
                return sb.toString();
        }
    }

    private char getChar() {
        char c = '0';
        switch (getFactorKind()) {
            case X:
                c = 'x';
                break;
            case Y:
                c = 'y';
                break;
            case Z:
                c = 'z';
                break;
            default: break;
        }
        return c;
    }
}
