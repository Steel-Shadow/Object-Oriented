package node;

import calculator.SinFactor;
import calculator.Vector;

public class SinNode extends TriFuncNode {
    public SinNode(Node content) {
        super(content);
    }

    @Override
    public Vector calc() {
        return new Vector(new SinFactor(getContent().calc()));
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new SinNode(getContent().substitute(xnode, ynode, znode));
    }
}
