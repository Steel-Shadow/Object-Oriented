import java.math.BigInteger;

public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        Term term;
        term = parseFirTerm();
        expr.getTerms().add(term);
        while (lexer.getNow().equals("+") || lexer.getNow().equals("-")) {
            if (lexer.getNow().equals("+")) {
                lexer.next();
                term = parseTerm("+");
                expr.getTerms().add(term);
            } else {
                lexer.next();
                term = parseTerm("-");
                expr.getTerms().add(term);
            }
        }
        return expr;
    }

    public Term parseFirTerm() {
        Term term = new Term();
        Factor factor;
        if (lexer.getNow().equals("-")) {
            term.setSymbol("-");
            lexer.next();
        } else if (lexer.getNow().equals("+")) {
            term.setSymbol("+");
            lexer.next();
        } else {
            term.setSymbol("+");
        }
        factor = parseFactor();
        term.getFactors().add(factor);
        while (lexer.getNow().equals("*")) {
            lexer.next();
            factor = parseFactor();
            term.getFactors().add(factor);
        }
        return term;
    }

    public Term parseTerm(String op) {
        Term term = new Term();
        term.setSymbol(op);
        Factor factor;
        factor = parseFactor();
        term.getFactors().add(factor);
        while (lexer.getNow().equals("*")) {
            lexer.next();
            factor = parseFactor();
            term.getFactors().add(factor);
        }
        return term;
    }

    public Factor parseFactor() {
        if (lexer.getNow().equals("(")) {
            return parseExprFac();
        } else if (lexer.getNow().charAt(0) == 'd') {
            return parseDeriFac();
        } else if (lexer.getNow().equals("sin") || lexer.getNow().equals("cos")) {
            return parseTriFac();
        } else if (lexer.getNow().charAt(0) == '!' || Character.isDigit(lexer.getNow().charAt(0)) ||
                lexer.getNow().charAt(0) == '@') {
            return parseNumFac();
        } else {
            return parsePowerFac();
        }
    }

    public NumFac parseNumFac() {
        BigInteger num;
        NumFac numfac = new NumFac();
        if (lexer.getNow().charAt(0) == '!' || lexer.getNow().charAt(0) == '@') {
            num = new BigInteger(lexer.getNow().substring(1));
        } else {
            num = new BigInteger(lexer.getNow());
        }
        numfac.setNum(num);
        if (lexer.getNow().charAt(0) == '!' || Character.isDigit(lexer.getNow().charAt(0))) {
            numfac.setOp("+");
        } else {
            numfac.setOp("-");
        }
        lexer.next();
        return numfac;
    }

    public PowerFac parsePowerFac() {
        if (lexer.getNow().indexOf('^') != -1) {
            PowerFac powerfac = new PowerFac();
            powerfac.setVariable(lexer.getNow().charAt(0) + "");
            powerfac.setIndex(Integer.parseInt(lexer.getNow().substring(2)));
            if (powerfac.getIndex() == 0) {
                powerfac.setVariable("");
            }
            lexer.next();
            return powerfac;
        } else {
            PowerFac powerfac = new PowerFac();
            powerfac.setVariable(lexer.getNow().charAt(0) + "");
            lexer.next();
            return powerfac;
        }
    }

    public ExprFac parseExprFac() {
        lexer.next();
        ExprFac exprfac = new ExprFac();
        Expr expr = parseExpr();
        exprfac.setExpr(expr);
        lexer.next();
        if (lexer.getNow().equals("^")) {
            lexer.next();
            exprfac.setIndex(Integer.parseInt(lexer.getNow()));
            lexer.next();
        }
        return exprfac;
    }

    public TriFac parseTriFac() {
        TriFac triFac = new TriFac();
        if (lexer.getNow().equals("sin")) {
            triFac.setType(1);
        } else {
            triFac.setType(2);
        }
        lexer.next();
        lexer.next();
        Expr expr = parseExpr();
        triFac.setExpr(expr);
        lexer.next();
        if (lexer.getNow().equals("^")) {
            lexer.next();
            triFac.setIndex(Integer.parseInt(lexer.getNow()));
            lexer.next();
        }
        return triFac;
    }

    public DeriFac parseDeriFac() {
        DeriFac deriFac = new DeriFac();
        deriFac.setAimVar(lexer.getNow().charAt(1) + "");
        Lexer lexer1 = new Lexer(lexer.getNow().substring(3, lexer.getNow().length() - 1));
        Parser parser = new Parser(lexer1);
        Expr expr = parser.parseExpr();
        deriFac.setTarExpr(expr);
        deriFac.setTarPoly(expr.toPoly(1));
        lexer.next();
        return deriFac;
    }
}
