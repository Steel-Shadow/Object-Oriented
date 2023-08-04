public class DxOp extends Op {
    private Op expr;

    public DxOp(Op expr) {
        this.expr = expr;
    }

    @Override
    public Poly calculate() {
        Poly val = expr.calculate();
        return val.derivative('x');
    }
}
