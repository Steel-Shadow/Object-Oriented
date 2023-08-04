package node;

import calculator.CosFactor;
import calculator.Vector;

public class CosNode extends TriFuncNode {
    public CosNode(Node content) {
        super(content);
    }

    @Override
    public Vector calc() {
        return new Vector(new CosFactor(getContent().calc()));
    }

    @Override
    public Node substitute(Node xnode, Node ynode, Node znode) {
        return new CosNode(getContent().substitute(xnode, ynode, znode));
    }
}