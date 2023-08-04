public class MulOp extends Op {
    private Op left;
    private Op right;

    public MulOp(Op left, Op right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Poly calculate() {
        return Poly.multiply(left.calculate(), right.calculate());
    }
}
