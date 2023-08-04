import java.math.BigInteger;
import java.util.ArrayList;

public class SinFactor implements Factor {
    private Expr expression;
    private BigInteger num;

    public void setExpression(Expr expression, BigInteger num) {
        this.num = num;
        this.expression = expression;
    }

    public Poly toPoly() {
        Unit unit = new Unit();
        unit.getSinMap().put(expression.toPoly(),num);
        unit.setCoe(BigInteger.ONE);
        ArrayList<Unit> unitlist = new ArrayList<>();
        unitlist.add(unit);
        return new Poly(unitlist);
    }

}
