import node.AdditionNode;
import node.CosNode;
import node.DerivativeNode;
import node.DigitNode;
import node.MultiNode;
import node.Node;
import node.PowNode;
import node.SinNode;
import node.SubtractNode;
import node.VarNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final Lexer lexer;
    private final HashMap<Character, CustomizedFunc> customizedFuncMap;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        customizedFuncMap = new HashMap<>();
    }

    public Parser(Lexer lexer, HashMap<Character, CustomizedFunc> customizedFuncMap) {
        this.lexer = lexer;
        this.customizedFuncMap = customizedFuncMap;
    }

    public CustomizedFunc parseCustomisedFunc() {
        char nameTmp = lexer.getToken().charAt(0);
        lexer.next();
        ArrayList<Character> varList = new ArrayList<>();
        char name2 = nameTmp;
        do {
            lexer.next(); // "(" or ","
            varList.add(lexer.getToken().charAt(0));
            lexer.next(); // 'x'
        } while (lexer.getToken().equals(","));
        char name3 = name2;
        lexer.next();
        lexer.next();
        Node root = parseExpr();
        char name = name3;
        return new CustomizedFunc(name, root, varList);
    }

    public Node parseExpr() {
        boolean flag;
        switch (lexer.getToken()) {
            case "+":
                flag = false;
                lexer.next();
                break;
            case "-":
                flag = true;
                lexer.next();
                break;
            default:
                flag = false;
        }
        Node node = parseTerm();
        if (flag) {
            node = new SubtractNode(new DigitNode(BigInteger.ZERO), node);
        }
        while (lexer.getToken().equals("+") || lexer.getToken().equals("-")) {
            if (lexer.getToken().equals("+")) {
                lexer.next();
                node = new AdditionNode(node, parseTerm());
            } else {
                lexer.next();
                node = new SubtractNode(node, parseTerm());
            }
            if (!lexer.hasNext()) {
                break;
            }
        }
        return node;
    }

    public Node parseTerm() {
        boolean flag;
        switch (lexer.getToken()) {
            case "+":
                flag = false;
                lexer.next();
                break;
            case "-":
                flag = true;
                lexer.next();
                break;
            default:
                flag = false;
        }

        Node node = parseFactor();
        if (flag) {
            node = new SubtractNode(new DigitNode(BigInteger.ZERO), node);
        }
        while (lexer.getToken().equals("*")) {
            if (lexer.getToken().equals("*")) {
                lexer.next();
                node = new MultiNode(node, parseFactor());
            }
            if (!lexer.hasNext()) {
                break;
            }
        }
        return node;
    }

    public Node parseFactor() {
        Node node;
        node = parsePowerFactor();
        if (node != null) {
            return node;
        }

        node = parseDigitFactor();
        if (node != null) {
            return node;
        }

        node = parseExpressionFactor();
        if (node != null) {
            return node;
        }

        node = parseTriFuncFactor();
        if (node != null) {
            return node;
        }

        node = parseDerivativeFactor();
        if (node != null) {
            return node;
        }

        node = parseCustomizedFactor();
        return node;
    }

    private Node parsePowerFactor() {
        if (lexer.getToken().equals("x")
            || lexer.getToken().equals("y")
            || lexer.getToken().equals("z")) {
            Node node = new VarNode(lexer.getToken().charAt(0));
            lexer.next();
            Node index = parseIndex();
            if (index != null) {
                return new PowNode(node, index);
            } else {
                return node;
            }
        }
        return null;
    }

    private Node parseDigitFactor() {
        if (lexer.getToken().equals("+")) {
            lexer.next();
            Node node = new DigitNode(new BigInteger(lexer.getToken()));
            lexer.next();
            return node;
        }
        if (lexer.getToken().equals("-")) {
            lexer.next();
            Node node = new DigitNode(new BigInteger(lexer.getToken()).negate());
            lexer.next();

            return node;
        }
        if (Character.isDigit(lexer.getToken().charAt(0))) {
            Node node = new DigitNode(new BigInteger(lexer.getToken()));
            lexer.next();

            return node;
        }
        return null;
    }

    private Node parseExpressionFactor() {
        if (lexer.getToken().equals("(")) {
            lexer.next();
            Node node = parseExpr();
            lexer.next();                                   //remove ")"
            Node index = parseIndex();
            if (index != null) {
                return new PowNode(node, index);
            } else {
                return node;
            }
        }
        return null;
    }

    private Node parseTriFuncFactor() {
        if (lexer.getToken().equals("sin")) {
            lexer.next();//pass "("
            lexer.next();
            Node node = new SinNode(parseFactor());
            lexer.next();//pass ")"
            Node index = parseIndex();
            if (index != null) {
                return new PowNode(node, index);
            }
            return node;
        } else if (lexer.getToken().equals("cos")) {
            lexer.next();//pass "("
            lexer.next();
            Node node = new CosNode(parseFactor());
            lexer.next();//pass ")"
            Node index = parseIndex();
            if (index != null) {
                return new PowNode(node, index);
            }
            return node;
        }
        return null;
    }

    private Node parseCustomizedFactor() {
        if (lexer.getToken().equals("f")
                || lexer.getToken().equals("g")
                || lexer.getToken().equals("h")) {
            CustomizedFunc customizedFunc1 = customizedFuncMap.get(lexer.getToken().charAt(0));
            lexer.next(); // "f"\
            ArrayList<Node> nodeList = new ArrayList<>();
            CustomizedFunc customizedFunc2 = customizedFunc1;
            do {
                lexer.next(); // "(" or ",
                nodeList.add(parseFactor());
            } while (lexer.getToken().equals(","));
            CustomizedFunc customizedFunc = customizedFunc2;
            lexer.next();
            return customizedFunc.substitute(nodeList);
        }
        return null;
    }

    private Node parseDerivativeFactor() {
        Node node;
        switch (lexer.getToken()) {
            case "dx":
                lexer.next();
                lexer.next();
                node = new DerivativeNode(parseExpr(), 'x');
                lexer.next();
                return node;
            case "dy":
                lexer.next();
                lexer.next();
                node = new DerivativeNode(parseExpr(), 'y');
                lexer.next();
                return node;
            case "dz":
                lexer.next();
                lexer.next();
                node = new DerivativeNode(parseExpr(), 'z');
                lexer.next();
                return node;
            default:
                return null;
        }
    }

    private Node parseIndex() {
        if (lexer.getToken().equals("**")) {
            lexer.next();
            if (lexer.getToken().equals("+")) {
                lexer.next();
            }
            Node node = new DigitNode(new BigInteger(lexer.getToken()));
            lexer.next();
            return node;
        }
        return null;
    }
}
