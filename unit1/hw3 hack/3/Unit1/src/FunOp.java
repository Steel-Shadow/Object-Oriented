
public class FunOp extends Op {
    private char name;
    private Op expr1 = null;
    private Op expr2 = null;
    private Op expr3 = null;

    public FunOp(char name) {
        this.name = name;
    }

    void addExpr(Op expr) {
        if (expr1 == null) {
            expr1 = expr;
        } else if (expr2 == null) {
            expr2 = expr;
        } else {
            expr3 = expr;
        }
    }

    @Override
    public Poly calculate() {
        FunDefine func = FunEnv.getFunc(name);
        if (func == null) {
            System.out.println("Function named:" + name + " Not Found");
        }
        FunEnv.enterEnv(expr1, expr2, expr3, func.getRank());
        Poly ans = func.calculate();
        FunEnv.exitEnv();
        return ans;
    }
}
