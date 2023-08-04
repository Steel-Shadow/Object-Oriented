public class SubOp extends Op {
    private Op left;
    private Op right;

    public SubOp(Op left, Op right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Poly calculate() {
        Poly lval = left.calculate();
        Poly rval = right.calculate();
        rval.flip();
        return Poly.add(lval, rval);
    }
}
