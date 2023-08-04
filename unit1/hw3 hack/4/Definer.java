import java.util.ArrayList;
import java.util.HashMap;

public class Definer {
    private static final HashMap<String, String> funcMap = new HashMap<>();

    private static final HashMap<String, ArrayList<String>> paraMap = new HashMap<>();

    public static void addFunc(String input) {
        int flag1 = 1;
        int flag2 = 1;
        String funcName = "";
        ArrayList<String> paraString = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (flag2 == 0) {
                sb.append(c);
            }
            else {
                if (flag1 == 1) {
                    funcName = String.valueOf(c);
                    flag1 = 0;
                }
                else if (c == 'x' | c == 'y' | c == 'z') {
                    paraString.add(String.valueOf(c));
                }
                else if (c == '=') {
                    flag2 = 0;
                }
            }
            pos++;
        }
        Lexer lexer = new Lexer(sb.toString());
        Parser parser = new Parser(lexer);
        funcMap.put(funcName,parser.parseExpr().toPoly().toString());
        paraMap.put(funcName, paraString);
    }

    public static ArrayList<String> readFunc(String input) {
        int stack = 0;
        int pos = 0;
        int flag = 1;
        int last = 2;
        ArrayList<String> arrayList = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == '(') {
                stack++;
            }
            else if (c == ')') {
                if (stack == 1 && flag == 1) {
                    arrayList.add(input.substring(last,pos));
                    flag = 0;
                }
                stack--;
            }
            else if (c == ',' && stack == 1) {
                arrayList.add(input.substring(last,pos));
                last = pos + 1;
            }
            pos++;
        }
        return arrayList;
    }

    public static String callFunc(String funcName, ArrayList<String> actualPara) {
        String func = funcMap.get(funcName).replaceAll("x","w")
                .replaceAll("y","j").replaceAll("z","k");
        for (int i = 0; i < paraMap.get(funcName).size(); i++) {
            if (paraMap.get(funcName).get(i).equals("x")) {
                func = func.replaceAll("w","(" + actualPara.get(i) + ")");
            }
            else if (paraMap.get(funcName).get(i).equals("y")) {
                func = func.replaceAll("j","(" + actualPara.get(i) + ")");
            }
            else if (paraMap.get(funcName).get(i).equals("z")) {
                func = func.replaceAll("k","(" + actualPara.get(i) + ")");
            }
        }
        return func;
    }

}
