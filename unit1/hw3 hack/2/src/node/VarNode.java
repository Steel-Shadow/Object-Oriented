package node;

import calculator.PowerFuncFactor;
import calculator.Vector;

public final class VarNode extends OperandNode {
    private final char value;

    public VarNode(char value) {
        this.value = value;
    }

    @Override
    public Vector calc() {
        return new Vector(new PowerFuncFactor(value));
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        switch (value) {
            case 'x': return xnode;
            case 'y': return ynode;
            case 'z': return znode;
            default: return this;
        }
    }
}
