package node;

public abstract class OperatorNode implements Node {
    private final Node left;
    private final Node right;

    public OperatorNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
