import java.util.HashMap;

public class FunDefine {
    private HashMap<Character, Integer> rank = new HashMap<>();
    private Op root;

    public FunDefine(Op op) {
        root = op;
    }

    void setRank(char name, int rank) {
        this.rank.put(name, rank);
    }

    HashMap<Character, Integer> getRank() {
        return rank;
    }

    public Poly calculate() {
        return root.calculate();
    }
}
