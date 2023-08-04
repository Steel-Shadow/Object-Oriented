package node;

import calculator.Vector;

public class MultiNode extends OperatorNode {
    public MultiNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Vector calc() {
        return getLeft().calc().multi(getRight().calc());
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new MultiNode(getLeft().substitute(xnode, ynode, znode),
                getRight().substitute(xnode, ynode, znode));
    }
}
