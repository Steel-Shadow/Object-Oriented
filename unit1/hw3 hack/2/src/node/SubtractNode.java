package node;

import calculator.Vector;

public class SubtractNode extends OperatorNode {
    public SubtractNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Vector calc() {
        return getLeft().calc().subtract(getRight().calc());
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new SubtractNode(getLeft().substitute(xnode, ynode, znode),
                getRight().substitute(xnode, ynode, znode));
    }
}
