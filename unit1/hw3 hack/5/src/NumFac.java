import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class NumFac implements Factor {
    private String op;

    private BigInteger num;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }

    public BigInteger getNum() {
        return num;
    }

    @Override
    public Polynomial toPoly(int index) {
        Monomial mono = new Monomial();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getOp());
        stringBuilder.append(this.getNum());
        mono.setCoefficient(new BigInteger(stringBuilder.toString()));
        mono.setVariables(new ArrayList<>());
        mono.setIndexTable(new HashMap<>());
        mono.setTriFacs(new ArrayList<>());
        Polynomial poly = new Polynomial();
        poly.getMonomials().add(mono);
        return poly;
    }
}
