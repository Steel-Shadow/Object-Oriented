import java.math.BigInteger;

public class ExprFactor implements Factor {
    private final Expr expression;
    private final BigInteger num;

    public ExprFactor(Expr expression, BigInteger num) {
        this.num = num;
        this.expression = expression;
    }

    public Poly toPoly() {
        return expression.toPoly().powPoly(num);
    }
}
