import java.util.ArrayList;

public class SelfDefFuc {
    private String name;
    private ArrayList<String> parameters;
    private String expr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public SelfDefFuc() {
        this.parameters = new ArrayList<>();
    }

}
