import java.math.BigInteger;

public class Parser {
    private Lexer input;

    public Parser(Lexer lexer) {
        input = lexer;
    }

    public Op parseOp(int l, int r, boolean f) {
        if (l > r) {
            return new NumOp(new BigInteger("0"));
        }
        if (l == r) {
            Token tmp = input.getToken(l);
            if (tmp.getName().equals("variable")) {
                if (f) {
                    return new PlaceHolderOp(tmp.getCharacter());
                } else {
                    return new VarOp(tmp.getCharacter());
                }
            }
            if (tmp.getName().equals("number")) {
                return new NumOp(tmp.getValue());
            }
        }
        int pos = findAddOrSub(l, r);
        if (pos != -1) {
            if (input.getToken(pos).getName().equals("+")) {
                return new AddOp(parseOp(l, pos - 1, f),
                        parseOp(pos + 1, r, f));
            } else {
                return new SubOp(parseOp(l, pos - 1, f),
                        parseOp(pos + 1, r, f));
            }
        }
        pos = findMul(l, r);
        if (pos != -1) {
            return new MulOp(parseOp(l, pos - 1, f), parseOp(pos + 1, r, f));
        }
        pos = findPow(l, r);
        if (pos != -1) {
            return new PowOp(parseOp(l, pos - 1, f), parseOp(pos + 1, r, f));
        }
        if (input.getToken(l).getName().equals("(") && input.getToken(r).getName().equals(")")) {
            return new ExpOp(parseOp(l + 1, r - 1, f));
        }
        if (input.getToken(l).getName().equals("sin")) {
            return new SinOp(parseOp(l + 2, r - 1, f));
        }
        if (input.getToken(l).getName().equals("cos")) {
            return new CosOp(parseOp(l + 2, r - 1, f));
        }
        if (input.getToken(l).getName().equals("dx")) {
            return new DxOp(parseOp(l + 2, r - 1, f));
        }
        if (input.getToken(l).getName().equals("dy")) {
            return new DyOp(parseOp(l + 2, r - 1, f));
        }
        if (input.getToken(l).getName().equals("dz")) {
            return new DzOp(parseOp(l + 2, r - 1, f));
        }
        return parseSelfDefineFunction(l, r, f);
    }

    public Op parseSelfDefineFunction(int l, int r, boolean f) {
        char name = input.getToken(l).getCharacter();
        int last = l + 2;
        int top = 0;
        FunOp ans = new FunOp(name);
        for (int i = l + 2; i <= r - 1; i++) {
            if (input.getToken(i).getName().equals("(")) {
                top++;
            }
            if (input.getToken(i).getName().equals(")")) {
                top--;
            }
            if (input.getToken(i).getName().equals(",") && top == 0) {
                ans.addExpr(parseOp(last, i - 1, f));
                last = i + 1;
            }
        }
        ans.addExpr(parseOp(last, r - 1, f));
        return ans;
    }

    int findAddOrSub(int l, int r) {
        int pos = -1;
        int top = 0;
        for (int i = l; i <= r; i++) {
            Token tmp = input.getToken(i);
            if (tmp.getName().equals("(")) {
                top++;
            }
            if (tmp.getName().equals(")")) {
                top--;
            }
            if (top == 0 && (tmp.getName().equals("+") || tmp.getName().equals("-"))) {
                if (i - 1 >= l &&
                    (!input.getToken(i - 1).getName().equals("**")) &&
                    (!input.getToken(i - 1).getName().equals("*"))) {
                    pos = i;
                }
                if (i == l) {
                    pos = i;
                }
            }
        }
        return pos;
    }

    int findMul(int l, int r) {
        int pos = -1;
        int top = 0;
        for (int i = l; i <= r; i++) {
            Token tmp = input.getToken(i);
            if (tmp.getName().equals("(")) {
                top++;
            }
            if (tmp.getName().equals(")")) {
                top--;
            }
            if (top == 0 && tmp.getName().equals("*")) {
                pos = i;
            }
        }
        return pos;
    }

    int findPow(int l, int r) {
        int pos = -1;
        int top = 0;
        for (int i = r; i >= l; i--) {
            Token tmp = input.getToken(i);
            if (tmp.getName().equals(")")) {
                top++;
            }
            if (tmp.getName().equals("(")) {
                top--;
            }
            if (top == 0 && tmp.getName().equals("**")) {
                pos = i;
            }
        }
        return pos;
    }
}
