package node;

import calculator.Vector;

public class PowNode extends OperatorNode {
    public PowNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Vector calc() {
        return getLeft().calc().pow(getRight().calc());
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new PowNode(getLeft().substitute(xnode, ynode, znode),
                getRight().substitute(xnode, ynode, znode));
    }
}
