package node;

import calculator.Vector;

public class AdditionNode extends OperatorNode {
    public AdditionNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Vector calc() {
        return getLeft().calc().add(getRight().calc());
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new AdditionNode(getLeft().substitute(xnode, ynode, znode),
                getRight().substitute(xnode, ynode, znode));
    }
}
