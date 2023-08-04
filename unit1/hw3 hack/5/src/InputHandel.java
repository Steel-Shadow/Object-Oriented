import java.util.ArrayList;

public class InputHandel {
    private int numOfSelfDefFunc;
    private ArrayList<String> rawDefine;
    private ArrayList<SelfDefFuc> selfDefFucs = new ArrayList<>();

    public ArrayList<String> getRawDefine() {
        return rawDefine;
    }

    public void setRawDefine(ArrayList<String> rawDefine) {
        this.rawDefine = rawDefine;
    }

    public ArrayList<SelfDefFuc> getSelfDefFucs() {
        return selfDefFucs;
    }

    public void setSelfDefFucs(ArrayList<SelfDefFuc> selfDefFucs) {
        this.selfDefFucs = selfDefFucs;
    }

    public int getNumOfSelfDefFunc() {
        return numOfSelfDefFunc;
    }

    public void setNumOfSelfDefFunc(int numOfSelfDefFunc) {
        this.numOfSelfDefFunc = numOfSelfDefFunc;
    }

    public InputHandel(int num) {
        this.numOfSelfDefFunc = num;
        this.rawDefine = new ArrayList<>();
    }

    public void handleSelfDef() {
        for (String string : this.rawDefine) {
            string = string.replaceAll("\\t", "");
            string = string.replaceAll(" ", "");
            String[] strings = string.split("=");
            SelfDefFuc selfDefFuc = new SelfDefFuc();
            selfDefFuc.setName(strings[0].substring(0, 1));
            String temp;
            if (strings[1].contains("f") || strings[1].contains("g") || strings[1].contains("h")) {
                temp = handleInput(strings[1], 'i');
            } else {
                temp = strings[1];
            }
            Lexer lexer = new Lexer(temp);
            Parser parser = new Parser(lexer);
            selfDefFuc.setExpr(parser.parseExpr().toPoly(1).toString());
            String variable = strings[0].substring(2, strings[0].length() - 1);
            String[] variables = variable.split(",");
            for (String vari : variables) {
                selfDefFuc.getParameters().add(vari);
            }
            this.selfDefFucs.add(selfDefFuc);
        }
    }

    public String handleInput(String input, char funcName) {
        String demo = input;
        demo = demo.replaceAll("\\t", "");
        demo = demo.replaceAll(" ", "");
        String finalResult = "";
        while (demo.contains("f") || demo.contains("g") || demo.contains("h")) {
            int start = 0;
            int end = 0;
            for (int i = 0; i < demo.length(); i++) {
                if (demo.charAt(i) == 'f' || demo.charAt(i) == 'g' || demo.charAt(i) == 'h') {
                    start = i;
                    int cnt = 0;
                    for (int j = start + 1; j < demo.length(); j++) {
                        if (demo.charAt(j) == '(') { cnt++; }
                        else if (demo.charAt(j) == ')') { cnt--; }
                        if (cnt == 0) {
                            end = j;
                            break;
                        }
                    }
                    String sub = demo.substring(start + 2, end);
                    String result = handleInput(sub, demo.charAt(start));
                    StringBuilder sb = new StringBuilder();
                    if (start > 0) { sb.append(demo.substring(0, start)); }
                    sb.append("(");
                    sb.append(result);
                    sb.append(demo.substring(end));
                    demo = sb.toString();
                    break;
                }
            }
        }
        if (funcName != 'i') {
            String[] strings = demo.split(",");
            for (SelfDefFuc selfDefFuc : this.selfDefFucs) {
                if (selfDefFuc.getName().equals(funcName + "")) {
                    StringBuilder sb = new StringBuilder();
                    int length = selfDefFuc.getExpr().length();
                    for (int i = 0; i < length; i++) {
                        int flag = 0;
                        for (int j = 0; j < selfDefFuc.getParameters().size(); j++) {
                            if (selfDefFuc.getParameters().get(j).equals(
                                    selfDefFuc.getExpr().charAt(i) + "")) {
                                sb.append("(" + strings[j] + ")");
                                flag = 1;
                            }
                        }
                        if (flag == 0) { sb.append(selfDefFuc.getExpr().charAt(i)); }
                        finalResult = sb.toString();
                    }
                }
            }
        } else {
            finalResult = demo;
        }
        return finalResult;
    }

}
