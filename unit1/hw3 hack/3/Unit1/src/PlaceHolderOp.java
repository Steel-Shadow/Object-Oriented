public class PlaceHolderOp extends Op {
    private char name;

    public PlaceHolderOp(char name) {
        this.name = name;
    }

    @Override
    public Poly calculate() {
        Poly val = FunEnv.calculate(name);
        return val;
    }
}
