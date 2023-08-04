import java.math.BigInteger;
import java.util.ArrayList;

@SuppressWarnings("checkstyle:EmptyLineSeparator")
public class PowFactor implements Factor {
    private BigInteger expX = BigInteger.ZERO;
    private BigInteger expY = BigInteger.ZERO;
    private BigInteger expZ = BigInteger.ZERO;

    public void setExpX(BigInteger expX) {
        this.expX = expX;
    }

    public void setExpY(BigInteger expY) {
        this.expY = expY;
    }

    public void setExpZ(BigInteger expZ) {
        this.expZ = expZ;
    }

    public Poly toPoly() {
        Unit unit = new Unit();
        unit.setExpX(expX);
        unit.setExpY(expY);
        unit.setExpZ(expZ);
        unit.setCoe(BigInteger.ONE);
        ArrayList<Unit> unitlist = new ArrayList<>();
        unitlist.add(unit);
        return new Poly(unitlist);
    }

}
