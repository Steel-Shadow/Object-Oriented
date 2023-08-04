import java.math.BigInteger;
import java.util.ArrayList;

public class NumFactor implements Factor {
    private BigInteger num = BigInteger.ZERO;

    public void setNum(BigInteger num) {
        this.num = num;
    }

    public Poly toPoly() {
        Unit unit = new Unit();
        unit.setCoe(num);
        ArrayList<Unit> unitList = new ArrayList<>();
        unitList.add(unit);
        return new Poly(unitList);
    }
}
