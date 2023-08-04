public class SinOp extends Op {
    private Op inner;

    public SinOp(Op inner) {
        this.inner = inner;
    }

    @Override
    public Poly calculate() {
        Poly inn = inner.calculate();
        Poly ans = new Poly(new AtomFunc("sin", inn));
        return ans;
    }
}
