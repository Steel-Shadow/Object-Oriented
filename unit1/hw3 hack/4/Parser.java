import java.math.BigInteger;
import java.util.ArrayList;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        expr.addTerm(parseTerm());
        while (lexer.peek().equals("+") | lexer.peek().equals("-")) {
            expr.addTerm(parseTerm());
        }
        return expr;
    }

    public Term parseTerm() {
        Term term = new Term();
        Boolean termSign = lexer.ensureSign();
        termSign = termSign == lexer.ensureSign();
        term.addFactor(parseFactor());
        while (lexer.peek().equals("*")) {
            lexer.next();
            termSign = termSign == lexer.ensureSign();
            term.addFactor(parseFactor());
        }
        term.setSign(termSign);
        return term;
    }

    public Factor parseFactor() {
        if (lexer.peek().equals("(")) {
            lexer.next();
            Expr expr = parseExpr();
            lexer.next();
            return new ExprFactor(expr,lexer.ensureExp());
        }
        else if (lexer.peek().equals("x") | lexer.peek().equals("y") | lexer.peek().equals("z")) {
            PowFactor powFactor = new PowFactor();
            String a = lexer.peek();
            lexer.next();
            switch (a) {
                case "x" :
                    powFactor.setExpX(lexer.ensureExp());
                    break;
                case "y" :
                    powFactor.setExpY(lexer.ensureExp());
                    break;
                case "z" :
                    powFactor.setExpZ(lexer.ensureExp());
                    break;
                default:
            }
            return powFactor;
        }
        else if (lexer.peek().equals("f") | lexer.peek().equals("g") | lexer.peek().equals("h")) {
            return parseFunc();
        }
        else if (lexer.peek().equals("s") | lexer.peek().equals("c")) {
            return parseSc();
        }
        else if (lexer.peek().equals("d")) {
            return parseDri();
        }
        else {
            NumFactor numFactor = new NumFactor();
            boolean sign = lexer.ensureSign();
            BigInteger num = new BigInteger(lexer.peek());
            numFactor.setNum(lexer.turn(sign, num));
            lexer.next();
            return numFactor;
        }
    }

    public Factor parseSc() {
        if (lexer.peek().equals("s")) {
            lexer.next();
            lexer.next();
            Expr expr = parseExpr();
            lexer.next();
            SinFactor sinFactor = new SinFactor();
            sinFactor.setExpression(expr,lexer.ensureExp());
            return sinFactor;
        }
        else {
            lexer.next();
            lexer.next();
            Expr expr = parseExpr();
            lexer.next();
            CosFactor cosFactor = new CosFactor();
            cosFactor.setExpression(expr,lexer.ensureExp());
            return cosFactor;
        }
    }

    public Factor parseFunc() {
        StringBuilder content = new StringBuilder();
        final String funcName = lexer.peek();
        content.append(lexer.peek());//读f
        lexer.next();
        content.append(lexer.peek());   //读（
        lexer.next();
        int stack = 1;
        //获取自定义函数字符串
        while (stack != 0) {
            content.append(lexer.peek());
            if (lexer.peek().equals("(")) {
                stack++;
            }
            else if (lexer.peek().equals(")")) {
                stack--;
            }
            lexer.next();
        }
        //根据字符串得到实参list
        ArrayList<String> actualPara = Definer.readFunc(content.toString());
        Lexer lexer2 = new Lexer(Definer.callFunc(funcName, actualPara));
        Parser parser2 = new Parser(lexer2);
        return parser2.parseExpr();
    }

    public Factor parseDri() {
        lexer.next();
        StringBuilder content = new StringBuilder();
        final String op = lexer.peek();
        lexer.next();
        content.append(lexer.peek());   //读（
        lexer.next();
        int stack = 1;
        //获取自定义函数字符串
        while (stack != 0) {
            content.append(lexer.peek());
            if (lexer.peek().equals("(")) {
                stack++;
            }
            else if (lexer.peek().equals(")")) {
                stack--;
            }
            lexer.next();
        }
        return new Derivation(content.toString(), op);
    }

}
