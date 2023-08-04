package node;

import calculator.ConstantFactor;
import calculator.Vector;

import java.math.BigInteger;

public final class DigitNode extends OperandNode {
    private final BigInteger value;

    public DigitNode(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public Vector calc() {
        return new Vector(new ConstantFactor(value));
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return this;
    }
}
