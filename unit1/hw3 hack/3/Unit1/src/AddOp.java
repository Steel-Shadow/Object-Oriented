public class AddOp extends Op {
    private Op left;
    private Op right;

    public AddOp(Op left, Op right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Poly calculate() {
        Poly lval = left.calculate();
        Poly rval = right.calculate();
        return Poly.add(lval, rval);
    }
}
