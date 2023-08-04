package node;

public abstract class TriFuncNode implements Node {
    private final Node content;

    public TriFuncNode(Node content) {
        this.content = content;
    }

    public Node getContent() {
        return content;
    }
}