package node;

import calculator.Vector;

public interface Node {
    Vector calc();

    Node substitute(Node xnode, Node ynode, Node znode);
}
