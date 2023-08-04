public class ExpOp extends Op {
    private Op expr;

    public ExpOp(Op expr) {
        this.expr = expr;
    }

    @Override
    public Poly calculate() {
        Poly val = expr.calculate();
        return val;
    }
}