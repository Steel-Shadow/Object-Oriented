import node.Node;

import java.util.ArrayList;

public class CustomizedFunc {
    private final char name;
    private final Node root;
    private final ArrayList<Character> varList;

    public CustomizedFunc(char name, Node root, ArrayList<Character> varList) {
        this.name = name;

        //this.root = root;
        String preInput = root.calc().toString();
        Lexer preLexer = new Lexer(preInput);
        Parser preParser = new Parser(preLexer);
        this.root = preParser.parseExpr();

        this.varList = varList;
    }

    public Node substitute(ArrayList<Node> nodeList) {
        Node xnode = null;
        Node ynode = null;
        Node znode = null;
        for (int i = 0;i < varList.size();i++) {
            switch (varList.get(i)) {
                case 'x':
                    xnode = nodeList.get(i);
                    break;
                case 'y':
                    ynode = nodeList.get(i);
                    break;
                case 'z':
                    znode = nodeList.get(i);
                    break;
                default: break;
            }
        }
        return root.substitute(xnode, ynode, znode);
    }

    public char getName() {
        return name;
    }
}
