package node;

import calculator.Vector;

public class DerivativeNode implements Node {
    private final Node content;
    private final char var;

    public DerivativeNode(Node content, char var) {
        this.content = content;
        this.var = var;
    }

    @Override
    public Vector calc() {
        return content.calc().differentiate(var);
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return content.substitute(xnode, ynode, znode);
    }
}
