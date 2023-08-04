public class PowOp extends Op {
    private Op down;
    private Op up;

    public PowOp(Op down, Op up) {
        this.down = down;
        this.up = up;
    }

    @Override
    public Poly calculate() {
        Poly ans = new Poly();
        ans.add(new Unit(1));
        Poly mult = down.calculate();
        Poly time = up.calculate();
        int max = time.getConstant();
        for (int i = 0; i < max; i++) {
            ans = Poly.multiply(ans, mult);
        }
        return ans;
    }
}
