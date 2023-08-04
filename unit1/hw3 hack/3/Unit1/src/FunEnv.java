import java.util.ArrayList;
import java.util.HashMap;

public class FunEnv {
    private static ArrayList<Op> envX = new ArrayList<>();
    private static ArrayList<Op> envY = new ArrayList<>();
    private static ArrayList<Op> envZ = new ArrayList<>();
    private static ArrayList<HashMap<Character, Integer>> rfle = new ArrayList<>();
    private static HashMap<Character, FunDefine> functions = new HashMap<>();

    public static void enterEnv(Op x, Op y, Op z, HashMap<Character, Integer> fle) {
        envX.add(x);
        envY.add(y);
        envZ.add(z);
        rfle.add(fle);
    }

    public static void exitEnv() {
        envX.remove(envX.size() - 1);
        envY.remove(envY.size() - 1);
        envZ.remove(envZ.size() - 1);
        rfle.remove(rfle.size() - 1);
    }

    public static void addFunc(char name, FunDefine func) {
        functions.put(name, func);
    }

    public static FunDefine getFunc(char name) {
        return functions.get(name);
    }

    public static Poly calculate(char name) {
        int top = envX.size() - 1;
        int rank = rfle.get(top).get(name);
        if (rank == 1) {
            return envX.get(top).calculate();
        } else if (rank == 2) {
            return envY.get(top).calculate();
        } else {
            return envZ.get(top).calculate();
        }
    }
}
