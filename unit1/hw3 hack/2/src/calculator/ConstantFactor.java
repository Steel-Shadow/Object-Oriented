package calculator;

import java.math.BigInteger;

public final class ConstantFactor extends Factor implements Comparable<ConstantFactor> {
    private final BigInteger value;

    public static final ConstantFactor ZERO = new ConstantFactor(BigInteger.ZERO);

    public static final ConstantFactor ONE = new ConstantFactor(BigInteger.ONE);

    public static final ConstantFactor NEGATIVE_ONE = new ConstantFactor(new BigInteger("-1"));

    public ConstantFactor(BigInteger value) {
        super(FactorKind.CONSTANT);
        this.value = value;
    }

    public ConstantFactor(int value) {
        this(new BigInteger(String.valueOf(value)));
    }

    public boolean isZero() {
        return value.equals(BigInteger.ZERO);
    }

    public boolean isNegative() {
        return value.compareTo(BigInteger.ZERO) < 0;
    }

    public ConstantFactor multi(ConstantFactor constantFactor) {
        return new ConstantFactor(value.multiply(constantFactor.value));
    }

    public ConstantFactor add(ConstantFactor constantFactor) {
        return new ConstantFactor(value.add(constantFactor.value));
    }

    public ConstantFactor negate() {
        return new ConstantFactor(value.negate());
    }

    public int getIntValue() {
        return value.intValue();
    }

    @Override
    public Factor pow(int index) {
        return new ConstantFactor(value.pow(index));
    }

    @Override
    public Vector differentiate(char var) {
        return new Vector(ZERO);
    }

    @Override
    public int compareTo(ConstantFactor o) {
        return value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
