import node.Node;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int l = Integer.parseInt(input);
        HashMap<Character, CustomizedFunc> customizedFuncMap = new HashMap<>();
        for (int i = 0;i < l;i++) {
            Parser parser = new Parser(new Lexer(scanner.nextLine()), customizedFuncMap);
            CustomizedFunc customizedFunc = parser.parseCustomisedFunc();
            customizedFuncMap.put(customizedFunc.getName(), customizedFunc);
        }
        Lexer lexer = new Lexer(scanner.nextLine());
        Parser parser = new Parser(lexer, customizedFuncMap);
        Node root = parser.parseExpr();
        System.out.println(root.calc());
    }
}
