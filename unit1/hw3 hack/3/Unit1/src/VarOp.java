public class VarOp extends Op {
    private char name;

    public VarOp(char name) {
        this.name = name;
    }

    @Override
    public Poly calculate() {
        StringBuffer name = new StringBuffer();
        name.append(this.name);
        Poly ans = new Poly(new AtomFunc(new String(name), null));
        return ans;
    }
}
