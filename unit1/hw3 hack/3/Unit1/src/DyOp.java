public class DyOp extends Op {
    private Op expr;

    public DyOp(Op expr) {
        this.expr = expr;
    }

    @Override
    public Poly calculate() {
        Poly val = expr.calculate();
        return val.derivative('y');
    }
}
