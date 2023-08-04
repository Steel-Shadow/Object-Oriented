public class DeriFac implements Factor {
    private String aimVar;
    private Expr tarExpr;
    private Polynomial tarPoly;

    public String getAimVar() {
        return aimVar;
    }

    public void setAimVar(String aimVar) {
        this.aimVar = aimVar;
    }

    public Expr getTarExpr() {
        return tarExpr;
    }

    public void setTarExpr(Expr tarExpr) {
        this.tarExpr = tarExpr;
    }

    public Polynomial getTarPoly() {
        return tarPoly;
    }

    public void setTarPoly(Polynomial tarPoly) {
        this.tarPoly = tarPoly;
    }

    public Polynomial toPoly(int index) {
        Polynomial result = this.tarPoly.diff(aimVar);
        return result;
    }
}
