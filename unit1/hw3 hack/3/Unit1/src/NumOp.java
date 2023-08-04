import java.math.BigInteger;

public class NumOp extends Op {
    private BigInteger value;

    public NumOp(BigInteger value) {
        this.value = value;
    }

    @Override
    public Poly calculate() {
        Poly ans = new Poly();
        ans.add(new Unit(value));
        return ans;
    }
}
