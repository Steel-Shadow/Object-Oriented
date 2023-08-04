import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class PowerFac implements Factor {
    private String variable;
    private int index = 1;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Polynomial toPoly(int index) {
        Monomial mono = new Monomial();
        mono.setCoefficient(new BigInteger("1"));
        if (this.index == 0) {
            mono.setVariables(new ArrayList<>());
            mono.setIndexTable(new HashMap<>());
        } else {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(this.getVariable());
            mono.setVariables(arrayList);
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(this.getVariable(), this.getIndex());
            mono.setIndexTable(hashMap);
        }
        mono.setTriFacs(new ArrayList<>());
        Polynomial poly = new Polynomial();
        poly.getMonomials().add(mono);
        return poly;
    }
}
