public class DzOp extends Op {
    private Op expr;

    public DzOp(Op expr) {
        this.expr = expr;
    }

    @Override
    public Poly calculate() {
        Poly val = expr.calculate();
        return val.derivative('z');
    }
}
