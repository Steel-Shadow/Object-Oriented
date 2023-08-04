public class CosOp extends Op {
    private Op inner;

    public CosOp(Op inner) {
        this.inner = inner;
    }

    @Override
    public Poly calculate() {
        Poly inn = inner.calculate();
        Poly ans = new Poly(new AtomFunc("cos", inn));
        return ans;
    }
}
